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

import com.poseidoncapitalsolutions.trading.dto.RuleNameDTO;
import com.poseidoncapitalsolutions.trading.exception.ResourceNotFoundException;
import com.poseidoncapitalsolutions.trading.mapper.RuleNameMapper;
import com.poseidoncapitalsolutions.trading.model.RuleName;
import com.poseidoncapitalsolutions.trading.repository.RuleNameRepository;

@ExtendWith(MockitoExtension.class)
public class RuleNameServiceTest {

    @Mock
    private RuleNameRepository ruleNameRepository;

    @Mock
    private RuleNameMapper ruleNameMapper;

    @InjectMocks
    private RuleNameService ruleNameService;

    private RuleName ruleName;
    private RuleNameDTO ruleNameDTO;

    @BeforeEach
    void setUp() {
        // Initialize test data
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
    }

    @Test
    void findAllShouldReturnListOfRuleNames() {
        // Given
        List<RuleName> ruleNames = Arrays.asList(ruleName);
        when(ruleNameRepository.findAll()).thenReturn(ruleNames);

        // When
        List<RuleName> result = ruleNameService.findAll();

        // Then
        assertEquals(1, result.size());
        assertEquals("TestRule", result.get(0).getName());
    }

    @Test
    void findByIdShouldReturnRuleNameWhenExists() {
        // Given
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));

        // When
        RuleName result = ruleNameService.findById(1);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("TestRule", result.getName());
    }

    @Test
    void findByIdShouldThrowExceptionWhenRuleNameNotFound() {
        // Given
        when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> ruleNameService.findById(99));
    }

    @Test
    void saveShouldReturnSavedRuleName() {
        // Given
        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName);

        // When
        RuleName savedRuleName = ruleNameService.save(ruleName);

        // Then
        verify(ruleNameRepository, times(1)).save(ruleName);
        assertEquals(ruleName, savedRuleName);
    }

    @Test
    void updateShouldUpdateExistingRuleName() {
        // Given
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));
        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName);

        // When
        ruleNameService.update(ruleNameDTO);

        // Then
        verify(ruleNameRepository, times(1)).findById(1);
        verify(ruleNameRepository, times(1)).save(ruleName);
    }

    @Test
    void deleteShouldRemoveRuleName() {
        // When
        ruleNameService.delete(ruleName);

        // Then
        verify(ruleNameRepository, times(1)).delete(ruleName);
    }

    @Test
    void getListResponseDTOShouldReturnListOfDTOs() {
        // Given
        List<RuleName> ruleNames = Arrays.asList(ruleName);
        when(ruleNameMapper.toDto(ruleName)).thenReturn(ruleNameDTO);

        // When
        List<RuleNameDTO> result = ruleNameService.getListResponseDTO(ruleNames);

        // Then
        assertEquals(1, result.size());
        assertEquals(ruleNameDTO, result.get(0));
    }
}