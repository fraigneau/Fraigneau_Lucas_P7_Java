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

import com.poseidoncapitalsolutions.trading.dto.BidListDTO;
import com.poseidoncapitalsolutions.trading.mapper.BidListMapper;
import com.poseidoncapitalsolutions.trading.model.BidList;
import com.poseidoncapitalsolutions.trading.service.BidListService;

@WebMvcTest(BidListController.class)
public class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BidListService bidListService;

    @MockitoBean
    private BidListMapper bidListMapper;

    private BidList bidList;
    private BidListDTO bidListDTO;
    private List<BidListDTO> bidListDTOs;

    @BeforeEach
    void setUp() {
        // Initialize test data
        bidList = new BidList();
        bidList.setId(1);
        bidList.setAccount("Account1");
        bidList.setType("Type1");
        bidList.setBidQuantity(100.0);

        bidListDTO = new BidListDTO();
        bidListDTO.setId(1);
        bidListDTO.setAccount("Account1");
        bidListDTO.setType("Type1");
        bidListDTO.setBidQuantity(100.0);

        bidListDTOs = new ArrayList<>();
        bidListDTOs.add(bidListDTO);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void homeShouldReturnBidListPage() throws Exception {
        // Given
        List<BidList> bidLists = new ArrayList<>();
        bidLists.add(bidList);
        when(bidListService.findAll()).thenReturn(bidLists);
        when(bidListService.getListResponseDTO(bidLists)).thenReturn(bidListDTOs);

        // When & Then
        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("bidLists"))
                .andExpect(model().attribute("bidLists", bidListDTOs));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void addBidFormShouldReturnAddBidPage() throws Exception {
        // When & Then
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().attributeExists("newBidList"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void validateShouldRedirectToBidListWhenNoErrors() throws Exception {
        // Given
        when(bidListMapper.toEntity(any(BidListDTO.class))).thenReturn(bidList);
        doNothing().when(bidListService).add(bidList);

        // When & Then
        mockMvc.perform(post("/bidList/validate")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("account", "Account1")
                .param("type", "Type1")
                .param("bidQuantity", "100.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService, times(1)).add(any(BidList.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void validateShouldReturnToAddPageWhenValidationErrors() throws Exception {
        // When & Then
        mockMvc.perform(post("/bidList/validate")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("account", "") // Empty account should cause validation error
                .param("type", "Type1")
                .param("bidQuantity", "100.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void showUpdateFormShouldReturnUpdatePage() throws Exception {
        // Given
        when(bidListService.findById(anyInt())).thenReturn(bidList);
        when(bidListMapper.toDto(bidList)).thenReturn(bidListDTO);

        // When & Then
        mockMvc.perform(get("/bidList/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeExists("updatedBidList"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void updateBidShouldRedirectToBidListWhenNoErrors() throws Exception {
        // Given
        doNothing().when(bidListService).update(any(BidListDTO.class));

        // When & Then
        mockMvc.perform(post("/bidList/update/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1")
                .param("account", "Account1")
                .param("type", "Type1")
                .param("bidQuantity", "100.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService, times(1)).update(any(BidListDTO.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteBidShouldRedirectToBidList() throws Exception {
        // Given
        when(bidListService.findById(anyInt())).thenReturn(bidList);
        doNothing().when(bidListService).delete(any(BidList.class));

        // When & Then
        mockMvc.perform(get("/bidList/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService, times(1)).delete(any(BidList.class));
    }
}