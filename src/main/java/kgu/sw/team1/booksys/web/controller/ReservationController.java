package kgu.sw.team1.booksys.web.controller;

import kgu.sw.team1.booksys.domain.Reservation;
import kgu.sw.team1.booksys.domain.User;
import kgu.sw.team1.booksys.web.service.ReservationService;
import kgu.sw.team1.booksys.web.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
     * 메인 페이지
     */
    @GetMapping("/reservation/main")
    public String main(User user, Model model) {
        List<Reservation> allReservations = reservationService.findAllReservations();
        model.addAttribute("user", user);
        model.addAttribute("allReservations", allReservations);
        return "basic/reservation/main";
    }

}
