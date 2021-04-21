package whut_404notfound.audio_editing_tool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import whut_404notfound.audio_editing_tool.service.FinishAllService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import static whut_404notfound.audio_editing_tool.constant.Constant.SESSION_KEY_VIDEO;

/**
 * @author Xiaoyu Fan
 * @description 响应用户请求，发送视频给用户
 * @create 2021-04-14 23:24
 */
@Controller
public class GetFileController {

    @Autowired
    private FinishAllService finishAllService;

    @GetMapping("/getFileStream")
    public void videoPreview(@RequestParam String url, HttpServletResponse resp, HttpSession httpSession) throws Exception {
        System.out.println(url);
        if (url.equals("success")) {
            Object value = httpSession.getAttribute(SESSION_KEY_VIDEO);
            Integer videoId = Integer.parseInt(String.valueOf(value));
            url = finishAllService.finish(videoId);//合并视频后，把视频链接给它
            System.out.println("视频合成后得到的地址是：" + url);
//            url="D:/upload/videoResources/101/01.mp4";
        }
        File file = new File(url);
        //resp.setCharacterEncoding("UTF-8");
        resp.addHeader("Content-Length", "" + file.length());
        //设置输出文件类型
//        resp.setContentType("arraybuffer");
        FileInputStream fis = null;
        OutputStream os = null;

        try {
            os = resp.getOutputStream();
            fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
        } catch (Exception e) {
            if (null != fis) {
                fis.close();
            }
            if (null != os) {
                os.flush();
                os.close();
            }
        }
    }
}