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

import com.poseidoncapitalsolutions.trading.dto.TradeDTO;
import com.poseidoncapitalsolutions.trading.mapper.TradeMapper;
import com.poseidoncapitalsolutions.trading.model.Trade;
import com.poseidoncapitalsolutions.trading.service.TradeService;

@WebMvcTest(TradeController.class)
public class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TradeService tradeService;

    @MockitoBean
    private TradeMapper tradeMapper;

    private Trade trade;
    private TradeDTO tradeDTO;
    private List<TradeDTO> tradeDTOs;

    @BeforeEach
    void setUp() {

        trade = new Trade();
        trade.setId(1);
        trade.setAccount("TestAccount");
        trade.setType("TestType");
        trade.setBuyQuantity(100.0);

        tradeDTO = new TradeDTO();
        tradeDTO.setId(1);
        tradeDTO.setAccount("TestAccount");
        tradeDTO.setType("TestType");
        tradeDTO.setBuyQuantity(100.0);

        tradeDTOs = new ArrayList<>();
        tradeDTOs.add(tradeDTO);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void homeShouldReturnTradeListPage() throws Exception {
        // Given
        List<Trade> trades = new ArrayList<>();
        trades.add(trade);
        when(tradeService.findAll()).thenReturn(trades);
        when(tradeService.getListResponseDTO(trades)).thenReturn(tradeDTOs);

        // When & Then
        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attributeExists("trades"))
                .andExpect(model().attribute("trades", tradeDTOs));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void addUserShouldReturnAddTradePage() throws Exception {
        // When & Then
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().attributeExists("newTrade"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void validateShouldRedirectToTradeListWhenNoErrors() throws Exception {
        // Given
        when(tradeMapper.toEntity(any(TradeDTO.class))).thenReturn(trade);
        doNothing().when(tradeService).add(trade);

        // When & Then
        mockMvc.perform(post("/trade/validate")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("account", "TestAccount")
                .param("type", "TestType")
                .param("buyQuantity", "100.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(tradeService, times(1)).add(any(Trade.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void validateShouldReturnToAddPageWhenValidationErrors() throws Exception {
        // When & Then
        mockMvc.perform(post("/trade/validate")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("account", "")
                .param("type", "TestType")
                .param("buyQuantity", "100.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void showUpdateFormShouldReturnUpdatePage() throws Exception {
        // Given
        when(tradeService.findById(anyInt())).thenReturn(trade);
        when(tradeMapper.toDto(trade)).thenReturn(tradeDTO);

        // When & Then
        mockMvc.perform(get("/trade/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeExists("updatedTrade"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void updateTradeShouldRedirectToTradeListWhenNoErrors() throws Exception {
        // Given
        doNothing().when(tradeService).update(any(TradeDTO.class));

        // When & Then
        mockMvc.perform(post("/trade/update/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1")
                .param("account", "UpdatedAccount")
                .param("type", "UpdatedType")
                .param("buyQuantity", "200.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(tradeService, times(1)).update(any(TradeDTO.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteTradeShouldRedirectToTradeList() throws Exception {
        // Given
        when(tradeService.findById(anyInt())).thenReturn(trade);
        doNothing().when(tradeService).delete(any(Trade.class));

        // When & Then
        mockMvc.perform(get("/trade/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(tradeService, times(1)).delete(any(Trade.class));
    }
}