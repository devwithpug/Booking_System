package kgu.sw.team1.booksys.web.service;

import kgu.sw.team1.booksys.domain.*;
import kgu.sw.team1.booksys.domain.param.TablesParam;
import kgu.sw.team1.booksys.repository.AdminRepository;
import kgu.sw.team1.booksys.repository.ReservationHistoryRepository;
import kgu.sw.team1.booksys.repository.TablesRepository;
import kgu.sw.team1.booksys.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    private final ReservationService reservationService;
    private final AdminRepository  adminRepository;
    private final TablesRepository tablesRepository;
    private final UserRepository userRepository;
    private final ReservationHistoryRepository reservationHistoryRepository;

    public AdminService(ReservationService reservationService, AdminRepository adminRepository, TablesRepository tablesRepository, UserRepository userRepository, ReservationHistoryRepository reservationHistoryRepository) {
        this.reservationService = reservationService;
        this.adminRepository = adminRepository;
        this.tablesRepository = tablesRepository;
        this.userRepository = userRepository;
        this.reservationHistoryRepository = reservationHistoryRepository;
    }

    /**
     * 테이블 추가
     */
    public Tables addTables(TablesParam param) {
        if (tablesRepository.existsByNumber(param.getNumber())) return null;
        Tables tables = new Tables();
        tables.setNumber(param.getNumber());
        tables.setPlaces(param.getPlaces());

        return tablesRepository.save(tables);
    }

    /**
     * 테이블 정보 변경
     */
    public Tables modifyTables(Integer number, TablesParam param) {
        if (tablesRepository.existsByNumber(param.getNumber())) return null;
        Tables tables = tablesRepository.findByNumber(number);
        tables.setNumber(param.getNumber());
        tables.setPlaces(param.getPlaces());

        return tablesRepository.save(tables);
    }

    /**
     * 테이블 삭제
     */
    public void deleteTables(Integer number) {
        Tables tables = tablesRepository.findByNumber(number);
        tablesRepository.delete(tables);
    }

    /**
     * 테이블 조회
     */
    public Tables findOne(Integer number) {
        return tablesRepository.findByNumber(number);
    }

    /**
     * 모든 테이블 조회
     */
    public List<Tables> findAll() {
        return tablesRepository.findAll();
    }

    /**
     * 빈 테이블 모두 조회
     */
    public List<Tables> findAllEmptyTables() {
        return tablesRepository.findAllEmptyTables();
    }

    /**
     * 예약 가능한 테이블 모두 조회
     */
    public List<Tables> findAllAvailableTables(String date, String time) {
        List<Tables> all = tablesRepository.findAll();
        List<Tables> result = new ArrayList<>();
        for (Tables tables : all) {
            if (reservationService.isBookable(tables.getOid(), date, time)) {
                result.add(tables);
            }
        }
        return result;
    }

    /**
     * 등급 변경
     */
    public User changeUserGrade(User user, Grade grade) {
        user = userRepository.findById(user.getOid()).get();
        user.setGrade(grade);
        return userRepository.save(user);
    }

    /**
     * VIP 여부 판별
     */
    public User processBasicToVIP(User user) {
        if (user.getGrade() == Grade.BASIC) {
            List<ReservationHistory> histories = reservationHistoryRepository.findAllByUserOid(user.getOid());
            if (histories.size() >= 9) {
                return changeUserGrade(user, Grade.VIP);
            }
        }
        return user;
    }

    /**
     * 도착 처리(예약 기록)
     */
    public ReservationHistory setReservationArrival(Reservation reservation) {
        reservation = reservationService.saveArrivalInfo(reservation.getOid());
        ReservationHistory reservationHistory = new ReservationHistory(reservation);
        reservationService.cancelReservation(reservation.getOid());
        processBasicToVIP(reservation.getCustomer().getUser());
        return reservationHistoryRepository.save(reservationHistory);
    }

    /**
     * 현장 예약 기록
     */
    public ReservationHistory setReservationArrival(WalkIn walkIn) {
        ReservationHistory reservationHistory = new ReservationHistory(walkIn);
        return reservationHistoryRepository.save(reservationHistory);
    }

    /**
     * 회원 예약 기록 조회
     */
    public List<ReservationHistory> findAllHistoryByUser(User user) {
        return reservationHistoryRepository.findAllByUserOid(user.getOid());
    }

    /**
     * 등급별 예약 기록 조회
     */
    public List<ReservationHistory> findAllHistoryByGrade(Grade grade) {
        return reservationHistoryRepository.findAllByGrade(grade);
    }

    /**
     * 날짜 범위 내 예약 기록 조회
     */
    public List<ReservationHistory> findAllHistoryByDateBetween(LocalDate from, LocalDate to) {
        return reservationHistoryRepository.findAllByDateBetween(from, to);
    }
}
