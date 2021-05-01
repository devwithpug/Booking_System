package kgu.sw.team1.booksys.domain;

import javax.persistence.*;


@Entity
@Table(name = "tables")
public class Tables {

    @Id
    @GeneratedValue
    @Column(name = "oid")
    private Integer oid;

    @Column(name = "number")
    private Integer number;

    @Column(name = "places")
    private Integer places;

    @OneToOne
    @JoinColumn(name = "reservation_oid")
    private Reservation reservation;

    @OneToOne
    @JoinColumn(name = "walk_in_oid")
    private WalkIn walk_in;

    public Tables() {
    }

    public Tables(Integer oid, Integer number, Integer places, Reservation reservation, WalkIn walk_in) {
        this.oid = oid;
        this.number = number;
        this.places = places;
        this.reservation = reservation;
        this.walk_in = walk_in;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getPlaces() {
        return places;
    }

    public void setPlaces(Integer places) {
        this.places = places;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public WalkIn getWalk_in() {
        return walk_in;
    }

    public void setWalk_in(WalkIn walk_in) {
        this.walk_in = walk_in;
    }
}
