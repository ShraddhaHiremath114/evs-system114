package com.evs.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evs.entity.EvStation;
import java.util.List;


public interface EvRepo extends JpaRepository<EvStation,Integer>{

    Optional<EvStation> findById(int id);

}
