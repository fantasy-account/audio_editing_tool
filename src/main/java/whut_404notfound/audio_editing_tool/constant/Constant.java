package whut_404notfound.audio_editing_tool.constant;

import java.util.Arrays;
import java.util.List;

/**
 * 全局公共常量类
 *
 * @author <a href="mailto:873406454@qq.com">Li Hangfei</a>
 * @date 2021/3/14
 */
public class Constant {
    public static final String SESSION_KEY_USER = "session_user_id";
    public static final String SESSION_KEY_VIDEO = "session_video_id";
    public static final String UPLOAD_FILE_SAVE_ROOT_PATH = "D:/upload/originalVideos/";
    public static final String SRC_FILE_SAVE_ROOT_PATH = "D:/upload/videoResources/";
    public static final List<String > RECEIVE_FILE_SUFFIXS= Arrays.asList(".jpg",".mp4");
    public static final String JWT_SECRET="whut404notfound_audio_editing_tool";

    public static final Integer PART_DURATION=10;
    private Constant() {
    }
}
