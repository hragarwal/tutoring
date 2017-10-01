package com.tutoring.model;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.beans.factory.annotation.Configurable;

import com.tutoring.util.DateTimeUtil;

/**
 * Created by himanshu.agarwal on 20-02-2017.
 */

@Configurable
public class AuditChangeListener {

    @PrePersist
    public void onCreate(AuditableBaseEntity target) {
        Date now = DateTimeUtil.getCurrentDate();
        target.setCreatedDate(now);
        target.setModifiedDate(now);
    }

    @PreUpdate
    public void onUpdate(AuditableBaseEntity target) {
        target.setModifiedDate(DateTimeUtil.getCurrentDate());
    }

}
