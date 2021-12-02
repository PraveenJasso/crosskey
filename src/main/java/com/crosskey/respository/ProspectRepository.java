package com.crosskey.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crosskey.model.Prospect;

@Repository
public interface ProspectRepository extends JpaRepository<Prospect, String> {

}
