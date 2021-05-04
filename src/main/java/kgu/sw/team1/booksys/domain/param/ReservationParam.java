package kgu.sw.team1.booksys.domain.param;

import java.util.List;

public class ReservationParam {
    private Integer customerOid;
    private String date;
    private String time;
    private Integer covers;
    private List<Integer> tablesOid;

    public ReservationParam() {
    }

    public ReservationParam(Integer customerOid, String date, String time, Integer covers, List<Integer> tablesOid) {
        this.customerOid = customerOid;
        this.date = date;
        this.time = time;
        this.covers = covers;
        this.tablesOid = tablesOid;
    }

    public Integer getCustomerOid() {
        return customerOid;
    }

    public void setCustomerOid(Integer customerOid) {
        this.customerOid = customerOid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getCovers() {
        return covers;
    }

    public void setCovers(Integer covers) {
        this.covers = covers;
    }

    public List<Integer> getTablesOid() {
        return tablesOid;
    }

    public void setTablesOid(List<Integer> tablesOid) {
        this.tablesOid = tablesOid;
    }
}
