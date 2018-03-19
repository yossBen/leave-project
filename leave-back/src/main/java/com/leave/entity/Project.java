package com.leave.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "PROJECT")
public class Project extends AbstractEntity {
    private static final long serialVersionUID = 1L;
    public static final String JOIN_ID = "project_id";

    private String name;
    private Set<View> views;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = JOIN_ID)
    public Set<View> getViews() {
        return views;
    }

    public void setViews(Set<View> views) {
        this.views = views;
    }
}