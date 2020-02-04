package com.hotel.service;

import com.hotel.model.entity.Hotel;
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
    public void save(Hotel hotel) {
        hotelRepository.save(hotel);

    }

    @Transactional
    @Override
    public void delete(int id) {
     Hotel hotel = findById(id).get();
     hotelRepository.delete(hotel);
    }

    @Transactional
    public Optional<Hotel> findById(int id){
        Optional<Hotel> hotel = hotelRepository.findById(id);
        return Optional.ofNullable(hotel).orElse(null);
    }
}
