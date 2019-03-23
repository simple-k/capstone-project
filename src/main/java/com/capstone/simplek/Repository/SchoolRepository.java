package com.capstone.simplek.Repository;

import com.capstone.simplek.Model.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// comes with built in findAll, findOne, save, and delete methods
// JpaRepository implements the same functionality as the CrudRepository
public interface SchoolRepository extends JpaRepository<School, String> {
    @Query(
            value = "SELECT * FROM schools WHERE district_id = :district_id",
            nativeQuery = true)
    List<School> findSchoolsWithinDistrictById (@Param("district_id") long district_id);
}
