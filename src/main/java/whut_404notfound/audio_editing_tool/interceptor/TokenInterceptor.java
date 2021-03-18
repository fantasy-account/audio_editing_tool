package whut_404notfound.audio_editing_tool.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;
import whut_404notfound.audio_editing_tool.domain.BaseResponse;
import whut_404notfound.audio_editing_tool.util.JsonWebTokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static whut_404notfound.audio_editing_tool.constant.Constant.JWT_SECRET;

/**
 * Token验证拦截器
 *
 * @author <a href="mailto:873406454@qq.com">Li Hangfei</a>
 * @date 2021/3/17
 */
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        System.out.println(token);
        if (null == token || token.length() == 0) {
            response.setContentType("application/json;charset=UTF-8");
            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().println(mapper.writeValueAsString(new BaseResponse<Void>(HttpServletResponse.SC_FORBIDDEN, "请先登录")));
            System.out.println("token为空");
            return false;
        } else if (!JsonWebTokenUtil.verifyTokenByHMAC(token, JWT_SECRET)) {
            response.setContentType("application/json;charset=UTF-8");
            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().println(mapper.writeValueAsString(new BaseResponse<Void>(HttpServletResponse.SC_FORBIDDEN, "Token未授权，请重新登录")));
            System.out.println("token验证未通过");
            return false;
        }
        int id = Integer.valueOf(JsonWebTokenUtil.getPayloadFromToken(token));
        System.out.println("Token中的用户Id:" + id);
        return true;
    }
}
