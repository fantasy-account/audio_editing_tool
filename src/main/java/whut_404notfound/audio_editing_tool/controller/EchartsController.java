package whut_404notfound.audio_editing_tool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import whut_404notfound.audio_editing_tool.domain.BaseResponse;
import whut_404notfound.audio_editing_tool.service.EchartsService;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author Xiaoyu Fan
 * @description 接收管理员发来的时间信息
 * @create 2021-04-19 20:07
 */
@Controller
public class EchartsController {

    @Autowired
    private EchartsService echartsService;
    @GetMapping("/echarts")
    @ResponseBody
    public BaseResponse sendStatistics(@RequestParam String startTime, @RequestParam String endTime){
        return echartsService.searchService(startTime.substring(0, 10),endTime.substring(0, 10));
    }
}
