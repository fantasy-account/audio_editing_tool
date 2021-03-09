package whut_404notfound.audio_editing_tool.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author Xiaoyu Fan
 * @description 对应数据库中的video表，记录用户上传的视频信息
 * @create 2021-03-09 16:28
 */

@Entity
@Data
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer videoId;
    private String videoName;
    private String videoSize;
    private Date addTime;
    private Integer videoDuration;
    private String imageUrl;
    private Integer userId;
    private Integer isModify;

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

    public String getVideoSize() {
        return videoSize;
    }

    public void setVideoSize(String videoSize) {
        this.videoSize = videoSize;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime() {
        Date addTime=new Date();
        this.addTime = addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(Integer videoDuration) {
        this.videoDuration = videoDuration;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIsModify() {
        return isModify;
    }

    public void setIsModify(Integer isModify) {
        this.isModify = isModify;
    }
}
