package com.hotel.service;

import com.hotel.model.entity.Hotel;
import com.hotel.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
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

    @Transactional(readOnly = true)
    @Override
    public List<Hotel> listAll() {
        return hotelRepository.findAll();
    }

    @Transactional
    public Optional<Hotel> findById(int id){
        Optional<Hotel> hotel = hotelRepository.findById(id);
        return Optional.ofNullable(hotel).orElse(null);
    }
}
