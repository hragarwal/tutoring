package com.tutoring.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by himanshu.agarwal on 20-02-2017.
 */
@Entity
@Table(name = "LESSON",uniqueConstraints ={ @UniqueConstraint(name= "uc_lesson", columnNames = {"lessonUniqueId"})})
public class Lesson extends AuditableBaseEntity {

    @Column(nullable = false, length = 36, unique = true)
    private String lessonUniqueId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SUBJECT_ID",nullable = false)
    private Subject subject;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Lob
    private String taskDescription;

    @Column(length = 2048)
    private String specialRequirement;

    @Column(nullable = false)
    private Date deadline;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PROFILE_ID")
    private Profile studentProfile;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "STATUS_ID", nullable = false)
    private LessonStatus status;

    private double dueAmount;

    private double estimatedWorkEffort;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TUTOR_ID")
    private Profile tutorProfile;

    private String feedback;

    private boolean isLike;

    @Lob
    private String lessonResponse;

    private boolean isActive = true;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "LESSON_ID")
    private Set<Files> fileList;

    @Lob
    private String lessonAnswerDesc;
    
    @Column(name = "SUBMITTED_DATE")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date submittedDate;
    
    private transient int subjectID;

    public String getLessonUniqueId() {
        return lessonUniqueId;
    }

    public void setLessonUniqueId(String lessonUniqueId) {
        this.lessonUniqueId = lessonUniqueId;
    }

    public Set<Files> getFileList() {
        return fileList;
    }

    public void setFileList(Set<Files> fileList) {
        this.fileList = fileList;
    }

    public String getLessonAnswerDesc() {
        return lessonAnswerDesc;
    }

    public void setLessonAnswerDesc(String lessonAnswerDesc) {
        this.lessonAnswerDesc = lessonAnswerDesc;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getSpecialRequirement() {
        return specialRequirement;
    }

    public void setSpecialRequirement(String specialRequirement) {
        this.specialRequirement = specialRequirement;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Profile getStudentProfile() {
        return studentProfile;
    }

    public void setStudentProfile(Profile studentProfile) {
        this.studentProfile = studentProfile;
    }

    public LessonStatus getStatus() {
        return status;
    }

    public void setStatus(LessonStatus status) {
        this.status = status;
    }

    public double getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(double dueAmount) {
        this.dueAmount = dueAmount;
    }

    public double getEstimatedWorkEffort() {
        return estimatedWorkEffort;
    }

    public void setEstimatedWorkEffort(double estimatedWorkEffort) {
        this.estimatedWorkEffort = estimatedWorkEffort;
    }

    public Profile getTutorProfile() {
        return tutorProfile;
    }

    public void setTutorProfile(Profile tutorProfile) {
        this.tutorProfile = tutorProfile;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getLessonResponse() {
        return lessonResponse;
    }

    public void setLessonResponse(String lessonResponse) {
        this.lessonResponse = lessonResponse;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

	public int getSubjectID() {
		return subjectID;
	}

	public void setSubjectID(int subjectID) {
		this.subjectID = subjectID;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }
}
