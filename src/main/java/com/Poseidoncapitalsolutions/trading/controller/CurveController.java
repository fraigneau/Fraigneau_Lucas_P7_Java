package com.poseidoncapitalsolutions.trading.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.poseidoncapitalsolutions.trading.dto.CurvePointDTO;
import com.poseidoncapitalsolutions.trading.mapper.CurvepointMapper;
import com.poseidoncapitalsolutions.trading.service.CurvePointService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@Controller
@Tag(name = "Curve Controller", description = "API for curve point management")
public class CurveController {

    private CurvePointService curvePointService;
    private CurvepointMapper curvepointMapper;

    public CurveController(CurvePointService curvePointService, CurvepointMapper curvepointMapper) {
        this.curvePointService = curvePointService;
        this.curvepointMapper = curvepointMapper;
    }

    @Operation(summary = "Get all curve points", description = "Returns a page with the list of all curve points")
    @GetMapping("/curvePoint/list")
    public String home(Model model) {
        List<CurvePointDTO> lists = curvePointService.getListResponseDTO(curvePointService.findAll());
        model.addAttribute("curvePoints", lists);
        return "curvePoint/list";
    }

    @Operation(summary = "Display curve point add form", description = "Returns a page with the form to add a new curve point")
    @GetMapping("/curvePoint/add")
    public String addBidForm(Model model) {
        model.addAttribute("newCurvePoint", new CurvePointDTO());
        return "curvePoint/add";
    }

    @Operation(summary = "Validate and add a new curve point", description = "Adds a new curve point from form data")
    @PostMapping("/curvePoint/validate")
    public String validate(
            @Parameter(description = "New curve point data to add", required = true, schema = @Schema(implementation = CurvePointDTO.class)) @ModelAttribute("newCurvePoint") @Valid CurvePointDTO curvePoint,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("newCurvePoint", curvePoint);
            return "curvePoint/add";
        }
        curvePointService.add(curvepointMapper.toEntity(curvePoint));
        return "redirect:/curvePoint/list";
    }

    @Operation(summary = "Display curve point update form", description = "Returns a page with the form to update an existing curve point")
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(
            @Parameter(description = "ID of the curve point to update", required = true) @PathVariable("id") int id,
            Model model) {
        model.addAttribute("updatedCurvePoint", curvepointMapper.toDto(curvePointService.findById(id)));
        return "curvePoint/update";
    }

    @Operation(summary = "Update an existing curve point", description = "Updates a curve point with the provided form data")
    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(
            @Parameter(description = "ID of the curve point to update", required = true) @PathVariable("id") int id,
            @Parameter(description = "Updated curve point data", required = true, schema = @Schema(implementation = CurvePointDTO.class)) @ModelAttribute("updatedCurvePoint") @Valid CurvePointDTO updatedCurvePoint,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("updatedCurvePoint", updatedCurvePoint);
            return "curvePoint/update";
        }
        curvePointService.update(updatedCurvePoint);
        return "redirect:/curvePoint/list";
    }

    @Operation(summary = "Delete a curve point", description = "Deletes a curve point by its ID")
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(
            @Parameter(description = "ID of the curve point to delete", required = true) @PathVariable("id") int id,
            Model model) {
        curvePointService.delete(curvePointService.findById(id));
        return "redirect:/curvePoint/list";
    }
}