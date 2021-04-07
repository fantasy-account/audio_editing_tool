package whut_404notfound.audio_editing_tool;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import whut_404notfound.audio_editing_tool.domain.Modify;
import whut_404notfound.audio_editing_tool.domain.Video;
import whut_404notfound.audio_editing_tool.domain.VideoPart;
import whut_404notfound.audio_editing_tool.repository.ModifyRepository;
import whut_404notfound.audio_editing_tool.repository.UserRepository;
import whut_404notfound.audio_editing_tool.repository.VideoRepository;
import whut_404notfound.audio_editing_tool.service.UploadService;

import javax.sql.DataSource;
import java.sql.Time;


@SpringBootTest
class AudioEditingToolApplicationTests {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private ModifyRepository modifyRepository;
    @Autowired
    DataSource dataSource;


    @Autowired
    private UploadService uploadService;

    @Test
    void contextLoads() {
//        System.out.println(dataSource.getClass());
//        System.out.println(userRepository.findUserByUsernameAndPassword("13720113769", "li001015fei"));
//        User user=new User();
//        user.setUsername("2");
//        user.setPassword("2");
//        System.out.println(userRepository.saveAndFlush(user));
        //System.out.println(UUID.randomUUID().toString().replace("-","").length());

    }

    @Test
    void xuliehuatest() throws Exception {
        VideoPart testVideoPart = new VideoPart(3);

        for (int i = 0; i < 3; i++) {
            Time now = new Time(10, 10, i);
            testVideoPart.setStartTime(now);
            Time now1 = new Time(10, 10, (i + 10));
            testVideoPart.setEndTime(now1);
            testVideoPart.setContent("测试文字，老师说的第" + (i + 1) + "段话");
            testVideoPart.addNum();
        }
        System.out.println("这里是形成的视频片对象" + testVideoPart);
        Modify testModify = new Modify(1, 1, 3, 2, 500, testVideoPart, testVideoPart);
        System.out.println("保存的返回结果" + modifyRepository.saveAndFlush(testModify));

    }

    @Test
    void savevideotest() {
        Video s = new Video();
        s.setVideoName("测试视频名称2");
        s.setVideoSize(32546512);
        s.setVideoDuration(3215642);
        s.setImageUrl("D://upload//image2");
        s.setUserId(2);
        s.setIsModify(1);
        s.setAddTime();
        System.out.println(s.getAddTime());
        System.out.println(s);
        System.out.println(videoRepository.saveAndFlush(s));
        System.out.println(videoRepository.saveAndFlush(new Video("ceshi", 1123, 1235, "D://upload", "D://upload", 1)));
        System.out.println(videoRepository.findVideoByVideoIdOrderByAddTime(2));
    }

    @Test
    void saveModify() {
        Modify hhh = modifyRepository.findModifyByVideoId(14).get(0);
        System.out.println(hhh);
    }

    @Test
    void qiepianceshi() throws Exception{
        String a="D:/upload/originalVideos/14/bb.mp4";
        String b="D:/upload/videoResources/14/";
        uploadService.cuttingVideo(1,14,a,b);
       // VideoUtil.mp4ToCut1(a,b,"60");
    }

    @Test
    void updateTest(){
        //String a="D:/upload/originalVideos/11/bb.mp4";
       // String b="D:/upload/videoResources/11/";
        int videoId=13;
        int duration=434;
        String videoInputPath="D:/upload/originalVideos/12/bb.mp4";
        String videoOutputPath="D:/upload/videoResources/12/";
        System.out.println(videoRepository.updateVideo(videoId,duration,videoInputPath,videoOutputPath+"image.jpg"));
    }

    @Test
    void modifyTest(){
        int part_num=434/60+1;//7+1=8
        for(int i=0;i<part_num;i++){
            if(i<10){
                String n="0"+i;
                System.out.println(n);
            }
        }
    }

}
