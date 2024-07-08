package com.evs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evs.dao.EvRepo;
import com.evs.entity.EvStation;
import com.evs.helper.ResourceNotFoundException;

@Service
public class EvServiceImpl implements EvService{

    

    @Autowired
    private EvRepo evRepo;

    @Override
    public EvStation saveEvStation(EvStation evStation) {
        return evRepo.save(evStation);
    }

    @Override
    public Optional<EvStation> getEvStationById(int id) {
        return evRepo.findById(id);
    }

    @Override
    public List<EvStation> getAllEvStations() {
        return evRepo.findAll();
    }

    @Override
    public Optional<EvStation> updateEvStationData(EvStation evStation) {
        EvStation ev=evRepo.findById(evStation.getId()).orElseThrow(()->new ResourceNotFoundException("Ev Station not exist"));
        ev.setAddress(evStation.getAddress());
        ev.setCity(evStation.getCity());
        ev.setEvname(evStation.getEvname());
        ev.setLatitude(evStation.getLatitude());
        ev.setLongitude(evStation.getLongitude());
        ev.setPincode(evStation.getPincode());
        ev.setState(evStation.getState());
        ev.setEvimg(evStation.getEvimg());
        EvStation save=evRepo.save(ev);
        return Optional.ofNullable(save);
    }

    @Override
    public void deleteEvStation(int id) {
        evRepo.deleteById(id);
        
    }


}
