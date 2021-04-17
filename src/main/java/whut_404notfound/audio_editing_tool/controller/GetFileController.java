package whut_404notfound.audio_editing_tool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import whut_404notfound.audio_editing_tool.domain.Modify;
import whut_404notfound.audio_editing_tool.domain.NonStaticResourceHttpRequestHandler;
import whut_404notfound.audio_editing_tool.repository.ModifyRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static whut_404notfound.audio_editing_tool.constant.Constant.SESSION_KEY_VIDEO;

/**
 * @author Xiaoyu Fan
 * @description 响应用户请求，发送视频给用户
 * @create 2021-04-14 23:24
 */
@Controller
public class GetFileController {
//    private ModifyRepository modifyRepository;
//    private final NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;
//
//    public GetVideoController(ModifyRepository modifyRepository, NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler) {
//        this.modifyRepository = modifyRepository;
//        this.nonStaticResourceHttpRequestHandler = nonStaticResourceHttpRequestHandler;
//    }

    @GetMapping("/getFileStream")
    public void videoPreview(@RequestParam String imgUrl, HttpServletResponse resp) throws Exception {
        //假如我把视频1.mp4放在了static下的video文件夹里面
        //sourcePath 是获取resources文件夹的绝对地址
        //realPath 即是视频所在的磁盘地址
        //String sourcePath = ClassUtils.getDefaultClassLoader().getResource("").getPath().substring(1);
        //获取session中携带的视频id
//        Object value = httpSession.getAttribute(SESSION_KEY_VIDEO);
//        int videoId = Integer.parseInt(String.valueOf(value));
//        List<Modify> modifyList = modifyRepository.findModifyByVideoId(videoId);
//        String realPath = null;
//        if (!modifyList.isEmpty()) {
//            Modify modify = modifyList.get(0);
//            String videourl = modify.getInclusivePart().getVideoPartUrl()[requestVideoPartId];
//            //String imageurl = modify.getInclusivePart().getImageUrl()[requestVideoPartId];
//            //System.out.println(modify);
//            //return new FileResponse(HttpServletResponse.SC_OK, "视频分片成功，具体信息见data",modify);
//            realPath = videourl;
//        }
        //String realPath = "C:/Users/fch11/Desktop/外包/bb.mp4";
//
//        Path filePath = Paths.get(realPath);
//        if (Files.exists(filePath)) {
//            String mimeType = Files.probeContentType(filePath);
//            System.out.println(mimeType);
//            if (!StringUtils.isEmpty(mimeType)) {
//                response.setContentType(mimeType);
//            }
//            request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, filePath);
//            nonStaticResourceHttpRequestHandler.handleRequest(request, response);
//        } else {
//            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
//        }
        //加载文件的位置"D:/upload/videoResources/29/00.mp4"
        System.out.println(imgUrl);
        File file = new File(imgUrl);
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

//    @GetMapping("/getVideoPart")
//    @ResponseBody
//    public FileResponse obtain(@RequestBody Integer requestVideoPartId, HttpSession httpSession){
//        //获取session中携带的视频id
//        Object value=httpSession.getAttribute(SESSION_KEY_VIDEO);
//        int videoId=Integer.parseInt(String.valueOf(value));
//
//        List<Modify> modifyList=modifyRepository.findModifyByVideoId(videoId);
//        if(!modifyList.isEmpty()){
//            Modify modify=modifyList.get(0);
//            String videourl = modify.getInclusivePart().getVideoPartUrl()[requestVideoPartId];
//            String imageurl = modify.getInclusivePart().getImageUrl()[requestVideoPartId];
//            //System.out.println(modify);
//            return new FileResponse(HttpServletResponse.SC_OK, "视频分片成功，具体信息见data",modify);
//        }
//        // 状态码404
//        return new FileResponse(HttpServletResponse.SC_NOT_FOUND, "数据库中查询不到视频分片信息");
//    }
//    @RequestMapping(value = "getVideo")
//    public void getVideo(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        File file = new File("e:/" + request.getParameter("name"));
//        try (
//            InputStream in = new FileInputStream(file); ServletOutputStream out = response.getOutputStream();) {
//            int length;
//            byte[] buffer = new byte[in.available()];
//            // 向前台输出视频流
//            while ((length = in.read(buffer)) > 0) {
//                out.write(buffer, 0, length);
//            }
//        } catch (FileNotFoundException e) {
//            System.out.println("文件读取失败, 文件不存在");
//        }
//    }

//    @ApiOperation(value = "mp4", notes = "mp4")
//    @GetMapping("/mp4")
//    public void mp4(@ApiParam(value = "mp4路径") @RequestParam String path, HttpServletResponse response) {
//
//        //获取session中携带的视频id
//        Object value=httpSession.getAttribute(SESSION_KEY_VIDEO);
//        int videoId=Integer.parseInt(String.valueOf(value));
//
//        String file = this.fileDir +path;
//        try {
//            FileInputStream inputStream = new FileInputStream(file);
//            byte[] data = new byte[inputStream.available()];
//            inputStream.read(data);
//            String diskfilename = "final.mp4";
//            response.setContentType("video/mp4");
//            response.setHeader("Content-Disposition", "attachment; filename=\"" + diskfilename + "\"");
//            //System.out.println("data.length " + data.length);
//            response.setContentLength(data.length);
//            response.setHeader("Content-Range", "" + Integer.valueOf(data.length - 1));
//            response.setHeader("Accept-Ranges", "bytes");
//            response.setHeader("Etag", "W/\"9767057-1323779115364\"");
//            OutputStream os = response.getOutputStream();
//
//            os.write(data);
//            //先声明的流后关掉！
//            os.flush();
//            os.close();
//            inputStream.close();
//
//        } catch (Exception e) {
//
//        }
//    }


