package com.sky.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class MyTask {

    @Scheduled(cron = "0 0 0/1 * * *")
    public void task() {
        log.info("定时任务：{}", new Date());
    }
}
