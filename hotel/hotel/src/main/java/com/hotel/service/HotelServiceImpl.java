package com.hotel.service;

import com.hotel.model.HotelEntity;
import com.hotel.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    @Transactional
    @Override
    public void save(HotelEntity hotelEntity) {
        hotelRepository.save(hotelEntity);
    }
}
