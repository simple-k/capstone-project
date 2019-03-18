package com.capstone.simplek.Repository;

import com.capstone.simplek.Model.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// comes with built in findAll, findOne, save, and delete methods
// JpaRepository implements the same functionality as the CrudRepository
public interface SchoolRepository extends JpaRepository<School, String> {
//    @Query(
//            value = "SELECT * FROM schools WHERE ",
//            nativeQuery = true)
//    List<School> findAddress ();
}
