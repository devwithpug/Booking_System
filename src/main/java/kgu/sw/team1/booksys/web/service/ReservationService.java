package kgu.sw.team1.booksys.web.service;

import kgu.sw.team1.booksys.domain.Customer;
import kgu.sw.team1.booksys.domain.Reservation;
import kgu.sw.team1.booksys.domain.Tables;
import kgu.sw.team1.booksys.domain.WalkIn;
import kgu.sw.team1.booksys.domain.param.ReservationParam;
import kgu.sw.team1.booksys.domain.param.WalkInParam;
import kgu.sw.team1.booksys.repository.CustomerRepository;
import kgu.sw.team1.booksys.repository.ReservationRepository;
import kgu.sw.team1.booksys.repository.TablesRepository;
import kgu.sw.team1.booksys.repository.WalkInRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Service
public class ReservationService {

    private CustomerRepository customerRepository;
    private ReservationRepository reservationRepository;
    private TablesRepository tablesRepository;
    private WalkInRepository walkInRepository;

    public ReservationService(CustomerRepository customerRepository, ReservationRepository reservationRepository, TablesRepository tablesRepository, WalkInRepository walkInRepository) {
        this.customerRepository = customerRepository;
        this.reservationRepository = reservationRepository;
        this.tablesRepository = tablesRepository;
        this.walkInRepository = walkInRepository;
    }

    /**
     * 예약 사전 등록
     * 날짜 포맷 : "yyyy-MM-dd"
     * 시간 포맷 : "HH:mm:ss"
     */
    public Reservation makePreReservation(ReservationParam param) {
        Customer customer = customerRepository.findById(param.getCustomerOid()).get();
        // TODO - 다중 테이블 예약
        Tables tables = tablesRepository.findById(param.getTablesOid().get(0)).get();
        if (tables.isEmpty()) tables.toggle();

        Reservation reservation = new Reservation();
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
    public WalkIn makeOnSiteReservation(WalkInParam param) {
        List<Tables> emptyTables = tablesRepository.findAllEmptyTables();
        int target = (int) (Math.random() * emptyTables.size());
        Tables tables = tablesRepository.findById(emptyTables.get(target).getOid()).get();
        if (tables.isEmpty()) tables.toggle();

        WalkIn walkIn = new WalkIn();
        walkIn.setTables(tables);
        walkIn.setDate(LocalDate.parse(param.getDate()));
        walkIn.setTime(LocalTime.parse(param.getTime()));
        walkIn.setCovers(param.getCovers());
        return walkInRepository.save(walkIn);
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
    public List<Reservation> findCustomerReservations(Integer customerOid) {
        return reservationRepository.findByCustomerOid(customerOid);
    }

    /**
     * 사전 예약 취소 (식사 종료)
     */
    public void cancelReservation(Integer reservationOid) {
        Reservation reservation = reservationRepository.findById(reservationOid).get();
        Tables tables = reservation.getTables();
        reservationRepository.delete(reservation);
        if (reservationRepository.findByTablesOid(tables.getOid()).size() == 0) {
            tables.toggle();
            tablesRepository.save(tables);
        }
    }

    /**
     * 현장 예약 식사 종료
     */
    public void finishWalkIn(Integer walkInOid) {
        WalkIn walkIn = walkInRepository.findById(walkInOid).get();
        Tables tables = walkIn.getTables();
        walkInRepository.delete(walkIn);
        if (reservationRepository.findByTablesOid(tables.getOid()).size() == 0) {
            tables.toggle();
            tablesRepository.save(tables);
        }
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
        // TODO - 다중 테이블 변경
        Tables tables = tablesRepository.findById(tablesOid[0]).get();
        reservation.setTables(tables);

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
        List<Reservation> tablesReservations = reservationRepository.findByTablesOid(tables.getOid());

        if (tables.isEmpty()) return true;
        for (Reservation reservation : tablesReservations) {
            if (reservation.getDate().isEqual(LocalDate.parse(date))) {
                LocalTime reservationStart = reservation.getTime();
                LocalTime reservationEnd = reservation.getEndTime();
                LocalTime tempStart = LocalTime.parse(time);
                LocalTime tempEnd = tempStart.plusHours(2);
                if (reservationStart.isBefore(tempStart) && reservationEnd.isAfter(tempStart)) return false;
                if (reservationStart.isBefore(tempEnd) && reservationEnd.isAfter(tempEnd)) return false;
            }
        }
        return true;
    }

    /**
     * 빈 테이블이 있는지 확인
     */
    public boolean isExistsEmptyTables() {
        return tablesRepository.findAllEmptyTables().size() > 0;
    }


    // TODO - 다중 테이블 예약 / 변경
    // TODO - 현장 대기리스트
    // TODO - 테이블 자동 배정
    // TODO - 예약 알림
}