package com.Poseidon_Capital_Solutions.Trading.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.Poseidon_Capital_Solutions.Trading.Repository.TradeRepository;
import com.Poseidon_Capital_Solutions.Trading.model.Trade;

public class TradeService implements GenericService<Trade> {

    private TradeRepository tradeRepository;

    public TradeService() {
    }

    @Autowired
    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Override
    public List<Trade> findAll() {
        return tradeRepository.findAll();
    }

    @Override
    public Trade findById(int id) {
        return tradeRepository.findById(id).get();
    }

    @Override
    public Trade save(Trade Object) {
        return tradeRepository.save(Object);
    }

    @Override
    public void delete(Trade Object) {
        tradeRepository.delete(Object);
    }

}
