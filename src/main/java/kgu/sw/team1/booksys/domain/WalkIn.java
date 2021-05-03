package kgu.sw.team1.booksys.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "walk_in")
public class WalkIn implements Booking{
    @Id
    @GeneratedValue
    @Column(name = "oid")
    private Integer oid;

    @Column(name = "covers")
    private Integer covers;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private LocalTime time;

    @OneToOne(mappedBy = "walk_in")
    private Tables table;

    public WalkIn() {
    }

    public WalkIn(Integer oid, Integer covers, LocalDate date, LocalTime time, Tables table) {
        this.oid = oid;
        this.covers = covers;
        this.date = date;
        this.time = time;
        this.table = table;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    @Override
    public LocalTime getArrivalTime() {
        return null;
    }

    @Override
    public Integer getCovers() {
        return covers;
    }

    @Override
    public void setCovers(Integer covers) {
        this.covers = covers;
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public LocalTime getEndTime() {
        LocalTime endTime = time;
        return endTime.plusHours(2);
    }

    @Override
    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public LocalTime getTime() {
        return time;
    }

    @Override
    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public Tables getTable() {
        return table;
    }

    @Override
    public void setArrivalTime(LocalTime t) {
    }

    @Override
    public void setTable(Tables table) {
        this.table = table;
    }
}
