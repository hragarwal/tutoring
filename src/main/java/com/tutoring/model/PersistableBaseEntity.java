package com.tutoring.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Created by himanshu.agarwal on 20-02-2017.
 */
@MappedSuperclass
public abstract class PersistableBaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", unique = true, nullable = false)
    protected long id;

    public long getId() {
        return id;
    }

}
