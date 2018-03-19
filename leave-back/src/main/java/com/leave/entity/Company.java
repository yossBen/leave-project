package com.leave.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "COMPANY")
public class Company extends AbstractEntity {
    private static final long serialVersionUID = 1L;
    public static final String JOIN_ID = "company_id";

    private String name;
    private Set<User> employees;
    private Set<Project> projects;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(cascade = { CascadeType.PERSIST })
    @JoinTable(name = "COMPANY_EMPLOYE", joinColumns = @JoinColumn(name = Company.JOIN_ID), inverseJoinColumns = @JoinColumn(name = "employe_id"))
    public Set<User> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<User> employees) {
        this.employees = employees;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = Company.JOIN_ID)
    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

}