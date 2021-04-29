package kgu.sw.team1.booksys.domain;

import javax.persistence.*;

import java.sql.Time;
import java.time.LocalDateTime;

@Entity
@Table(name = "WalkIn")
public class WalkIn implements Booking{
    @Id
    @GeneratedValue
    @Column(name = "oid")
    private int oid;

    @Column(name = "covers")
    private int covers;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "time")
    private Time time;

    @OneToOne(mappedBy = "walkin")
    private Tables table;

    public WalkIn() {
    }

    public WalkIn(int oid, int covers, LocalDateTime date, Time time, Tables table) {
        this.oid = oid;
        this.covers = covers;
        this.date = date;
        this.time = time;
        this.table = table;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    @Override
    public Time getArrivalTime() {
        return null;
    }

    public int getCovers() {
        return covers;
    }

    public void setCovers(int covers) {
        this.covers = covers;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public Time getEndTime() {
        Time endTime = new Time(time.getTime() + (2 * 60 * 60 * 1000));
        return endTime;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Tables getTable() {
        return table;
    }

    @Override
    public int getTableNumber() {
        return table.getNumber();
    }

    @Override
    public void setArrivalTime(Time t) {
    }

    public void setTable(Tables table) {
        this.table = table;
    }
}
