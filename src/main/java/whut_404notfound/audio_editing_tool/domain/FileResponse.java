package whut_404notfound.audio_editing_tool.domain;

import java.io.File;

/**
 * @author Xiaoyu Fan
 * @description 文件返回格式
 * @create 2021-04-14 23:48
 */
public class FileResponse {
    /**
     * 状态码
     */
    private int code;

    /**
     *
     */
    private int videoPartId;

    /**
     * 响应消息
     */
    private String startTime;

    /**
     * 响应消息
     */
    private String endTime;

    /**
     * 响应消息
     */
    private String content;

    /**
     * 响应数据，可为空
     */
    private File image;

    /**
     * 响应数据，可为空
     */
    private File video;

    public FileResponse(int code, int videoPartId, String startTime, String endTime, String content, File image, File video) {
        this.code = code;
        this.videoPartId = videoPartId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.content = content;
        this.image = image;
        this.video = video;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getVideoPartId() {
        return videoPartId;
    }

    public void setVideoPartId(int videoPartId) {
        this.videoPartId = videoPartId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public File getVideo() {
        return video;
    }

    public void setVideo(File video) {
        this.video = video;
    }
}
