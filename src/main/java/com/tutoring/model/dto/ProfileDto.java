package com.tutoring.model.dto;

import com.tutoring.model.Profile;

import java.io.Serializable;

/**
 * Created by NEX3JCE on 2/10/2018.
 */
public class ProfileDto implements Serializable {

    private long id;

    private String name;

    private String username;

    private long roleId;

    public ProfileDto(Profile profile) {
        this.id = profile.getId();
        this.name = profile.getName();
    }

    public ProfileDto(Profile profile, boolean detail) {
        this.id = profile.getId();
        this.name = profile.getName();
        this.roleId = profile.getRole().getId();
        this.username = profile.getUsername();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
