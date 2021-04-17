package whut_404notfound.audio_editing_tool.domain;

import java.io.Serializable;
import java.sql.Time;
import java.util.Arrays;

/**
 * @author Xiaoyu Fan
 * @description 这个类里面的对象是用来记录时间片信息的, 该对象既可以保存
 * 视频全部的时间片信息，也可以保存被修改后的时间片信息
 * @create 2021-03-09 16:43
 */
public class VideoPart implements Serializable {
    private static final long serialVersionUID = 4359709211352400087L;
    private Integer partNum;//该对象总共有多少个时间片
    private Integer num = 0;//该对象目前操作的时间片
    private Time[] startTime;//数组保存所有的起始时间片信息
    private Time[] endTime;//数组保存所有的结束时间片信息
    private String[] content;//数组保存某时间片内的文字信息,注：修改后的时间片对象，保存的也是修改后的文字
    private String[] videoPartUrl;//视频片路径
    private String[] imageUrl;//视频片封面路径

    public VideoPart(Integer partNum) {
        Time[] startTime = new Time[partNum];
        Time[] endTime = new Time[partNum];
        String[] content = new String[partNum];
        String[] videoPartUrl = new String[partNum];
        String[] imageUrl = new String[partNum];
        this.partNum = partNum;
        this.startTime = startTime;
        this.endTime = endTime;
        this.content = content;
        this.videoPartUrl=videoPartUrl;
        this.imageUrl = imageUrl;
    }

    public Integer getPartNum() {
        return partNum;
    }

    public void setPartNum(Integer partNum) {
        this.partNum = partNum;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Time[] getStartTime() {
        return startTime;
    }

    public void setStartTime(Time time) {
        this.startTime[this.num] = time;
    }

    public Time[] getEndTime() {
        return endTime;
    }

    public void setEndTime(Time time) {
        this.endTime[this.num] = time;
    }

    public String[] getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content[num] = content;
    }

    public void addNum() {
        this.num++;
    }

    public String[] getVideoPartUrl() { return videoPartUrl; }

    public void setVideoPartUrl(String videoPartUrl) { this.videoPartUrl[num] = videoPartUrl; }

    public String[] getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) { this.imageUrl[num] = imageUrl; }

    //下面五个setter定点放置modified对象
    public void setStartTime(Integer index,Time startTime) {
        this.startTime[index] = startTime;
    }

    public void setEndTime(Integer index,Time endTime) {
        this.endTime[index] = endTime;
    }

    public void setContent(Integer index,String content) {
        this.content[index] = content;
    }

    public void setVideoPartUrl(Integer index,String videoPartUrl) {
        this.videoPartUrl[index] = videoPartUrl;
    }

    public void setImageUrl(Integer index,String imageUrl) {
        this.imageUrl[index] = imageUrl;
    }

    @Override
    public String toString() {
        return "VideoPart{" +
                "partNum=" + partNum +
                ", num=" + num +
                ", startTime=" + Arrays.toString(startTime) +
                ", endTime=" + Arrays.toString(endTime) +
                ", content=" + Arrays.toString(content) +
                ", videoPartUrl=" + Arrays.toString(videoPartUrl) +
                ", imageUrl=" + Arrays.toString(imageUrl) +
                '}';
    }
}
