package com.hotel.repository.specifications;

import com.hotel.model.FilterDTO;
import com.hotel.model.entity.Hotel;
import com.hotel.model.entity.Hotel_;
import com.hotel.model.entity.Room_;
import com.hotel.model.enumeration.Meals;
import com.hotel.model.enumeration.StarRating;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class HotelSpecification implements Specification<Hotel> {
    private final FilterDTO filterDTO;

    @Override
    public Predicate toPredicate(Root<Hotel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();


        if (filterDTO.getDistance() != null) {
            predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get(Hotel_.DISTANCE), filterDTO.getDistance()));
        }


        if (filterDTO.getPrice() != null) {
            query.distinct(true);
            predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.join(Hotel_.ROOM_LIST).get(Room_.PRICE), filterDTO.getPrice()));
        }
        if (filterDTO.getMeal() != null) {

            Meals meal = filterDTO.getMeal();

            query.distinct(true);
            predicateList.add(criteriaBuilder.equal(root.get(Hotel_.MEALS), meal));
        }

        if (filterDTO.getStarRating() != null) {
            StarRating starRating = filterDTO.getStarRating();

            query.distinct(true);
            predicateList.add(criteriaBuilder.equal(root.get(Hotel_.STAR_RATING), starRating));
        }

        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }
}
