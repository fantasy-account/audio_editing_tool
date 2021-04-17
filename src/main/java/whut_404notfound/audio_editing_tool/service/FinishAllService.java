package whut_404notfound.audio_editing_tool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import whut_404notfound.audio_editing_tool.domain.Modify;
import whut_404notfound.audio_editing_tool.domain.VideoPart;
import whut_404notfound.audio_editing_tool.repository.ModifyRepository;
import whut_404notfound.audio_editing_tool.util.VideoUtil;

import java.util.List;

import static whut_404notfound.audio_editing_tool.constant.Constant.SRC_FILE_SAVE_ROOT_PATH;

/**
 * @author Xiaoyu Fan
 * @description 合成总视频服务层
 * @create 2021-04-17 13:30
 */
@Component
public class FinishAllService {
    @Autowired
    private ModifyRepository modifyRepository;

    public String finish(Integer videoId){
        List<Modify> modifyList=modifyRepository.findModifyByVideoId(videoId);
        if(!modifyList.isEmpty()) {
            Modify modify = modifyList.get(0);
            System.out.println("从数据库中获取到的原始信息是" + modify);
            VideoPart inclusivePart = modify.getInclusivePart();
            VideoPart modifiedPart = modify.getModifiedPart();

            String[] allUrl = new String[inclusivePart.getPartNum()];
            for(int i=0;i<inclusivePart.getPartNum();i++){
                if(modifiedPart.getVideoPartUrl()[i]==null){
                    allUrl[i]=inclusivePart.getVideoPartUrl()[i];
                }else{
                    allUrl[i]=modifiedPart.getVideoPartUrl()[i];
                }
            }
            for(int i=0;i<inclusivePart.getPartNum();i++){
                System.out.println(allUrl[i]);
            }

            String finalVideoUrl=SRC_FILE_SAVE_ROOT_PATH +videoId+"/finalVideo.mp4";
            //执行合并
            try {
//                VideoUtil.changeToTs2(allUrl);
                if (VideoUtil.mp4ToUnit2(allUrl,finalVideoUrl)) {
                    return finalVideoUrl;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}
