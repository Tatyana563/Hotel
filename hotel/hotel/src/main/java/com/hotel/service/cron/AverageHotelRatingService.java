package com.hotel.service.cron;

import com.hotel.model.dto.AverageHotelRatingDto;
import com.hotel.model.entity.AverageCustomersFeedback;
import com.hotel.repository.AverageCustomersFeedbackRepository;
import com.hotel.repository.CustomersFeedbackRepository;
import com.hotel.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AverageHotelRatingService {

    private final CustomersFeedbackRepository hotelRatingRepository;
    private final HotelRepository hotelRepository;
    private final AverageCustomersFeedbackRepository averageHotelRatingRepository;

    @Scheduled(cron = "* * * * * ?")
    @Transactional
    public void calculateAverageRating() {
        List<AverageHotelRatingDto> averageHotelRatingDtos = hotelRatingRepository.calculateAverageMarkGroupById();

        for (AverageHotelRatingDto dto : averageHotelRatingDtos) {
            // Check if a record for this hotel already exists
            Optional<AverageCustomersFeedback> averageHotelRating = averageHotelRatingRepository.findByHotelId(dto.getHotelId());

            if (averageHotelRating.isPresent()) {
                // If record exists, update the average mark
                averageHotelRating.get().setAverageMark(dto.getAverageMark());
                averageHotelRatingRepository.save(averageHotelRating.get());
            } else {
                // If no record exists, create a new one
                hotelRepository.findById(dto.getHotelId()).ifPresent(hotel -> {
                    AverageCustomersFeedback newRating = new AverageCustomersFeedback();
                    newRating.setHotel(hotel);
                    newRating.setAverageMark(dto.getAverageMark());
                    averageHotelRatingRepository.save(newRating);
                });
            }
        }
    }
}
