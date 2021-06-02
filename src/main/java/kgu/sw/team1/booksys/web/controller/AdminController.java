package kgu.sw.team1.booksys.web.controller;

import kgu.sw.team1.booksys.domain.*;
import kgu.sw.team1.booksys.domain.param.TablesParam;
import kgu.sw.team1.booksys.web.service.AdminService;
import kgu.sw.team1.booksys.web.service.ReservationService;
import kgu.sw.team1.booksys.web.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin/{adminOid}")
public class AdminController {

    private final AdminService adminService;
    private final ReservationService reservationService;
    private final UserService userService;

    public AdminController(AdminService adminService, ReservationService reservationService, UserService userService) {
        this.adminService = adminService;
        this.reservationService = reservationService;
        this.userService = userService;
    }

    /**
     * 메인 페이지 리다이렉트
     */
    @GetMapping
    public String admin(@PathVariable Integer adminOid) {
        return "redirect:/admin/{adminOid}/main";
    }


    /**
     * 관리자 메인 페이지
     */
    @GetMapping("/main")
    public String adminMain(@PathVariable Integer adminOid, Model model) {
        Admin admin = userService.findOneAdmin(adminOid);
        if (admin == null) {
            return "redirect:/";
        }
        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        LocalDate thisMonth = LocalDate.now();

        List<Reservation> last1 = reservationService.findAllReservationsBetween(lastMonth.withDayOfMonth(1), lastMonth.withDayOfMonth(lastMonth.lengthOfMonth()));
        List<ReservationHistory> last2 = adminService.findAllHistoriesByDateBetween(lastMonth.withDayOfMonth(1), lastMonth.withDayOfMonth(lastMonth.lengthOfMonth()));
        List<Reservation> this1 = reservationService.findAllReservationsBetween(thisMonth.withDayOfMonth(1), thisMonth.withDayOfMonth(thisMonth.lengthOfMonth()));
        List<ReservationHistory> this2 = adminService.findAllHistoriesByDateBetween(thisMonth.withDayOfMonth(1), thisMonth.withDayOfMonth(thisMonth.lengthOfMonth()));
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "oid"));
        Page<Reservation> page = reservationService.findAllReservations(pageRequest);

        List<Reservation> reservations = reservationService.findAllReservations();
        List<WalkIn> walkIns = reservationService.findAllWalkIns();
        List<Integer> waitingList = reservationService.getWaitingList();
        List<Tables> tables = adminService.findAll();
        Integer totalCustomers = 0;
        for (ReservationHistory history : last2) {
            totalCustomers += history.getCovers();
        }
        for (ReservationHistory history : this2) {
            totalCustomers += history.getCovers();
        }
        for (WalkIn walkIn : walkIns) {
            totalCustomers += walkIn.getCovers();
        }
        for (Reservation reservation : reservations) {
            totalCustomers += reservation.getCovers();
        }

        model.addAttribute("admin", admin);
        model.addAttribute("lastMonthReservations", last1.size() + last2.size());
        model.addAttribute("thisMonthReservations", this1.size() + this2.size());
        model.addAttribute("reservations", page.toList());
        model.addAttribute("waitingList", waitingList);
        model.addAttribute("tables", tables);
        model.addAttribute("totalCustomers", totalCustomers);

        return "/basic/admin/admin";
    }

    /**
     * 예약 기록 페이지
     */
    @GetMapping("/history")
    public String reservationHistories(@PathVariable Integer adminOid, Model model) {
        List<ReservationHistory> histories = adminService.findAllHistoriesDesc();
        Admin admin = userService.findOneAdmin(adminOid);
        model.addAttribute("admin", admin);
        model.addAttribute("histories", histories);

        return "/basic/admin/adminHistory";
    }

    /**
     * 현장 예약 추가 요청
     */
    @PostMapping("/main")
    public String makeOnSiteReservation(@PathVariable Integer adminOid, Integer number) {
        List<WalkIn> walkIns = reservationService.makeOnSiteReservation(number);

        return "redirect:/admin/{adminOid}/main";
    }

    /**
     * 관리자 현장 예약 관리
     */
    @GetMapping("/walkin")
    public String adminWalkIn(@PathVariable Integer adminOid, Model model) {
        List<WalkIn> walkIns = reservationService.findAllWalkIns();
        Admin admin = userService.findOneAdmin(adminOid);
        model.addAttribute("admin", admin);
        model.addAttribute("walkIns", walkIns);

        return "/basic/admin/adminWalkIn";
    }

    /**
     * 관리자 현장 예약 완료 요청
     */
    @PostMapping("/walkin")
    public String finishWalkIn(@PathVariable Integer adminOid, Integer walkInOid) {
        WalkIn walkIn = reservationService.findOneWalkIn(walkInOid);
        adminService.setReservationArrival(walkIn);
        reservationService.finishWalkIn(walkInOid);
        adminService.setEmptyTables();

        return "redirect:/admin/{adminOid}/walkin";
    }

    /**
     * 관리자 사전 예약 관리
     */
    @GetMapping("/reservation")
    public String adminReservation(Model model, @PathVariable Integer adminOid) {
        List<Reservation> reservations = reservationService.findAllReservations();
        Admin admin = userService.findOneAdmin(adminOid);
        model.addAttribute("admin", admin);
        model.addAttribute("reservations", reservations);

        return "/basic/admin/adminReservation";
    }

    /**
     * 관리자 사전 예약 삭제
     */
    @GetMapping("/reservation/{reservationOid}/delete")
    public String adminReservationDeleteForm(@PathVariable Integer reservationOid, @PathVariable Integer adminOid, Model model) {
        Reservation reservation = reservationService.findOneReservation(reservationOid);
        Admin admin = userService.findOneAdmin(adminOid);
        model.addAttribute("admin", admin);
        model.addAttribute("reservation", reservation);

        return "/basic/admin/deleteReservationForm";
    }

    /**
     * 관리자 사전 예약 삭제 요청
     */
    @PostMapping("/reservation/{reservationOid}/delete")
    public String deleteReservation(@PathVariable Integer reservationOid, @PathVariable Integer adminOid) {
        reservationService.cancelReservation(reservationOid);
        adminService.setEmptyTables();

        return "redirect:/admin/{adminOid}/reservation";
    }

    /**
     * 관리자 사전 예약 완료 요청
     */
    @PostMapping("/reservation")
    public String finishReservation(@PathVariable Integer adminOid, Integer reservationOid) {
        Reservation reservation = reservationService.findOneReservation(reservationOid);
        adminService.setReservationArrival(reservation);
        reservationService.cancelReservation(reservationOid);
        adminService.setEmptyTables();

        return "redirect:/admin/{adminOid}/reservation";
    }

    /**
     * 관리자 테이블 관리
     */
    @GetMapping("/tables")
    public String adminTables(Model model, @PathVariable Integer adminOid) {
        List<Tables> tables = reservationService.findAllTables();
        Admin admin = userService.findOneAdmin(adminOid);
        model.addAttribute("admin", admin);
        model.addAttribute("tables", tables);

        return "/basic/admin/adminTables";
    }

    /**
     * 관리자 테이블 생성
     */
    @GetMapping("/tables/create")
    public String adminTablesCreateForm(@PathVariable Integer adminOid, Model model) {
        Admin admin = userService.findOneAdmin(adminOid);
        model.addAttribute("admin", admin);
        return "/basic/admin/addTablesForm";
    }

    /**
     * 관리자 테이블 생성 요청
     */
    @PostMapping("/tables/create")
    public String createTables(@PathVariable Integer adminOid, TablesParam param) {
        adminService.addTables(param);

        return "redirect:/admin/{adminOid}/tables";
    }

    /**
     * 관리자 테이블 수정
     */
    @GetMapping("/tables/{tablesOid}/edit")
    public String adminTablesEditForm(@PathVariable Integer tablesOid, @PathVariable Integer adminOid, Model model) {
        Tables tables = adminService.findOneWithOid(tablesOid);
        Admin admin = userService.findOneAdmin(adminOid);
        model.addAttribute("admin", admin);
        model.addAttribute("tables", tables);

        return "/basic/admin/editTablesForm";
    }

    /**
     * 관리자 테이블 수정 요청
     */
    @PostMapping("/tables/{tablesOid}/edit")
    public String editTables(@PathVariable Integer tablesOid, @PathVariable Integer adminOid, TablesParam param) {
        Tables tables = adminService.findOneWithOid(tablesOid);
        adminService.modifyTables(tables.getNumber(), param);

        return "redirect:/admin/{adminOid}/tables";
    }


    /**
     * 관리자 테이블 삭제
     */
    @GetMapping("/tables/{tablesOid}/delete")
    public String adminTablesDeleteForm(@PathVariable Integer tablesOid, @PathVariable Integer adminOid, Model model) {
        Tables tables = adminService.findOneWithOid(tablesOid);
        Admin admin = userService.findOneAdmin(adminOid);
        model.addAttribute("admin", admin);
        model.addAttribute("tables", tables);

        return "/basic/admin/deleteTablesForm";
    }

    /**
     * 관리자 테이블 삭제 요청
     */
    @PostMapping("/tables/{tablesOid}/delete")
    public String deleteTables(@PathVariable Integer tablesOid, @PathVariable Integer adminOid) {
        adminService.deleteTablesWithOid(tablesOid);

        return "redirect:/admin/{adminOid}/tables";
    }

    /**
     * 관리자 고객 관리
     */
    @GetMapping("/customer")
    public String adminCustomerForm(@PathVariable Integer adminOid, Model model) {
        List<Customer> customers = userService.findAllCustomers();
        Admin admin = userService.findOneAdmin(adminOid);
        model.addAttribute("admin", admin);
        model.addAttribute("customers", customers);

        return "basic/admin/adminCustomer";
    }

    /**
     * 관리자 고객 삭제
     */
    @GetMapping("/customer/{customerOid}/delete")
    public String adminCustomerDeleteForm(@PathVariable Integer customerOid, @PathVariable Integer adminOid, Model model) {
        Customer customer = userService.findOneCustomer(customerOid);
        Admin admin = userService.findOneAdmin(adminOid);
        model.addAttribute("admin", admin);
        model.addAttribute("customer", customer);

        return "basic/admin/deleteCustomerForm";
    }

    /**
     * 관리자 고객 삭제 요청
     */
    @PostMapping("/customer/{customerOid}/delete")
    public String deleteCustomer(@PathVariable Integer customerOid, @PathVariable Integer adminOid) {
        Customer customer = userService.findOneCustomer(customerOid);
        userService.deleteAccount(customer.getUser().getOid());

        return "redirect:/admin/{adminOid}/customer";
    }
}
