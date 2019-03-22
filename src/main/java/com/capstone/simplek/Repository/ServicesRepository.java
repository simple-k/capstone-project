package com.capstone.simplek.Repository;

import com.capstone.simplek.Model.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// comes with built in findAll, findOne, save, and delete methods
// JpaRepository implements the same functionality as the CrudRepository
@Repository
public interface ServicesRepository extends JpaRepository<Services, String> {

}
