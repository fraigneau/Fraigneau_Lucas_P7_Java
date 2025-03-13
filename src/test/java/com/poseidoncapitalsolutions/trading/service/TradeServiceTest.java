package com.poseidoncapitalsolutions.trading.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.poseidoncapitalsolutions.trading.dto.TradeDTO;
import com.poseidoncapitalsolutions.trading.exception.ResourceNotFoundException;
import com.poseidoncapitalsolutions.trading.mapper.TradeMapper;
import com.poseidoncapitalsolutions.trading.model.Trade;
import com.poseidoncapitalsolutions.trading.repository.TradeRepository;

@ExtendWith(MockitoExtension.class)
public class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;

    @Mock
    private TradeMapper tradeMapper;

    @InjectMocks
    private TradeService tradeService;

    private Trade trade;
    private TradeDTO tradeDTO;

    @BeforeEach
    void setUp() {
        // Initialize test data
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
    }

    @Test
    void findAllShouldReturnListOfTrades() {
        // Given
        List<Trade> trades = Arrays.asList(trade);
        when(tradeRepository.findAll()).thenReturn(trades);

        // When
        List<Trade> result = tradeService.findAll();

        // Then
        assertEquals(1, result.size());
        assertEquals("TestAccount", result.get(0).getAccount());
    }

    @Test
    void findByIdShouldReturnTradeWhenExists() {
        // Given
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

        // When
        Trade result = tradeService.findById(1);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("TestAccount", result.getAccount());
    }

    @Test
    void findByIdShouldThrowExceptionWhenTradeNotFound() {
        // Given
        when(tradeRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> tradeService.findById(99));
    }

    @Test
    void saveShouldReturnSavedTrade() {
        // Given
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

        // When
        Trade savedTrade = tradeService.save(trade);

        // Then
        verify(tradeRepository, times(1)).save(trade);
        assertEquals(trade, savedTrade);
    }

    @Test
    void updateShouldUpdateExistingTrade() {
        // Given
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

        // When
        tradeService.update(tradeDTO);

        // Then
        verify(tradeRepository, times(1)).findById(1);
        verify(tradeRepository, times(1)).save(trade);
    }

    @Test
    void deleteShouldRemoveTrade() {
        // When
        tradeService.delete(trade);

        // Then
        verify(tradeRepository, times(1)).delete(trade);
    }

    @Test
    void getListResponseDTOShouldReturnListOfDTOs() {
        // Given
        List<Trade> trades = Arrays.asList(trade);
        when(tradeMapper.toDto(trade)).thenReturn(tradeDTO);

        // When
        List<TradeDTO> result = tradeService.getListResponseDTO(trades);

        // Then
        assertEquals(1, result.size());
        assertEquals(tradeDTO, result.get(0));
    }

    @Test
    void addShouldSetCreationDateAndSaveTrade() {
        // Given
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

        // When
        tradeService.add(trade);

        // Then
        assertNotNull(trade.getCreationDate());
        verify(tradeRepository, times(1)).save(trade);
    }
}