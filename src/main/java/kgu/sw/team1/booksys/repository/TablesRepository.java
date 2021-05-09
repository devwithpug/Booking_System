package kgu.sw.team1.booksys.repository;

import kgu.sw.team1.booksys.domain.Tables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TablesRepository extends JpaRepository<Tables, Integer> {
    @Query("SELECT t from Tables t where t.empty = true")
    List<Tables> findAllEmptyTables();
    Tables findByNumber(Integer number);
    List<Tables> findAllByPlaces(Integer places);
    boolean existsByNumber(Integer number);
}
