package whut_404notfound.audio_editing_tool.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import whut_404notfound.audio_editing_tool.domain.User;
import whut_404notfound.audio_editing_tool.exception.IllegalRequestParamException;
import whut_404notfound.audio_editing_tool.repository.UserRepository;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Xiaoyu Fan
 * @description 登录控制器
 * @create 2021-03-07 16:27
 */
@RestController
public class LoginController {

    final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public String login(@RequestBody User user, HttpSession httpSession, HttpServletResponse response) throws Exception{
        if(null==user||user.getUsername().isEmpty()||user.getPassword().isEmpty()){
            throw new IllegalRequestParamException();
        }

        if(!userRepository.findUserByUsernameAndPassword(user.getUsername(), user.getPassword()).isEmpty()){
            httpSession.setAttribute("SESSION_KEY_USER",user.getUsername());
            return "登录成功";
        }

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        return "用户名或密码错误";
    }
}