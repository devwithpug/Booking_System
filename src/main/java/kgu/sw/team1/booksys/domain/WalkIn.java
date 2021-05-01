package kgu.sw.team1.booksys.domain;

import javax.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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
    private LocalDateTime date;

    @Column(name = "time")
    private Timestamp time;

    @OneToOne(mappedBy = "walk_in")
    private Tables table;

    public WalkIn() {
    }

    public WalkIn(Integer oid, Integer covers, LocalDateTime date, Timestamp time, Tables table) {
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
    public Timestamp getArrivalTime() {
        return null;
    }

    public Integer getCovers() {
        return covers;
    }

    public void setCovers(Integer covers) {
        this.covers = covers;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public Timestamp getEndTime() {
        return new Timestamp(time.getTime() + (2 * 60 * 60 * 1000));
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Tables getTable() {
        return table;
    }

    @Override
    public Integer getTableNumber() {
        return table.getNumber();
    }

    @Override
    public void setArrivalTime(Timestamp t) {
    }

    public void setTable(Tables table) {
        this.table = table;
    }
}
