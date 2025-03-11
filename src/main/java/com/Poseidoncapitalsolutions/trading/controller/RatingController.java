package com.poseidoncapitalsolutions.trading.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.poseidoncapitalsolutions.trading.dto.RatingDTO;
import com.poseidoncapitalsolutions.trading.mapper.RatingMapper;
import com.poseidoncapitalsolutions.trading.service.RatingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@Controller
@Tag(name = "Rating Controller", description = "API for rating management")
public class RatingController {

    private RatingService ratingService;
    private RatingMapper ratingMapper;

    public RatingController(RatingService ratingService, RatingMapper ratingMapper) {
        this.ratingService = ratingService;
        this.ratingMapper = ratingMapper;
    }

    @Operation(summary = "Get all ratings", description = "Returns a page with the list of all ratings")
    @GetMapping("/rating/list")
    public String home(Model model) {
        List<RatingDTO> lists = ratingService.getListResponseDTO(ratingService.findAll());
        model.addAttribute("ratings", lists);
        return "rating/list";
    }

    @Operation(summary = "Display rating add form", description = "Returns a page with the form to add a new rating")
    @GetMapping("/rating/add")
    public String addRatingForm(Model model) {
        model.addAttribute("newRating", new RatingDTO());
        return "rating/add";
    }

    @Operation(summary = "Validate and add a new rating", description = "Adds a new rating from form data")
    @PostMapping("/rating/validate")
    public String validate(
            @Parameter(description = "New rating data to add", required = true, schema = @Schema(implementation = RatingDTO.class)) @ModelAttribute("newRating") @Valid RatingDTO rating,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("newRating", rating);
            return "rating/add";
        }
        ratingService.save(ratingMapper.toEntity(rating));
        return "redirect:/rating/list";
    }

    @Operation(summary = "Display rating update form", description = "Returns a page with the form to update an existing rating")
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(
            @Parameter(description = "ID of the rating to update", required = true) @PathVariable("id") int id,
            Model model) {
        model.addAttribute("updatedRating", ratingMapper.toDto(ratingService.findById(id)));
        return "rating/update";
    }

    @Operation(summary = "Update an existing rating", description = "Updates a rating with the provided form data")
    @PostMapping("/rating/update/{id}")
    public String updateRating(
            @Parameter(description = "ID of the rating to update", required = true) @PathVariable("id") int id,
            @Parameter(description = "Updated rating data", required = true, schema = @Schema(implementation = RatingDTO.class)) @ModelAttribute("updatedRating") @Valid RatingDTO rating,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("updatedRating", rating);
            return "rating/update";
        }
        ratingService.update(rating);
        return "redirect:/rating/list";
    }

    @Operation(summary = "Delete a rating", description = "Deletes a rating by its ID")
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(
            @Parameter(description = "ID of the rating to delete", required = true) @PathVariable("id") int id,
            Model model) {
        ratingService.delete(ratingService.findById(id));
        return "redirect:/rating/list";
    }
}