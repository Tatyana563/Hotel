package com.hotel.service;

import com.hotel.model.HotelEntity;
import com.hotel.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    @Transactional
    @Override
    public void save(HotelEntity hotelEntity) {
        hotelRepository.save(hotelEntity);

    }

    @Transactional
    @Override
    public void delete(int id) {
     HotelEntity hotelEntity = findById(id).get();
     hotelRepository.delete(hotelEntity);
    }

    @Transactional
    public Optional<HotelEntity> findById(int id){
        Optional<HotelEntity> hotel = hotelRepository.findById(id);
        return Optional.ofNullable(hotel).orElse(null);
    }
}
