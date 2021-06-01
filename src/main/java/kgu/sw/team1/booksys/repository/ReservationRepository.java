package kgu.sw.team1.booksys.repository;

import kgu.sw.team1.booksys.domain.Customer;
import kgu.sw.team1.booksys.domain.Reservation;
import kgu.sw.team1.booksys.domain.Tables;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByCustomer(Customer customer);
    List<Reservation> findAllByTables(Tables tables);
    Reservation findByArrivalTime(LocalTime arrivalTime);
    Reservation findByTime(LocalTime time);
    List<Reservation> findAllByDateBetween(
            LocalDate from, LocalDate to
    );
}
