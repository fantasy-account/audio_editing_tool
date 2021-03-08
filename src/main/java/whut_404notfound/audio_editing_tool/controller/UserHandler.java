package whut_404notfound.audio_editing_tool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import whut_404notfound.audio_editing_tool.entity.User;
import whut_404notfound.audio_editing_tool.repository.UserRepository;

import java.util.List;

/**
 * @author Xiaoyu Fan
 * @version 1.0
 * @program audio_editing_tool
 * @description 前端控制器，控制登录时候的各种操作，数据首先从前端来到这里
 * @create 2021-03-07 16:27 
 */
@RestController
@RequestMapping("/login")
public class UserHandler {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/search")
    public boolean login(@RequestBody User user){
        System.out.println(user);
        List<User> userList= userRepository.findUserByNameAndPwd(user.getName(),user.getPwd());
        System.out.println("这里是数据库查询结果"+userList);
        if(userList.isEmpty()){
            return false;
        }else{
            return true;
        }
    }
}
