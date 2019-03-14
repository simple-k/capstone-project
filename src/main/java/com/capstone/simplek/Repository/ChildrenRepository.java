package com.capstone.simplek.Repository;

import com.capstone.simplek.Model.Children;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// comes with built in findAll, findOne, save, and delete methods
// JpaRepository implements the same functionality as the CrudRepository
@Repository
public interface ChildrenRepository extends JpaRepository<Children, String> {
}
