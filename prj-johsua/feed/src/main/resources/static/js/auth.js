/**
 * 인증 관리 모듈
 * 로그인 상태 관리와 인증 관련 기능을 담당
 */

import { AuthAPI, ApiUtils } from './api.js';

class AuthManager {
    constructor() {
        this.currentUser = null;
        this.isAuthenticated = false;
        this.init();
    }

    /**
     * 초기화 - 저장된 사용자 정보 복원
     */
    init() {
        console.log('AuthManager.init() 호출됨');
        
        const savedUser = localStorage.getItem('currentUser');
        const token = localStorage.getItem('authToken');
        
        console.log('저장된 사용자 정보:', savedUser);
        console.log('저장된 토큰:', token);
        
        if (savedUser) {
            try {
                this.currentUser = JSON.parse(savedUser);
                this.isAuthenticated = true;
                console.log('사용자 정보 복원 성공:', this.currentUser);
            } catch (error) {
                console.error('저장된 사용자 정보 파싱 실패:', error);
                this.clearAuth();
            }
        } else {
            console.log('저장된 사용자 정보가 없음');
            this.clearAuth();
        }
    }

    /**
     * 로그인 상태 확인
     * @returns {boolean} 로그인 상태
     */
    async isLoggedIn() {
        console.log('AuthManager.isLoggedIn() 호출됨');
        console.log('isAuthenticated:', this.isAuthenticated);
        console.log('currentUser:', this.currentUser);
        console.log('localStorage currentUser:', localStorage.getItem('currentUser'));
        console.log('localStorage authToken:', localStorage.getItem('authToken'));
        
        // 실제 인증 상태만 확인 (localStorage 정보만으로는 로그인 상태로 인식하지 않음)
        const result = this.isAuthenticated && this.currentUser !== null;
        
        console.log('로그인 상태 결과:', result);
        return result;
    }

    /**
     * 현재 사용자 정보 반환
     * @returns {Object|null} 사용자 정보
     */
    getCurrentUser() {
        return this.currentUser;
    }

    /**
     * 로그인 처리
     * @param {Object} credentials - 로그인 정보
     * @param {Function} showError - 에러 표시 함수
     * @param {Function} setLoading - 로딩 상태 설정 함수
     * @returns {Promise<boolean>} 로그인 성공 여부
     */
    async login(credentials, showError, setLoading) {
        try {
            console.log('AuthManager.login() 호출됨');
            console.log('로그인 credentials:', credentials);
            
            ApiUtils.setLoading(true, setLoading);
            
            console.log('AuthAPI.login 호출 전');
            const response = await AuthAPI.login(credentials);
            console.log('AuthAPI.login 응답:', response);
            
            // 로그인 성공 시 사용자 정보와 토큰 저장
            this.currentUser = response.user || response;
            this.isAuthenticated = true;
            
            console.log('로그인 성공 - currentUser 설정:', this.currentUser);
            console.log('로그인 성공 - isAuthenticated:', this.isAuthenticated);
            
            // localStorage에 저장
            localStorage.setItem('currentUser', JSON.stringify(this.currentUser));
            if (response.token) {
                localStorage.setItem('authToken', response.token);
                console.log('토큰 저장:', response.token);
            } else {
                console.log('응답에 토큰이 없음');
            }
            
            console.log('localStorage 저장 후 확인:', {
                currentUser: localStorage.getItem('currentUser'),
                authToken: localStorage.getItem('authToken')
            });
            
            return true;
        } catch (error) {
            console.error('로그인 실패:', error);
            ApiUtils.handleError(error, showError);
            return false;
        } finally {
            ApiUtils.setLoading(false, setLoading);
        }
    }

    /**
     * 회원가입 처리
     * @param {Object} userData - 회원가입 정보
     * @param {Function} showError - 에러 표시 함수
     * @param {Function} setLoading - 로딩 상태 설정 함수
     * @returns {Promise<boolean>} 회원가입 성공 여부
     */
    async register(userData, showError, setLoading) {
        try {
            ApiUtils.setLoading(true, setLoading);
            
            const response = await AuthAPI.register(userData);
            
            // 회원가입 성공 시 자동 로그인 처리
            if (response.user || response.token) {
                this.currentUser = response.user || response;
                this.isAuthenticated = true;
                
                localStorage.setItem('currentUser', JSON.stringify(this.currentUser));
                if (response.token) {
                    localStorage.setItem('authToken', response.token);
                }
                
                return true;
            }
            
            return false;
        } catch (error) {
            ApiUtils.handleError(error, showError);
            return false;
        } finally {
            ApiUtils.setLoading(false, setLoading);
        }
    }

    /**
     * 로그아웃 처리
     * @param {Function} showError - 에러 표시 함수
     * @returns {Promise<boolean>} 로그아웃 성공 여부
     */
    async logout(showError) {
        try {
            // 서버에 로그아웃 요청
            await AuthAPI.logout();
        } catch (error) {
            // 서버 오류가 있어도 클라이언트에서는 로그아웃 처리
            console.warn('서버 로그아웃 실패:', error);
        } finally {
            // 클라이언트 로그아웃 처리
            this.clearAuth();
        }
        
        return true;
    }

    /**
     * 인증 정보 초기화
     */
    clearAuth() {
        this.currentUser = null;
        this.isAuthenticated = false;
        
        // localStorage에서 제거
        localStorage.removeItem('currentUser');
        localStorage.removeItem('authToken');
    }

    /**
     * 토큰 갱신 (필요시)
     * @returns {Promise<boolean>} 갱신 성공 여부
     */
    async refreshToken() {
        // 토큰 갱신 로직이 필요한 경우 구현
        // 현재는 단순히 토큰 존재 여부만 확인
        const token = localStorage.getItem('authToken');
        return !!token;
    }

    /**
     * 인증 상태 검증
     * @returns {Promise<boolean>} 유효한 인증 상태 여부
     */
    async validateAuth() {
        if (!this.isAuthenticated || !this.currentUser) {
            return false;
        }

        // 토큰이 있는지 확인
        const token = localStorage.getItem('authToken');
        if (!token) {
            this.clearAuth();
            return false;
        }

        return true;
    }
}

// 싱글톤 인스턴스 생성
const authManager = new AuthManager();

// 유틸리티 함수들
export const AuthUtils = {
    /**
     * 폼 유효성 검사
     * @param {Object} formData - 폼 데이터
     * @returns {Object} 검사 결과
     */
    validateForm: (formData) => {
        const errors = {};
        
        // 로그인인지 회원가입인지 구분 (email이 있으면 회원가입)
        const isSignup = formData.hasOwnProperty('email');
        
        if (isSignup) {
            // 회원가입 시 검증
            // 이메일 검사
            if (!formData.email) {
                errors.email = '이메일을 입력해주세요.';
            } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email)) {
                errors.email = '올바른 이메일 형식을 입력해주세요.';
            }
            
            // 사용자명 검사
            if (!formData.username) {
                errors.username = '사용자명을 입력해주세요.';
            } else if (formData.username.length < 2) {
                errors.username = '사용자명은 2자 이상이어야 합니다.';
            }
        } else {
            // 로그인 시 검증
            // 사용자명 검사
            if (!formData.username) {
                errors.username = '사용자명을 입력해주세요.';
            }
        }
        
        // 비밀번호 검사 (공통)
        if (!formData.password) {
            errors.password = '비밀번호를 입력해주세요.';
        } else if (formData.password.length < 6) {
            errors.password = '비밀번호는 6자 이상이어야 합니다.';
        }
        
        return {
            isValid: Object.keys(errors).length === 0,
            errors
        };
    },

    /**
     * 에러 메시지 표시
     * @param {Object} errors - 에러 객체
     * @param {Function} showError - 에러 표시 함수
     */
    showFormErrors: (errors, showError) => {
        if (errors && typeof errors === 'object') {
            const errorMessages = Object.values(errors);
            if (errorMessages.length > 0) {
                showError(errorMessages[0]);
            }
        }
    },

    /**
     * 입력 필드 에러 표시
     * @param {string} fieldName - 필드명
     * @param {string} errorMessage - 에러 메시지
     */
    showFieldError: (fieldName, errorMessage) => {
        const field = document.querySelector(`[name="${fieldName}"]`);
        if (field) {
            field.classList.add('error');
            
            // 기존 에러 메시지 제거
            const existingError = field.parentNode.querySelector('.field-error');
            if (existingError) {
                existingError.remove();
            }
            
            // 새 에러 메시지 추가
            const errorElement = document.createElement('div');
            errorElement.className = 'field-error';
            errorElement.textContent = errorMessage;
            field.parentNode.appendChild(errorElement);
        }
    },

    /**
     * 모든 필드 에러 제거
     */
    clearFieldErrors: () => {
        const errorFields = document.querySelectorAll('.error');
        errorFields.forEach(field => field.classList.remove('error'));
        
        const errorMessages = document.querySelectorAll('.field-error');
        errorMessages.forEach(message => message.remove());
    },

    /**
     * 로그인 페이지로 리다이렉트
     */
    redirectToLogin: () => {
        window.location.href = '/login.html';
    },

    /**
     * 피드 페이지로 리다이렉트
     */
    redirectToFeed: () => {
        window.location.href = '/feed.html';
    },
};

export { authManager as AuthManager }; 