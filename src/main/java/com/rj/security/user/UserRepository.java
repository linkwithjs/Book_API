package com.rj.security.user;

// import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

     Optional<User> findByEmail(String email);
//    Boolean findByEmail(String email);

}
