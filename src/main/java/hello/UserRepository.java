package hello;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT t FROM User t WHERE t.salary > 0 AND t.salary < 4000")
    List<User> validUserAndSalary();
}
