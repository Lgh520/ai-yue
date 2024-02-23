package com.project.aiyue.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CorsInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        // 允许跨域
        response.addHeader("Access-Control-Allow-Origin", "*");
        // 允许前端携带cookie：启用此项后，上面的域名不能为'*'，必须指定具体的域名
        // response.addHeader("Access-Control-Allow-Credentials", "true");
        // 允许跨域的方法
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT,PATCH, HEAD");
        // 允许跨域的头
        response.addHeader("Access-Control-Allow-Headers", "Content-Type, X-Requested-With");
        // 跨域的有效期，在有效期内，无需检查跨域问题
        response.addHeader("Access-Control-Max-Age", "7200");

        // 该字段可选。CORS请求时，XMLHttpRequest对象的getResponseHeader()方法只能拿到6个基本字段：Cache-Control、Content-Language、Content-Type、Expires、Last-Modified、Pragma。如果想拿到其他字段，就必须在Access-Control-Expose-Headers里面指定。
        // response.addHeader("Access-Control-Expose-Headers", "XXX");

        return super.preHandle(request, response, handler);
    }
}
