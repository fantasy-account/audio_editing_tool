package whut_404notfound.audio_editing_tool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import whut_404notfound.audio_editing_tool.domain.BaseResponse;
import whut_404notfound.audio_editing_tool.domain.Statistics;
import whut_404notfound.audio_editing_tool.repository.ModifyRepository;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Xiaoyu Fan
 * @description 为管理员服务，查询统计信息
 * @create 2021-04-19 20:22
 */
@Component
public class EchartsService {

    @Autowired
    private ModifyRepository modifyRepository;
    public BaseResponse searchService(String startTime, String endTime){
        System.out.println("查询的开始时间为："+startTime);
        System.out.println("查询的结束时间为："+endTime);
        List<Map<String,Object>> dbList=modifyRepository.findStatisticsByTime(startTime,endTime);
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
        //已经获取了所有的信息
        for(int i=0;i<user_id.size();i++){
            System.out.println(sta.get(i));
        }
        List<String>teacherSelect=new ArrayList<>();//教师选择器
        List<Integer>pieData_value=new ArrayList<>();//每个教师修改的片段数量

        List<String>timeList=new ArrayList<>();//时间跨度，x轴
        List<Integer>uploadVideoNum=new ArrayList<>();//上传的视频数量
        List<Integer>modifiedVideoNum=new ArrayList<>();//更改的视频数量

        List<String>table_date=new ArrayList<>();
        List<Integer>table_videoId=new ArrayList<>();
        List<String>table_videoName=new ArrayList<>();
        List<Integer>table_userId=new ArrayList<>();
        List<String>table_userName=new ArrayList<>();
        List<Integer>table_partNum=new ArrayList<>();
        List<Integer>table_modifiedNum=new ArrayList<>();

        for(int i=0;i<sta.size();i++){

            table_date.add(sta.get(i).getAddTime().toString());
            table_videoId.add(sta.get(i).getVideoId());
            table_videoName.add(sta.get(i).getVideoName());
            table_userId.add(sta.get(i).getUserId());
            table_userName.add(sta.get(i).getUserName());
            table_partNum.add(sta.get(i).getPartNum());
            table_modifiedNum.add(sta.get(i).getModifiedPartNum());

            String teacherName=sta.get(i).getUserName();
            Integer modifiedNum=sta.get(i).getModifiedPartNum();
            if(teacherSelect.contains(teacherName)){//已经存在
                int index = teacherSelect.indexOf(teacherName);//获取下标
                pieData_value.set(index,pieData_value.get(index)+modifiedNum);//更新下标对应值
            }else{
                teacherSelect.add(teacherName);
                pieData_value.add(modifiedNum);
            }

            String localTime=sta.get(i).getAddTime().toString().substring(5,10);//获取当前时间
            if(timeList.contains(localTime)){//已经存在
                int index = timeList.indexOf(localTime);//获取下标
                uploadVideoNum.set(index,uploadVideoNum.get(index)+1);//该日期上传视频数+1
                if(sta.get(i).getModifiedPartNum()!=0){//该视频被修改过，修改表更新
                    modifiedVideoNum.set(index,modifiedVideoNum.get(index)+1);
                }
            }else{
                timeList.add(localTime);
                uploadVideoNum.add(1);//该日期上传视频数1
                if(sta.get(i).getModifiedPartNum()!=0){//该视频被修改过，修改表更新
                    modifiedVideoNum.add(1);
                }else{
                    modifiedVideoNum.add(0);
                }
            }
        }
//        System.out.println(teacherSelect);
//        System.out.println(pieData_value);
//
//        System.out.println(timeList);
//        System.out.println(uploadVideoNum);
//        System.out.println(modifiedVideoNum);
//
//        System.out.println(table_date);
//        System.out.println(table_videoId);
//        System.out.println(table_videoName);
//        System.out.println(table_userId);
//        System.out.println(table_userName);
//        System.out.println(table_partNum);
//        System.out.println(table_modifiedNum);

        Map<String,Object> dataMap=new HashMap<String,Object>();
        dataMap.put("xAxis",timeList);
        dataMap.put("videoNum",uploadVideoNum);
        dataMap.put("modifiedNum",modifiedVideoNum);
        dataMap.put("userName",teacherSelect);
        dataMap.put("valueOfPieData",pieData_value);

        dataMap.put("tableDate",table_date);
        dataMap.put("tableVideoId",table_videoId);
        dataMap.put("tableVideoName",table_videoName);
        dataMap.put("tableUserId",table_userId);
        dataMap.put("tableUserName",table_userName);
        dataMap.put("tablePartNum",table_partNum);
        dataMap.put("tableModifiedNum",table_modifiedNum);

        return new BaseResponse(HttpServletResponse.SC_OK, "查询审计信息成功",dataMap);
    }
}
