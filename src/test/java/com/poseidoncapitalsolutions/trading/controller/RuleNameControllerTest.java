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

import com.poseidoncapitalsolutions.trading.dto.RuleNameDTO;
import com.poseidoncapitalsolutions.trading.mapper.RuleNameMapper;
import com.poseidoncapitalsolutions.trading.model.RuleName;
import com.poseidoncapitalsolutions.trading.service.RuleNameService;

@WebMvcTest(RuleNameController.class)
public class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RuleNameService ruleNameService;

    @MockitoBean
    private RuleNameMapper ruleNameMapper;

    private RuleName ruleName;
    private RuleNameDTO ruleNameDTO;
    private List<RuleNameDTO> ruleNameDTOs;

    @BeforeEach
    void setUp() {

        ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("TestRule");
        ruleName.setDescription("Test Rule Description");
        ruleName.setJson("{\"test\": \"value\"}");
        ruleName.setTemplate("Test Template");
        ruleName.setSqlStr("SELECT * FROM test");
        ruleName.setSqlPart("WHERE id = 1");

        ruleNameDTO = new RuleNameDTO();
        ruleNameDTO.setId(1);
        ruleNameDTO.setName("TestRule");
        ruleNameDTO.setDescription("Test Rule Description");
        ruleNameDTO.setJson("{\"test\": \"value\"}");
        ruleNameDTO.setTemplate("Test Template");
        ruleNameDTO.setSqlStr("SELECT * FROM test");
        ruleNameDTO.setSqlPart("WHERE id = 1");

        ruleNameDTOs = new ArrayList<>();
        ruleNameDTOs.add(ruleNameDTO);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void homeShouldReturnRuleNameListPage() throws Exception {
        // Given
        List<RuleName> ruleNames = new ArrayList<>();
        ruleNames.add(ruleName);
        when(ruleNameService.findAll()).thenReturn(ruleNames);
        when(ruleNameService.getListResponseDTO(ruleNames)).thenReturn(ruleNameDTOs);

        // When & Then
        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attributeExists("ruleNames"))
                .andExpect(model().attribute("ruleNames", ruleNameDTOs));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void addRuleFormShouldReturnAddRulePage() throws Exception {
        // When & Then
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"))
                .andExpect(model().attributeExists("newRuleName"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void validateShouldRedirectToRuleNameListWhenNoErrors() throws Exception {
        // Given
        when(ruleNameMapper.toEntity(any(RuleNameDTO.class))).thenReturn(ruleName);
        when(ruleNameService.save(ruleName)).thenReturn(ruleName);

        // When & Then
        mockMvc.perform(post("/ruleName/validate")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "TestRule")
                .param("description", "Test Rule Description")
                .param("json", "{\"test\": \"value\"}")
                .param("template", "Test Template")
                .param("sqlStr", "SELECT * FROM test")
                .param("sqlPart", "WHERE id = 1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService, times(1)).save(any(RuleName.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void validateShouldReturnToAddPageWhenValidationErrors() throws Exception {
        // When & Then
        mockMvc.perform(post("/ruleName/validate")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "")
                .param("description", "Test Rule Description")
                .param("json", "{\"test\": \"value\"}")
                .param("template", "Test Template")
                .param("sqlStr", "SELECT * FROM test")
                .param("sqlPart", "WHERE id = 1"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void showUpdateFormShouldReturnUpdatePage() throws Exception {
        // Given
        when(ruleNameService.findById(anyInt())).thenReturn(ruleName);
        when(ruleNameMapper.toDto(ruleName)).thenReturn(ruleNameDTO);

        // When & Then
        mockMvc.perform(get("/ruleName/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attributeExists("updatedRuleName"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void updateRuleNameShouldRedirectToRuleNameListWhenNoErrors() throws Exception {
        // Given
        doNothing().when(ruleNameService).update(any(RuleNameDTO.class));

        // When & Then
        mockMvc.perform(post("/ruleName/update/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1")
                .param("name", "UpdatedRule")
                .param("description", "Updated Rule Description")
                .param("json", "{\"updated\": \"value\"}")
                .param("template", "Updated Template")
                .param("sqlStr", "SELECT * FROM updated")
                .param("sqlPart", "WHERE id = 2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService, times(1)).update(any(RuleNameDTO.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteRuleNameShouldRedirectToRuleNameList() throws Exception {
        // Given
        when(ruleNameService.findById(anyInt())).thenReturn(ruleName);
        doNothing().when(ruleNameService).delete(any(RuleName.class));

        // When & Then
        mockMvc.perform(get("/ruleName/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService, times(1)).delete(any(RuleName.class));
    }
}