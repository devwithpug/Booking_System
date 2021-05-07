package kgu.sw.team1.booksys.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;


@Entity
@Table(name = "tables")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class Tables {

    @Id
    @GeneratedValue
    @Column(name = "oid")
    private Integer oid;

    @Column(name = "number")
    private Integer number;

    @Column(name = "places")
    private Integer places;

    @Column(name = "empty")
    private Boolean empty;

    @OneToOne(mappedBy = "tables", cascade = CascadeType.ALL)
    private Reservation reservation;

    @OneToOne(mappedBy = "tables", cascade = CascadeType.ALL)
    private WalkIn walk_in;

    public Tables() {
        empty = true;
    }

    public Tables(Integer oid, Integer number, Integer places, Boolean empty, Reservation reservation, WalkIn walk_in) {
        this.oid = oid;
        this.number = number;
        this.places = places;
        this.empty = empty;
        this.reservation = reservation;
        this.walk_in = walk_in;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public void toggle() {
        empty = !empty;
    }

    public Boolean isEmpty() {
        return empty;
    }

    public void setEmpty(Boolean empty) {
        this.empty = empty;
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
