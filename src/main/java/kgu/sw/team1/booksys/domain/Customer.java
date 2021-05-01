package kgu.sw.team1.booksys.domain;

import javax.persistence.*;


@Entity
@Table(name = "Customer")
public class Customer {
    @Id
    @GeneratedValue
    @Column(name = "oid")
    private Integer oid;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne
    @JoinColumn(name = "reservation_oid")
    private Reservation reservation;

    public Customer() {
    }

    public Customer(Integer oid, String name, String phoneNumber, Reservation reservation) {
        this.oid = oid;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.reservation = reservation;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
