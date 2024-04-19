package com.hotel.service.impl;

import com.hotel.exception_handler.exception.BookingRequestNotFoundException;
import com.hotel.model.dto.request.HotelFeedbackRequest;
import com.hotel.model.entity.AverageCustomersFeedback;
import com.hotel.model.entity.BookRequest;
import com.hotel.model.entity.CustomersFeedback;
import com.hotel.model.entity.Hotel;
import com.hotel.repository.AverageCustomersFeedbackRepository;
import com.hotel.repository.BookRequestRepository;
import com.hotel.repository.CustomersFeedbackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RatingServiceImpl {
    private final CustomersFeedbackRepository customersFeedbackRepository;
    private final BookRequestRepository bookRequestRepository;

    private final AverageCustomersFeedbackRepository averageCustomersFeedbackRepository;

    public void addComment(HotelFeedbackRequest hotelFeedbackRequest, int userId) throws IllegalAccessException {
        Instant now = Instant.now();
        int bookRequestId = hotelFeedbackRequest.getBookRequestId();
//TODO: change findById Optional<BookRequest> bookRequest = bookRequestRepository.findByIdAndUserId(bookRequestId,userId);
        BookRequest bookRequest = bookRequestRepository.findById(bookRequestId)
                .orElseThrow(() -> new BookingRequestNotFoundException(bookRequestId));


        boolean commentDateNotMoreThan30DaysFromCheckOut = customersFeedbackRepository.validateDateDifferenceFromCheckOut(bookRequestId, now.minus(30, ChronoUnit.DAYS));
//TODO: custom AbstractBadRequestExc exc+ControllerAdvice HttpStatus.BAD_REQUEST
        if (!commentDateNotMoreThan30DaysFromCheckOut) {
            throw new IllegalAccessException("You can leave comments in a period of 30 days after your check out");
        }
        CustomersFeedback feedback = Optional.ofNullable(bookRequest.getFeedback()).orElseGet(() -> {
            CustomersFeedback newFeedback = new CustomersFeedback();
            newFeedback.setBookRequest(bookRequest);

            return newFeedback;
        });


        feedback.setComment(hotelFeedbackRequest.getComment());
        feedback.setLocation(hotelFeedbackRequest.getLocation().getValue());
        feedback.setPriceQuality(hotelFeedbackRequest.getPriceQuality().getValue());
        feedback.setCleanliness(hotelFeedbackRequest.getCleanliness().getValue());
        double mark = (double) (hotelFeedbackRequest.getLocation().getValue() + hotelFeedbackRequest.getPriceQuality().getValue() + hotelFeedbackRequest.getCleanliness().getValue()) / 3;
        feedback.setMark(mark);
        feedback.setDate(now);

        customersFeedbackRepository.save(feedback);

        calculateAverageFeedback(feedback);
    }

    private void calculateAverageFeedback(CustomersFeedback feedback) {
        Hotel hotel = feedback.getBookRequest().getRoom().getHotel();

        AverageCustomersFeedback averageCustomersFeedback = Optional.ofNullable(hotel.getAverageCustomersFeedback())
                .map(avgFeedback -> {
                    avgFeedback.setAverageMark((avgFeedback.getAverageMark()*avgFeedback.getFeedbacksNumber() + feedback.getMark()) / (avgFeedback.getFeedbacksNumber() + 1));
                    avgFeedback.setFeedbacksNumber(avgFeedback.getFeedbacksNumber() + 1);
                    return avgFeedback;
                })
                .orElseGet(() -> {
                    AverageCustomersFeedback newAverageCustomFeedback = new AverageCustomersFeedback();
                    newAverageCustomFeedback.setAverageMark(feedback.getMark());
                    newAverageCustomFeedback.setFeedbacksNumber(1);
                    newAverageCustomFeedback.setHotel(hotel);
                    return newAverageCustomFeedback;
                });
        averageCustomersFeedbackRepository.save(averageCustomersFeedback);

    }

}


