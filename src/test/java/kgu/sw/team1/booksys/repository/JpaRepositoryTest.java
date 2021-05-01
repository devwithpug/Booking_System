package kgu.sw.team1.booksys.repository;

import kgu.sw.team1.booksys.domain.Customer;
import kgu.sw.team1.booksys.domain.Reservation;
import kgu.sw.team1.booksys.domain.Tables;
import kgu.sw.team1.booksys.domain.WalkIn;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 기본 DB의 CRUD 단위 테스트
 * CREATE, READ, UPDATE, DELETE
 */

@DataJpaTest
class JpaRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private TablesRepository tablesRepository;
    @Autowired
    private WalkInRepository walkInRepository;


    /**
     * CREATE
     */
    @Test
    void 고객_생성_테스트() {
        // given
        Customer customer = new Customer();
        customer.setName("c1");
        customer.setPhoneNumber("010-1234-5678");
        // when
        Customer savedCustomer = customerRepository.save(customer);
        // then
        Customer result = customerRepository.findById(savedCustomer.getOid()).get();
        assertThat(result).isEqualTo(savedCustomer);
    }

    @Test
    void 예약_생성_테스트() {
        // given
        Customer customer = new Customer();
        customer.setName("c1");
        customer.setPhoneNumber("010-1234-5678");

        Tables table = new Tables();
        table.setNumber(10);
        table.setPlaces(1);

        Reservation reservation = new Reservation();
        reservation.setCovers(4);
        reservation.setArrivalTime(new Timestamp(System.currentTimeMillis()));
        reservation.setDate(LocalDateTime.now());
        reservation.setCustomer(customer);
        reservation.setTable(table);
        // when
        Reservation savedReservation = reservationRepository.save(reservation);
        // then
        Reservation result = reservationRepository.findById(savedReservation.getOid()).get();
        assertThat(result).isEqualTo(savedReservation);
    }

    @Test
    void 테이블_생성_테스트() {
        // given
        Tables table = new Tables();
        table.setNumber(10);
        table.setPlaces(1);
        // when
        Tables savedTable = tablesRepository.save(table);
        // then
        Tables result = tablesRepository.findById(table.getOid()).get();
        assertThat(result).isEqualTo(savedTable);
    }

    @Test
    void 워크인_생성_테스트() {
        // given
        Tables table = new Tables();
        table.setNumber(10);
        table.setPlaces(1);

        WalkIn walkIn = new WalkIn();
        walkIn.setCovers(1);
        walkIn.setTime(new Timestamp(System.currentTimeMillis()));
        walkIn.setDate(LocalDateTime.now());
        walkIn.setTable(table);
        // when
        WalkIn savedWalkIn = walkInRepository.save(walkIn);
        // then
        WalkIn result = walkInRepository.findById(savedWalkIn.getOid()).get();
        assertThat(result).isEqualTo(savedWalkIn);
    }


    /**
     * READ
     */
    @Test
    void 고객_휴대폰_번호로_찾기() {
        // given
        Customer customer = new Customer();
        customer.setName("c1");
        customer.setPhoneNumber("010-1234-5678");
        // when
        Customer savedCustomer = customerRepository.save(customer);
        // then
        Customer result = customerRepository.findByPhoneNumber("010-1234-5678");
        assertThat(result).isEqualTo(savedCustomer);
    }

    @Test
    void 테이블_번호로_찾기() {
        // given
        Tables table = new Tables();
        table.setNumber(10);
        table.setPlaces(1);
        // when
        Tables savedTable = tablesRepository.save(table);
        // then
        Tables result = tablesRepository.findByNumber(10);
        assertThat(result).isEqualTo(savedTable);
    }

    @Test
    void 해당_구역의_모든_테이블_찾기() {
        // given
        Tables table = new Tables();
        table.setNumber(10);
        table.setPlaces(1);
        Tables table2 = new Tables();
        table2.setNumber(11);
        table2.setPlaces(1);
        // when
        Tables savedTable1 = tablesRepository.save(table);
        Tables savedTable2 = tablesRepository.save(table2);
        // then
        List<Tables> result = tablesRepository.findAllByPlaces(1);
        assertThat(result).contains(savedTable1, savedTable2);
    }

    @Test
    void 고객의_예약_찾기() {
        // given
        Customer customer = new Customer();
        customer.setName("c1");
        customer.setPhoneNumber("010-1234-5678");

        Tables table = new Tables();
        table.setNumber(10);
        table.setPlaces(1);

        Reservation reservation = new Reservation();
        reservation.setCovers(4);
        reservation.setArrivalTime(new Timestamp(System.currentTimeMillis()));
        reservation.setDate(LocalDateTime.now());
        reservation.setCustomer(customer);
        reservation.setTable(table);
        // when
        customerRepository.save(customer);
        tablesRepository.save(table);
        Reservation savedReservation = reservationRepository.save(reservation);
        // then

        Reservation result = reservationRepository.findByArrivalTime(savedReservation.getArrivalTime());
        assertThat(result).isEqualTo(savedReservation);
    }

    @Test
    void 워크인_시간으로_찾기() {
        // given
        Tables table = new Tables();
        table.setNumber(10);
        table.setPlaces(1);

        WalkIn walkIn = new WalkIn();
        walkIn.setCovers(1);
        walkIn.setTime(new Timestamp(System.currentTimeMillis()));
        walkIn.setDate(LocalDateTime.now());
        walkIn.setTable(table);
        // when
        tablesRepository.save(table);
        WalkIn savedWalkIn = walkInRepository.save(walkIn);
        // then
        Timestamp time = savedWalkIn.getTime();
        WalkIn result = walkInRepository.findByTime(time);
        assertThat(result).isEqualTo(savedWalkIn);
    }

    /**
     * UPDATE
     */
    @Test
    void 예약_테이블_변경() {
        // given
        Customer customer = new Customer();
        customer.setName("c1");
        customer.setPhoneNumber("010-1234-5678");

        Tables table = new Tables();
        table.setNumber(10);
        table.setPlaces(1);
        Tables table2 = new Tables();
        table2.setNumber(11);
        table2.setPlaces(1);

        Reservation reservation = new Reservation();
        reservation.setCovers(4);
        reservation.setArrivalTime(new Timestamp(System.currentTimeMillis()));
        reservation.setDate(LocalDateTime.now());
        reservation.setCustomer(customer);
        reservation.setTable(table);
        // when
        Reservation savedReservation = reservationRepository.save(reservation);
        // then
        savedReservation.setTable(table2);
        Reservation result = reservationRepository.save(reservation);
        assertThat(result.getTable()).isEqualTo(table2);
    }

    @Test
    void 예약_시간_변경() {
        // given
        Customer customer = new Customer();
        customer.setName("c1");
        customer.setPhoneNumber("010-1234-5678");

        Tables table = new Tables();
        table.setNumber(10);
        table.setPlaces(1);
        Tables table2 = new Tables();
        table2.setNumber(11);
        table2.setPlaces(1);

        Reservation reservation = new Reservation();
        reservation.setCovers(4);
        reservation.setArrivalTime(new Timestamp(System.currentTimeMillis()));
        reservation.setDate(LocalDateTime.now());
        reservation.setCustomer(customer);
        reservation.setTable(table);
        // when
        Reservation savedReservation = reservationRepository.save(reservation);
        // then
        Timestamp before = savedReservation.getArrivalTime();
        Timestamp after = new Timestamp(before.getTime() + (3 * 60 * 60 * 1000));
        savedReservation.setArrivalTime(after);
        Reservation result = reservationRepository.save(reservation);
        assertThat(result.getArrivalTime()).isEqualTo(after);
    }

    /**
     * DELETE
     */
    @Test
    void 고객_삭제_테스트() {
        // given
        Customer customer = new Customer();
        customer.setName("c1");
        customer.setPhoneNumber("010-1234-5678");
        // when
        Customer savedCustomer = customerRepository.save(customer);
        // then
        customerRepository.delete(savedCustomer);
        Optional<Customer> result = customerRepository.findById(savedCustomer.getOid());
        assertThat(result).isEmpty();
    }

    @Test
    void 예약_삭제_테스트() {
        // given
        Customer customer = new Customer();
        customer.setName("c1");
        customer.setPhoneNumber("010-1234-5678");

        Tables table = new Tables();
        table.setNumber(10);
        table.setPlaces(1);

        Reservation reservation = new Reservation();
        reservation.setCovers(4);
        reservation.setArrivalTime(new Timestamp(System.currentTimeMillis()));
        reservation.setDate(LocalDateTime.now());
        reservation.setCustomer(customer);
        reservation.setTable(table);
        // when
        Reservation savedReservation = reservationRepository.save(reservation);
        // then
        reservationRepository.delete(savedReservation);
        Optional<Reservation> result = reservationRepository.findById(savedReservation.getOid());
        assertThat(result).isEmpty();
    }

    @Test
    void 테이블_삭제_테스트() {
        // given
        Tables table = new Tables();
        table.setNumber(10);
        table.setPlaces(1);
        // when
        Tables savedTable = tablesRepository.save(table);
        // then
        tablesRepository.delete(savedTable);
        Optional<Tables> result = tablesRepository.findById(table.getOid());
        assertThat(result).isEmpty();
    }

    @Test
    void 워크인_삭제_테스트() {
        // given
        Tables table = new Tables();
        table.setNumber(10);
        table.setPlaces(1);

        WalkIn walkIn = new WalkIn();
        walkIn.setCovers(1);
        walkIn.setTime(new Timestamp(System.currentTimeMillis()));
        walkIn.setDate(LocalDateTime.now());
        walkIn.setTable(table);
        // when
        WalkIn savedWalkIn = walkInRepository.save(walkIn);
        // then
        walkInRepository.delete(savedWalkIn);
        Optional<WalkIn> result = walkInRepository.findById(savedWalkIn.getOid());
        assertThat(result).isEmpty();
    }
}