package kgu.sw.team1.booksys.repository;

import kgu.sw.team1.booksys.domain.Customer;
import kgu.sw.team1.booksys.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findByPhoneNumber(String phoneNumber);
    Customer findByUser(User user);
    Customer findByUserId(String id);
}
