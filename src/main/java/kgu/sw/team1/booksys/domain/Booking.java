package kgu.sw.team1.booksys.domain;


import java.sql.Time;
import java.time.LocalDateTime;

public interface Booking {
    public Time getArrivalTime();
    public int getCovers() ;
    public LocalDateTime getDate() ;
    public Time getEndTime() ;
    public Time getTime() ;
    public Tables getTable() ;
    public int getTableNumber() ;

//    public String getDetails() ;

    public void setArrivalTime(Time t) ;
    public void setCovers(int c) ;
    public void setDate(LocalDateTime d) ;
    public void setTime(Time t) ;
    public void setTable(Tables t) ;
}
