package com.hotel.repository.specifications;

import com.hotel.model.FilterDTO;
import com.hotel.model.entity.Hotel_;
import com.hotel.model.entity.Room;
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
public class RoomSpecification implements Specification<Room> {
    private final FilterDTO filterDTO;

    @Override
    public Predicate toPredicate(Root<Room> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();

        if (filterDTO.getDistance() != null) {
            predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.join(Room_.HOTEL).get(Hotel_.DISTANCE), filterDTO.getDistance()));
        }

        if (filterDTO.getPrice() != null) {
            predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get(Room_.PRICE), filterDTO.getPrice()));
        }
        if (filterDTO.getMeal() != null) {

            Meals meal = filterDTO.getMeal();
            predicateList.add(criteriaBuilder.equal(root.join(Room_.HOTEL).get(Hotel_.MEALS), meal));
        }

        if (filterDTO.getStarRating() != null) {
            StarRating starRating = filterDTO.getStarRating();

            predicateList.add(criteriaBuilder.equal(root.join(Room_.HOTEL).get(Hotel_.STAR_RATING), starRating));
        }

        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }
}
