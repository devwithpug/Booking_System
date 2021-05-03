package kgu.sw.team1.booksys.repository;

import kgu.sw.team1.booksys.domain.WalkIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;

@Repository
public interface WalkInRepository extends JpaRepository<WalkIn, Integer> {
    WalkIn findByTime(LocalTime time);
}
