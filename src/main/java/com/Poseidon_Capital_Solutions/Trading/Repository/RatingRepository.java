package com.Poseidon_Capital_Solutions.Trading.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Poseidon_Capital_Solutions.Trading.model.Rating;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

}
