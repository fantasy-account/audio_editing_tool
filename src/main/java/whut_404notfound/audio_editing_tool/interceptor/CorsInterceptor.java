package whut_404notfound.audio_editing_tool.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

/**
 * 跨域拦截器
 *
 * @author <a href="mailto:873406454@qq.com">Li Hangfei</a>
 * @date 2021/3/15
 */
@Deprecated
public class CorsInterceptor implements HandlerInterceptor {
    private static final String OPTIONS = "OPTIONS";

    List<String> DEFAULT_SUPPORTED_REQUEST_HEADERS = Arrays.asList(
            "Host",
            "User-Agent",
            "Origin",
            "Referer",
            "Connection",
            "Cache-Control",
            "X-Requested-With",
            "Content-Type",
            "Accept",
            "Accept-Language",
            "Accept-Encoding",
            "Access-Control-Request-Method",
            "Access-Control-Request-Header",
            "Pragma"
    );

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", !request.getHeader("origin").isEmpty() ? request.getHeader("origin") : "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS");
        List<String> allowHeaders = new LinkedList<>(DEFAULT_SUPPORTED_REQUEST_HEADERS);
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            allowHeaders.add(headerNames.nextElement());
        }
        response.setHeader("Access-Control-Allow-Headers", String.join(",", allowHeaders));
        if (OPTIONS.equalsIgnoreCase(request.getMethod())) {
            System.out.println("options");
            response.setStatus(HttpServletResponse.SC_OK);
            return false;
        }
        return true;
    }
}
