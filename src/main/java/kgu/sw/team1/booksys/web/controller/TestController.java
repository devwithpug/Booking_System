package kgu.sw.team1.booksys.web.controller;

import kgu.sw.team1.booksys.domain.Reservation;
import kgu.sw.team1.booksys.domain.param.ReservationParam;
import kgu.sw.team1.booksys.web.service.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * JSON 으로 데이터 송수신하는 테스트 컨트롤러
 */
@RestController
@RequestMapping("/test")
public class TestController {

    private final ReservationService reservationService;

    public TestController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    /**
     * 요청으로 JSON 데이터를 받아 새로운 예약 생성
     * @param param
     * {
     *     "customerOid": 16,
     *     "date": "2021-05-04",
     *     "time": "15:30:00",
     *     "covers": 2,
     *     "tablesOid": [17]
     * }
     * @return Reservation
     * {
     *     "id": 1,
     *     "oid": 25,
     *     "covers": 2,
     *     "date": "2021-05-04",
     *     "time": "15:30:00",
     *     "arrivalTime": null,
     *     "tables": {
     *         "id": 2,
     *         "oid": 17,
     *         "number": 1,
     *         "places": 1,
     *         "reservation": null,
     *         "walk_in": null
     *     },
     *     "customer": {
     *         "id": 3,
     *         "oid": 16,
     *         "name": null,
     *         "phoneNumber": null,
     *         "reservation": null
     *     },
     *     "endTime": "17:30:00"
     * }
     */
    @PostMapping("/res")
    public Reservation makePreReservationTest(@RequestBody ReservationParam param) {
        System.out.println("TestController.test");

        if (reservationService.isBookable(param.getTablesOid().get(0), param.getDate(), param.getTime())) {
            System.out.println("param.getCustomerOid() = " + param.getCustomerOid());
            System.out.println("param.getDate() = " + param.getDate());
            System.out.println("param.getTime() = " + param.getTime());
            System.out.println("param.getCovers() = " + param.getCovers());
            System.out.println("param.getTables() = " + param.getTablesOid());
            return reservationService.makePreReservation(param);
        }
        return null;
    }

    /**
     * 요청으로 JSON 데이터를 받아 예약 도착 기록
     * @param param
     * {
     *     "reservationOid": 22
     * }
     * @return Reservation
     * {
     *     "id": 1,
     *     "oid": 23,
     *     "covers": 2,
     *     "date": "2021-05-04",
     *     "time": "15:30:00",
     *     "arrivalTime": "20:15:13.5399987",
     *     "tables": {
     *         "id": 2,
     *         "oid": 20,
     *         "number": 1,
     *         "places": 1,
     *         "reservation": 1,
     *         "walk_in": null
     *     },
     *     "customer": {
     *         "id": 3,
     *         "oid": 19,
     *         "name": null,
     *         "phoneNumber": null,
     *         "reservation": 1
     *     },
     *     "endTime": "17:30:00"
     * }
     */
    @PostMapping("/arrival")
    public Reservation saveArrivalInfoTest(@RequestBody Map<String, Integer> param) {
        return reservationService.saveArrivalInfo(param.get("reservationOid"));
    }

    /**
     * 기존 예약 삭제
     * @param param
     * {
     *     "reservationOid": 149
     * }
     */
    @PostMapping("/cancel")
    public void cancelReservationTest(@RequestBody Map<String, Integer> param) {
        reservationService.cancelReservation(param.get("reservationOid"));
    }
}
