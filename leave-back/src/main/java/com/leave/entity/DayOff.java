package com.leave.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

import static com.leave.entity.DayOff.*;

@Entity
@Table(name = "DAY_OFF", uniqueConstraints = @UniqueConstraint(columnNames = { "date", Agenda.JOIN_ID }))
@NamedQueries(value = { @NamedQuery(name = QUERY_FIND_BETWEEN_TWO_DATE, query = "FROM DayOff as dayOff WHERE dayOff.agenda.user.id = :userId and dayOff.date BETWEEN :beginDate and :endDate"),
                @NamedQuery(name = QUERY_FIND_BT_DATE, query = "FROM DayOff as dayOff WHERE dayOff.agenda.user.id = :userId and dayOff.date = :beginDate"),
                @NamedQuery(name = QUERY_DELETE_BT_DATE, query = "DELETE DayOff day WHERE day.agenda.user.id = :userId and day.date = :beginDate") })
public class DayOff extends AbstractEntity {
    public static final String QUERY_FIND_BETWEEN_TWO_DATE = "QUERY_FIND_BETWEEN_TWO_DATE";
    public static final String QUERY_FIND_BT_DATE = "QUERY_FIND_BT_DATE";
    public static final String QUERY_DELETE_BT_DATE = "QUERY_DELETE_BT_DATE";
    private static final long serialVersionUID = 1L;
    private Date date;
    private Agenda agenda;

    public DayOff() {
    }

    public DayOff(Date date) {
        this.date = date;
    }

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Europe/Paris")
    private Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Agenda.JOIN_ID)
    @JsonIgnore
    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }
}