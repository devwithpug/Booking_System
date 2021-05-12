package kgu.sw.team1.booksys.repository;

import kgu.sw.team1.booksys.domain.Customer;
import kgu.sw.team1.booksys.domain.Reservation;
import kgu.sw.team1.booksys.domain.Tables;
import kgu.sw.team1.booksys.domain.WalkIn;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
        customer = customerRepository.save(customer);

        Tables table = new Tables();
        table.setNumber(10);
        Tables table2 = new Tables();
        table2.setNumber(11);
        table = tablesRepository.save(table);
        table2 = tablesRepository.save(table2);

        Reservation reservation = new Reservation();
        reservation.setCovers(4);
        reservation.setTime(LocalTime.now());
        reservation.setDate(LocalDate.now());
        reservation.setCustomer(customer);
        reservation.setTables(List.of(table, table2));
        // when
        Reservation savedReservation = reservationRepository.save(reservation);
        // then
        Reservation result = reservationRepository.findById(savedReservation.getOid()).get();
        Reservation result2 = reservationRepository.findByCustomer(customer);
        Reservation result3 = reservationRepository.findByTables(table).get(0);

        assertThat(result).isEqualTo(savedReservation);
        assertThat(result2).isEqualTo(savedReservation);
        assertThat(result3).isEqualTo(savedReservation);
        assertThat(result.getTables()).contains(table, table2);
        assertThat(result2.getTables()).contains(table, table2);
        assertThat(result3.getTables()).contains(table, table2);
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
        walkIn.setTime(LocalTime.now());
        walkIn.setDate(LocalDate.now());
        walkIn.setTables(List.of(table));
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
    void 예약_번호로_예약_찾기() {
        // given
        Customer customer = customerRepository.save(new Customer());
        Tables table = new Tables();
        table.setNumber(10);
        table = tablesRepository.save(table);

        Reservation reservation = new Reservation();
        reservation.setCovers(4);
        reservation.setTime(LocalTime.now());
        reservation.setDate(LocalDate.now());
        reservation.setCustomer(customer);
        reservation.setTables(List.of(table));
        Reservation savedReservation = reservationRepository.save(reservation);
        // when
        Reservation result = reservationRepository.findById(savedReservation.getOid()).get();
        // then
        assertThat(result).isEqualTo(savedReservation);
    }

    @Test
    void 고객의_예약_찾기() {
        // given
        Customer customer = customerRepository.save(new Customer());
        Tables table = new Tables();
        table.setNumber(10);
        table = tablesRepository.save(table);

        Reservation reservation = new Reservation();
        reservation.setCovers(4);
        reservation.setTime(LocalTime.now());
        reservation.setDate(LocalDate.now());
        reservation.setCustomer(customer);
        reservation.setTables(List.of(table));
        Reservation savedReservation = reservationRepository.save(reservation);
        // when
        Reservation result = reservationRepository.findByCustomer(customer);
        // then
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
        walkIn.setTime(LocalTime.now());
        walkIn.setDate(LocalDate.now());
        walkIn.setTables(List.of(table));
        // when
        tablesRepository.save(table);
        WalkIn savedWalkIn = walkInRepository.save(walkIn);
        // then
        LocalTime time = savedWalkIn.getTime();
        WalkIn result = walkInRepository.findByTime(time);
        assertThat(result).isEqualTo(savedWalkIn);
    }

    @Test
    void 사용중이_아닌_테이블_검색() {
        // given
        Tables tables1 = new Tables();
        Tables tables2 = new Tables();
        tables1.setEmpty(true);
        tables2.setEmpty(true);
        Tables savedTables1 = tablesRepository.save(tables1);
        Tables savedTables2 = tablesRepository.save(tables2);

        Customer customer = new Customer();
        Customer savedCustomer = customerRepository.save(customer);
        Reservation reservation = new Reservation();
        savedTables2.toggle();
        reservation.setTables(List.of(savedTables2));
        reservation.setCustomer(savedCustomer);
        reservationRepository.save(reservation);
        // when
        List<Tables> emptyTables = tablesRepository.findAllEmptyTables();
        // then
        for (Tables table : emptyTables) {
            assertThat(table.isEmpty()).isTrue();
        }
    }

    @Test
    void 테이블_번호로_예약_찾기() {
        Tables tables = tablesRepository.save(new Tables());
        Reservation reservation1 = new Reservation();
        Reservation reservation2 = new Reservation();
        tables.setReservation(List.of(reservation1, reservation2));
        Reservation saved1 = reservationRepository.save(reservation1);
        Reservation saved2 = reservationRepository.save(reservation2);

        List<Reservation> result = reservationRepository.findByTables(tables);
        assertThat(result).contains(reservation1, reservation2);
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
        table = tablesRepository.save(table);
        table2 = tablesRepository.save(table2);

        Reservation reservation = new Reservation();
        reservation.setCovers(4);
        reservation.setTime(LocalTime.now());
        reservation.setDate(LocalDate.now());
        reservation.setCustomer(customer);
        reservation.setTables(List.of(table));
        // when
        Reservation savedReservation = reservationRepository.save(reservation);
        // then
        savedReservation.setTables(List.of(table2));
        Reservation result = reservationRepository.save(reservation);
        assertThat(result.getTables()).containsExactly(table2);
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
        reservation.setTime(LocalTime.now());
        reservation.setDate(LocalDate.now());
        reservation.setCustomer(customer);
        reservation.setTables(List.of(table));
        // when
        Reservation savedReservation = reservationRepository.save(reservation);
        // then
        LocalTime before = savedReservation.getTime();
        LocalTime after = before.plusHours(3);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("HH:mm:ss");
        System.out.println("before = " + f.format(before));
        System.out.println("after = " + f.format(after));
        savedReservation.setTime(after);
        Reservation result = reservationRepository.save(reservation);
        assertThat(result.getTime()).isEqualTo(after);
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
        customer = customerRepository.save(customer);

        Tables table = new Tables();
        table.setNumber(10);
        table.setPlaces(1);
        table = tablesRepository.save(table);

        Reservation reservation = new Reservation();
        reservation.setCovers(4);
        reservation.setTime(LocalTime.now());
        reservation.setDate(LocalDate.now());
        reservation.setCustomer(customer);
        reservation.setTables(List.of(table));
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
        walkIn.setTime(LocalTime.now());
        walkIn.setDate(LocalDate.now());
        walkIn.setTables(List.of(table));
        // when
        WalkIn savedWalkIn = walkInRepository.save(walkIn);
        // then
        walkInRepository.delete(savedWalkIn);
        Optional<WalkIn> result = walkInRepository.findById(savedWalkIn.getOid());
        assertThat(result).isEmpty();
    }
    
}