package kgu.sw.team1.booksys.web.controller;

import kgu.sw.team1.booksys.domain.*;
import kgu.sw.team1.booksys.domain.param.UserParam;
import kgu.sw.team1.booksys.web.service.ReservationService;
import kgu.sw.team1.booksys.web.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final ReservationService reservationService;

    public UserController(UserService userService, ReservationService reservationService) {
        this.userService = userService;
        this.reservationService = reservationService;
    }

    /**
     * 로그인 페이지
     */
    @GetMapping
    public String loginForm() {
        return "basic/user/loginForm";
    }

    /**
     * 로그인 요청
     */
    @PostMapping
    public String login(UserParam param, RedirectAttributes redirectAttributes) {
        Grade grade = userService.getUserGrade(param.getId(), param.getPw());
        if (grade == null) return "basic/user/loginForm";

        User user = userService.login(param.getId(), param.getPw());
        List<Reservation> allReservations = reservationService.findAllReservations();
        redirectAttributes.addAttribute("user", user);
        return "redirect:/reservation/main";
    }

    /**
     * 회원가입 페이지
     */
    @GetMapping("/user/signup")
    public String signUpForm() {
        return "basic/user/signUpForm";
    }

    /**
     * 회원가입 요청
     */
    @PostMapping("/user/signup")
    public String signUp(UserParam param) {
        userService.signUp(param);
        return "redirect:/";
    }

    /**
     * 회원정보 페이지
     */
    @GetMapping("/user/info/{customerOid}")
    public String customerInfo(@PathVariable Integer customerOid, Model model) {
        Customer customer = userService.findOneCustomer(customerOid);
        model.addAttribute("customer", customer);
        return "";
    }

    /**
     * 회원정보 변경 요청
     */
    @PostMapping("/user/info/{customerOid}")
    public String modifyCustomer(@PathVariable Integer customerOid, @RequestBody UserParam param, Model model) {
        Customer customer = userService.modify(customerOid, param);
        model.addAttribute("customer", customer);
        return "redirect:";
    }

    /**
     * 회원 탈퇴 요청
     */
    @GetMapping("/user/delete/{userOid}")
    public String deleteAccount(@PathVariable Integer userOid) {
        userService.deleteAccount(userOid);
        return "basic/user/loginForm";
    }
}
