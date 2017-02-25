package com.tutoring.model;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.beans.factory.annotation.Configurable;

/**
 * Created by himanshu.agarwal on 20-02-2017.
 */

@Configurable
public class AuditChangeListener {

    @PrePersist
    public void onCreate(AuditableBaseEntity target) {
        Date now = new Date();
        target.setCreatedDate(now);
        target.setModifiedDate(now);
    }

    @PreUpdate
    public void onUpdate(AuditableBaseEntity target) {
        target.setModifiedDate(new Date());
    }

}
