package whut_404notfound.audio_editing_tool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static whut_404notfound.audio_editing_tool.constant.Constant.SESSION_KEY_USER;

/**
 * 简单url测试控制器
 *
 * @author <a href="mailto:873406454@qq.com">Li Hangfei</a>
 * @date 2021/3/14
 */
@Controller
public class TestController {
    @PostMapping("/test")
    @ResponseBody
    String test(HttpServletResponse response, HttpSession httpSession) {
        int a = 1 / 0;
//        System.out.println(httpSession.getAttribute(SESSION_KEY_USER));
//        response.setContentType("application/json;charset=UTF-8");
        return "test";
    }
}
