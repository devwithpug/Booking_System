package kgu.sw.team1.booksys.web.service;

import kgu.sw.team1.booksys.domain.Customer;
import kgu.sw.team1.booksys.domain.Reservation;
import kgu.sw.team1.booksys.domain.Tables;
import kgu.sw.team1.booksys.domain.WalkIn;
import kgu.sw.team1.booksys.domain.param.ReservationParam;
import kgu.sw.team1.booksys.domain.param.WalkInParam;
import kgu.sw.team1.booksys.repository.CustomerRepository;
import kgu.sw.team1.booksys.repository.TablesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TablesRepository tablesRepository;


    @Test
    void 사전_예약_생성() {
        // given
        Customer savedCustomer = customerRepository.save(new Customer());
        Tables savedTables = tablesRepository.save(new Tables());

        ReservationParam param = new ReservationParam();
        param.setCustomerOid(savedCustomer.getOid());
        param.setTablesOid(List.of(savedTables.getOid()));
        param.setCovers(2);
        param.setDate("2021-07-30");
        param.setTime("15:30:00");
        // when
        Reservation savedReservation = reservationService.makePreReservation(param);
        // then
        List<Reservation> result = reservationService.findCustomerReservations(savedCustomer.getOid());
        assertThat(result.get(0).getOid()).isEqualTo(savedReservation.getOid());
        assertThat(savedReservation.getTables().isEmpty()).isFalse();
    }

    @Test
    void 현장_예약_생성() {
        // given
        Tables tables = new Tables();
        tables.toggle();
        Tables savedTables = tablesRepository.save(tables);
        tablesRepository.save(new Tables());
        WalkInParam param = new WalkInParam();
        param.setCovers(2);
        param.setDate("2021-12-25");
        param.setTime("12:00:00");
        // when
        WalkIn savedWalkIn = reservationService.makeOnSiteReservation(param);
        // then
        assertThat(savedWalkIn.getTables().getOid()).isNotEqualTo(savedTables.getOid());
    }

    @Test
    void 예약_좌석_변경() {
        // given
        Customer savedCustomer = customerRepository.save(new Customer());
        Tables savedTables = tablesRepository.save(new Tables());
        Tables savedNewTables = tablesRepository.save(new Tables());

        ReservationParam param = new ReservationParam();
        param.setCustomerOid(savedCustomer.getOid());
        param.setTablesOid(List.of(savedTables.getOid()));
        param.setCovers(2);
        param.setDate("2021-07-30");
        param.setTime("15:30:00");
        // when
        Reservation savedReservation = reservationService.makePreReservation(param);
        Reservation changed = reservationService.modifyReservationTable(savedReservation.getOid(), savedNewTables.getOid());
        // then
        List<Reservation> result = reservationService.findCustomerReservations(savedCustomer.getOid());
        assertThat(result.get(0).getTables().getOid()).isEqualTo(changed.getTables().getOid());
    }

    @Test
    void 예약_시간_변경() {
        // given
        Customer savedCustomer = customerRepository.save(new Customer());
        Tables savedTables = tablesRepository.save(new Tables());

        ReservationParam param = new ReservationParam();
        param.setCustomerOid(savedCustomer.getOid());
        param.setTablesOid(List.of(savedTables.getOid()));
        param.setCovers(2);
        param.setDate("2021-07-30");
        param.setTime("15:30:00");
        // when
        Reservation savedReservation = reservationService.makePreReservation(param);
        String date = "2021-08-05";
        String time = "16:00:00";
        Reservation changed = reservationService.modifyReservationTime(savedReservation.getOid(), date, time);
        // then
        List<Reservation> result = reservationService.findCustomerReservations(savedCustomer.getOid());
        assertThat(result.get(0).getDate()).isEqualTo(date);
        assertThat(result.get(0).getTime()).isEqualTo(time);
    }

    @Test
    void 예약_취소() {
        // given
        Customer savedCustomer = customerRepository.save(new Customer());
        Tables savedTables = tablesRepository.save(new Tables());

        ReservationParam param = new ReservationParam();
        param.setCustomerOid(savedCustomer.getOid());
        param.setTablesOid(List.of(savedTables.getOid()));
        param.setCovers(2);
        param.setDate("2021-07-30");
        param.setTime("15:30:00");
        // when
        Reservation savedReservation = reservationService.makePreReservation(param);
        reservationService.cancelReservation(savedReservation.getOid());
        // then
        List<Reservation> result = reservationService.findCustomerReservations(savedCustomer.getOid());
        assertThat(result).isEmpty();
    }

    @Test
    void 예약_가능_여부() {
        // given
        Customer savedCustomer = customerRepository.save(new Customer());
        Tables savedTables = tablesRepository.save(new Tables());

        ReservationParam param = new ReservationParam();
        param.setCustomerOid(savedCustomer.getOid());
        param.setTablesOid(List.of(savedTables.getOid()));
        param.setDate("2021-07-30");
        param.setTime("15:00:00");

        Reservation reservation = reservationService.makePreReservation(param);
        param.setTime("19:00:00");
        Reservation reservation2 = reservationService.makePreReservation(param);
        // when
        String time1 = "17:00:00";
        String time2 = "16:59:00";
        String time3 = "17:01:00";
        // then
        assertThat(reservationService.isBookable(savedTables.getOid(), param.getDate(), time1)).isTrue();
        assertThat(reservationService.isBookable(savedTables.getOid(), param.getDate(), time2)).isFalse();
        assertThat(reservationService.isBookable(savedTables.getOid(), param.getDate(), time3)).isFalse();
    }
}