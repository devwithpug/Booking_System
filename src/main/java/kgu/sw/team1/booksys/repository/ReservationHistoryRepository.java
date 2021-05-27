package kgu.sw.team1.booksys.repository;

import kgu.sw.team1.booksys.domain.Grade;
import kgu.sw.team1.booksys.domain.ReservationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationHistoryRepository extends JpaRepository<ReservationHistory, Integer> {
    List<ReservationHistory> findAllByUserOid(Integer userOid);
    List<ReservationHistory> findAllByGrade(Grade grade);
    void deleteAllByUserOid(Integer userOid);
    List<ReservationHistory> findAllByDateBetween(
            LocalDate from, LocalDate to
    );
}
