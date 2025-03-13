package com.poseidoncapitalsolutions.trading.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.poseidoncapitalsolutions.trading.dto.RatingDTO;
import com.poseidoncapitalsolutions.trading.exception.ResourceNotFoundException;
import com.poseidoncapitalsolutions.trading.mapper.RatingMapper;
import com.poseidoncapitalsolutions.trading.model.Rating;
import com.poseidoncapitalsolutions.trading.repository.RatingRepository;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private RatingMapper ratingMapper;

    @InjectMocks
    private RatingService ratingService;

    private Rating rating;
    private RatingDTO ratingDTO;

    @BeforeEach
    void setUp() {
        // Initialize test data
        rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("Aaa");
        rating.setSandPRating("AAA");
        rating.setFitchRating("AAA");
        rating.setOrderNumber(1);

        ratingDTO = new RatingDTO();
        ratingDTO.setId(1);
        ratingDTO.setMoodysRating("Aaa");
        ratingDTO.setSandPRating("AAA");
        ratingDTO.setFitchRating("AAA");
        ratingDTO.setOrderNumber(1);
    }

    @Test
    void findAllShouldReturnListOfRatings() {
        // Given
        List<Rating> ratings = Arrays.asList(rating);
        when(ratingRepository.findAll()).thenReturn(ratings);

        // When
        List<Rating> result = ratingService.findAll();

        // Then
        assertEquals(1, result.size());
        assertEquals("Aaa", result.get(0).getMoodysRating());
    }

    @Test
    void findByIdShouldReturnRatingWhenExists() {
        // Given
        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

        // When
        Rating result = ratingService.findById(1);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Aaa", result.getMoodysRating());
    }

    @Test
    void findByIdShouldThrowExceptionWhenRatingNotFound() {
        // Given
        when(ratingRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> ratingService.findById(99));
    }

    @Test
    void saveShouldReturnSavedRating() {
        // Given
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        // When
        Rating savedRating = ratingService.save(rating);

        // Then
        verify(ratingRepository, times(1)).save(rating);
        assertEquals(rating, savedRating);
    }

    @Test
    void updateShouldUpdateExistingRating() {
        // Given
        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        // When
        ratingService.update(ratingDTO);

        // Then
        verify(ratingRepository, times(1)).findById(1);
        verify(ratingRepository, times(1)).save(rating);
    }

    @Test
    void deleteShouldRemoveRating() {
        // When
        ratingService.delete(rating);

        // Then
        verify(ratingRepository, times(1)).delete(rating);
    }

    @Test
    void getListResponseDTOShouldReturnListOfDTOs() {
        // Given
        List<Rating> ratings = Arrays.asList(rating);
        when(ratingMapper.toDto(rating)).thenReturn(ratingDTO);

        // When
        List<RatingDTO> result = ratingService.getListResponseDTO(ratings);

        // Then
        assertEquals(1, result.size());
        assertEquals(ratingDTO, result.get(0));
    }
}