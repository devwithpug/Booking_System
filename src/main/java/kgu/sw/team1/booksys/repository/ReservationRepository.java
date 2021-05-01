package kgu.sw.team1.booksys.repository;

import kgu.sw.team1.booksys.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    Reservation findByArrivalTime(Timestamp timestamp);
}
