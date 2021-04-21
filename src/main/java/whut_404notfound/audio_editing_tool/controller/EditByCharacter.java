package whut_404notfound.audio_editing_tool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import whut_404notfound.audio_editing_tool.domain.BaseResponse;
import whut_404notfound.audio_editing_tool.service.EditService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static whut_404notfound.audio_editing_tool.constant.Constant.SESSION_KEY_VIDEO;

/**
 * @author Xiaoyu Fan
 * @description 用户上传文字，转语音后合并新视频
 * @create 2021-04-17 10:38
 */
@Controller
public class EditByCharacter {

    @Autowired
    private EditService editService;
    @PostMapping("/editByCharacter")
    @ResponseBody
    public BaseResponse receiveFileFromBrowser(@RequestParam Integer videoPartId, @RequestBody String text, HttpSession httpSession){
        Object value = httpSession.getAttribute(SESSION_KEY_VIDEO);
        Integer videoId = Integer.parseInt(String.valueOf(value));
        System.out.println("文字转语音，视频编号是"+videoId);
        System.out.println("文字转语音"+text);
        if (editService.text2speech(text.substring(8), videoId, videoPartId) != null) {
            System.out.println("文本转化语音替换成功");
            return new BaseResponse(HttpServletResponse.SC_OK, "文本转化语音替换成功");
        }
        return new BaseResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "文本转化语音替换失败");
    }
}
