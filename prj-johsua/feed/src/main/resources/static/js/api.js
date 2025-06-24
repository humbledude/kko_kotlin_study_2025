/**
 * API 호출 유틸리티 모듈
 * 백엔드 API와의 통신을 담당
 */

class ApiClient {
    constructor() {
        this.baseUrl = '/api';
        this.defaultHeaders = {
            'Content-Type': 'application/json',
        };
    }

    /**
     * HTTP 요청을 보내는 기본 메서드
     * @param {string} url - 요청 URL
     * @param {Object} options - 요청 옵션
     * @returns {Promise} 응답 데이터
     */
    async request(url, options = {}) {
        const config = {
            headers: {
                ...this.defaultHeaders,
                ...options.headers,
            },
            ...options,
        };

        // 인증 토큰이 있으면 헤더에 추가
        const token = this.getAuthToken();
        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`;
        }

        try {
            const response = await fetch(`${this.baseUrl}${url}`, config);
            
            if (!response.ok) {
                throw new ApiError(response.status, response.statusText, await response.text());
            }

            // 응답이 JSON인지 확인
            const contentType = response.headers.get('content-type');
            if (contentType && contentType.includes('application/json')) {
                return await response.json();
            } else {
                return await response.text();
            }
        } catch (error) {
            if (error instanceof ApiError) {
                throw error;
            }
            throw new ApiError(0, 'Network Error', error.message);
        }
    }

    /**
     * GET 요청
     * @param {string} url - 요청 URL
     * @param {Object} params - 쿼리 파라미터
     * @returns {Promise} 응답 데이터
     */
    async get(url, params = {}) {
        const queryString = new URLSearchParams(params).toString();
        const fullUrl = queryString ? `${url}?${queryString}` : url;
        
        return this.request(fullUrl, {
            method: 'GET',
        });
    }

    /**
     * POST 요청
     * @param {string} url - 요청 URL
     * @param {Object} data - 요청 데이터
     * @returns {Promise} 응답 데이터
     */
    async post(url, data = {}) {
        return this.request(url, {
            method: 'POST',
            body: JSON.stringify(data),
        });
    }

    /**
     * PUT 요청
     * @param {string} url - 요청 URL
     * @param {Object} data - 요청 데이터
     * @returns {Promise} 응답 데이터
     */
    async put(url, data = {}) {
        return this.request(url, {
            method: 'PUT',
            body: JSON.stringify(data),
        });
    }

    /**
     * DELETE 요청
     * @param {string} url - 요청 URL
     * @returns {Promise} 응답 데이터
     */
    async delete(url) {
        return this.request(url, {
            method: 'DELETE',
        });
    }

    /**
     * 인증 토큰 가져오기
     * @returns {string|null} 토큰 또는 null
     */
    getAuthToken() {
        return localStorage.getItem('authToken');
    }

    /**
     * 인증 토큰 설정
     * @param {string} token - 인증 토큰
     */
    setAuthToken(token) {
        localStorage.setItem('authToken', token);
    }

    /**
     * 인증 토큰 제거
     */
    removeAuthToken() {
        localStorage.removeItem('authToken');
    }
}

/**
 * API 에러 클래스
 */
class ApiError extends Error {
    constructor(status, statusText, message) {
        super(message);
        this.name = 'ApiError';
        this.status = status;
        this.statusText = statusText;
    }

    /**
     * 사용자 친화적인 에러 메시지 반환
     * @returns {string} 에러 메시지
     */
    getUserMessage() {
        switch (this.status) {
            case 400:
                return '잘못된 요청입니다. 입력 정보를 확인해주세요.';
            case 401:
                return '로그인이 필요합니다.';
            case 403:
                return '접근 권한이 없습니다.';
            case 404:
                return '요청한 리소스를 찾을 수 없습니다.';
            case 500:
                return '서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.';
            default:
                return this.message || '알 수 없는 오류가 발생했습니다.';
        }
    }
}

// API 인스턴스 생성
const apiClient = new ApiClient();

// API 엔드포인트별 메서드들
export const AuthAPI = {
    /**
     * 로그인
     * @param {Object} credentials - 로그인 정보
     * @returns {Promise} 로그인 응답
     */
    login: (credentials) => {
        console.log('AuthAPI.login 호출됨, credentials:', credentials);
        return apiClient.post('/auth/login', credentials);
    },

    /**
     * 회원가입
     * @param {Object} userData - 회원가입 정보
     * @returns {Promise} 회원가입 응답
     */
    register: (userData) => apiClient.post('/auth/register', userData),

    /**
     * 로그아웃
     * @returns {Promise} 로그아웃 응답
     */
    logout: () => apiClient.post('/auth/logout'),
};

export const FeedAPI = {
    /**
     * 피드 조회
     * @param {Object} params - 쿼리 파라미터
     * @returns {Promise} 피드 데이터
     */
    getFeed: (params = {}) => apiClient.get('/feed', params),

    /**
     * 컨텐츠 읽음 처리
     * @param {string} contentId - 컨텐츠 ID
     * @param {string} username - 사용자명
     * @returns {Promise} 응답
     */
    markAsRead: (contentId, username) => apiClient.post(`/content/${contentId}/read?username=${username}`),

    /**
     * 컨텐츠 좋아요
     * @param {string} contentId - 컨텐츠 ID
     * @param {string} username - 사용자명
     * @returns {Promise} 응답
     */
    like: (contentId, username) => apiClient.post(`/content/${contentId}/like?username=${username}`),

    /**
     * 컨텐츠 싫어요
     * @param {string} contentId - 컨텐츠 ID
     * @param {string} username - 사용자명
     * @returns {Promise} 응답
     */
    dislike: (contentId, username) => apiClient.post(`/content/${contentId}/dislike?username=${username}`),
};

export const PokemonAPI = {
    /**
     * Pokemon 추천 조회
     * @param {Object} params - 쿼리 파라미터
     * @returns {Promise} Pokemon 추천 데이터
     */
    getRecommendations: (params = {}) => apiClient.get('/pokemon-feed/recommendations', params),
};

// 유틸리티 함수들
export const ApiUtils = {
    /**
     * 에러 처리 유틸리티
     * @param {Error} error - 에러 객체
     * @param {Function} showError - 에러 표시 함수
     */
    handleError: (error, showError) => {
        console.error('API Error:', error);
        
        if (error instanceof ApiError) {
            showError(error.getUserMessage());
            
            // 401 에러 시 로그인 페이지로 리다이렉트
            if (error.status === 401) {
                setTimeout(() => {
                    window.location.href = '/login.html';
                }, 2000);
            }
        } else {
            showError('네트워크 오류가 발생했습니다. 인터넷 연결을 확인해주세요.');
        }
    },

    /**
     * 로딩 상태 관리
     * @param {boolean} isLoading - 로딩 상태
     * @param {Function} setLoading - 로딩 상태 설정 함수
     */
    setLoading: (isLoading, setLoading) => {
        setLoading(isLoading);
        // 로딩 중일 때 스크롤 방지
        document.body.style.overflow = isLoading ? 'hidden' : 'auto';
    },
};

export { apiClient, ApiError }; 