package whut_404notfound.audio_editing_tool.domain;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * @author Xiaoyu Fan
 * @description 合并了modify和用户以及视频的名字，给前端用来统计信息
 * @create 2021-04-19 15:41
 */
public class Statistics {
    private Integer videoId;
    private String videoName;
    private Integer userId;
    private String userName;
    private Date addTime;
    private Integer partNum;
    private Integer modifiedPartNum;//被修改过的视频片段有多少
    private Integer modifiedDuration;//被修改过的总时长是多少
    //下面两个属性，在get或者set的时候会自动转对象类型为VideoPart
    private VideoPart inclusivePart;//视频片对象，记录每片起始和结束位置
    private VideoPart modifiedPart;//被修改过的视频片对象，记录修改的起始结束位置

    public Statistics(){}

    public Statistics(Object videoId, Object videoName, Object userId, Object userName, Object addTime,Object partNum, Object modifiedPartNum, Object modifiedDuration, Object inclusivePart, Object modifiedPart) {
        this.videoId = (Integer)videoId;
        this.videoName =(String) videoName;
        this.userId =(Integer) userId;
        this.userName =(String) userName;
        this.addTime=(Date)addTime;
        this.partNum = (Integer)partNum;
        this.modifiedPartNum = (Integer)modifiedPartNum;
        this.modifiedDuration = (Integer)modifiedDuration;
        this.inclusivePart = Modify.toObject((byte[])inclusivePart);
        this.modifiedPart =Modify.toObject((byte[])modifiedPart);
    }

    public Statistics(Integer videoId, String videoName, Integer userId, String userName, Date addTime,Integer partNum, Integer modifiedPartNum, Integer modifiedDuration, byte[] inclusivePart, byte[] modifiedPart) {
        this.videoId = videoId;
        this.videoName = videoName;
        this.userId = userId;
        this.userName = userName;
        this.addTime=addTime;
        this.partNum = partNum;
        this.modifiedPartNum = modifiedPartNum;
        this.modifiedDuration = modifiedDuration;
        this.inclusivePart = Modify.toObject(inclusivePart);
        this.modifiedPart = Modify.toObject(modifiedPart);
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getPartNum() {
        return partNum;
    }

    public void setPartNum(Integer partNum) {
        this.partNum = partNum;
    }

    public Integer getModifiedPartNum() {
        return modifiedPartNum;
    }

    public void setModifiedPartNum(Integer modifiedPartNum) {
        this.modifiedPartNum = modifiedPartNum;
    }

    public Integer getModifiedDuration() {
        return modifiedDuration;
    }

    public void setModifiedDuration(Integer modifiedDuration) {
        this.modifiedDuration = modifiedDuration;
    }

    public VideoPart getInclusivePart() {
        return inclusivePart;
    }

    public void setInclusivePart(VideoPart inclusivePart) {
        this.inclusivePart = inclusivePart;
    }

    public VideoPart getModifiedPart() {
        return modifiedPart;
    }

    public void setModifiedPart(VideoPart modifiedPart) {
        this.modifiedPart = modifiedPart;
    }

    /**
     * 对象转数组
     * @param obj
     * @return
     */
    public static byte[] toByteArray (Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray ();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }
    @Override
    public String toString() {
        return "Statistics{" +
                "videoId=" + videoId +
                ", videoName='" + videoName + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", addTime=" + addTime +
                ", partNum=" + partNum +
                ", modifiedPartNum=" + modifiedPartNum +
                ", modifiedDuration=" + modifiedDuration +
                ", inclusivePart=" + inclusivePart +
                ", modifiedPart=" + modifiedPart +
                '}';
    }
}
