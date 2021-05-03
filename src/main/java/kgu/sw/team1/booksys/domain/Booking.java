package kgu.sw.team1.booksys.domain;


import java.time.LocalDate;
import java.time.LocalTime;

public interface Booking {
    LocalTime getArrivalTime();
    Integer getCovers() ;
    LocalDate getDate() ;
    LocalTime getEndTime() ;
    LocalTime getTime() ;
    Tables getTable() ;

    void setArrivalTime(LocalTime t) ;
    void setCovers(Integer c) ;
    void setDate(LocalDate d) ;
    void setTime(LocalTime t) ;
    void setTable(Tables t) ;
}
