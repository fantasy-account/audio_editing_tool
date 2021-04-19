package whut_404notfound.audio_editing_tool.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Xiaoyu Fan
 * @description 对应数据库中的video表，记录用户上传的视频信息
 * @create 2021-03-09 16:28
 */

@Entity
@Data
public class Video implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer videoId;
    private String videoName;
    private long videoSize;
    private Date addTime;
    private Integer videoDuration;
    private String videoUrl;
    private String imageUrl;
    private Integer userId;
    private Integer isModify;

    //默认构造函数不能删掉，不然数据库查询会报错
    public Video() {

    }

    public Video(String videoName, long videoSize, Integer videoDuration, String videoUrl, String imageUrl, Integer userId) {
        this.videoName = videoName;
        this.videoSize = videoSize;
        Date addTime = new Date();
        this.addTime = addTime;
        this.videoDuration = videoDuration;
        this.videoUrl = videoUrl;
        this.imageUrl = imageUrl;
        this.userId = userId;
        this.isModify = 0;
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

    public long getVideoSize() {
        return videoSize;
    }

    public void setVideoSize(long videoSize) {
        this.videoSize = videoSize;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime() {
        Date addTime = new Date();
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

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
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
