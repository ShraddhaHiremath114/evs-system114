package com.evs.service;

import java.util.List;
import java.util.Optional;

import com.evs.entity.EvStation;

public interface EvService {

    EvStation saveEvStation(EvStation evStation);

    Optional<EvStation> getEvStationById(int id);
    List<EvStation> getAllEvStations();
    
    Optional<EvStation> updateEvStationData(EvStation evStation);
    void deleteEvStation(int id);

}
