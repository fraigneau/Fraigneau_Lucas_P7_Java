package com.poseidoncapitalsolutions.trading.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.poseidoncapitalsolutions.trading.dto.RatingDTO;
import com.poseidoncapitalsolutions.trading.mapper.RatingMapper;
import com.poseidoncapitalsolutions.trading.model.Rating;
import com.poseidoncapitalsolutions.trading.service.RatingService;

@WebMvcTest(RatingController.class)
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RatingService ratingService;

    @MockitoBean
    private RatingMapper ratingMapper;

    private Rating rating;
    private RatingDTO ratingDTO;
    private List<RatingDTO> ratingDTOs;

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

        ratingDTOs = new ArrayList<>();
        ratingDTOs.add(ratingDTO);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void homeShouldReturnRatingListPage() throws Exception {
        // Given
        List<Rating> ratings = new ArrayList<>();
        ratings.add(rating);
        when(ratingService.findAll()).thenReturn(ratings);
        when(ratingService.getListResponseDTO(ratings)).thenReturn(ratingDTOs);

        // When & Then
        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("ratings"))
                .andExpect(model().attribute("ratings", ratingDTOs));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void addRatingFormShouldReturnAddRatingPage() throws Exception {
        // When & Then
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(model().attributeExists("newRating"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void validateShouldRedirectToRatingListWhenNoErrors() throws Exception {
        // Given
        when(ratingMapper.toEntity(any(RatingDTO.class))).thenReturn(rating);
        when(ratingService.save(rating)).thenReturn(rating);

        // When & Then
        mockMvc.perform(post("/rating/validate")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("moodysRating", "Aaa")
                .param("sandPRating", "AAA")
                .param("fitchRating", "AAA")
                .param("orderNumber", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService, times(1)).save(any(Rating.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void validateShouldReturnToAddPageWhenValidationErrors() throws Exception {
        // When & Then
        mockMvc.perform(post("/rating/validate")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("moodysRating", "") // Empty Moody's rating should cause validation error
                .param("sandPRating", "AAA")
                .param("fitchRating", "AAA")
                .param("orderNumber", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void showUpdateFormShouldReturnUpdatePage() throws Exception {
        // Given
        when(ratingService.findById(anyInt())).thenReturn(rating);
        when(ratingMapper.toDto(rating)).thenReturn(ratingDTO);

        // When & Then
        mockMvc.perform(get("/rating/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attributeExists("updatedRating"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void updateRatingShouldRedirectToRatingListWhenNoErrors() throws Exception {
        // Given
        doNothing().when(ratingService).update(any(RatingDTO.class));

        // When & Then
        mockMvc.perform(post("/rating/update/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1")
                .param("moodysRating", "Aaa")
                .param("sandPRating", "AAA")
                .param("fitchRating", "AAA")
                .param("orderNumber", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService, times(1)).update(any(RatingDTO.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteRatingShouldRedirectToRatingList() throws Exception {
        // Given
        when(ratingService.findById(anyInt())).thenReturn(rating);
        doNothing().when(ratingService).delete(any(Rating.class));

        // When & Then
        mockMvc.perform(get("/rating/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService, times(1)).delete(any(Rating.class));
    }
}