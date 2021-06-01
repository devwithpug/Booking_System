package kgu.sw.team1.booksys.web.service;

import kgu.sw.team1.booksys.domain.Admin;
import kgu.sw.team1.booksys.domain.Customer;
import kgu.sw.team1.booksys.domain.Grade;
import kgu.sw.team1.booksys.domain.User;
import kgu.sw.team1.booksys.domain.param.UserParam;
import kgu.sw.team1.booksys.repository.AdminRepository;
import kgu.sw.team1.booksys.repository.CustomerRepository;
import kgu.sw.team1.booksys.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AdminRepository adminRepository;

    @Test
    void 회원_가입() {
        // given
        UserParam param = new UserParam();
        param.setId("abc");
        param.setPw("1234");
        // when
        Customer customer = userService.signUp(param);
        // then
        User result = userRepository.findById("abc");
        User result2 = userRepository.findById(customer.getUser().getOid()).get();
        assertThat(result).isEqualTo(customer.getUser());
        assertThat(result2).isEqualTo(customer.getUser());
    }

    @Test
    void 중복_아이디() {
        // given
        UserParam param = new UserParam();
        param.setId("abc");
        param.setPw("1234");
        // when
        userService.signUp(param);
        Customer duplicatedUser = userService.signUp(param);
        // then
        List<User> allUsers = userRepository.findAll();
        assertThat(duplicatedUser).isNull();
    }

    @Test
    void 로그인() {
        // given
        UserParam param = new UserParam();
        param.setId("abc");
        param.setPw("1234");
        // when
        Customer customer = userService.signUp(param);
        param.setId("admin");
        Admin admin = userService.createAdmin(param);
        // then
        User user = userService.login("abc", "1234");
        Customer result = customerRepository.findByUser(user);
        assertThat(result).isEqualTo(customer);

        User user2 = userService.login("admin", "1234");
        Admin result2 = adminRepository.findByUser(user2);
        assertThat(result2).isEqualTo(admin);
    }

    @Test
    void 회원_등급_변경() {
        // given
        UserParam param = new UserParam();
        param.setId("abc");
        param.setPw("1234");
        // when
        Customer customer = userService.signUp(param);
        // then
        assertThat(customer.getUser().getGrade()).isEqualTo(Grade.BASIC);
        userService.changeGrade(customer.getOid(), Grade.VIP);
        assertThat(customer.getUser().getGrade()).isEqualTo(Grade.VIP);
    }

    @Test
    void 회원_정보_변경() {
        // given
        UserParam param = new UserParam();
        param.setId("abc");
        param.setPw("1234");
        // when
        Customer customer = userService.signUp(param);
        // then
        assertThat(customer.getPhoneNumber()).isNull();
        param.setPhoneNumber("010-1234-5678");
        param.setPw("4321");
        userService.modify(customer.getOid(), param);
        assertThat(customer.getPhoneNumber()).isEqualTo("010-1234-5678");
        assertThat(customer.getUser().getPw()).isEqualTo("4321");
    }

    @Test
    void 계정_삭제() {
        // given
        UserParam param = new UserParam();
        param.setId("abc");
        param.setPw("1234");
        // when
        Customer customer = userService.signUp(param);
        param.setId("qwe");
        userService.signUp(param);
        // then
        userService.deleteAccount(customer.getUser().getOid());
        assertThat(userService.login("qwe", "1234")).isNull();
    }
}