package kgu.sw.team1.booksys.domain;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;

@Entity
@Table(name = "Reservation")
public class Reservation implements Booking{
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

    @Column(name = "arrivalTime")
    private Time arrivalTime;

    @OneToOne(mappedBy = "reservation")
    private Tables table;

    @OneToOne(mappedBy = "reservation")
    private Customer customer;

    public Reservation() {
    }

    public Reservation(int oid, int covers, LocalDateTime date, Time time, Time arrivalTime, Tables table, Customer customer) {
        this.oid = oid;
        this.covers = covers;
        this.date = date;
        this.time = time;
        this.arrivalTime = arrivalTime;
        this.table = table;
        this.customer = customer;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
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

    public Time getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Time arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Tables getTable() {
        return table;
    }

    @Override
    public int getTableNumber() {
        return table.getNumber();
    }

    public void setTable(Tables table) {
        this.table = table;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
