package com.Poseidon_Capital_Solutions.Trading.service;

import java.util.List;

import com.Poseidon_Capital_Solutions.Trading.Repository.RatingRepository;
import com.Poseidon_Capital_Solutions.Trading.model.Rating;

public class RatingService implements GenericService<Rating> {

    private RatingRepository ratingRepository;

    public RatingService() {
    }

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    @Override
    public Rating findById(int id) {
        return ratingRepository.findById(id).get();
    }

    @Override
    public Rating save(Rating Object) {
        return ratingRepository.save(Object);
    }

    @Override
    public void delete(Rating Object) {
        ratingRepository.delete(Object);
    }

}
