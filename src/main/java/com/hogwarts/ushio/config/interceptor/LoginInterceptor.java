package com.hogwarts.ushio.config.interceptor;

import com.hogwarts.ushio.common.db.TokenDb;
import com.hogwarts.ushio.exception.ServiceException;
import com.hogwarts.ushio.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author: ushio
 * @description:
 **/
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenDb tokenDb;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle()");
        String requestUrl = request.getRequestURI();
        log.info("preHandle() requestUrl:" + requestUrl);

        //如果为swagger文档地址,直接通过
        boolean swaggerFlag = requestUrl.contains("swagger")
                //过滤spring默认错误页面
                || requestUrl.equals("/error")
                //过滤csrf
                || requestUrl.equals("/csrf")
                //过滤http://127.0.0.1:8093/v2/api-docs
                || requestUrl.equals("/favicon.ico")
                //演示map local 不用校验是否登录
                || requestUrl.equals("/report/showMapLocal")
                || requestUrl.equals("/");
        if (swaggerFlag) {
            return true;
        }

        //从请求的head头中获取客户端token
        String token = request.getHeader(Constant.LOGIN_TOKEN);
        log.info("preHandle() token:" + token);

        //注册登录接口没有token无需拦截(单点登录的话这里拦截ushio/login，根据tokenDb.isOnline(token)判断)
        if (requestUrl.contains("ushio/register") ||
                requestUrl.contains("ushio/login")) {
            return true;
        }

        if(TextUtils.isEmpty(token) || Objects.isNull(tokenDb.getUserInfo(token))) {
            //无token或token对应tokenDto为空，响应码设401，抛出业务异常：客户端未传token
            response.setStatus(401);
            ServiceException.throwException("客户端未传token");
            return false;
        }


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle()");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("afterCompletion()");
    }
}
