package whut_404notfound.audio_editing_tool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import whut_404notfound.audio_editing_tool.domain.Modify;
import whut_404notfound.audio_editing_tool.domain.VideoPart;
import whut_404notfound.audio_editing_tool.repository.ModifyRepository;
import whut_404notfound.audio_editing_tool.util.VideoUtil;

import java.util.ArrayList;
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

            String[] videoPartUrl = modify.getInclusivePart().getVideoPartUrl();
            String[] videoPartUrl1 = modify.getModifiedPart().getVideoPartUrl();

            List<Integer> listmap=new ArrayList<>();
            for(int i=0;i<modify.getModifiedPartNum();i++){
                int a=videoPartUrl1[i].lastIndexOf('/');
//                System.out.println(a);
                int b=videoPartUrl1[i].indexOf("merged.mp4");
//                System.out.println(b);
                String s=videoPartUrl1[i].substring(a+1,b);
                int num=Integer.parseInt(s);
//                System.out.println(num);
                listmap.add(num);
            }

            String[] allUrl = new String[modify.getPartNum()];
            int j=0;
            for(int i=0;i<modify.getPartNum();i++){
                if(listmap.contains(i)){
                    allUrl[i]=videoPartUrl1[j++];
                }else{
                    allUrl[i]=videoPartUrl[i];
                }
            }

            for(int i=0;i<modify.getPartNum();i++){
                System.out.println(allUrl[i]);
            }

            String finalVideoUrl=SRC_FILE_SAVE_ROOT_PATH +videoId+"/finalVideo.mp4";
            //执行合并
            try {
                VideoUtil.changeToTs2(allUrl);
                if (VideoUtil.mp4ToUnit2(allUrl,finalVideoUrl)) {
                    System.out.println(finalVideoUrl);

                    System.out.println("总视频合成完毕，链接是"+finalVideoUrl);
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
