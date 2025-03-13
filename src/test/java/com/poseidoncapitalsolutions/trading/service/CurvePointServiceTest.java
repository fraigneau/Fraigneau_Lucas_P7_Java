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

import com.poseidoncapitalsolutions.trading.dto.CurvePointDTO;
import com.poseidoncapitalsolutions.trading.exception.ResourceNotFoundException;
import com.poseidoncapitalsolutions.trading.mapper.CurvepointMapper;
import com.poseidoncapitalsolutions.trading.model.CurvePoint;
import com.poseidoncapitalsolutions.trading.repository.CurvePointRepository;

@ExtendWith(MockitoExtension.class)
public class CurvePointServiceTest {

    @Mock
    private CurvePointRepository curvePointRepository;

    @Mock
    private CurvepointMapper curvepointMapper;

    @InjectMocks
    private CurvePointService curvePointService;

    private CurvePoint curvePoint;
    private CurvePointDTO curvePointDTO;

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
    }

    @Test
    void findAllShouldReturnListOfCurvePoints() {
        // Given
        List<CurvePoint> curvePoints = Arrays.asList(curvePoint);
        when(curvePointRepository.findAll()).thenReturn(curvePoints);

        // When
        List<CurvePoint> result = curvePointService.findAll();

        // Then
        assertEquals(1, result.size());
        assertEquals(1.5, result.get(0).getTerm());
    }

    @Test
    void findByIdShouldReturnCurvePointWhenExists() {
        // Given
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));

        // When
        CurvePoint result = curvePointService.findById(1);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(1.5, result.getTerm());
    }

    @Test
    void findByIdShouldThrowExceptionWhenCurvePointNotFound() {
        // Given
        when(curvePointRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> curvePointService.findById(99));
    }

    @Test
    void saveShouldReturnSavedCurvePoint() {
        // Given
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);

        // When
        CurvePoint savedCurvePoint = curvePointService.save(curvePoint);

        // Then
        verify(curvePointRepository, times(1)).save(curvePoint);
        assertEquals(curvePoint, savedCurvePoint);
    }

    @Test
    void updateShouldUpdateExistingCurvePoint() {
        // Given
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);

        // When
        curvePointService.update(curvePointDTO);

        // Then
        verify(curvePointRepository, times(1)).findById(1);
        verify(curvePointRepository, times(1)).save(curvePoint);
    }

    @Test
    void deleteShouldRemoveCurvePoint() {
        // When
        curvePointService.delete(curvePoint);

        // Then
        verify(curvePointRepository, times(1)).delete(curvePoint);
    }

    @Test
    void getListResponseDTOShouldReturnListOfDTOs() {
        // Given
        List<CurvePoint> curvePoints = Arrays.asList(curvePoint);
        when(curvepointMapper.toDto(curvePoint)).thenReturn(curvePointDTO);

        // When
        List<CurvePointDTO> result = curvePointService.getListResponseDTO(curvePoints);

        // Then
        assertEquals(1, result.size());
        assertEquals(curvePointDTO, result.get(0));
    }

    @Test
    void addShouldSetCreationDateAndSaveCurvePoint() {
        // Given
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);

        // When
        curvePointService.add(curvePoint);

        // Then
        assertNotNull(curvePoint.getCreationDate());
        verify(curvePointRepository, times(1)).save(curvePoint);
    }
}