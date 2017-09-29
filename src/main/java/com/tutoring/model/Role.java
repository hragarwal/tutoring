package com.tutoring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by himanshu.agarwal on 20-02-2017.
 */
@Entity
@Table(name = "ROLE")
public class Role extends PersistableBaseEntity {

    @Column(nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
