package com.poseidoncapitalsolutions.trading.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

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

import com.poseidoncapitalsolutions.trading.dto.CurvePointDTO;
import com.poseidoncapitalsolutions.trading.mapper.CurvepointMapper;
import com.poseidoncapitalsolutions.trading.model.CurvePoint;
import com.poseidoncapitalsolutions.trading.service.CurvePointService;

@WebMvcTest(CurveController.class)
public class CurveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CurvePointService curvePointService;

    @MockitoBean
    private CurvepointMapper curvepointMapper;

    private CurvePoint curvePoint;
    private CurvePointDTO curvePointDTO;
    private List<CurvePointDTO> curvePointDTOs;

    @BeforeEach
    void setUp() {
        // Initialize test data
        curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(1);
        curvePoint.setTerm(1.5);
        curvePoint.setValue(2.5);

        curvePointDTO = new CurvePointDTO();
        curvePointDTO.setId(1);
        curvePointDTO.setTerm(1.5);
        curvePointDTO.setValue(2.5);

        curvePointDTOs = new ArrayList<>();
        curvePointDTOs.add(curvePointDTO);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void homeShouldReturnCurvePointListPage() throws Exception {
        // Given
        List<CurvePoint> curvePoints = new ArrayList<>();
        curvePoints.add(curvePoint);
        when(curvePointService.findAll()).thenReturn(curvePoints);
        when(curvePointService.getListResponseDTO(curvePoints)).thenReturn(curvePointDTOs);

        // When & Then
        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("curvePoints"))
                .andExpect(model().attribute("curvePoints", curvePointDTOs));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void addBidFormShouldReturnAddCurvePointPage() throws Exception {
        // When & Then
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().attributeExists("newCurvePoint"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void validateShouldRedirectToCurvePointListWhenNoErrors() throws Exception {
        // Given
        when(curvepointMapper.toEntity(any(CurvePointDTO.class))).thenReturn(curvePoint);
        doNothing().when(curvePointService).add(curvePoint);

        // When & Then
        mockMvc.perform(post("/curvePoint/validate")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("term", "1.5")
                .param("value", "2.5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointService, times(1)).add(any(CurvePoint.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void validateShouldReturnToAddPageWhenValidationErrors() throws Exception {
        // When & Then
        mockMvc.perform(post("/curvePoint/validate")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("term", "-1.5") // Negative term should cause validation error
                .param("value", "2.5"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void showUpdateFormShouldReturnUpdatePage() throws Exception {
        // Given
        when(curvePointService.findById(anyInt())).thenReturn(curvePoint);
        when(curvepointMapper.toDto(curvePoint)).thenReturn(curvePointDTO);

        // When & Then
        mockMvc.perform(get("/curvePoint/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeExists("updatedCurvePoint"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void updateBidShouldRedirectToCurvePointListWhenNoErrors() throws Exception {
        // Given
        doNothing().when(curvePointService).update(any(CurvePointDTO.class));

        // When & Then
        mockMvc.perform(post("/curvePoint/update/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1")
                .param("term", "1.5")
                .param("value", "2.5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointService, times(1)).update(any(CurvePointDTO.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteBidShouldRedirectToCurvePointList() throws Exception {
        // Given
        when(curvePointService.findById(anyInt())).thenReturn(curvePoint);
        doNothing().when(curvePointService).delete(any(CurvePoint.class));

        // When & Then
        mockMvc.perform(get("/curvePoint/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointService, times(1)).delete(any(CurvePoint.class));
    }
}