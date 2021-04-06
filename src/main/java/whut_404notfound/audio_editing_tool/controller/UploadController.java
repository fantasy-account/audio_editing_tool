package whut_404notfound.audio_editing_tool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import whut_404notfound.audio_editing_tool.domain.BaseResponse;
import whut_404notfound.audio_editing_tool.domain.Video;
import whut_404notfound.audio_editing_tool.repository.VideoRepository;
import whut_404notfound.audio_editing_tool.service.VideoService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

import static whut_404notfound.audio_editing_tool.constant.Constant.*;

/**
 * 文件上传控制器
 *
 * @author <a href="mailto:873406454@qq.com">Li Hangfei</a>
 * @date 2021/3/14
 */
@Controller
public class UploadController {

    final VideoRepository videoRepository;

    public UploadController(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @PostMapping("/upload/video")
    @ResponseBody
    public BaseResponse receiveFileFromBrowser(@RequestBody MultipartFile file, HttpSession httpSession) throws IOException {
        if (!file.isEmpty()) {
            // ==================================================
            // TODO 在这里完成对文件类型与大小判断，简单后缀判断不安全
            // TODO 可更改Constant.java下的全局常量RECEIVE_FILE_SUFFIXS
            // ==================================================

            //先在数据库中为该接收的文件申请一个id，并将id作为一个文件夹名，防止上传的视频名称冲突
            Object value=httpSession.getAttribute(SESSION_KEY_USER);
            Integer userId=Integer.parseInt(String.valueOf(value));
            //以上两步是从session中获取用户的id
            Video uploadVideo=videoRepository.saveAndFlush(new Video(file.getOriginalFilename(),file.getSize(), null, null, null,userId));
            System.out.println(uploadVideo);

            String filePath = UPLOAD_FILE_SAVE_ROOT_PATH +uploadVideo.getVideoId()+"/"+file.getOriginalFilename();
            File saveFile = new File(filePath);
            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }
            file.transferTo(saveFile);

            //System.out.println(filePath);

            httpSession.setAttribute(SESSION_KEY_VIDEO, uploadVideo.getVideoId());//这里的是视频编号，存数据库是会返回这个ID
            System.out.println("视频id保存到session:" + httpSession.getId());

            String outputPath=SRC_FILE_SAVE_ROOT_PATH +uploadVideo.getVideoId()+"/";
            File outputFile = new File(outputPath+"1.txt");
            if (!outputFile.getParentFile().exists()) {
                outputFile.getParentFile().mkdirs();
            }
            //System.out.println(outputPath);
            VideoService.cuttingVideo(filePath,outputPath);
        }
        return new BaseResponse(HttpServletResponse.SC_OK,"上传成功");
    }
}
