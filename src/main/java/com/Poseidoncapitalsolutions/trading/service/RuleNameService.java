package com.poseidoncapitalsolutions.trading.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.poseidoncapitalsolutions.trading.dto.RuleNameDTO;
import com.poseidoncapitalsolutions.trading.exception.ResourceNotFoundException;
import com.poseidoncapitalsolutions.trading.mapper.RuleNameMapper;
import com.poseidoncapitalsolutions.trading.model.RuleName;
import com.poseidoncapitalsolutions.trading.repository.RuleNameRepository;

/**
 * Service class responsible for handling operations related to RuleNames.
 * Provides methods for CRUD operations, mapping, and managing rule name data.
 */
@Service
public class RuleNameService implements GenericService<RuleName> {

    private RuleNameRepository ruleNameRepository;
    private RuleNameMapper ruleNameMapper;

    /**
     * Constructs a RuleNameService with the given repository and mapper.
     * 
     * @param ruleNameRepository The repository to interact with RuleName data.
     * @param ruleNameMapper     The mapper to convert RuleName entities to DTOs.
     */
    public RuleNameService(RuleNameRepository ruleNameRepository, RuleNameMapper ruleNameMapper) {
        this.ruleNameRepository = ruleNameRepository;
        this.ruleNameMapper = ruleNameMapper;
    }

    /**
     * Retrieves all RuleNames from the repository.
     * 
     * @return A list of all RuleNames.
     */
    @Override
    public List<RuleName> findAll() {
        return ruleNameRepository.findAll();
    }

    /**
     * Retrieves a RuleName by its ID.
     * 
     * @param id The ID of the RuleName to retrieve.
     * @return The RuleName with the specified ID.
     * @throws ResourceNotFoundException If no RuleName with the given ID is found.
     */
    @Override
    public RuleName findById(int id) {
        return ruleNameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RuleName with id " + id + " not found"));
    }

    /**
     * Saves a given RuleName entity.
     * 
     * @param Object The RuleName entity to save.
     * @return The saved RuleName entity.
     */
    @Override
    public RuleName save(RuleName Object) {
        return ruleNameRepository.save(Object);
    }

    /**
     * Deletes the specified RuleName entity.
     * 
     * @param Object The RuleName entity to delete.
     */
    @Override
    public void delete(RuleName Object) {
        ruleNameRepository.delete(Object);
    }

    /**
     * Converts a list of RuleName entities to a list of RuleNameDTOs.
     * 
     * @param ruleNames The list of RuleName entities to convert.
     * @return A list of RuleNameDTOs corresponding to the provided RuleNames.
     */
    public List<RuleNameDTO> getListResponseDTO(List<RuleName> ruleNames) {
        return ruleNames.stream()
                .map(ruleNameMapper::toDto)
                .toList();
    }

    /**
     * Updates the RuleName entity based on the given RuleNameDTO.
     * 
     * @param ruleNameDTO The RuleNameDTO containing updated information.
     */
    public void update(RuleNameDTO ruleNameDTO) {
        ruleNameRepository.save(merge(ruleNameDTO));
    }

    /**
     * Merges the data from a RuleNameDTO into an existing RuleName entity.
     * 
     * @param ruleNameDTO The RuleNameDTO containing the updated data.
     * @return The updated RuleName entity.
     */
    private RuleName merge(RuleNameDTO ruleNameDTO) {
        RuleName rule = findById(ruleNameDTO.getId());
        rule.setName(ruleNameDTO.getName());
        rule.setDescription(ruleNameDTO.getDescription());
        rule.setJson(ruleNameDTO.getJson());
        rule.setTemplate(ruleNameDTO.getTemplate());
        rule.setSqlStr(ruleNameDTO.getSqlStr());
        rule.setSqlPart(ruleNameDTO.getSqlPart());
        return rule;
    }
}
