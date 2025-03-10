package com.poseidoncapitalsolutions.trading.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poseidoncapitalsolutions.trading.dto.RatingDTO;
import com.poseidoncapitalsolutions.trading.mapper.RatingMapper;
import com.poseidoncapitalsolutions.trading.service.RatingService;

import jakarta.validation.Valid;

@Controller
public class RatingController {

    private RatingService ratingService;
    private RatingMapper ratingMapper;

    public RatingController() {
    }

    @Autowired
    public RatingController(RatingService ratingService, RatingMapper ratingMapper) {
        this.ratingService = ratingService;
        this.ratingMapper = ratingMapper;
    }

    @RequestMapping("/rating/list")
    public String home(Model model) {
        List<RatingDTO> lists = ratingService.getListResponseDTO(ratingService.findAll());
        model.addAttribute("ratings", lists);
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Model model) {
        model.addAttribute("newRating", new RatingDTO());
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@ModelAttribute("newRating") @Valid RatingDTO rating, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("newRating", rating);
            return "rating/add";
        }
        ratingService.save(ratingMapper.toEntity(rating));
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("updatedRating", ratingMapper.toDto(ratingService.findById(id)));
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") int id,
            @ModelAttribute("updatedRating") @Valid RatingDTO rating,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("updatedRating", rating);
            return "rating/update";
        }
        ratingService.update(rating);
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") int id, Model model) {
        ratingService.delete(ratingService.findById(id));
        return "redirect:/rating/list";
    }
}
