package kgu.sw.team1.booksys.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
public class Reservation implements Booking{
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

    @Column(name = "arrival_time")
    private Timestamp arrivalTime;

    @OneToOne(mappedBy = "reservation")
    private Tables table;

    @OneToOne(mappedBy = "reservation")
    private Customer customer;

    public Reservation() {
    }

    public Reservation(Integer oid, Integer covers, LocalDateTime date, Timestamp time, Timestamp arrivalTime, Tables table, Customer customer) {
        this.oid = oid;
        this.covers = covers;
        this.date = date;
        this.time = time;
        this.arrivalTime = arrivalTime;
        this.table = table;
        this.customer = customer;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {}

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

    public Timestamp getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Timestamp arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Tables getTable() {
        return table;
    }

    @Override
    public Integer getTableNumber() {
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
