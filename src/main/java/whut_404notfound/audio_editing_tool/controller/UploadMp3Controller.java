package whut_404notfound.audio_editing_tool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import whut_404notfound.audio_editing_tool.domain.BaseResponse;
import whut_404notfound.audio_editing_tool.domain.Modify;
import whut_404notfound.audio_editing_tool.domain.Video;
import whut_404notfound.audio_editing_tool.domain.VideoPart;
import whut_404notfound.audio_editing_tool.service.UploadMp3Service;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static whut_404notfound.audio_editing_tool.constant.Constant.*;
import static whut_404notfound.audio_editing_tool.constant.Constant.SRC_FILE_SAVE_ROOT_PATH;

/**
 * @author Xiaoyu Fan
 * @description 接收用户上传的mp3文件
 * @create 2021-04-15 12:39
 */
public class UploadMp3Controller {

    @Autowired
    private UploadMp3Service uploadMp3Service;
    @PostMapping("/upload/mp3")
    @ResponseBody
    public BaseResponse receiveFileFromBrowser(@RequestBody Integer requestVideoPartId, @RequestBody MultipartFile file, HttpSession httpSession) throws IOException {
        if (!file.isEmpty()) {
            Object value = httpSession.getAttribute(SESSION_KEY_VIDEO);
            Integer videoId = Integer.parseInt(String.valueOf(value));
            //以上两步是从session中获取视频的id

            String mp3SavePath = UPLOAD_FILE_SAVE_ROOT_PATH + videoId + "/" + requestVideoPartId + "uploaded" + file.getOriginalFilename().substring(file.getOriginalFilename().length() - 4);
            File saveFile = new File(mp3SavePath);
            file.transferTo(saveFile);


            String s = uploadMp3Service.mergeMp3ToMp4(videoId, requestVideoPartId, mp3SavePath);
            if(s!=null){
                System.out.println("用户上传声音已经和视频合并，合并结果为"+s);
                return new BaseResponse(HttpServletResponse.SC_OK, "上传替换的声音格式文件成功");
            }

            // uploadService.cuttingVideo(userId,uploadVideo.getVideoId(),filePath,outputPath);
        }
        return new BaseResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "可能是上传的文件格式问题，未能成功执行替换");
    }
}
