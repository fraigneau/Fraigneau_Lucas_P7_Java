package com.Poseidon_Capital_Solutions.Trading.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.Poseidon_Capital_Solutions.Trading.Repository.RuleNameRepository;
import com.Poseidon_Capital_Solutions.Trading.model.RuleName;

public class RuleNameService implements GenericService<RuleName> {

    private RuleNameRepository ruleNameRepository;

    public RuleNameService() {
    }

    @Autowired
    public RuleNameService(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    @Override
    public List<RuleName> findAll() {
        return ruleNameRepository.findAll();
    }

    @Override
    public RuleName findById(int id) {
        return ruleNameRepository.findById(id).get();
    }

    @Override
    public RuleName save(RuleName Object) {
        return ruleNameRepository.save(Object);
    }

    @Override
    public void delete(RuleName Object) {
        ruleNameRepository.delete(Object);
    }

}
