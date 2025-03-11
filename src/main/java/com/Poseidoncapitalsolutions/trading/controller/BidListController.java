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

import com.poseidoncapitalsolutions.trading.dto.BidListDTO;
import com.poseidoncapitalsolutions.trading.mapper.BidListMapper;
import com.poseidoncapitalsolutions.trading.service.BidListService;

import jakarta.validation.Valid;

@Controller
public class BidListController {

    private BidListService bidListService;
    private BidListMapper bidListMapper;

    public BidListController() {
    }

    @Autowired
    public BidListController(BidListService bidListService, BidListMapper bidListMapper) {
        this.bidListService = bidListService;
        this.bidListMapper = bidListMapper;
    }

    @RequestMapping("/bidList/list")
    public String home(Model model) {
        List<BidListDTO> lists = bidListService.getListResponseDTO(bidListService.findAll());
        model.addAttribute("bidLists", lists);
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(Model model) {
        model.addAttribute("newBidList", new BidListDTO());
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@ModelAttribute("newBidList") @Valid BidListDTO bid, BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("newBidList", bid);
            return "bidList/add";
        }

        bidListService.add(bidListMapper.toEntity(bid));
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("updatedBidList", bidListMapper.toDto(bidListService.findById(id)));
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") int id,
            @ModelAttribute("updatedBidList") @Valid BidListDTO updatedBidList,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("updatedBidList", updatedBidList);
            return "bidList/update";
        }
        bidListService.update(updatedBidList);
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") int id, Model model) {
        bidListService.delete(bidListService.findById(id));
        return "redirect:/bidList/list";
    }
}
