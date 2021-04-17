package whut_404notfound.audio_editing_tool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import whut_404notfound.audio_editing_tool.domain.BaseResponse;
import whut_404notfound.audio_editing_tool.service.FinishAllService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static whut_404notfound.audio_editing_tool.constant.Constant.SESSION_KEY_VIDEO;

/**
 * @author Xiaoyu Fan
 * @description 用户提交了更改，合成视频并返回路径
 * @create 2021-04-17 13:28
 */
@Controller
public class FinishAllController {

    @Autowired
    private FinishAllService finishAllService;
    @PostMapping("/finish")
    @ResponseBody
    public BaseResponse receiveFileFromBrowser(HttpSession httpSession) {
        Object value = httpSession.getAttribute(SESSION_KEY_VIDEO);
        Integer videoId = Integer.parseInt(String.valueOf(value));
        String finish = finishAllService.finish(videoId);
        if(finish!=null){
            return new BaseResponse(HttpServletResponse.SC_OK,finish);
        }
        return new BaseResponse(HttpServletResponse.SC_EXPECTATION_FAILED, "视频合并失败");
    }
}
