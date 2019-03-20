package com.capstone.simplek.Repository;
import com.capstone.simplek.Model.Children;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

// comes with built in findAll, findOne, save, and delete methods
// JpaRepository implements the same functionality as the CrudRepository
@Repository
public interface ChildrenRepository extends JpaRepository<Children, Long> {

    @Query(value = "SELECT * FROM children WHERE user_id = :user_id", nativeQuery = true)
    List<Children> getAllChildren(@Param("user_id") long user_id);

    @Query(value = "SELECT * FROM children WHERE user_id = :user_id LIMIT 1", nativeQuery = true)
    Children getFirstChild(@Param("user_id") long user_id);

}