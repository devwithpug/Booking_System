package kgu.sw.team1.booksys.repository;

import kgu.sw.team1.booksys.domain.Tables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TablesRepository extends JpaRepository<Tables, Integer> {
    Tables findByNumber(Integer number);
    List<Tables> findAllByPlaces(Integer places);
}
