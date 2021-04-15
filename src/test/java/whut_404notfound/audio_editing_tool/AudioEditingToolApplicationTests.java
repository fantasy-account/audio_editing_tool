package whut_404notfound.audio_editing_tool;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import whut_404notfound.audio_editing_tool.domain.Modify;
import whut_404notfound.audio_editing_tool.domain.NonStaticResourceHttpRequestHandler;
import whut_404notfound.audio_editing_tool.domain.Video;
import whut_404notfound.audio_editing_tool.domain.VideoPart;
import whut_404notfound.audio_editing_tool.repository.ModifyRepository;
import whut_404notfound.audio_editing_tool.repository.UserRepository;
import whut_404notfound.audio_editing_tool.repository.VideoRepository;
import whut_404notfound.audio_editing_tool.service.EditService;
import whut_404notfound.audio_editing_tool.service.UploadMp3Service;
import whut_404notfound.audio_editing_tool.service.UploadService;
import whut_404notfound.audio_editing_tool.util.Transform;
import whut_404notfound.audio_editing_tool.util.VideoUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.sql.Time;
import java.util.*;

import static whut_404notfound.audio_editing_tool.constant.Constant.SESSION_KEY_VIDEO;


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
    private Transform transform;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private EditService editService;

    @Autowired
    private UploadMp3Service uploadMp3Service;

    @Autowired
    private NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;

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
    void qiepianceshi() throws Exception {
        String a = "D:/upload/originalVideos/14/bb.mp4";
        String b = "D:/upload/videoResources/14/";
        uploadService.cuttingVideo(1, 14, a, b);
        // VideoUtil.mp4ToCut1(a,b,"60");
    }

    @Test
    void updateTest() {
        //String a="D:/upload/originalVideos/11/bb.mp4";
        // String b="D:/upload/videoResources/11/";
        int videoId = 13;
        int duration = 434;
        String videoInputPath = "D:/upload/originalVideos/12/bb.mp4";
        String videoOutputPath = "D:/upload/videoResources/12/";
        System.out.println(videoRepository.updateVideo(videoId, duration, videoInputPath, videoOutputPath + "image.jpg"));
    }

    @Test
    void modifyTest() {
        int part_num = 434 / 60 + 1;//7+1=8
        for (int i = 0; i < part_num; i++) {
            if (i < 10) {
                String n = "0" + i;
                System.out.println(n);
            }
        }
    }

    @Test
    void APITest() throws Exception {
        //String a="C:/Users/fch11/Desktop/MobileFile/001.wav";
//        String b="C:/Users/fch11/Desktop/外包/bb.mp4";
//        String c="D:/upload/videoResources/09.mp4";
//        String d="D:/upload/videoResources/09.wav";
//        String e="D:/upload/01.mp4";
        String f = "C:/Users/fch11/Desktop/chunks/b_0";//081.wav
        //VideoUtil.mp4ToCut1(c,"D:/upload/","10");
        //VideoUtil.changeFileToWav(e,f);
        String sum = null;
        String g = null;
        String result = null;
        Integer result1 = 0;
        Integer result2 = 0;
        for (int i = 0; i <= 133; i++) {
            if (i <= 9) {
                g = f + "00" + i + ".wav";
            } else if (i <= 99) {
                g = f + "0" + i + ".wav";
            } else {
                g = f + i + ".wav";
            }
            result = transform.Audio2Character(g, g);
            result1 = result.indexOf(":[\"") + 3;
            result2 = result.indexOf("\"],");
            sum = sum + result.substring(result1, result2);
        }
        System.out.println(sum);
//        String result=transform.Audio2Character(f, f);
//        Integer result1=result.indexOf(":[\"")+3;
//        Integer result2=result.indexOf("\"],");
//        System.out.println(result);
//        System.out.println(result1);
//        System.out.println(result2);
//        System.out.println(result.substring(result1, result2));
        //transform.Character2Audio("武汉理工大学计算机科学与技术学院，计算机1802班",a);

    }

    @Test
    void EditService() throws Exception{
        //editService.text2speech("将这段话转化成语音，替换掉原来视频中的语音",29,5);
        //以上测试成功，文件和数据库都完成了更新
        uploadMp3Service.mergeMp3ToMp4(29,6,"D:/upload/videoResources/29/00.wav");
        //////////////////////////////////////////////////////////////////////////////////
//        FileInputStream fis = null;
//        InputStreamReader isr = null;
//        BufferedReader br = null;
//        String str = "";
//        //String str1 = "";
//        String[] str2 = new String[14];
//        fis = new FileInputStream("D:/IntelliJ IDEA 2019.1/workspace/audio_editing_tool/src/input.txt");// FileInputStream
//        // 从文件系统中的某个文件中获取字节
//        isr = new InputStreamReader(fis);// InputStreamReader 是字节流通向字符流的桥梁,
//        br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new InputStreamReader的对象
//        for(int i=0;i<14;i++){
//            if((str = br.readLine()) != null){
//                str2[i]=str;
//            }
//        }for(int i=0;i<14;i++){
//            System.out.println(str2[i]);
//        }
//
//        br.close();
//        isr.close();
//        fis.close();

        //////////////////////////////////////////////////
    }
}
