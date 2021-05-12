package kgu.sw.team1.booksys.domain;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface Booking {
    LocalTime getArrivalTime();
    Integer getCovers() ;
    LocalDate getDate() ;
    LocalTime getEndTime() ;
    LocalTime getTime() ;
    List<Tables> getTables() ;

    void setArrivalTime(LocalTime t) ;
    void setCovers(Integer c) ;
    void setDate(LocalDate d) ;
    void setTime(LocalTime t) ;
    void setTables(List<Tables> t) ;
}
