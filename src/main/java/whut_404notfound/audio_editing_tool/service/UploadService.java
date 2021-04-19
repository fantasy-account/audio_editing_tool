package whut_404notfound.audio_editing_tool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import whut_404notfound.audio_editing_tool.domain.Modify;
import whut_404notfound.audio_editing_tool.domain.VideoPart;
import whut_404notfound.audio_editing_tool.repository.ModifyRepository;
import whut_404notfound.audio_editing_tool.repository.VideoRepository;
import whut_404notfound.audio_editing_tool.util.Transform;
import whut_404notfound.audio_editing_tool.util.VideoUtil;
import java.sql.Time;

import static whut_404notfound.audio_editing_tool.constant.Constant.PART_DURATION;

/**
 * @author Xiaoyu Fan
 * @description 切割服务
 * @create 2021-03-27 22:05
 */
@Component
public class UploadService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private ModifyRepository modifyRepository;

    @Autowired
    private Transform transform;

    public void cuttingVideo(Integer userId, Integer videoId, String videoInputPath, String videoOutputPath) {
        try {
            VideoUtil.mp4ToCut3(videoInputPath, videoOutputPath, PART_DURATION);
            int duration=VideoUtil.getVideoTime(videoInputPath);

            VideoUtil.changeToJpg(videoInputPath,videoOutputPath+"image.jpg",0,0);

            System.out.println("视频id:"+videoId);
            System.out.println("视频总时长:"+duration);
            System.out.println("视频路径:"+videoInputPath);
            System.out.println("封面路径:"+videoOutputPath+"image.jpg");
            System.out.println(videoRepository.updateVideo(videoId,duration,videoInputPath,videoOutputPath+"image.jpg"));
            System.out.println("video表数据导入完毕");

            int part_num = duration / PART_DURATION;
            VideoPart videoPart = new VideoPart(part_num);
            String result=null;
            Integer result1=0;
            Integer result2=0;
            for (int i = 0; i < part_num; i++) {
                int hh = i * PART_DURATION / 3600;
                int mm = (i * PART_DURATION % 3600) / 60;
                int ss = (i * PART_DURATION % 3600) % 60;
                int h1 = (i + 1) * PART_DURATION / 3600;
                int m1 = ((i + 1) * PART_DURATION % 3600) / 60;
                int s1 = ((i + 1) * PART_DURATION % 3600) % 60;

                Time st = new Time(hh, mm, ss);
                videoPart.setStartTime(st);
                Time end = new Time(h1, m1, s1);
                videoPart.setEndTime(end);

                if(i==part_num-1){
                    int h2 = duration / 3600;
                    int m2 = (duration % 3600) / 60;
                    int s2 = (duration % 3600) % 60;
                    Time end2 = new Time(h2, m2, s2);
                    videoPart.setEndTime(end2);
                }
                String n = "" + i;
                if (i < 10) {
                    n = "0" + i;
                }
                VideoUtil.changeToJpg(videoOutputPath+n+".mp4",videoOutputPath+n+".jpg",0,0);
                videoPart.setVideoPartUrl(videoOutputPath+n+".mp4");
                videoPart.setImageUrl(videoOutputPath + n + ".jpg");
                VideoUtil.changeFileToWav(videoOutputPath+n+".mp4",videoOutputPath+n+".wav");

                result=transform.Audio2Character(videoOutputPath+n+".wav");
                try {
                    result1 = result.indexOf(":[\"") + 3;
                    result2 = result.indexOf("\"],");
                    videoPart.setContent(result.substring(result1, result2));
                }catch (Exception e){
                    videoPart.setContent("在第"+i+"段语音转文字的时候出现了问题");
                }
                videoPart.addNum();
            }
            System.out.println("这里已经生成好了，除了教师文字的视频片videoPart");
            System.out.println(videoPart);

            System.out.println(modifyRepository.saveAndFlush(new Modify(videoId,userId,part_num,videoPart)));
            System.out.println("视频片对象保存完毕");

        } catch (Exception e) {
            System.out.println("uploadService出现问题");
            System.out.println(e.getMessage());
        }
    }
}
