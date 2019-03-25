package com.capstone.simplek.Repository;
import com.capstone.simplek.Model.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// comes with built in findAll, findOne, save, and delete methods
// JpaRepository implements the same functionality as the CrudRepository
public interface DistrictRepository extends JpaRepository<District, String> {

    // find district by id from databse
    @Query(value = "SELECT * FROM districts WHERE id = :id", nativeQuery = true)
    District findById (@Param("id") long id);

}