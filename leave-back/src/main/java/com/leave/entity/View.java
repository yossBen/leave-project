package com.leave.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "VIEW")
public class View extends AbstractEntity {
    private static final long serialVersionUID = 1L;
    public static final String JOIN_ID = "view_id";

    private String name;
    private Date date;

    private User creator;
    private Set<Agenda> agendas;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "create_date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @ManyToMany(cascade = { CascadeType.PERSIST })
    @JoinTable(name = "VIEW_AGENDA", joinColumns = @JoinColumn(name = View.JOIN_ID), inverseJoinColumns = @JoinColumn(name = Agenda.JOIN_ID))
    public Set<Agenda> getAgendas() {
        return agendas;
    }

    public void setAgendas(Set<Agenda> agendas) {
        this.agendas = agendas;
    }
}