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

import com.poseidoncapitalsolutions.trading.dto.BidListDTO;
import com.poseidoncapitalsolutions.trading.exception.ResourceNotFoundException;
import com.poseidoncapitalsolutions.trading.mapper.BidListMapper;
import com.poseidoncapitalsolutions.trading.model.BidList;
import com.poseidoncapitalsolutions.trading.repository.BidListRepository;

@ExtendWith(MockitoExtension.class)
public class BidListServiceTest {

    @Mock
    private BidListRepository bidListRepository;

    @Mock
    private BidListMapper bidListMapper;

    @InjectMocks
    private BidListService bidListService;

    private BidList bidList;
    private BidListDTO bidListDTO;

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
    }

    @Test
    void findAllShouldReturnListOfBidLists() {
        // Given
        List<BidList> bidLists = Arrays.asList(bidList);
        when(bidListRepository.findAll()).thenReturn(bidLists);

        // When
        List<BidList> result = bidListService.findAll();

        // Then
        assertEquals(1, result.size());
        assertEquals("Account1", result.get(0).getAccount());
    }

    @Test
    void findByIdShouldReturnBidListWhenExists() {
        // Given
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));

        // When
        BidList result = bidListService.findById(1);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Account1", result.getAccount());
    }

    @Test
    void findByIdShouldThrowExceptionWhenBidListNotFound() {
        // Given
        when(bidListRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> bidListService.findById(99));
    }

    @Test
    void saveShouldReturnSavedBidList() {
        // Given
        when(bidListRepository.save(any(BidList.class))).thenReturn(bidList);

        // When
        BidList savedBidList = bidListService.save(bidList);

        // Then
        verify(bidListRepository, times(1)).save(bidList);
        assertEquals(bidList, savedBidList);
    }

    @Test
    void updateShouldUpdateExistingBidList() {
        // Given
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));
        when(bidListRepository.save(any(BidList.class))).thenReturn(bidList);

        // When
        bidListService.update(bidListDTO);

        // Then
        verify(bidListRepository, times(1)).findById(1);
        verify(bidListRepository, times(1)).save(bidList);
    }

    @Test
    void deleteShouldRemoveBidList() {
        // When
        bidListService.delete(bidList);

        // Then
        verify(bidListRepository, times(1)).delete(bidList);
    }

    @Test
    void getListResponseDTOShouldReturnListOfDTOs() {
        // Given
        List<BidList> bidLists = Arrays.asList(bidList);
        when(bidListMapper.toDto(bidList)).thenReturn(bidListDTO);

        // When
        List<BidListDTO> result = bidListService.getListResponseDTO(bidLists);

        // Then
        assertEquals(1, result.size());
        assertEquals(bidListDTO, result.get(0));
    }

    @Test
    void addShouldSetCreationDateAndSaveBidList() {
        // Given
        when(bidListRepository.save(any(BidList.class))).thenReturn(bidList);

        // When
        bidListService.add(bidList);

        // Then
        assertNotNull(bidList.getCreationDate());
        verify(bidListRepository, times(1)).save(bidList);
    }
}