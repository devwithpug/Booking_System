package kgu.sw.team1.booksys.web.service;

import kgu.sw.team1.booksys.domain.*;
import kgu.sw.team1.booksys.domain.param.ReservationParam;
import kgu.sw.team1.booksys.domain.param.TablesParam;
import kgu.sw.team1.booksys.domain.param.UserParam;
import kgu.sw.team1.booksys.repository.ReservationHistoryRepository;
import kgu.sw.team1.booksys.repository.TablesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class AdminServiceTest {

    @Autowired
    private AdminService adminService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private UserService userService;
    @Autowired
    private TablesRepository tablesRepository;
    @Autowired
    private ReservationHistoryRepository reservationHistoryRepository;

    @BeforeEach
    void beforeEach() {
        tablesRepository.deleteAll();
        reservationHistoryRepository.deleteAll();
        UserParam param = new UserParam();
        param.setId("adminServiceTestId");
        param.setPw("1234");
        param.setName("TEST");
        param.setPhoneNumber("010-1");
        param.setEmail("zz@zz.cm");
        userService.signUp(param);
    }

    @Test
    void 테이블_추가() {
        // given
        TablesParam param = new TablesParam();
        param.setNumber(101);
        param.setPlaces(100);
        // when
        Tables savedTables = adminService.addTables(param);
        // then
        Tables result = adminService.findOne(101);
        assertThat(result).isEqualTo(savedTables);
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void 테이블_정보_변경() {
        // given
        TablesParam param = new TablesParam();
        param.setNumber(101);
        param.setPlaces(100);
        // when
        Tables savedTables = adminService.addTables(param);
        param.setNumber(102);
        adminService.modifyTables(savedTables.getNumber(), param);
        // then
        Tables result = adminService.findOne(102);
        assertThat(result).isEqualTo(savedTables);
    }

    @Test
    void 테이블_삭제() {
        // given
        TablesParam param = new TablesParam();
        param.setNumber(101);
        param.setPlaces(100);
        // when
        Tables savedTables = adminService.addTables(param);
        // then
        adminService.deleteTables(savedTables.getNumber());
        Tables result = adminService.findOne(101);
        assertThat(result).isNull();
    }

    @Test
    void 빈_테이블_모두_조회() {
        // given
        TablesParam param = new TablesParam();
        param.setNumber(3331);
        Tables savedTables = adminService.addTables(param);
        param.setNumber(3332);
        Tables savedTables2 = adminService.addTables(param);
        param.setNumber(3333);
        Tables savedTables3 = adminService.addTables(param);
        // when
        Customer customer = userService.findOneCustomerById("adminServiceTestId");
        ReservationParam param2 = new ReservationParam();
        param2.setTablesOid(List.of(savedTables3.getOid()));
        param2.setCustomerOid(customer.getOid());
        param2.setTime("11:11:11");
        param2.setDate("2021-11-11");
        reservationService.makePreReservation(param2);
        // then
        List<Tables> result = adminService.findAllEmptyTables();
        assertThat(result).contains(savedTables, savedTables2);
        assertThat(result).doesNotContain(savedTables3);
    }

    @Test
    void 예약_가능한_테이블_모두_조회() {
        // given
        TablesParam param = new TablesParam();
        param.setNumber(3331);
        Tables savedTables = adminService.addTables(param);
        param.setNumber(3332);
        Tables savedTables2 = adminService.addTables(param);
        param.setNumber(3333);
        Tables savedTables3 = adminService.addTables(param);
        // when
        Customer customer = userService.findOneCustomerById("adminServiceTestId");
        ReservationParam param2 = new ReservationParam();
        param2.setTablesOid(List.of(savedTables3.getOid()));
        param2.setCustomerOid(customer.getOid());
        param2.setTime("12:00:00");
        param2.setDate("2021-11-11");
        reservationService.makePreReservation(param2);
        // then
        List<Tables> result = adminService.findAllAvailableTables("2021-11-11", "14:00:00");
        assertThat(result).contains(savedTables, savedTables2, savedTables3);
        List<Tables> result2 = adminService.findAllAvailableTables("2021-11-11", "13:59:00");
        assertThat(result2).doesNotContain(savedTables3);
    }

    @Test
    void 회원_등급_변경() {
        // given
        User user = userService.findAllUsers().get(0);
        // when
        adminService.changeUserGrade(user, Grade.VIP);
        // then
        assertThat(user.getGrade()).isEqualTo(Grade.VIP);
    }

    @Test
    void 회원_등급_자동_변경() {
        // given
        User user = userService.findAllUsers().get(0);
        ReservationHistory history = new ReservationHistory();
        ReservationHistory history2 = new ReservationHistory();
        ReservationHistory history3 = new ReservationHistory();
        ReservationHistory history4 = new ReservationHistory();
        ReservationHistory history5 = new ReservationHistory();
        ReservationHistory history6 = new ReservationHistory();
        ReservationHistory history7 = new ReservationHistory();
        ReservationHistory history8 = new ReservationHistory();
        ReservationHistory history9 = new ReservationHistory();
        history.setUser(user);
        history2.setUser(user);
        history3.setUser(user);
        history4.setUser(user);
        history5.setUser(user);
        history6.setUser(user);
        history7.setUser(user);
        history8.setUser(user);
        history9.setUser(user);
        reservationHistoryRepository.saveAll(List.of(
                history, history2, history3, history4, history5, history6, history7, history8, history9
        ));
        // when
        TablesParam param = new TablesParam();
        param.setNumber(3331);
        Tables savedTables = adminService.addTables(param);

        ReservationParam param2 = new ReservationParam();
        param2.setTablesOid(List.of(savedTables.getOid()));
        param2.setCustomerOid(user.getCustomer().getOid());
        param2.setTime("12:00:00");
        param2.setDate("2021-11-11");
        Reservation reservation = reservationService.makePreReservation(param2);
        // then
        assertThat(user.getGrade()).isEqualTo(Grade.BASIC);
        adminService.setReservationArrival(reservation);
        assertThat(user.getGrade()).isEqualTo(Grade.VIP);
    }

    @Test
    void 예약_도착_처리() {
        // given
        User user = userService.findAllUsers().get(0);

        TablesParam param = new TablesParam();
        param.setNumber(3331);
        Tables savedTables = adminService.addTables(param);

        ReservationParam param2 = new ReservationParam();
        param2.setTablesOid(List.of(savedTables.getOid()));
        param2.setCustomerOid(user.getCustomer().getOid());
        param2.setTime("12:00:00");
        param2.setDate("2021-11-11");
        Reservation reservation = reservationService.makePreReservation(param2);
        // when
        ReservationHistory reservationHistory = adminService.setReservationArrival(reservation);
        // then
        assertThat(reservationService.findAllReservations()).isEmpty();
        assertThat(adminService.findAllHistoryByUser(user).size()).isEqualTo(1);
    }
}