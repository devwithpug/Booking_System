package kgu.sw.team1.booksys.web.controller;

import kgu.sw.team1.booksys.domain.*;
import kgu.sw.team1.booksys.web.service.AdminService;
import kgu.sw.team1.booksys.web.service.ReservationService;
import kgu.sw.team1.booksys.web.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
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
     * 관리자 페이지
     */
    @GetMapping("/admin")
    public String admin(User user, Model model, RedirectAttributes redirectAttributes) {
        if (user.getGrade() != Grade.ADMIN) {
            redirectAttributes.addAttribute("user", user);
            return "redirect:/reservation/main";
        }
        List<Reservation> allReservations = reservationService.findAllReservations();
        List<Tables> allTables = adminService.findAll();
        List<User> allUsers = userService.findAllUsers();
        List<Customer> allCustomers = userService.findAllCustomers();
        List<Admin> allAdmins = userService.findAllAdmins();

        model.addAttribute("allReservations", allReservations);
        model.addAttribute("allTables", allTables);
        model.addAttribute("allUsers", allUsers);
        model.addAttribute("allCustomers", allCustomers);
        model.addAttribute("allAdmins", allAdmins);

        return "";
    }

    // TODO - 회원 관리 페이지 등등
    // TODO - 통계 관련 페이지
}
