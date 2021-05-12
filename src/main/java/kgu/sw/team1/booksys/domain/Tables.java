package kgu.sw.team1.booksys.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


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

    @ManyToMany(mappedBy = "tables", cascade = CascadeType.ALL)
    private List<Reservation> reservation;

    @ManyToMany(mappedBy = "tables", cascade = CascadeType.ALL)
    private List<WalkIn> walk_in;

    public Tables() {
        empty = true;
        reservation = new ArrayList<>();
        walk_in = new ArrayList<>();
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

    public List<Reservation> getReservation() {
        return reservation;
    }

    public void setReservation(List<Reservation> reservation) {
        if (reservation != null) {
            List<Reservation> list = new ArrayList<>();
            for (Reservation res : reservation) {
                list.add(res);
            }
            this.reservation = list;
        }
    }

    public List<WalkIn> getWalk_in() {
        return walk_in;
    }

    public void setWalk_in(List<WalkIn> walk_in) {
        this.walk_in = walk_in;
    }
}
