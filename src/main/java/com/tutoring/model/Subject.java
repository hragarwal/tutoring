package com.tutoring.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by himanshu.agarwal on 20-02-2017.
 */

@Entity
@Table(name = "SUBJECT")
public class Subject extends PersistableBaseEntity {

    @Column(nullable = false)
    private String name;
    
    private boolean isActive = true;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "PARENT_SUBJECT_ID")
    private Set<SubjectName> subjectNames;

    public Set<SubjectName> getSubjectNames() {
        return subjectNames;
    }

    public void setSubjectNames(Set<SubjectName> subjectNames) {
        this.subjectNames = subjectNames;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
    
}
