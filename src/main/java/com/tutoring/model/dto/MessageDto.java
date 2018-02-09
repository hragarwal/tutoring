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

    private long senderId;

    private String senderName;

    private long receiverId;

    private String receiverName;

    private boolean isRead;

    private Date createdDate;

    public MessageDto(Message message){
        this.description = message.getDescription();
        this.actualFileName = message.getActualFileName();
        this.isRead = message.isRead();
        this.messageType = message.getMessageType();
        this.lessonUniqueId = message.getLesson() != null ? message.getLesson().getLessonUniqueId(): AppConstants.BLANK;
        this.senderId = message.getSenderProfile()!= null ? message.getSenderProfile().getId() : 0;
        this.senderName = message.getSenderProfile()!= null ? message.getSenderProfile().getName() : AppConstants.BLANK;
        this.receiverId = message.getReceiverProfile() != null ? message.getReceiverProfile().getId() : 0;
        this.receiverName = message.getReceiverProfile()!= null ? message.getReceiverProfile().getName() : AppConstants.BLANK;
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

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(long receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
