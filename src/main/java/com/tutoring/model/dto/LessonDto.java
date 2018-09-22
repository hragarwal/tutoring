package com.tutoring.model.dto;

import com.tutoring.model.Files;
import com.tutoring.model.Lesson;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by NEX3JCE on 2/10/2018.
 */

public class LessonDto implements Serializable {

    private String lessonUniqueId;

    private SubjectDto subject;

    private String title;

    private LessonStatusDto status;

    private String taskDescription;

    private String specialRequirement;

    private Date createdDate;

    private Date deadline;

    private double dueAmount;

    private double estimatedWorkEffort;

    private ProfileDto student;

    private ProfileDto tutor;

    private String feedback;

    private Set<FileDto> fileList;

    private String answerDesc;

    private Date submittedDate;

    private boolean isLike;

    public LessonDto(Lesson lesson) {
        this.lessonUniqueId = lesson.getLessonUniqueId();
        this.subject = lesson.getSubject() != null ? new SubjectDto(lesson.getSubject()) : null;
        this.title = lesson.getTitle();
        this.taskDescription = lesson.getTaskDescription();
        this.specialRequirement = lesson.getSpecialRequirement();
        this.status = lesson.getStatus() !=null ? new LessonStatusDto(lesson.getStatus()) : null;
        this.deadline = lesson.getDeadline();
        this.dueAmount= lesson.getDueAmount();
        this.estimatedWorkEffort = lesson.getEstimatedWorkEffort();
        this.student = lesson.getStudentProfile() !=null ? new ProfileDto(lesson.getStudentProfile()) : null;
        this.tutor = lesson.getTutorProfile()!= null ? new ProfileDto(lesson.getTutorProfile()) : null;
        this.feedback = lesson.getFeedback();
        this.fileList = buildFileList(lesson.getFileList());
        this.createdDate = lesson.getCreatedDate();
        this.answerDesc = lesson.getLessonAnswerDesc();
        this.submittedDate = lesson.getSubmittedDate();
        this.isLike = lesson.isLike();
    }

    public Set<FileDto> buildFileList(Set<Files> files){
        Set<FileDto> fileDtoList = new LinkedHashSet<>();
        if(Objects.nonNull(files)) {
            files.forEach(file ->  {
                fileDtoList.add(new FileDto(file)); });
        }
        return fileDtoList;
    }

    public String getLessonUniqueId() {
        return lessonUniqueId;
    }

    public void setLessonUniqueId(String lessonUniqueId) {
        this.lessonUniqueId = lessonUniqueId;
    }

    public SubjectDto getSubject() {
        return subject;
    }

    public void setSubject(SubjectDto subject) {
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

    public ProfileDto getStudent() {
        return student;
    }

    public void setStudent(ProfileDto student) {
        this.student = student;
    }

    public ProfileDto getTutor() {
        return tutor;
    }

    public void setTutor(ProfileDto tutor) {
        this.tutor = tutor;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Set<FileDto> getFileList() {
        return fileList;
    }

    public void setFileList(Set<FileDto> fileList) {
        this.fileList = fileList;
    }

    public LessonStatusDto getStatus() {
        return status;
    }

    public void setStatus(LessonStatusDto status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getAnswerDesc() {
        return answerDesc;
    }

    public void setAnswerDesc(String answerDesc) {
        this.answerDesc = answerDesc;
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
