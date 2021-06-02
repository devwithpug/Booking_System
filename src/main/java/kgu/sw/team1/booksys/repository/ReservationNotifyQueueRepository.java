package kgu.sw.team1.booksys.repository;

import kgu.sw.team1.booksys.domain.ReservationNotifyQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationNotifyQueueRepository extends JpaRepository<ReservationNotifyQueue, Integer> {
    List<ReservationNotifyQueue> findAllByDate(LocalDate date);
    ReservationNotifyQueue findByReservationOid(Integer reservationOid);
}