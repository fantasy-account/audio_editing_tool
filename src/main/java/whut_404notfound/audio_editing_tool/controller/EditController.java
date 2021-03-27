package whut_404notfound.audio_editing_tool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import whut_404notfound.audio_editing_tool.domain.BaseResponse;
import whut_404notfound.audio_editing_tool.domain.Modify;
import whut_404notfound.audio_editing_tool.repository.ModifyRepository;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static whut_404notfound.audio_editing_tool.constant.Constant.SESSION_KEY_USER;
import static whut_404notfound.audio_editing_tool.constant.Constant.SESSION_KEY_VIDEO;

/**
 * @author Xiaoyu Fan
 * @description 视频编辑控制器
 * @create 2021-03-20 09:10
 */
@Controller
public class EditController {

    final ModifyRepository modifyRepository;

    public EditController(ModifyRepository modifyRepository) {
        this.modifyRepository = modifyRepository;
    }

    @GetMapping("/edit")
    @ResponseBody
    public BaseResponse obtain(HttpSession httpSession){
        //获取session中携带的视频id
        Object value=httpSession.getAttribute(SESSION_KEY_VIDEO);
        int videoId=Integer.parseInt(String.valueOf(value));

        List<Modify> modifyList=modifyRepository.findModifyByVideoId(videoId);
        if(!modifyList.isEmpty()){
            Modify modify=modifyList.get(0);
            System.out.println(modify);

            return new BaseResponse(HttpServletResponse.SC_OK, "视频分片成功，具体信息见data",modify);
        }
        // 状态码404
        return new BaseResponse(HttpServletResponse.SC_NOT_FOUND, "数据库中查询不到视频分片信息");
    }
}
