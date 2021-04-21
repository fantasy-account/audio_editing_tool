package whut_404notfound.audio_editing_tool;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import whut_404notfound.audio_editing_tool.domain.Modify;
import whut_404notfound.audio_editing_tool.domain.Statistics;
import whut_404notfound.audio_editing_tool.domain.Video;
import whut_404notfound.audio_editing_tool.domain.VideoPart;
import whut_404notfound.audio_editing_tool.repository.ModifyRepository;
import whut_404notfound.audio_editing_tool.repository.UserRepository;
import whut_404notfound.audio_editing_tool.repository.VideoRepository;
import whut_404notfound.audio_editing_tool.service.*;
import whut_404notfound.audio_editing_tool.util.Transform;
import whut_404notfound.audio_editing_tool.util.VideoUtil;

import javax.sql.DataSource;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static whut_404notfound.audio_editing_tool.constant.Constant.SRC_FILE_SAVE_ROOT_PATH;


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
    private FinishAllService finishAllService;

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
//        String f = "C:/Users/fch11/Desktop/chunks/b_0";//081.wav
        //VideoUtil.mp4ToCut1(c,"D:/upload/","10");
        //VideoUtil.changeFileToWav(e,f);
//        String sum = null;
//        String g = null;
//        String result = null;
//        Integer result1 = 0;
//        Integer result2 = 0;
//        for (int i = 0; i <= 133; i++) {
//            if (i <= 9) {
//                g = f + "00" + i + ".wav";
//            } else if (i <= 99) {
//                g = f + "0" + i + ".wav";
//            } else {
//                g = f + i + ".wav";
//            }
//            result = transform.Audio2Character(g, g);
//            result1 = result.indexOf(":[\"") + 3;
//            result2 = result.indexOf("\"],");
//            sum = sum + result.substring(result1, result2);
//        }
//        System.out.println(sum);
        //VideoUtil.mp4ToCut3("D:/upload/videoResources/29/07.mp4","D:/upload/videoResources",10);
        VideoUtil.changeFileToWav("D:/upload/videoResources/19/06.mp4","D:/upload/videoResources/06.wav");
        String result=transform.Audio2Character("D:/upload/videoResources/06.wav");
//        Integer result1=result.indexOf(":[\"")+3;
//        Integer result2=result.indexOf("\"],");
        System.out.println(result);
//        System.out.println(result1);
//        System.out.println(result2);
//        System.out.println(result.substring(result1, result2));
        //transform.Character2Audio("武汉理工大学计算机科学与技术学院，计算机1802班",a);

    }

    @Test
    void EditService() throws Exception{
        int videoId=119;
        List<Modify> modifyList=modifyRepository.findModifyByVideoId(videoId);
        if(!modifyList.isEmpty()) {
            Modify modify = modifyList.get(0);
            System.out.println("从数据库中获取到的原始信息是" + modify);

            String[] videoPartUrl = modify.getInclusivePart().getVideoPartUrl();
            String[] videoPartUrl1 = modify.getModifiedPart().getVideoPartUrl();

            HashMap<Integer,String> listhash=new HashMap<>();
            List<Integer> listmap=new ArrayList<>();
            for(int i=0;i<modify.getModifiedPartNum();i++){
                int a=videoPartUrl1[i].lastIndexOf('/');
//                System.out.println(a);
                int b=videoPartUrl1[i].indexOf("merged.mp4");
//                System.out.println(b);
                String s=videoPartUrl1[i].substring(a+1,b);
                int num=Integer.parseInt(s);
//                System.out.println(num);
                listhash.put(num,videoPartUrl1[i]);
                listmap.add(num);
            }

            String[] allUrl = new String[modify.getPartNum()];
            int j=0;
            for(int i=0;i<modify.getPartNum();i++){
                if(listhash.containsKey(i)){
                    allUrl[i]=listhash.get(i);
                }
//                if(listmap.contains(i)){
//                    allUrl[i]=videoPartUrl1[j++];
//                }
                else{
                    allUrl[i]=videoPartUrl[i];
                }
            }

            for(int i=0;i<modify.getPartNum();i++){
                System.out.println(allUrl[i]);
            }

            String finalVideoUrl=SRC_FILE_SAVE_ROOT_PATH +videoId+"/finalVideo.mp4";
//            System.out.println(finalVideoUrl);
//            VideoUtil.mp4ToUnit2(allUrl,finalVideoUrl);
            //执行合并
            try {
//                VideoUtil.changeToTs2(allUrl);
//                if (VideoUtil.mp4ToUnit2(allUrl,finalVideoUrl)) {
//                    System.out.println(finalVideoUrl);
//                    System.out.println("总视频合成完毕，链接是"+finalVideoUrl);
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
        //editService.text2speech("将这段话转化成语音，替换掉原来视频中的语音",29,5);
        //以上测试成功，文件和数据库都完成了更新
        //uploadMp3Service.mergeMp3ToMp4(29,6,"D:/upload/videoResources/29/00.wav");

    @Autowired
    private EchartsService echartsService;
    @Test
    void FinishAllTest(){
        System.out.println(finishAllService.finish(20));
    }

    @Test
    void searchTest(){
//        Date time1 = new Date(2020,3,20,0,0,0);
//        Date time2 = new Date(2022,4,20,0,0,0);
//        System.out.println(time1);
//        System.out.println(time2);
        List<Map<String,Object>>dbList=modifyRepository.findAllStatistics();
        List<Object> video_id = dbList.stream().map(map -> map.get("video_id")).collect(Collectors.toList());
        List<Object> video_name = dbList.stream().map(map -> map.get("video_name")).collect(Collectors.toList());
        List<Object> user_id = dbList.stream().map(map -> map.get("user_id")).collect(Collectors.toList());
        List<Object> user_name = dbList.stream().map(map -> map.get("user_name")).collect(Collectors.toList());
        List<Object> add_time = dbList.stream().map(map -> map.get("add_time")).collect(Collectors.toList());
        List<Object> part_num = dbList.stream().map(map -> map.get("part_num")).collect(Collectors.toList());
        List<Object> modified_part_num = dbList.stream().map(map -> map.get("modified_part_num")).collect(Collectors.toList());
        List<Object> modified_duration = dbList.stream().map(map -> map.get("modified_duration")).collect(Collectors.toList());
        List<Object> inclusive_part = dbList.stream().map(map -> map.get("inclusive_part")).collect(Collectors.toList());
        List<Object> modified_part = dbList.stream().map(map -> map.get("modified_part")).collect(Collectors.toList());


//        Statistics sta1=new Statistics(video_id.get(1),video_name.get(1),user_id.get(1),user_name.get(1),part_num.get(1),modified_part_num.get(1),modified_duration.get(1),inclusive_part.get(1),modified_part.get(1));
//        System.out.println(sta1);
        List<Statistics> sta = new ArrayList<>();
        for(int i=0;i<user_id.size();i++){
            sta.add(new Statistics(video_id.get(i),video_name.get(i),user_id.get(i),user_name.get(i),add_time.get(i),part_num.get(i),modified_part_num.get(i),modified_duration.get(i),inclusive_part.get(i),modified_part.get(i)));
        }
        for(int i=0;i<sta.size();i++){
            System.out.println(sta.get(i));
        }
//        System.out.println(dbList);
        // 键值集合
//        Set keySet=dbList.get(0).keySet();
        //[video_name, part_num, inclusive_part, user_id,user_name,
        // modified_part, modified_part_num, video_id, modified_duration]

        //找出所有老师姓名
//        List<String> userNameList=dbList.stream().map(map-> (String) map.get("user_name")).collect(Collectors.toList());

//        //获得每个老师修改过的视频总和
//        userNameList.stream().map(userName->{
//            dbList.stream().filter(map->map.get("user_name").equals(userName));
//        });
    }

    @Test
    void searchTestByTime(){

        List<Map<String,Object>>dbList=modifyRepository.findStatisticsByTime("2020-3-1","2022-4-1");
        List<Object> video_id = dbList.stream().map(map -> map.get("video_id")).collect(Collectors.toList());
        List<Object> video_name = dbList.stream().map(map -> map.get("video_name")).collect(Collectors.toList());
        List<Object> user_id = dbList.stream().map(map -> map.get("user_id")).collect(Collectors.toList());
        List<Object> user_name = dbList.stream().map(map -> map.get("user_name")).collect(Collectors.toList());
        List<Object> add_time = dbList.stream().map(map -> map.get("add_time")).collect(Collectors.toList());
        List<Object> part_num = dbList.stream().map(map -> map.get("part_num")).collect(Collectors.toList());
        List<Object> modified_part_num = dbList.stream().map(map -> map.get("modified_part_num")).collect(Collectors.toList());
        List<Object> modified_duration = dbList.stream().map(map -> map.get("modified_duration")).collect(Collectors.toList());
        List<Object> inclusive_part = dbList.stream().map(map -> map.get("inclusive_part")).collect(Collectors.toList());
        List<Object> modified_part = dbList.stream().map(map -> map.get("modified_part")).collect(Collectors.toList());

        List<Statistics> sta = new ArrayList<>();
        for(int i=0;i<user_id.size();i++){
            sta.add(new Statistics(video_id.get(i),video_name.get(i),user_id.get(i),user_name.get(i),add_time.get(i),part_num.get(i),modified_part_num.get(i),modified_duration.get(i),inclusive_part.get(i),modified_part.get(i)));
        }
        for(int i=0;i<sta.size();i++){
            System.out.println(sta.get(i).getAddTime().toString());
            System.out.println(sta.get(i).getAddTime().toString().substring(5,10));
        }

    }

    @Test
    void freshDateBase(){
//        for(int i=1;i<=100;i++){
//            Modify modify = modifyRepository.findModifyByVideoId(i).get(0);
//            VideoPart videoPart=new VideoPart(modify.getPartNum());//空间申请完毕
//
//            for(int j=0;j<modify.getPartNum();j++){
//                videoPart.setPartNum(modify.getPartNum());
//                Time a=new Time(j);
//                videoPart.setStartTime(a);
//                Time b=new Time(j+1);
//                videoPart.setEndTime(b);
//                videoPart.setImageUrl("视频id为"+i+"的，第"+j+"个视频片段的封面图片");
//                videoPart.setVideoPartUrl("视频id为"+i+"的，第"+j+"个视频片段的保存路径");
//                videoPart.setContent("视频id为"+i+"的，第"+j+"个视频片段的文本信息");
//                videoPart.addNum();
//            }
//            modifyRepository.videoHasBeenModified1(i,videoPart);
//            if(modify.getModifiedPartNum()==0){
//                continue;
//            }
//
//            VideoPart modifiedVideoPart=new VideoPart(modify.getPartNum());//空间申请完毕
//            for(int j=0;j<modify.getModifiedPartNum();j++){
//                modifiedVideoPart.setPartNum(modify.getPartNum());
//                Time a=new Time(j);
//                modifiedVideoPart.setStartTime(a);
//                Time b=new Time(j+1);
//                modifiedVideoPart.setEndTime(b);
//                modifiedVideoPart.setImageUrl("视频id为"+i+"的，第"+j+"个被修改的视频片段的封面图片");
//                modifiedVideoPart.setVideoPartUrl("视频id为"+i+"的，第"+j+"个被修改的视频片段的保存路径");
//                modifiedVideoPart.setContent("视频id为"+i+"的，第"+j+"个被修改的视频片段的文本信息");
//                modifiedVideoPart.addNum();
//            }
//            modifyRepository.videoHasBeenModified2(i,modifiedVideoPart);
//
//        }
        System.out.println(modifyRepository.findModifyByVideoId(20).get(0));
        System.out.println(modifyRepository.findModifyByVideoId(28).get(0));
    }

    @Test
    void echartsTest(){
        echartsService.searchService("2021-4-1","2021-4-20");
    }

}
