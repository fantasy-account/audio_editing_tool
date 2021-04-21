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
 * @description ...
 * @create 2021-04-14 14:41
 */
@Component
public class EditService {

    @Autowired
    private Transform transform;

    @Autowired
    private ModifyRepository modifyRepository;

    @Autowired
    private VideoRepository videoRepository;

    public String text2speech(String text,Integer videoId,Integer videoPartId){

        List<Modify> modifyList=modifyRepository.findModifyByVideoId(videoId);
        if(!modifyList.isEmpty()){
            Modify modify=modifyList.get(0);
            System.out.println("从数据库中获取到的原始信息是"+modify);
            VideoPart inclusivePart = modify.getInclusivePart();
            String videoPartUrl = inclusivePart.getVideoPartUrl()[videoPartId];
            try {
                transform.Character2Audio(text,videoPartUrl.replace(".mp4","changed.wav"));
                VideoUtil.convetor(videoPartUrl,videoPartUrl.replace(".mp4","changed.wav"),videoPartUrl.replace(".mp4","merged.mp4"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            videoRepository.videoHasBeenModified(videoId);
            System.out.println("已经更新video表，视频已经被修改过");

            VideoPart modifiedPart = modify.getModifiedPart();
            if(modifiedPart!=null){//如果当前已经存在了更改片
                modifiedPart.setVideoPartUrl(videoPartUrl.replace(".mp4","merged.mp4"));
                modifiedPart.setContent(text);
                modifiedPart.setImageUrl(inclusivePart.getImageUrl()[videoPartId]);
                modifiedPart.setStartTime(inclusivePart.getStartTime()[videoPartId]);
                modifiedPart.setEndTime(inclusivePart.getEndTime()[videoPartId]);
                modifiedPart.addNum();
                modifyRepository.videoHasBeenModified(videoId,modify.getModifiedPartNum()+1,modify.getModifiedDuration()+VideoUtil.getVideoTime(videoPartUrl),modifiedPart);
                //保存更新后的视频片对象
            }else{//不存在已经更改的时间片
                VideoPart videoPart=new VideoPart(inclusivePart.getPartNum());
                videoPart.setVideoPartUrl(videoPartUrl.replace(".mp4","merged.mp4"));
                videoPart.setContent(text);
                videoPart.setStartTime(inclusivePart.getStartTime()[videoPartId]);
                videoPart.setEndTime(inclusivePart.getEndTime()[videoPartId]);
                videoPart.setImageUrl(inclusivePart.getImageUrl()[videoPartId]);
                videoPart.addNum();
                modifyRepository.videoHasBeenModified(videoId,1,VideoUtil.getVideoTime(videoPartUrl),videoPart);
            }
            System.out.println("文字转语音修改后的信息是"+modifyRepository.findModifyByVideoId(videoId).get(0));
            return videoPartUrl.replace(".mp4","merged.mp4");
        }
        return null;
    }


}