package com.tutoring.model;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * Created by himanshu.agarwal on 20-02-2017.
 */
@MappedSuperclass
@EntityListeners(AuditChangeListener.class)
public abstract class AuditableBaseEntity extends PersistableBaseEntity {

    @Column(name = "CREATED_DATE")
    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;

    @CreatedBy
    @Column(name = "CREATED_BY", nullable = false)
    private String createdBy = "";

    @LastModifiedBy
    @Column(name = "MODIFIED_BY", nullable = false)
    private String modifiedBy = "";


    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
