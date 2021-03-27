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
        Modify testModify = new Modify(3, 1, 3, 2, 500, testVideoPart, testVideoPart);
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
        Modify hhh = modifyRepository.findModifyByVideoId(3).get(0);
        System.out.println(hhh);
    }
}
