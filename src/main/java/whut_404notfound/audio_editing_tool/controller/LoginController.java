package whut_404notfound.audio_editing_tool.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import whut_404notfound.audio_editing_tool.domain.BaseResponse;
import whut_404notfound.audio_editing_tool.domain.User;
import whut_404notfound.audio_editing_tool.exception.IllegalRequestParamException;
import whut_404notfound.audio_editing_tool.repository.UserRepository;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;

import static whut_404notfound.audio_editing_tool.constant.Constant.SESSION_KEY_USER;

/**
 * @author Xiaoyu Fan
 * @description 登录控制器
 * @create 2021-03-07 16:27
 */
@Controller
public class LoginController {

    final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    @ResponseBody
    public BaseResponse login(@RequestBody User user, HttpSession httpSession, HttpServletResponse response) throws Exception{
        if(null==user||user.getUsername().isEmpty()||user.getPassword().isEmpty()){
            throw new IllegalRequestParamException("用户登录参数缺失");
        }

        List<User> userList=userRepository.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
        if(!userList.isEmpty()){
            httpSession.setAttribute(SESSION_KEY_USER,userList.get(0).getId());
            return new BaseResponse(HttpServletResponse.SC_OK, "登录成功");
        }

        // 状态码403
        return new BaseResponse(HttpServletResponse.SC_FORBIDDEN, "用户名或密码错误");
    }
}