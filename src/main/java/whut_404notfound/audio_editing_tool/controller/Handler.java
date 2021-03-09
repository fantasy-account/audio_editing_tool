package whut_404notfound.audio_editing_tool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import whut_404notfound.audio_editing_tool.entity.User;
import whut_404notfound.audio_editing_tool.repository.UserRepository;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 前端控制器，数据首先从前端来到这里
 * @author Xiaoyu Fan
 * @create 2021-03-07 16:27
 */
@RestController
@RequestMapping("")
public class Handler {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public boolean login(@RequestBody User user){
        //System.out.println(user);
        List<User> userList= userRepository.findUserByNameAndPwd(user.getName(),user.getPwd());
        //System.out.println("这里是数据库查询结果"+userList);
        if(userList.isEmpty()){
            return false;//查询结果为空说明用户名或者密码错误，不允许登录
        }else{
            return true;
        }
    }

    @PostMapping("/upload/video")
    public Map receiveFile(@RequestBody MultipartFile file){
        HashMap<String,String> map=new HashMap();
        if (!file.isEmpty()) {
            try {
                // getOriginalFilename()是包含源文件后缀的全名
                String filePath = "/upload/"+file.getOriginalFilename();
                //System.out.println(filePath);
                File saveFile = new File(filePath);
                if (!saveFile.getParentFile().exists())//如果保存路径不存在
                    saveFile.getParentFile().mkdirs();//强行创建多级目录以保存文件
                file.transferTo(saveFile);//保存文件到指定路径
                map.put("res","上传成功");
                return map;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        map.put("res","上传失败");
        return map;
    }
}
/**
 * 　　根目录：com.springboot
 *
 * 　　　　1.工程启动类(ApplicationServer.java)置于com.springboot.build包下
 *
 * 　　　　2.实体类(domain)置于com.springboot.domain
 *
 * 　　　　3.数据访问层(Dao)置于com.springboot.repository
 *
 * 　　　　4.数据服务层(Service)置于com,springboot.service,数据服务的实现接口(serviceImpl)至于com.springboot.service.impl
 *
 * 　　　　5.前端控制器(Controller)置于com.springboot.controller
 *
 * 　　　　6.工具类(utils)置于com.springboot.utils
 *
 * 　　　　7.常量接口类(constant)置于com.springboot.constant
 *
 * 　　　　8.配置信息类(config)置于com.springboot.config
 *
 * 　　　　9.数据传输类(vo)置于com.springboot.vo
 */