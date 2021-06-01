package kgu.sw.team1.booksys.domain.param;

import kgu.sw.team1.booksys.domain.Tables;

public class TablesParam {
    private Integer number;
    private Integer places;
    private boolean empty;

    public TablesParam() {
    }

    public TablesParam(Tables tables, boolean empty) {
        this.number = tables.getNumber();
        this.places = tables.getPlaces();
        this.empty = empty;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getPlaces() {
        return places;
    }

    public void setPlaces(Integer places) {
        this.places = places;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}
