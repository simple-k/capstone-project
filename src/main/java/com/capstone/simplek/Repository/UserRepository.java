package com.capstone.simplek.Repository;
import com.capstone.simplek.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}// interface