package com.omarket.auth;

import com.omarket.auth.exception.NoAuthorizationData;
import com.omarket.auth.exception.UnauthorizedException;
import com.omarket.global.constant.SessionKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws UnauthorizedException {
        try {
            if (isNeedToAuth((HandlerMethod)handler)) {
                getUserIdBySession(request);
            }
            return true;
        } catch (Exception e) {
            throw new UnauthorizedException(e);
        }
    }

    private boolean isNeedToAuth(HandlerMethod handler){
        if (handler.getMethodAnnotation(AuthRequired.class) == null) {
            return false;
        }
        return true;
    }

    private String getUserIdBySession(HttpServletRequest request){
        HttpSession session = request.getSession();
        return Optional.ofNullable(session.getAttribute(SessionKey.LOGIN_USER_ID))
                .map(v -> v.toString())
                .orElseThrow(NoAuthorizationData::new);
    }
}