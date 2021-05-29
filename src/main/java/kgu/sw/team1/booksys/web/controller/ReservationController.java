package kgu.sw.team1.booksys.web.controller;

import kgu.sw.team1.booksys.domain.Customer;
import kgu.sw.team1.booksys.domain.Reservation;
import kgu.sw.team1.booksys.domain.Tables;
import kgu.sw.team1.booksys.domain.User;
import kgu.sw.team1.booksys.domain.param.ReservationParam;
import kgu.sw.team1.booksys.domain.param.TablesParam;
import kgu.sw.team1.booksys.web.service.ReservationService;
import kgu.sw.team1.booksys.web.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller("/basic")
public class ReservationController {

    private final UserService userService;
    private final ReservationService reservationService;

    public ReservationController(UserService userService, ReservationService reservationService) {
        this.userService = userService;
        this.reservationService = reservationService;
    }

    /**
     * 메인 redirect
     */
    @GetMapping("/main/{customerOid}")
    public String mainRedirect(@PathVariable Integer customerOid) {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm:00"));
        return "redirect:/main/"+now+"/"+customerOid.toString();
    }

    /**
     * 메인 Form
     */
    @GetMapping("/main/{date}/{time}/{customerOid}")
    public String mainForm(@PathVariable String date, @PathVariable String time, @PathVariable Integer customerOid, Model model) {
        Customer customer = userService.findOneCustomer(customerOid);
        List<TablesParam> allTablesParam = reservationService.getAllTablesParam(date, time);
        List<Reservation> allReservations = reservationService.findAllReservations();
        int count = 0;
        for (TablesParam tables : allTablesParam) {
            if (tables.isEmpty()) count++;
        }
        model.addAttribute("customer", customer);
        model.addAttribute("tables", allTablesParam);
        model.addAttribute("count", count);
        model.addAttribute("allReservationCount", allReservations.size());
        return "basic/reservation/main";
    }

    /**
     * 메인 테이블 조회
     */
    @PostMapping("/main/**/**/{customerOid}")
    public String mainQuery(@PathVariable Integer customerOid, String datePicker, RedirectAttributes redirectAttributes) {
        return "redirect:/main/"+datePicker.replace(" ", "/")+"/"+customerOid.toString();
    }

    /**
     * 사전 예약 등록
     */
    @GetMapping("/reservation/{customerOid}/create")
    public String reservationCreateForm(@PathVariable Integer customerOid, Model model) {
        List<Tables> tables = reservationService.findAllTables();
        Customer customer = userService.findOneCustomer(customerOid);
        model.addAttribute("tables", tables);
        model.addAttribute("customer", customer);
        return "basic/reservation/reservationCreateForm";
    }

    /**
     * 사전 예약 등록 요청
     */
    @PostMapping("reservation/{customerOid}/create")
    public String reservationCreate(@PathVariable Integer customerOid, ReservationParam param, RedirectAttributes redirectAttributes) {
        System.out.println("param = " + param);
        String newTime = param.getTime().substring(0, param.getTime().indexOf(" ")).concat(":00");
        param.setTime(newTime);
        for (Integer tablesOid : param.getTablesOid()) {
            if (!reservationService.isBookable(tablesOid, param.getDate(), param.getTime())) {
                return "redirect:/reservation/"+customerOid+"/create";
            }
        }
        reservationService.makePreReservation(param);
        return "redirect:/user/info/"+customerOid;
    }

    /**
     * 사전 예약 수정
     */
    @GetMapping("/reservation/{customerOid}/edit/{reservationOid}")
    public String reservationEditFrom(@PathVariable Integer customerOid, @PathVariable Integer reservationOid, Model model) {
        Customer customer = userService.findOneCustomer(customerOid);
        Reservation reservation = reservationService.findOneReservation(reservationOid);
        List<Tables> tables = reservationService.findAllTables();

        model.addAttribute("customer", customer);
        model.addAttribute("reservation", reservation);
        model.addAttribute("tables", tables);
        return "basic/reservation/reservationEditForm";
    }

    /**
     * 사전 예약 수정 요청
     */
    @PostMapping("reservation/{customerOid}/edit/{reservationOid}")
    public String reservationEdit(@PathVariable Integer customerOid, @PathVariable Integer reservationOid, ReservationParam param, RedirectAttributes redirectAttributes) {
        Reservation reservation = reservationService.findOneReservation(reservationOid);
        String date = reservation.getDate().toString();
        String time = reservation.getTime().toString()+":00";
        for (Integer tablesOid : param.getTablesOid()) {
            Tables tables = reservationService.findOneTables(tablesOid);
            if (!reservation.getTables().contains(tables)) {
                if (!reservationService.isBookable(tablesOid, date, time)) {
                    return "redirect:/reservation/{customerOid}/edit/{reservationOid}";
                }
            }
        }
        reservationService.modifyReservationTable(reservationOid, param.getTablesOid().toArray(new Integer[0]));
        reservationService.modifyReservationCovers(reservationOid, param.getCovers());
        return "redirect:/user/info/{customerOid}";
    }

    /**
     * 사전 예약 삭제
     */
    @GetMapping("/reservation/{customerOid}/delete/{reservationOid}")
    public String reservationDeleteFrom(@PathVariable Integer customerOid, @PathVariable Integer reservationOid, Model model) {
        Customer customer = userService.findOneCustomer(customerOid);
        Reservation reservation = reservationService.findOneReservation(reservationOid);

        model.addAttribute("customer", customer);
        model.addAttribute("reservation", reservation);
        return "basic/reservation/reservationDeleteForm";
    }

    /**
     * 사전 예약 삭제 요청
     */
    @PostMapping("reservation/{customerOid}/delete/{reservationOid}")
    public String reservationDelete(@PathVariable Integer customerOid, @PathVariable Integer reservationOid) {
        reservationService.cancelReservation(reservationOid);
        return "redirect:/user/info/{customerOid}";
    }

    // TODO - [POST] 사전 예약 요청
    // TODO - [GET] 예약 완료
}
