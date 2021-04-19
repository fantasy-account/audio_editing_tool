package whut_404notfound.audio_editing_tool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import whut_404notfound.audio_editing_tool.domain.Modify;
import whut_404notfound.audio_editing_tool.domain.VideoPart;
import whut_404notfound.audio_editing_tool.repository.ModifyRepository;
import whut_404notfound.audio_editing_tool.repository.VideoRepository;
import whut_404notfound.audio_editing_tool.util.Transform;
import whut_404notfound.audio_editing_tool.util.VideoUtil;

import java.util.List;

/**
 * @author Xiaoyu Fan
 * @description 用户上传了音频文件后的服务层
 * @create 2021-04-15 13:15
 */
@Component
public class UploadMp3Service {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private ModifyRepository modifyRepository;

    @Autowired
    private Transform transform;

    public String mergeMp3ToMp4(Integer videoId, Integer videoPartId, String mp3filePath) {
        List<Modify> modifyList = modifyRepository.findModifyByVideoId(videoId);
        if (!modifyList.isEmpty()) {
            Modify modify = modifyList.get(0);//从数据库中获取当前视频，当前分片的url
            System.out.println("从数据库中获取到的原始信息是"+modify);
            VideoPart inclusivePart = modify.getInclusivePart();
            String videoPartUrl = inclusivePart.getVideoPartUrl()[videoPartId];
            try {//将两者执行合并
                VideoUtil.convetor(videoPartUrl,mp3filePath,videoPartUrl.replace(".mp4","merged.mp4"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            videoRepository.videoHasBeenModified(videoId);
            //以上，完成了音频与视频的合并，下面开始更新数据库
            VideoPart modifiedPart = modify.getModifiedPart();
            if(modifiedPart!=null){//如果当前已经存在了更改片
                modifiedPart.setVideoPartUrl(videoPartUrl.replace(".mp4","merged.mp4"));
                modifiedPart.setContent(transform.Audio2Character(mp3filePath));
                modifiedPart.setImageUrl(inclusivePart.getImageUrl()[videoPartId]);
                modifiedPart.setStartTime(inclusivePart.getStartTime()[videoPartId]);
                modifiedPart.setEndTime(inclusivePart.getEndTime()[videoPartId]);
                modifiedPart.addNum();
                modifyRepository.videoHasBeenModified(videoId,modify.getModifiedPartNum()+1,modify.getModifiedDuration()+VideoUtil.getVideoTime(videoPartUrl),modifiedPart);
                //保存更新后的视频片对象
            }else{//不存在已经更改的时间片
                VideoPart videoPart=new VideoPart(inclusivePart.getPartNum());
                videoPart.setVideoPartUrl(videoPartUrl.replace(".mp4","merged.mp4"));
                videoPart.setContent(transform.Audio2Character(mp3filePath));
                videoPart.setStartTime(inclusivePart.getStartTime()[videoPartId]);
                videoPart.setEndTime(inclusivePart.getEndTime()[videoPartId]);
                videoPart.setImageUrl(inclusivePart.getImageUrl()[videoPartId]);
                videoPart.addNum();
                modifyRepository.videoHasBeenModified(videoId,1,VideoUtil.getVideoTime(videoPartUrl),videoPart);
            }
            System.out.println("修改后的信息是"+modifyRepository.findModifyByVideoId(videoId).get(0));
            return videoPartUrl.replace(".mp4","merged.mp4");
        }
        return null;
    }

}
