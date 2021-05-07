package kgu.sw.team1.booksys.web.service;

import kgu.sw.team1.booksys.domain.Admin;
import kgu.sw.team1.booksys.domain.Customer;
import kgu.sw.team1.booksys.domain.Grade;
import kgu.sw.team1.booksys.domain.User;
import kgu.sw.team1.booksys.domain.param.UserParam;
import kgu.sw.team1.booksys.repository.AdminRepository;
import kgu.sw.team1.booksys.repository.CustomerRepository;
import kgu.sw.team1.booksys.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    private CustomerRepository customerRepository;
    private AdminRepository adminRepository;

    public UserService(UserRepository userRepository, CustomerRepository customerRepository, AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.adminRepository = adminRepository;
    }

    /**
     * 회원 가입
     */
    public Customer signUp(UserParam param) {
        if (userRepository.findById(param.getId()) != null) return null;

        User user = new User();
        user.setGrade(Grade.BASIC);
        user.setId(param.getId());
        user.setPw(param.getPw());
        User savedUser = userRepository.save(user);

        Customer customer = new Customer();
        customer.setUser(savedUser);
        customer.setName(param.getName());
        customer.setPhoneNumber(param.getPhoneNumber());
        customer.setEmail(param.getEmail());
        return customerRepository.save(customer);
    }

    /**
     * 관리자 계정 생성
     */
    public Admin createAdmin(UserParam param) {
        if (userRepository.findById(param.getId()) != null) return null;

        User user = new User();
        user.setGrade(Grade.ADMIN);
        user.setId(param.getId());
        user.setPw(param.getPw());
        User savedUser = userRepository.save(user);

        Admin admin = new Admin();
        admin.setUser(savedUser);
        admin.setName(param.getName());
        return adminRepository.save(admin);
    }

    /**
     * 고객 로그인
     */
    public User login(String id, String pw) {
        try {
            return userRepository.findByIdAndPw(id, pw);
        } catch (DataIntegrityViolationException e) {
            return null;
        }
    }

    /**
     * 회원 등급 변경
     */
    public Customer changeGrade(Integer customerOid, Grade grade) {
        Customer customer = customerRepository.findById(customerOid).get();
        customer.getUser().setGrade(grade);
        return customerRepository.save(customer);
    }

    /**
     * 회원 정보 변경
     */
    public Customer modify(Integer customerOid, UserParam param) {
        Customer customer = customerRepository.findById(customerOid).get();

        customer.getUser().setId(param.getId());
        customer.getUser().setPw(param.getPw());

        customer.setName(param.getName());
        customer.setPhoneNumber(param.getPhoneNumber());
        customer.setEmail(param.getEmail());
        return customerRepository.save(customer);
    }

    /**
     * 계정 삭제
     */
    public void deleteAccount(Integer userOid) {
        User user = userRepository.findById(userOid).get();
        userRepository.delete(user);
    }

    /**
     * 회원 확인
     */
    public Grade getUserGrade(String id, String pw) {
        User user = userRepository.findByIdAndPw(id, pw);
        if (user != null) return user.getGrade();
        else return null;
    }

    /**
     * 회원 조회
     */
    public Customer getCustomer(Integer customerOid) {
        return customerRepository.findById(customerOid).get();
    }

    /**
     * 관리자 조회
     */
    public Admin getAdmin(Integer adminOid) {
        return adminRepository.findById(adminOid).get();
    }
}
