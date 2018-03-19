package com.leave.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "AGENDA")
public class Agenda extends AbstractEntity {
    private static final long serialVersionUID = 1L;
    public static final String JOIN_ID = "agenda_id";
    private User user;
    private Set<DayOff> daysOff;

    public Agenda() {
        this.daysOff = new HashSet<>();
    }

    @OneToOne(mappedBy = "agenda")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agenda", orphanRemoval = true)
    public Set<DayOff> getDaysOff() {
        return daysOff;
    }

    public void setDaysOff(Set<DayOff> daysOff) {
        this.daysOff = daysOff;
    }
}