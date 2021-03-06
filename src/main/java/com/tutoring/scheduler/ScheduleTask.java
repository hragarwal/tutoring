package com.tutoring.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tutoring.exception.AppException;
import com.tutoring.service.LessonService;
import com.tutoring.util.DateTimeUtil;

/**
 * Created by himanshu.agarwal on 30-09-2017.
 */
@Component
public class ScheduleTask {

    @Autowired
    private LessonService lessonService;

    @Scheduled(fixedDelayString = "${scheduler.fixed.delay.millis}")
    public void updateExpiredLessons() throws AppException {
        lessonService.updateExpiredLessons(DateTimeUtil.getCurrentDate());
    }

}
