package kgu.sw.team1.booksys.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservation_notify_queue")
public class ReservationNotifyQueue {

    @Id
    @GeneratedValue
    @Column(name = "oid")
    private Integer oid;

    @Column(name = "email")
    private String email;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "reservation_oid")
    private Integer reservationOid;

    @Column(name = "customer_oid")
    private Integer customerOid;

    public ReservationNotifyQueue() {
    }

    public Integer getCustomerOid() {
        return customerOid;
    }

    public void setCustomerOid(Integer customerOid) {
        this.customerOid = customerOid;
    }

    public Integer getReservationOid() {
        return reservationOid;
    }

    public void setReservationOid(Integer reservationOid) {
        this.reservationOid = reservationOid;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
