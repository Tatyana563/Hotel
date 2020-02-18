package com.hotel.service;

import com.hotel.model.dto.HotelBriefInfo;
import com.hotel.model.entity.Hotel;
import com.hotel.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

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

    @Override
    public List<HotelBriefInfo> listAllHotelsBriefInfo() {
        return null;
    }

 /*   @Override
    public List<HotelBriefInfo> listAllHotelsBriefInfo() {

        jdbcTemplate.query("select h.name, c.name from hotel h inner join city c on h.city_id = c.id",
                );

        return hotelRepository.listHotelsBriefInfo();
    }*/

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

    @Transactional(readOnly = true)
    public List<Hotel> listAllSorted()
    {
        return hotelRepository.findByOrderByDistanceAsc();
    }

}
