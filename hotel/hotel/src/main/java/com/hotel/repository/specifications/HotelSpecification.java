package com.hotel.repository.specifications;

import com.hotel.model.FilterDTO;
import com.hotel.model.entity.Hotel;
import com.hotel.model.entity.Hotel_;
import com.hotel.model.entity.Room_;
import com.hotel.model.enumeration.Meals;
import com.hotel.model.enumeration.StarRating;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Builder
@RequiredArgsConstructor
public class HotelSpecification implements Specification<Hotel> {
    private final Meals meal;
    private final Integer distance;
    private final Integer price;
    private final StarRating starRating;

    public final int ownerId;

    @Override
    public Predicate toPredicate(Root<Hotel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();
        // Add condition to exclude deleted hotels
        predicateList.add(criteriaBuilder.equal(root.get(Hotel_.IS_DELETED), Boolean.FALSE));

      //  query.distinct(true);
        if (distance != null) {
            predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get(Hotel_.DISTANCE), distance));
        }

        if (price != null) {
            predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.join(Hotel_.ROOM_LIST).get(Room_.PRICE), price));
        }
        if (meal != null) {
            predicateList.add(criteriaBuilder.equal(root.get(Hotel_.MEALS), meal));
        }

        if (starRating != null) {
            predicateList.add(criteriaBuilder.equal(root.get(Hotel_.STAR_RATING), starRating));
        }

        if (ownerId != 0) {
            predicateList.add(criteriaBuilder.equal(root.get(Hotel_.OWNER_ID), ownerId));
        }

        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }
}
