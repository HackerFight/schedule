package com.hacker.schedule.handler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author Hacker
 * @date：2018/12/21
 * @project schedule
 * @describe
 */
@JobHandler(value = "hackerJobHandler")
@Component
public class HackerJobHandler extends IJobHandler{

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        XxlJobLogger.log("日志记录参数: " + param);
        XxlJobLogger.log("日志记录时间: " + LocalDateTime.now());
        return ReturnT.SUCCESS;
    }
}
