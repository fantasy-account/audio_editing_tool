package whut_404notfound.audio_editing_tool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import whut_404notfound.audio_editing_tool.domain.BaseResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static whut_404notfound.audio_editing_tool.constant.Constant.UPLOAD_FILE_SAVE_ROOT_PATH;

/**
 * 文件上传控制器
 *
 * @author <a href="mailto:873406454@qq.com">Li Hangfei</a>
 * @date 2021/3/14
 */
@Controller
public class UploadController {
    @PostMapping("/upload/video")
    @ResponseBody
    public BaseResponse receiveFileFromBrowser(@RequestBody MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            // ==================================================
            // TODO 在这里完成对文件类型与大小判断，简单后缀判断不安全
            // TODO 可更改Constant.java下的全局常量RECEIVE_FILE_SUFFIXS
            // ==================================================

            String filePath = UPLOAD_FILE_SAVE_ROOT_PATH + file.getOriginalFilename();
            File saveFile = new File(filePath);
            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }
            file.transferTo(saveFile);
        }
        return new BaseResponse(HttpServletResponse.SC_OK,"上传成功");
    }
}
