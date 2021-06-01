package kgu.sw.team1.booksys.repository;

import kgu.sw.team1.booksys.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findById(String id);
    User findByIdAndPw(String id, String pw);
}
