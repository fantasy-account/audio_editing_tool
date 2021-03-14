package whut_404notfound.audio_editing_tool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 请求参数有误异常
 *
 * @author <a href="mailto:873406454@qq.com">Li Hangfei</a>
 * @date 2021/3/14
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "IllegalRequestParamException")
public class IllegalRequestParamException extends IllegalArgumentException {
    public IllegalRequestParamException() {
    }
}
