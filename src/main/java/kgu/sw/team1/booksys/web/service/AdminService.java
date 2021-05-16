package kgu.sw.team1.booksys.web.service;

import kgu.sw.team1.booksys.domain.Tables;
import kgu.sw.team1.booksys.domain.param.TablesParam;
import kgu.sw.team1.booksys.repository.AdminRepository;
import kgu.sw.team1.booksys.repository.TablesRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    private final ReservationService reservationService;
    private final AdminRepository  adminRepository;
    private final TablesRepository tablesRepository;

    public AdminService(ReservationService reservationService, AdminRepository adminRepository, TablesRepository tablesRepository) {
        this.reservationService = reservationService;
        this.adminRepository = adminRepository;
        this.tablesRepository = tablesRepository;
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
}
