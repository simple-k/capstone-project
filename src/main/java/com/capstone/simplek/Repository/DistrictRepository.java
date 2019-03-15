package com.capstone.simplek.Repository;

import com.capstone.simplek.Model.District;
import org.springframework.data.jpa.repository.JpaRepository;

// comes with built in findAll, findOne, save, and delete methods
// JpaRepository implements the same functionality as the CrudRepository
public interface DistrictRepository extends JpaRepository<District, String> {


}
