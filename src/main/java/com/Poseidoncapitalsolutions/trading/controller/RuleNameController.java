package com.poseidoncapitalsolutions.trading.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.poseidoncapitalsolutions.trading.dto.RuleNameDTO;
import com.poseidoncapitalsolutions.trading.mapper.RuleNameMapper;
import com.poseidoncapitalsolutions.trading.service.RuleNameService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@Controller
@Tag(name = "Rule Name Controller", description = "API for rule name management")
public class RuleNameController {

    private RuleNameService ruleNameService;
    private RuleNameMapper ruleNameMapper;

    public RuleNameController(RuleNameService ruleNameService, RuleNameMapper ruleNameMapper) {
        this.ruleNameService = ruleNameService;
        this.ruleNameMapper = ruleNameMapper;
    }

    @Operation(summary = "Get all rule names", description = "Returns a page with the list of all rule names")
    @GetMapping("/ruleName/list")
    public String home(Model model) {
        List<RuleNameDTO> lists = ruleNameService.getListResponseDTO(ruleNameService.findAll());
        model.addAttribute("ruleNames", lists);
        return "ruleName/list";
    }

    @Operation(summary = "Display rule name add form", description = "Returns a page with the form to add a new rule name")
    @GetMapping("/ruleName/add")
    public String addRuleForm(Model model) {
        model.addAttribute("newRuleName", new RuleNameDTO());
        return "ruleName/add";
    }

    @Operation(summary = "Validate and add a new rule name", description = "Adds a new rule name from form data")
    @PostMapping("/ruleName/validate")
    public String validate(
            @Parameter(description = "New rule name data to add", required = true, schema = @Schema(implementation = RuleNameDTO.class)) @ModelAttribute("newRuleName") @Valid RuleNameDTO ruleName,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("newRuleName", ruleName);
            return "ruleName/add";
        }

        ruleNameService.save(ruleNameMapper.toEntity(ruleName));
        return "redirect:/ruleName/list";
    }

    @Operation(summary = "Display rule name update form", description = "Returns a page with the form to update an existing rule name")
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(
            @Parameter(description = "ID of the rule name to update", required = true) @PathVariable("id") int id,
            Model model) {
        model.addAttribute("updatedRuleName", ruleNameMapper.toDto(ruleNameService.findById(id)));
        return "ruleName/update";
    }

    @Operation(summary = "Update an existing rule name", description = "Updates a rule name with the provided form data")
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(
            @Parameter(description = "ID of the rule name to update", required = true) @PathVariable("id") int id,
            @Parameter(description = "Updated rule name data", required = true, schema = @Schema(implementation = RuleNameDTO.class)) @ModelAttribute("updatedRuleName") @Valid RuleNameDTO ruleName,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("updatedRuleName", ruleName);
            return "ruleName/update";
        }
        ruleNameService.update(ruleName);
        return "redirect:/ruleName/list";
    }

    @Operation(summary = "Delete a rule name", description = "Deletes a rule name by its ID")
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(
            @Parameter(description = "ID of the rule name to delete", required = true) @PathVariable("id") int id,
            Model model) {
        ruleNameService.delete(ruleNameService.findById(id));
        return "redirect:/ruleName/list";
    }
}