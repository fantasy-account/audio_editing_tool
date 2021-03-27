package whut_404notfound.audio_editing_tool.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.*;
import java.util.Arrays;

/**
 * @author Xiaoyu Fan
 * @description 对应数据库中的视频修改记录表
 * @create 2021-03-09 16:38
 */
@Entity
@Data
public class Modify {
    @Id
    private Integer videoId;
    private Integer userId;
    private Integer partNum;
    private Integer modifiedPartNum;//被修改过的视频片段有多少
    private Integer modifiedDuration;//被修改过的总时长是多少
    //下面两个属性，在get或者set的时候会自动转对象类型为VideoPart
    private byte[] inclusivePart;//视频片对象，记录每片起始和结束位置
    private byte[] modifiedPart;//被修改过的视频片对象，记录修改的起始结束位置

    public Modify() {
    }

    public Modify(Integer videoId, Integer userId, Integer partNum, Integer modifiedPartNum, Integer modifiedDuration, byte[] inclusivePart, byte[] modifiedPart) {
        this.videoId = videoId;
        this.userId = userId;
        this.partNum = partNum;
        this.modifiedPartNum = modifiedPartNum;
        this.modifiedDuration = modifiedDuration;
        this.inclusivePart = inclusivePart;
        this.modifiedPart = modifiedPart;
    }

    public Modify(Integer videoId, Integer userId, Integer partNum, Integer modifiedPartNum, Integer modifiedDuration, VideoPart inclusivePart, VideoPart modifiedPart) throws IOException {
        this.videoId = videoId;
        this.userId = userId;
        this.partNum = partNum;
        this.modifiedPartNum = modifiedPartNum;
        this.modifiedDuration = modifiedDuration;
        this.inclusivePart = Modify.toByteArray(inclusivePart);
        this.modifiedPart = Modify.toByteArray(modifiedPart);
    }

    public Modify(Integer videoId, Integer userId, Integer partNum, Integer modifiedPartNum, Integer modifiedDuration, byte[] inclusivePart) {
        this.videoId = videoId;
        this.userId = userId;
        this.partNum = partNum;
        this.modifiedPartNum = modifiedPartNum;
        this.modifiedDuration = modifiedDuration;
        this.inclusivePart = inclusivePart;
        this.modifiedPart = null;
    }

    public Modify(Integer videoId, Integer userId, Integer partNum, Integer modifiedPartNum, Integer modifiedDuration, VideoPart inclusivePart) throws IOException {
        this.videoId = videoId;
        this.userId = userId;
        this.partNum = partNum;
        this.modifiedPartNum = modifiedPartNum;
        this.modifiedDuration = modifiedDuration;
        this.inclusivePart = Modify.toByteArray(inclusivePart);
        this.modifiedPart = null;
    }

    /**
     * 将对象抓换成二进制对象
     * Ps：对象必须实现Serializable接口,最好能给定一个serialVersionUID
     *
     * @param data 对象
     * @return 二进制对象bytes
     */
    public static byte[] toByteArray(VideoPart data) {
        try {
            ByteArrayOutputStream byt = new ByteArrayOutputStream();
            ObjectOutputStream obj = new ObjectOutputStream(byt);
            obj.writeObject(data);
            byte[] bytes = byt.toByteArray();
            //System.out.println("这里是序列化结果" + bytes);
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将对象抓换成二进制对象
     * Ps：对象必须实现Serializable接口,最好能给定一个serialVersionUID
     *
     * @param bytes
     * @return VideoPart 对象
     */
    public static VideoPart toObject(byte[] bytes) {
        try {
            ByteArrayInputStream byteInt = new ByteArrayInputStream(bytes);
            ObjectInputStream objInt = new ObjectInputStream(byteInt);
            VideoPart data;
            data = (VideoPart) objInt.readObject();
            //System.out.println("这里是反序列化转化的对象" + data);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
        return Modify.toObject(inclusivePart);
    }

    public void setInclusivePart(byte[] inclusivePart) {
        this.inclusivePart = inclusivePart;
    }

    public void setInclusivePart(VideoPart inclusivePart) {
        this.inclusivePart = Modify.toByteArray(inclusivePart);
    }

    public VideoPart getModifiedPart() {
        return Modify.toObject(modifiedPart);
    }

    public void setModifiedPart(byte[] modifiedPart) {
        this.modifiedPart = modifiedPart;
    }

    public void setModifiedPart(VideoPart modifiedPart) {
        this.modifiedPart = Modify.toByteArray(modifiedPart);
    }

    @Override
    public String toString() {
        return "Modify{" +
                "videoId=" + videoId +
                ", userId=" + userId +
                ", partNum=" + partNum +
                ", modifiedPartNum=" + modifiedPartNum +
                ", modifiedDuration=" + modifiedDuration +
                ", inclusivePart=" + Modify.toObject(inclusivePart) +
                ", modifiedPart=" + Modify.toObject(modifiedPart) +
                '}';
    }
}
