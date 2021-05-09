package kgu.sw.team1.booksys.web.service;

import kgu.sw.team1.booksys.domain.Customer;
import kgu.sw.team1.booksys.domain.Tables;
import kgu.sw.team1.booksys.domain.param.ReservationParam;
import kgu.sw.team1.booksys.domain.param.TablesParam;
import kgu.sw.team1.booksys.domain.param.UserParam;
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

    @BeforeEach
    void beforeEach() {
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
}