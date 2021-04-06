package whut_404notfound.audio_editing_tool.service;

import whut_404notfound.audio_editing_tool.util.VideoUtil;

/**
 * @author Xiaoyu Fan
 * @description 切割服务
 * @create 2021-03-27 22:05
 */
public class VideoService {

    public static void cuttingVideo(String videoInputPath, String videoOutputPath){
        try {
            //VideoUtil.mp4ToCut1(videoInputPath, videoOutputPath, "60");
            int a=VideoUtil.getVideoTime(videoInputPath);
            System.out.println(a);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
