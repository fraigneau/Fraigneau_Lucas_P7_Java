package com.poseidoncapitalsolutions.trading.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.poseidoncapitalsolutions.trading.dto.RatingDTO;
import com.poseidoncapitalsolutions.trading.mapper.RatingMapper;
import com.poseidoncapitalsolutions.trading.model.Rating;
import com.poseidoncapitalsolutions.trading.repository.RatingRepository;

@Service
public class RatingService implements GenericService<Rating> {

    private RatingRepository ratingRepository;
    private RatingMapper ratingMapper;

    public RatingService(RatingRepository ratingRepository, RatingMapper ratingMapper) {
        this.ratingRepository = ratingRepository;
        this.ratingMapper = ratingMapper;
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

    public List<RatingDTO> getListResponseDTO(List<Rating> ratings) {
        return ratings.stream()
                .map(ratingMapper::toDto)
                .toList();
    }

    public void update(RatingDTO ratingDTO) {
        ratingRepository.save(merge(ratingDTO));
    }

    private Rating merge(RatingDTO ratingDTO) {
        Rating rating = findById(ratingDTO.getId());
        rating.setFitchRating(ratingDTO.getFitchRating());
        rating.setSandPRating(ratingDTO.getSandPRating());
        rating.setMoodysRating(ratingDTO.getMoodysRating());
        rating.setOrderNumber(ratingDTO.getOrderNumber());
        return rating;
    }

}
