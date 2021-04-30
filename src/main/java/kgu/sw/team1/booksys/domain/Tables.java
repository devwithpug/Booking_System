package kgu.sw.team1.booksys.domain;

import javax.persistence.*;


@Entity
@Table(name = "Table")
public class Tables {

    @Id
    @GeneratedValue
    @Column(name = "oid")
    private int oid;

    @Column(name = "number")
    private int number;

    @Column(name = "places")
    private int places;

    @OneToOne
    @JoinColumn(name = "reservation_oid")
    private Reservation reservation;

    @OneToOne
    @JoinColumn(name = "walkin_oid")
    private WalkIn walkIn;

    public Tables() {
    }

    public Tables(int oid, int number, int places, Reservation reservation, WalkIn walkIn) {
        this.oid = oid;
        this.number = number;
        this.places = places;
        this.reservation = reservation;
        this.walkIn = walkIn;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public WalkIn getWalkIn() {
        return walkIn;
    }

    public void setWalkIn(WalkIn walkIn) {
        this.walkIn = walkIn;
    }
}
