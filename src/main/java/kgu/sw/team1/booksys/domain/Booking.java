package kgu.sw.team1.booksys.domain;


import java.sql.Timestamp;
import java.time.LocalDateTime;

public interface Booking {
    Timestamp getArrivalTime();
    Integer getCovers() ;
    LocalDateTime getDate() ;
    Timestamp getEndTime() ;
    Timestamp getTime() ;
    Tables getTable() ;
    Integer getTableNumber() ;

//    String getDetails() ;

    void setArrivalTime(Timestamp t) ;
    void setCovers(Integer c) ;
    void setDate(LocalDateTime d) ;
    void setTime(Timestamp t) ;
    void setTable(Tables t) ;
}
