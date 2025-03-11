package com.poseidoncapitalsolutions.trading.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poseidoncapitalsolutions.trading.dto.CurvePointDTO;
import com.poseidoncapitalsolutions.trading.mapper.CurvepointMapper;
import com.poseidoncapitalsolutions.trading.service.CurvePointService;

import jakarta.validation.Valid;

@Controller
public class CurveController {

    private CurvePointService curvePointService;
    private CurvepointMapper curvepointMapper;

    public CurveController(CurvePointService curvePointService, CurvepointMapper curvepointMapper) {
        this.curvePointService = curvePointService;
        this.curvepointMapper = curvepointMapper;
    }

    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        List<CurvePointDTO> lists = curvePointService.getListResponseDTO(curvePointService.findAll());
        model.addAttribute("curvePoints", lists);
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(Model model) {
        model.addAttribute("newCurvePoint", new CurvePointDTO());
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@ModelAttribute("newCurvePoint") @Valid CurvePointDTO curvePoint, BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("newCurvePoint", curvePoint);
            return "curvePoint/add";
        }
        curvePointService.add(curvepointMapper.toEntity(curvePoint));
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("updatedCurvePoint", curvepointMapper.toDto(curvePointService.findById(id)));
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") int id,
            @ModelAttribute("updatedCurvePoint") @Valid CurvePointDTO updatedCurvePoint,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("updatedCurvePoint", updatedCurvePoint);
            return "curvePoint/update";
        }
        curvePointService.update(updatedCurvePoint);
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") int id, Model model) {
        curvePointService.delete(curvePointService.findById(id));
        return "redirect:/curvePoint/list";
    }
}
