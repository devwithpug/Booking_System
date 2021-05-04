package kgu.sw.team1.booksys.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reservation")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class Reservation implements Booking{

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

    @Column(name = "arrival_time")
    private LocalTime arrivalTime;

    @OneToOne
    @JoinColumn(name = "tables_oid")
    private Tables tables;

    @OneToOne
    @JoinColumn(name = "customer_oid")
    private Customer customer;

    public Reservation() {
    }

    public Reservation(Integer oid, Integer covers, LocalDate date, LocalTime time, Tables tables, Customer customer) {
        this.oid = oid;
        this.covers = covers;
        this.date = date;
        this.time = time;
        this.arrivalTime = null;
        this.tables = tables;
        this.customer = customer;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
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
    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    @Override
    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Tables getTables() {
        return tables;
    }

    public void setTables(Tables table) {
        this.tables = table;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
