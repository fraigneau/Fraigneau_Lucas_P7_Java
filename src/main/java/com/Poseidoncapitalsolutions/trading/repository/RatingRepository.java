package com.poseidoncapitalsolutions.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poseidoncapitalsolutions.trading.model.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

}
