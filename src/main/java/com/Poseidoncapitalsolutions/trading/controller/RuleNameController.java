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

import com.poseidoncapitalsolutions.trading.dto.RuleNameDTO;
import com.poseidoncapitalsolutions.trading.mapper.RuleNameMapper;
import com.poseidoncapitalsolutions.trading.service.RuleNameService;

import jakarta.validation.Valid;

@Controller
public class RuleNameController {
    private RuleNameService ruleNameService;
    private RuleNameMapper ruleNameMapper;

    public RuleNameController(RuleNameService ruleNameService, RuleNameMapper ruleNameMapper) {
        this.ruleNameService = ruleNameService;
        this.ruleNameMapper = ruleNameMapper;
    }

    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        List<RuleNameDTO> lists = ruleNameService.getListResponseDTO(ruleNameService.findAll());
        model.addAttribute("ruleNames", lists);
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(Model model) {
        model.addAttribute("newRuleName", new RuleNameDTO());
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@ModelAttribute("newRuleName") @Valid RuleNameDTO ruleName, BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("newRuleName", ruleName);
            return "ruleName/add";
        }

        ruleNameService.save(ruleNameMapper.toEntity(ruleName));
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("updatedRuleName", ruleNameMapper.toDto(ruleNameService.findById(id)));
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id,
            @ModelAttribute("updatedRuleName") @Valid RuleNameDTO ruleName,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("updatedRuleName", ruleName);
            return "ruleName/update";
        }
        ruleNameService.update(ruleName);
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        ruleNameService.delete(ruleNameService.findById(id));
        return "redirect:/ruleName/list";
    }
}
