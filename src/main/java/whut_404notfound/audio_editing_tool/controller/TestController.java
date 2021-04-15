package whut_404notfound.audio_editing_tool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import whut_404notfound.audio_editing_tool.domain.BaseResponse;
import whut_404notfound.audio_editing_tool.domain.Modify;
import whut_404notfound.audio_editing_tool.repository.ModifyRepository;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;

import static whut_404notfound.audio_editing_tool.constant.Constant.SESSION_KEY_USER;

/**
 * 简单url测试控制器
 *
 * @author <a href="mailto:873406454@qq.com">Li Hangfei</a>
 * @date 2021/3/14
 */
@Controller
public class TestController {
//
//    @Autowired
//    private ModifyRepository modifyRepository;
//
//
//    @GetMapping("/test")
//    @ResponseBody
//    public BaseResponse sdf(HttpServletResponse response, HttpSession httpSession) {
//        //int a = 1 / 0;
////        System.out.println(httpSession.getAttribute(SESSION_KEY_USER));
////        response.setContentType("application/json;charset=UTF-8");
//        //return "test";
//        List<Modify> modifyList=modifyRepository.findModifyByVideoId(19);
//        if(!modifyList.isEmpty()){
//            Modify modify=modifyList.get(0);
//            System.out.println(modify);
//            return new BaseResponse(HttpServletResponse.SC_OK, "视频分片成功，具体信息见data",modify);
//        }
//        // 状态码404
//        return new BaseResponse(HttpServletResponse.SC_NOT_FOUND, "数据库中查询不到视频分片信息");
//    }
}
