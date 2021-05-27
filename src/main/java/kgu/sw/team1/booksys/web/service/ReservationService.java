package kgu.sw.team1.booksys.web.service;

import kgu.sw.team1.booksys.domain.Customer;
import kgu.sw.team1.booksys.domain.Reservation;
import kgu.sw.team1.booksys.domain.Tables;
import kgu.sw.team1.booksys.domain.WalkIn;
import kgu.sw.team1.booksys.domain.param.ReservationParam;
import kgu.sw.team1.booksys.domain.param.TablesParam;
import kgu.sw.team1.booksys.repository.CustomerRepository;
import kgu.sw.team1.booksys.repository.ReservationRepository;
import kgu.sw.team1.booksys.repository.TablesRepository;
import kgu.sw.team1.booksys.repository.WalkInRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class ReservationService {

    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;
    private final TablesRepository tablesRepository;
    private final WalkInRepository walkInRepository;
    private final List<Integer> waitingList;

    public ReservationService(CustomerRepository customerRepository, ReservationRepository reservationRepository, TablesRepository tablesRepository, WalkInRepository walkInRepository) {
        this.customerRepository = customerRepository;
        this.reservationRepository = reservationRepository;
        this.tablesRepository = tablesRepository;
        this.walkInRepository = walkInRepository;
        this.waitingList = new ArrayList<>();
    }

    /**
     * 예약 사전 등록
     * 날짜 포맷 : "yyyy-MM-dd"
     * 시간 포맷 : "HH:mm:ss"
     */
    public Reservation makePreReservation(ReservationParam param) {
        Customer customer = customerRepository.findById(param.getCustomerOid()).get();
        Reservation reservation = reservationRepository.save(new Reservation());

        List<Tables> tables = new ArrayList<>();
        for (Integer tablesOid : param.getTablesOid()) {
            Tables table = tablesRepository.findById(tablesOid).get();
            if (table.isEmpty()) table.toggle();
            tables.add(table);
            tablesRepository.save(table);
        }
        reservation.setCustomer(customer);
        reservation.setTables(tables);
        reservation.setDate(LocalDate.parse(param.getDate()));
        reservation.setTime(LocalTime.parse(param.getTime()));
        reservation.setCovers(param.getCovers());
        return reservationRepository.save(reservation);
    }

    /**
     * 예약 현장 등록
     */
    public List<WalkIn> makeOnSiteReservation(Integer covers) {
        waitingList.add(covers);
        return processOnSiteReservation();
    }

    /**
     * 대기 리스트 입장 처리
     */
    public List<WalkIn> processOnSiteReservation() {
        List<WalkIn> result = new ArrayList<>();
        List<Tables> emptyTables = findAllBookableTables(LocalDate.now().toString(), LocalTime.now().toString());
        Collections.shuffle(emptyTables);

        while (!waitingList.isEmpty() && !emptyTables.isEmpty()) {
            Tables tables = emptyTables.remove(0);
            if (tables.isEmpty()) tables.toggle();
            WalkIn walkIn = WalkIn.createInstance(waitingList.remove(0), tables);
            walkIn.setTables(List.of(tables));
            WalkIn savedWalkIn = walkInRepository.save(walkIn);
            result.add(savedWalkIn);
        }

        return result;
    }

    /**
     * 예약 가능한 모든 테이블 조회
     */
    public List<Tables> findAllBookableTables(String date, String time) {
        List<Tables> result = new ArrayList<>();
        for (Tables tables : tablesRepository.findAll()) {
            if (isBookable(tables.getOid(), date, time)) {
                result.add(tables);
            }
        }
        return result;
    }

    /**
     * 모든 테이블 객체 파라미터로 변환
     */
    public List<TablesParam> getAllTablesParam(String date, String time) {
        List<TablesParam> result = new ArrayList<>();
        for (Tables tables : tablesRepository.findAll()) {
            if (isBookable(tables.getOid(), date, time)) {
                result.add(new TablesParam(tables, true));
            } else {
                result.add(new TablesParam(tables, false));
            }
        }
        return result;
    }

    /**
     * 모든 현장 예약 조회
     */
    public List<WalkIn> findAllWalkIns() {
        return walkInRepository.findAll();
    }

    /**
     * 현장 예약 대기리스트 조회
     */
    public List<Integer> getWaitingList() {
        return waitingList;
    }

    /**
     * 단일 예약 조회
     */
    public Reservation findOneReservation(Integer reservationOid) {
        return reservationRepository.findById(reservationOid).get();
    }

    /**
     * 모든 예약 조회
     */
    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    /**
     * 회원 예약 조회
     */
    public List<Reservation> findCustomerReservation(Customer customer) {
        return reservationRepository.findByCustomer(customer);
    }

    /**
     * 사전 예약 취소 (예약 도착)
     */
    public void cancelReservation(Integer reservationOid) {
        Reservation reservation = reservationRepository.findById(reservationOid).get();
        List<Reservation> reservations = reservation.getCustomer().getReservation();
        reservations.remove(reservation);
        reservation.getCustomer().setReservation(reservations);
        List<Tables> tables = reservation.getTables();
        reservationRepository.delete(reservation);
        processOnSiteReservation();
    }

    /**
     * 현장 예약 식사 종료
     */
    public void finishWalkIn(Integer walkInOid) {
        WalkIn walkIn = walkInRepository.findById(walkInOid).get();
        List<Tables> tables = walkIn.getTables();
        walkInRepository.delete(walkIn);
        processOnSiteReservation();
    }

    /**
     * 예약 시간 변경
     */
    public Reservation modifyReservationTime(Integer reservationOid, String date, String time) {
        Reservation reservation = reservationRepository.findById(reservationOid).get();
        reservation.setDate(LocalDate.parse(date));
        reservation.setTime(LocalTime.parse(time));

        return reservationRepository.save(reservation);
    }

    /**
     * 예약 좌석 변경
     */
    public Reservation modifyReservationTable(Integer reservationOid, Integer... tablesOid) {
        Reservation reservation = reservationRepository.findById(reservationOid).get();

        List<Tables> list = new ArrayList<>();
        for (Integer oid : tablesOid) {
            Tables tables = tablesRepository.findById(oid).get();
            list.add(tables);
        }
        reservation.setTables(list);

        return reservationRepository.save(reservation);
    }

    /**
     * 예약 인원수 변경
     */
    public Reservation modifyReservationCovers(Integer reservationOid, Integer covers) {
        Reservation reservation = reservationRepository.findById(reservationOid).get();
        reservation.setCovers(covers);
        return reservationRepository.save(reservation);
    }

    /**
     * 도착 기록
     */
    public Reservation saveArrivalInfo(Integer reservationOid) {
        Reservation reservation = reservationRepository.findById(reservationOid).get();
        reservation.setArrivalTime(LocalTime.now());

        return reservationRepository.save(reservation);
    }

    /**
     * 해당 테이블 예약 가능 여부
     */
    public boolean isBookable(Integer tablesOid, String date, String time) {
        Tables tables = tablesRepository.findById(tablesOid).get();
        List<Reservation> tablesReservations = reservationRepository.findAllByTables(tables);
        List<WalkIn> tablesWalkIns = walkInRepository.findAllByTables(tables);

        if (tables.isEmpty()) return true;
        for (WalkIn walkIn : tablesWalkIns) {
            if (compare(LocalDate.parse(date), LocalTime.parse(time), walkIn.getDate(), walkIn.getTime(), walkIn.getEndTime().plusMinutes(30))) return false;
        }
        for (Reservation reservation : tablesReservations) {
            if (compare(LocalDate.parse(date), LocalTime.parse(time), reservation.getDate(), reservation.getTime(), reservation.getEndTime())) return false;
        }
        return true;
    }

    private boolean compare(LocalDate date, LocalTime time, LocalDate date2, LocalTime time2, LocalTime endTime) {
        if (date2.isEqual(date)) {
            LocalTime end = time.plusHours(2);
            if (time2.equals(time)) return true;
            else if (time2.isBefore(time) && endTime.isAfter(time)) return true;
            return time2.isBefore(end) && endTime.isAfter(end);
        }
        return false;
    }

    /**
     * 빈 테이블이 있는지 확인
     */
    public boolean isExistsEmptyTables() {
        return tablesRepository.findAllEmptyTables().size() > 0;
    }

    /**
     * 테이블 번호 조회
     */
    public Tables findOneTablesByNumber(Integer number) {
        return tablesRepository.findByNumber(number);
    }

    /**
     * 테이블 oid 조회
     */
    public Tables findOneTables(Integer tablesOid) {
        return tablesRepository.findById(tablesOid).get();
    }

    /**
     * 모든 테이블 조회
     */
    public List<Tables> findAllTables() {
        return tablesRepository.findAll();
    }

    // TODO - 예약 알림
}