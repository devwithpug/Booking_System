package kgu.sw.team1.booksys.repository;

import kgu.sw.team1.booksys.domain.Admin;
import kgu.sw.team1.booksys.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findByUser(User user);
}
