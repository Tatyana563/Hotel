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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class HotelSpecification implements Specification<Hotel> {
    private final Meals meal;
    private final Integer distance;
    private final Integer price;
    private final StarRating starRating;
//TODO: hotel specifi with builder, extract filterDTO fields on hotelSpecification level; (instead of   private final FilterDTO filterDTO add all fields L23)
    @Override
    public Predicate toPredicate(Root<Hotel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();
        // Add condition to exclude deleted hotels
        predicateList.add(criteriaBuilder.equal(root.get(Hotel_.IS_DELETED), Boolean.FALSE));

        if (distance != null) {
            predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get(Hotel_.DISTANCE), distance));
        }

        if (price != null) {
            query.distinct(true);
            predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.join(Hotel_.ROOM_LIST).get(Room_.PRICE), price));
        }
        if (meal != null) {

            query.distinct(true);
            predicateList.add(criteriaBuilder.equal(root.get(Hotel_.MEALS), meal));
        }

        if (starRating != null) {

            query.distinct(true);
            predicateList.add(criteriaBuilder.equal(root.get(Hotel_.STAR_RATING), starRating));
        }

        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }
    public static class HotelSpecificationBuilder {
        private Meals meal;
        private Integer distance;
        private Integer price;
        private StarRating starRating;

        public HotelSpecificationBuilder meal(Meals meal) {
            this.meal = meal;
            return this;
        }

        public HotelSpecificationBuilder distance(Integer distance) {
            this.distance = distance;
            return this;
        }

        public HotelSpecificationBuilder price(Integer price) {
            this.price = price;
            return this;
        }

        public HotelSpecificationBuilder starRating(StarRating starRating) {
            this.starRating = starRating;
            return this;
        }

        public HotelSpecification build() {
            return new HotelSpecification(meal, distance, price, starRating);
        }
    }
}
