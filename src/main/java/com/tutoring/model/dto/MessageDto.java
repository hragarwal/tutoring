package com.tutoring.model.dto;

import com.tutoring.model.Message;
import com.tutoring.util.AppConstants;

import java.io.Serializable;
import java.util.Date;


public class MessageDto implements Serializable {

    private String description;

    private String actualFileName;

    private String messageType;

    private String lessonUniqueId;

    private ProfileDto sender;

    private ProfileDto receiver;

    private boolean isRead;

    private Date createdDate;

    public MessageDto(Message message){
        this.description = message.getDescription();
        this.actualFileName = message.getActualFileName();
        this.isRead = message.isRead();
        this.messageType = message.getMessageType();
        this.lessonUniqueId = message.getLesson() != null ? message.getLesson().getLessonUniqueId(): AppConstants.BLANK;
        this.sender = message.getSenderProfile()!= null ? new ProfileDto(message.getSenderProfile()) : null;
        this.receiver = message.getReceiverProfile() != null ? new ProfileDto(message.getReceiverProfile()) : null;
        this.createdDate = message.getCreatedDate();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActualFileName() {
        return actualFileName;
    }

    public void setActualFileName(String actualFileName) {
        this.actualFileName = actualFileName;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getLessonUniqueId() {
        return lessonUniqueId;
    }

    public void setLessonUniqueId(String lessonUniqueId) {
        this.lessonUniqueId = lessonUniqueId;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public ProfileDto getSender() {
        return sender;
    }

    public void setSender(ProfileDto sender) {
        this.sender = sender;
    }

    public ProfileDto getReceiver() {
        return receiver;
    }

    public void setReceiver(ProfileDto receiver) {
        this.receiver = receiver;
    }
}
