package whut_404notfound.audio_editing_tool.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;
import whut_404notfound.audio_editing_tool.domain.BaseResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static whut_404notfound.audio_editing_tool.constant.Constant.SESSION_KEY_USER;

/**
 * 登录请求拦截器
 *
 * @author <a href="mailto:873406454@qq.com">Li Hangfei</a>
 * @date 2021/3/11
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(null==request.getSession().getAttribute(SESSION_KEY_USER)){
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            ObjectMapper mapper=new ObjectMapper();
            response.getWriter().println(mapper.writeValueAsString(new BaseResponse<Void>(HttpServletResponse.SC_FORBIDDEN,"请先登录")));
            return false;
        }
        return true;
    }
}
