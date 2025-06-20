/**
 * 피드 데이터 관리 모듈
 * 피드 로드, 상호작용 처리, 상태 관리 기능을 담당
 */

import { FeedAPI, ApiUtils } from './api.js';
import { AuthManager } from './auth.js';

class FeedManager {
    constructor() {
        this.feedItems = [];
        this.isLoading = false;
        this.hasMore = true;
        this.currentPage = 0;
        this.interactions = new Map(); // contentId -> interactionType
    }

    /**
     * 피드 데이터 로드
     * @param {boolean} loadMore - 추가 로드 여부
     * @param {Function} setLoading - 로딩 상태 설정 함수
     * @param {Function} showError - 에러 표시 함수
     * @returns {Promise<Array>} 피드 아이템 배열
     */
    async loadFeed(loadMore = false, setLoading, showError) {
        try {
            if (this.isLoading) return this.feedItems;
            
            this.isLoading = true;
            if (setLoading) setLoading(true);

            // 현재 사용자 정보 가져오기
            const currentUser = AuthManager.getCurrentUser();
            const username = currentUser?.username || 'anonymous';

            const params = {
                username: username,
                page: loadMore ? this.currentPage + 1 : 0,
                size: 10
            };

            console.log('피드 요청 파라미터:', params);

            const response = await FeedAPI.getFeed(params);
            
            if (loadMore) {
                this.feedItems.push(...response.content || response);
                this.currentPage++;
            } else {
                this.feedItems = response.content || response;
                this.currentPage = 0;
            }

            // 더 로드할 데이터가 있는지 확인
            const responseData = response.content || response;
            // 응답받은 데이터가 있으면 계속 더 불러올 수 있다고 가정
            // 빈 배열이나 요청한 size보다 적게 받으면 더 이상 없음
            this.hasMore = responseData.length > 0 && responseData.length === params.size;
            console.log('hasMore 설정:', { 
                responseLength: responseData.length, 
                paramsSize: params.size,
                currentPage: this.currentPage,
                hasMore: this.hasMore 
            });

            return this.feedItems;
        } catch (error) {
            ApiUtils.handleError(error, showError);
            return [];
        } finally {
            this.isLoading = false;
            if (setLoading) setLoading(false);
        }
    }

    /**
     * 컨텐츠 읽음 처리
     * @param {string} contentId - 컨텐츠 ID
     * @param {Function} showError - 에러 표시 함수
     * @returns {Promise<boolean>} 성공 여부
     */
    async markAsRead(contentId, showError) {
        try {
            const currentUser = AuthManager.getCurrentUser();
            const username = currentUser?.username || 'anonymous';
            
            await FeedAPI.markAsRead(contentId, username);
            this.interactions.set(contentId, 'READ');
            return true;
        } catch (error) {
            ApiUtils.handleError(error, showError);
            return false;
        }
    }

    /**
     * 컨텐츠 좋아요
     * @param {string} contentId - 컨텐츠 ID
     * @param {Function} showError - 에러 표시 함수
     * @returns {Promise<boolean>} 성공 여부
     */
    async like(contentId, showError) {
        try {
            const currentUser = AuthManager.getCurrentUser();
            const username = currentUser?.username || 'anonymous';
            
            await FeedAPI.like(contentId, username);
            this.interactions.set(contentId, 'LIKE');
            return true;
        } catch (error) {
            ApiUtils.handleError(error, showError);
            return false;
        }
    }

    /**
     * 컨텐츠 싫어요
     * @param {string} contentId - 컨텐츠 ID
     * @param {Function} showError - 에러 표시 함수
     * @returns {Promise<boolean>} 성공 여부
     */
    async dislike(contentId, showError) {
        try {
            const currentUser = AuthManager.getCurrentUser();
            const username = currentUser?.username || 'anonymous';
            
            await FeedAPI.dislike(contentId, username);
            this.interactions.set(contentId, 'DISLIKE');
            return true;
        } catch (error) {
            ApiUtils.handleError(error, showError);
            return false;
        }
    }

    /**
     * 컨텐츠의 상호작용 상태 확인
     * @param {string} contentId - 컨텐츠 ID
     * @returns {string|null} 상호작용 타입
     */
    getInteraction(contentId) {
        return this.interactions.get(contentId) || null;
    }

    /**
     * 피드 데이터 초기화
     */
    reset() {
        this.feedItems = [];
        this.isLoading = false;
        this.hasMore = true;
        this.currentPage = 0;
        this.interactions.clear();
    }

    /**
     * 더 로드할 데이터가 있는지 확인
     * @returns {boolean} 더 로드 가능 여부
     */
    canLoadMore() {
        return this.hasMore && !this.isLoading;
    }

    /**
     * 현재 로딩 상태 확인
     * @returns {boolean} 로딩 상태
     */
    getIsLoading() {
        return this.isLoading;
    }
}

// 싱글톤 인스턴스 생성
const feedManager = new FeedManager();

// 피드 렌더링 유틸리티
export const FeedRenderer = {
    /**
     * 피드 카드 HTML 생성
     * @param {Object} item - 피드 아이템
     * @returns {string} HTML 문자열
     */
    createFeedCard: (item) => {
        const interaction = feedManager.getInteraction(item.id);
        const isRead = interaction === 'READ';
        const isLiked = interaction === 'LIKE';
        const isDisliked = interaction === 'DISLIKE';

        return `
            <div class="feed-card" data-content-id="${item.id}">
                ${item.imageUrl ? `<img src="${item.imageUrl}" alt="${item.title}" class="card-image" onerror="this.style.display='none'">` : ''}
                <div class="card-content">
                    <h3 class="card-title">${item.title}</h3>
                    <p class="card-description">${item.description}</p>
                    <div class="card-body">${item.body}</div>
                </div>
                <div class="card-footer">
                    <div class="interaction-buttons">
                        <button class="interaction-btn read ${isRead ? 'active' : ''}" 
                                onclick="handleRead('${item.id}')" 
                                ${isRead ? 'disabled' : ''}>
                            ${isRead ? '✓' : '👁️'} 읽음
                        </button>
                        <button class="interaction-btn like ${isLiked ? 'active' : ''}" 
                                onclick="handleLike('${item.id}')" 
                                ${isDisliked ? 'disabled' : ''}>
                            ${isLiked ? '❤️' : '🤍'} 좋아요
                        </button>
                        <button class="interaction-btn dislike ${isDisliked ? 'active' : ''}" 
                                onclick="handleDislike('${item.id}')" 
                                ${isLiked ? 'disabled' : ''}>
                            ${isDisliked ? '💔' : '🖤'} 싫어요
                        </button>
                    </div>
                </div>
            </div>
        `;
    },

    /**
     * 피드 컨테이너에 피드 렌더링
     * @param {Array} items - 피드 아이템 배열
     * @param {string} containerId - 컨테이너 ID
     * @param {boolean} append - 추가 렌더링 여부
     */
    renderFeed: (items, containerId, append = false) => {
        const container = document.getElementById(containerId);
        if (!container) return;

        if (!append) {
            container.innerHTML = '';
            
            if (items.length === 0) {
                container.innerHTML = `
                    <div class="empty-state">
                        <div class="empty-state-icon">📭</div>
                        <h3 class="empty-state-title">피드가 비어있습니다</h3>
                        <p class="empty-state-message">새로운 컨텐츠를 기다려주세요.</p>
                    </div>
                `;
                return;
            }
            
            const feedHTML = items.map(item => FeedRenderer.createFeedCard(item)).join('');
            container.innerHTML = feedHTML;
        } else {
            // append 모드에서는 새로운 아이템만 추가
            if (items.length > 0) {
                const feedHTML = items.map(item => FeedRenderer.createFeedCard(item)).join('');
                container.insertAdjacentHTML('beforeend', feedHTML);
            }
        }
    },

    /**
     * 로딩 상태 렌더링
     * @param {string} containerId - 컨테이너 ID
     */
    renderLoading: (containerId) => {
        const container = document.getElementById(containerId);
        if (!container) return;

        container.innerHTML = `
            <div class="loading-container">
                <div class="loading-spinner"></div>
                <p>피드를 불러오는 중...</p>
            </div>
        `;
    },

    /**
     * 에러 상태 렌더링
     * @param {string} containerId - 컨테이너 ID
     * @param {string} message - 에러 메시지
     * @param {Function} retryFunction - 재시도 함수
     */
    renderError: (containerId, message, retryFunction) => {
        const container = document.getElementById(containerId);
        if (!container) return;

        container.innerHTML = `
            <div class="error-state">
                <div class="error-state-icon">⚠️</div>
                <h3 class="error-state-title">오류가 발생했습니다</h3>
                <p class="error-state-message">${message}</p>
                ${retryFunction ? `<button class="retry-btn" onclick="(${retryFunction.toString()})()">다시 시도</button>` : ''}
            </div>
        `;
    },

    /**
     * 더 보기 버튼 렌더링
     * @param {string} containerId - 컨테이너 ID
     * @param {boolean} hasMore - 더 로드 가능 여부
     * @param {boolean} isLoading - 로딩 상태
     */
    renderLoadMore: (containerId, hasMore, isLoading) => {
        console.log('renderLoadMore 호출:', { containerId, hasMore, isLoading });
        const container = document.getElementById(containerId);
        if (!container) {
            console.error(`Container not found: ${containerId}`);
            return;
        }

        if (!hasMore) {
            console.log('더 이상 로드할 데이터가 없음');
            container.innerHTML = `
                <div class="load-more-container">
                    <p>모든 컨텐츠를 불러왔습니다.</p>
                </div>
            `;
            return;
        }

        console.log('더보기 버튼 렌더링');
        container.innerHTML = `
            <div class="load-more-container">
                <button class="load-more-btn" onclick="loadMoreFeed()" ${isLoading ? 'disabled' : ''}>
                    ${isLoading ? '<div class="spinner"></div>' : ''}
                    ${isLoading ? '불러오는 중...' : '더 보기'}
                </button>
            </div>
        `;
    }
};

// 전역 함수들 (HTML에서 직접 호출)
window.handleRead = async (contentId) => {
    const success = await feedManager.markAsRead(contentId, showError);
    if (success) {
        // 버튼 상태 업데이트
        const button = document.querySelector(`[data-content-id="${contentId}"] .interaction-btn.read`);
        if (button) {
            button.classList.add('active');
            button.disabled = true;
            button.innerHTML = '✓ 읽음';
        }
    }
};

window.handleLike = async (contentId) => {
    const success = await feedManager.like(contentId, showError);
    if (success) {
        // 버튼 상태 업데이트
        const likeBtn = document.querySelector(`[data-content-id="${contentId}"] .interaction-btn.like`);
        const dislikeBtn = document.querySelector(`[data-content-id="${contentId}"] .interaction-btn.dislike`);
        
        if (likeBtn) {
            likeBtn.classList.add('active');
            likeBtn.innerHTML = '❤️ 좋아요';
        }
        if (dislikeBtn) {
            dislikeBtn.classList.remove('active');
            dislikeBtn.disabled = true;
            dislikeBtn.innerHTML = '🖤 싫어요';
        }
    }
};

window.handleDislike = async (contentId) => {
    const success = await feedManager.dislike(contentId, showError);
    if (success) {
        // 버튼 상태 업데이트
        const likeBtn = document.querySelector(`[data-content-id="${contentId}"] .interaction-btn.like`);
        const dislikeBtn = document.querySelector(`[data-content-id="${contentId}"] .interaction-btn.dislike`);
        
        if (dislikeBtn) {
            dislikeBtn.classList.add('active');
            dislikeBtn.innerHTML = '💔 싫어요';
        }
        if (likeBtn) {
            likeBtn.classList.remove('active');
            likeBtn.disabled = true;
            likeBtn.innerHTML = '🤍 좋아요';
        }
    }
};

window.loadMoreFeed = async () => {
    const prevItemsCount = feedManager.feedItems.length;
    const items = await feedManager.loadFeed(true, setLoading, showError);
    
    // 새로 추가된 아이템만 렌더링
    const newItems = items.slice(prevItemsCount);
    if (newItems.length > 0) {
        FeedRenderer.renderFeed(newItems, 'feedContent', true);
    }
    
    FeedRenderer.renderLoadMore('loadMoreContainer', feedManager.canLoadMore(), feedManager.getIsLoading());
};

// 상태 관리 함수들
let isLoading = false;
let showError = (message) => console.error(message);
let setLoading = (loading) => { isLoading = loading; };

export { feedManager as FeedManager }; 