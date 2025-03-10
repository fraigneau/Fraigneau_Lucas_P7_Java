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

import com.poseidoncapitalsolutions.trading.dto.TradeDTO;
import com.poseidoncapitalsolutions.trading.mapper.TradeMapper;
import com.poseidoncapitalsolutions.trading.service.TradeService;

import jakarta.validation.Valid;

@Controller
public class TradeController {

    private TradeService tradeService;
    private TradeMapper tradeMapper;

    public TradeController(TradeService tradeService, TradeMapper tradeMapper) {
        this.tradeService = tradeService;
        this.tradeMapper = tradeMapper;
    }

    @RequestMapping("/trade/list")
    public String home(Model model) {
        List<TradeDTO> lists = tradeService.getListResponseDTO(tradeService.findAll());
        model.addAttribute("trades", lists);
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Model model) {
        model.addAttribute("newTrade", new TradeDTO());
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@ModelAttribute("newTrade") @Valid TradeDTO trade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("newTrade", trade);
            return "trade/add";
        }

        tradeService.save(tradeMapper.toEntity(trade));
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("updatedTrade", tradeMapper.toDto(tradeService.findById(id)));
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") int id,
            @ModelAttribute("updatedTrade") @Valid TradeDTO trade,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("updatedTrade", trade);
            return "trade/update";
        }

        tradeService.update(trade);
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") int id, Model model) {
        tradeService.delete(tradeService.findById(id));
        return "redirect:/trade/list";
    }
}
