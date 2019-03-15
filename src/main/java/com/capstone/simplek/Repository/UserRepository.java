package com.capstone.simplek.Repository;
import com.capstone.simplek.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// comes with built in findAll, findOne, save, and delete methods
// JpaRepository implements the same functionality as the CrudRepository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByEmail(String email);

}// interface