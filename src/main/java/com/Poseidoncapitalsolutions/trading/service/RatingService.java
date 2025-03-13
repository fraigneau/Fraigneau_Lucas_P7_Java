package com.poseidoncapitalsolutions.trading.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.poseidoncapitalsolutions.trading.dto.RatingDTO;
import com.poseidoncapitalsolutions.trading.exception.ResourceNotFoundException;
import com.poseidoncapitalsolutions.trading.mapper.RatingMapper;
import com.poseidoncapitalsolutions.trading.model.Rating;
import com.poseidoncapitalsolutions.trading.repository.RatingRepository;

/**
 * Service class responsible for handling operations related to Ratings.
 * Provides methods for CRUD operations, mapping, and managing rating data.
 */
@Service
public class RatingService implements GenericService<Rating> {

    private RatingRepository ratingRepository;
    private RatingMapper ratingMapper;

    /**
     * Constructs a RatingService with the given repository and mapper.
     * 
     * @param ratingRepository The repository to interact with Rating data.
     * @param ratingMapper     The mapper to convert Rating entities to DTOs.
     */
    public RatingService(RatingRepository ratingRepository, RatingMapper ratingMapper) {
        this.ratingRepository = ratingRepository;
        this.ratingMapper = ratingMapper;
    }

    /**
     * Retrieves all Ratings from the repository.
     * 
     * @return A list of all Ratings.
     */
    @Override
    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    /**
     * Retrieves a Rating by its ID.
     * 
     * @param id The ID of the Rating to retrieve.
     * @return The Rating with the specified ID.
     * @throws ResourceNotFoundException If no Rating with the given ID is found.
     */
    @Override
    public Rating findById(int id) {
        return ratingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rating with id " + id + " not found"));
    }

    /**
     * Saves a given Rating entity.
     * 
     * @param Object The Rating entity to save.
     * @return The saved Rating entity.
     */
    @Override
    public Rating save(Rating Object) {
        return ratingRepository.save(Object);
    }

    /**
     * Deletes the specified Rating entity.
     * 
     * @param Object The Rating entity to delete.
     */
    @Override
    public void delete(Rating Object) {
        ratingRepository.delete(Object);
    }

    /**
     * Converts a list of Rating entities to a list of RatingDTOs.
     * 
     * @param ratings The list of Rating entities to convert.
     * @return A list of RatingDTOs corresponding to the provided Ratings.
     */
    public List<RatingDTO> getListResponseDTO(List<Rating> ratings) {
        return ratings.stream()
                .map(ratingMapper::toDto)
                .toList();
    }

    /**
     * Updates the Rating entity based on the given RatingDTO.
     * 
     * @param ratingDTO The RatingDTO containing updated information.
     */
    public void update(RatingDTO ratingDTO) {
        ratingRepository.save(merge(ratingDTO));
    }

    /**
     * Merges the data from a RatingDTO into an existing Rating entity.
     * 
     * @param ratingDTO The RatingDTO containing the updated data.
     * @return The updated Rating entity.
     */
    private Rating merge(RatingDTO ratingDTO) {
        Rating rating = findById(ratingDTO.getId());
        rating.setFitchRating(ratingDTO.getFitchRating());
        rating.setSandPRating(ratingDTO.getSandPRating());
        rating.setMoodysRating(ratingDTO.getMoodysRating());
        rating.setOrderNumber(ratingDTO.getOrderNumber());
        return rating;
    }
}
