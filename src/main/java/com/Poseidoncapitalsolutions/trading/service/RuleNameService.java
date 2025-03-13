package com.poseidoncapitalsolutions.trading.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.poseidoncapitalsolutions.trading.dto.RuleNameDTO;
import com.poseidoncapitalsolutions.trading.exception.ResourceNotFoundException;
import com.poseidoncapitalsolutions.trading.mapper.RuleNameMapper;
import com.poseidoncapitalsolutions.trading.model.RuleName;
import com.poseidoncapitalsolutions.trading.repository.RuleNameRepository;

@Service
public class RuleNameService implements GenericService<RuleName> {

    private RuleNameRepository ruleNameRepository;
    private RuleNameMapper ruleNameMapper;

    public RuleNameService(RuleNameRepository ruleNameRepository, RuleNameMapper ruleNameMapper) {
        this.ruleNameRepository = ruleNameRepository;
        this.ruleNameMapper = ruleNameMapper;
    }

    @Override
    public List<RuleName> findAll() {
        return ruleNameRepository.findAll();
    }

    @Override
    public RuleName findById(int id) {
        return ruleNameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RuleName with id " + id + " not found"));
    }

    @Override
    public RuleName save(RuleName Object) {
        return ruleNameRepository.save(Object);
    }

    @Override
    public void delete(RuleName Object) {
        ruleNameRepository.delete(Object);
    }

    public List<RuleNameDTO> getListResponseDTO(List<RuleName> ruleNames) {
        return ruleNames.stream()
                .map(ruleNameMapper::toDto)
                .toList();
    }

    public void update(RuleNameDTO ruleNameDTO) {
        ruleNameRepository.save(merge(ruleNameDTO));
    }

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
