package com.poseidoncapitalsolutions.trading.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.poseidoncapitalsolutions.trading.dto.BidListDTO;
import com.poseidoncapitalsolutions.trading.mapper.BidListMapper;
import com.poseidoncapitalsolutions.trading.service.BidListService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Controller
@Tag(name = "Bid List Controller", description = "API for bid list management")
public class BidListController {

    private BidListService bidListService;
    private BidListMapper bidListMapper;

    public BidListController(BidListService bidListService, BidListMapper bidListMapper) {
        this.bidListService = bidListService;
        this.bidListMapper = bidListMapper;
    }

    @Operation(summary = "Get all bid lists", description = "Returns a page with the list of all bid lists")
    @GetMapping("/bidList/list")
    public String home(Model model) {
        List<BidListDTO> lists = bidListService.getListResponseDTO(bidListService.findAll());
        model.addAttribute("bidLists", lists);
        return "bidList/list";
    }

    @Operation(summary = "Display bid list add form", description = "Returns a page with the form to add a new bid list")
    @GetMapping("/bidList/add")
    public String addBidForm(Model model) {
        model.addAttribute("newBidList", new BidListDTO());
        return "bidList/add";
    }

    @Operation(summary = "Validate and add a new bid list", description = "Adds a new bid list from form data")
    @PostMapping("/bidList/validate")
    public String validate(
            @Parameter(description = "New bid list data to add", required = true, schema = @Schema(implementation = BidListDTO.class)) @ModelAttribute("newBidList") @Valid BidListDTO bid,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("newBidList", bid);
            return "bidList/add";
        }

        bidListService.add(bidListMapper.toEntity(bid));
        return "redirect:/bidList/list";
    }

    @Operation(summary = "Display bid list update form", description = "Returns a page with the form to update an existing bid list")
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(
            @Parameter(description = "ID of the bid list to update", required = true) @PathVariable("id") int id,
            Model model) {
        model.addAttribute("updatedBidList", bidListMapper.toDto(bidListService.findById(id)));
        return "bidList/update";
    }

    @Operation(summary = "Update an existing bid list", description = "Updates a bid list with the provided form data")
    @PostMapping("/bidList/update/{id}")
    public String updateBid(
            @Parameter(description = "ID of the bid list to update", required = true) @PathVariable("id") int id,
            @Parameter(description = "Updated bid list data", required = true, schema = @Schema(implementation = BidListDTO.class)) @ModelAttribute("updatedBidList") @Valid BidListDTO updatedBidList,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("updatedBidList", updatedBidList);
            return "bidList/update";
        }
        bidListService.update(updatedBidList);
        return "redirect:/bidList/list";
    }

    @Operation(summary = "Delete a bid list", description = "Deletes a bid list by its ID")
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(
            @Parameter(description = "ID of the bid list to delete", required = true) @PathVariable("id") int id,
            Model model) {
        bidListService.delete(bidListService.findById(id));
        return "redirect:/bidList/list";
    }
}