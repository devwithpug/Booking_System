package kgu.sw.team1.booksys.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reservation_history")
public class ReservationHistory {

    @Id
    @GeneratedValue
    @Column(name = "oid")
    private Integer oid;

    @Column(name = "user_oid")
    private Integer userOid;

    @Column(name = "grade")
    private Grade grade;

    @Column(name = "covers")
    private Integer covers;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private LocalTime time;

    @Column(name = "arrival_time")
    private LocalTime arrivalTime;

    public ReservationHistory() {
    }

    public ReservationHistory(Reservation reservation) {
        User user = reservation.getCustomer().getUser();
        this.userOid = user.getOid();
        this.grade = user.getGrade();
        this.covers = reservation.getCovers();
        this.date = reservation.getDate();
        this.time = reservation.getTime();
        this.arrivalTime = reservation.getArrivalTime();
    }

    public ReservationHistory(WalkIn walkIn) {
        this.covers = walkIn.getCovers();
        this.date = walkIn.getDate();
        this.time = walkIn.getTime();
        this.arrivalTime = walkIn.getTime();
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Integer getUserOid() {
        return userOid;
    }

    public void setUserOid(Integer userOid) {
        this.userOid = userOid;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Integer getCovers() {
        return covers;
    }

    public void setCovers(Integer covers) {
        this.covers = covers;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setUser(User user) {
        this.userOid = user.getOid();
        this.grade = user.getGrade();
    }
}
