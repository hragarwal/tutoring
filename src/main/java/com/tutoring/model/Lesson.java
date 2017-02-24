package com.tutoring.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;

/**
 * Created by himanshu.agarwal on 20-02-2017.
 */

@Entity
@Table(name = "LESSON")
public class Lesson extends AuditableBaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SUBJECT_ID",nullable = false)
    private Subject subject;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String taskDescription;

    private String specialRequirement;

    @Column(nullable = false)
    private Date deadline;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PROFILE_ID")
    private Profile studentProfile;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "STATUS_ID", nullable = false)
    private LessonStatus status;

    private Double dueAmount;

    private Double estimatedWorkEffort;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TUTOR_ID", nullable = false)
    private Profile tutorProfile;

    private String feedback;

    private String lessonResponse;

    private boolean isActive = true;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "LESSON_ID")
    private Set<Files> questionFileList;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "LESSON_ID")
    private Set<LessonAnswer> answerFileList;

    public Set<Files> getQuestionFileList() {
        return questionFileList;
    }

    public void setQuestionFileList(Set<Files> questionFileList) {
        this.questionFileList = questionFileList;
    }

    public Set<LessonAnswer> getAnswerFileList() {
        return answerFileList;
    }

    public void setAnswerFileList(Set<LessonAnswer> answerFileList) {
        this.answerFileList = answerFileList;
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

    public Double getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(Double dueAmount) {
        this.dueAmount = dueAmount;
    }

    public Double getEstimatedWorkEffort() {
        return estimatedWorkEffort;
    }

    public void setEstimatedWorkEffort(Double estimatedWorkEffort) {
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
}
