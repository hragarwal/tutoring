package com.tutoring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by himanshu.agarwal on 20-02-2017.
 */

@Entity
@Table(name = "MESSAGE")
public class Message extends AuditableBaseEntity {

    @Column(nullable = false)
    @Lob
    private String description;

    private String actualFileName;

    @Column(nullable = false)
    private String messageType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LESSON_ID", nullable = false)
    private Lesson lesson;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SENDER_PROFILE_ID", nullable = false)
    private Profile senderProfile;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RECEIVER_PROFILE_ID")
    private Profile receiverProfile;
    
    private boolean isRead;
    
    private transient long currentProfile;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Profile getSenderProfile() {
        return senderProfile;
    }

    public void setSenderProfile(Profile senderProfile) {
        this.senderProfile = senderProfile;
    }

    public Profile getReceiverProfile() {
        return receiverProfile;
    }

    public void setReceiverProfile(Profile receiverProfile) {
        this.receiverProfile = receiverProfile;
    }

	public long getCurrentProfile() {
		return currentProfile;
	}

	public void setCurrentProfile(long currentProfile) {
		this.currentProfile = currentProfile;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public String getActualFileName() {
        return actualFileName;
    }

    public void setActualFileName(String actualFileName) {
        this.actualFileName = actualFileName;
    }
}
