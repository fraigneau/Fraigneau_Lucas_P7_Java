package com.poseidoncapitalsolutions.trading.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;

import com.poseidoncapitalsolutions.trading.dto.TradeDTO;
import com.poseidoncapitalsolutions.trading.mapper.TradeMapper;
import com.poseidoncapitalsolutions.trading.model.Trade;
import com.poseidoncapitalsolutions.trading.repository.TradeRepository;

@Service
public class TradeService implements GenericService<Trade> {

    private TradeRepository tradeRepository;
    private TradeMapper tradeMapper;

    public TradeService(TradeRepository tradeRepository, TradeMapper tradeMapper) {
        this.tradeRepository = tradeRepository;
        this.tradeMapper = tradeMapper;
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

    public List<TradeDTO> getListResponseDTO(List<Trade> trades) {
        return trades.stream()
                .map(tradeMapper::toDto)
                .toList();
    }

    public void update(TradeDTO tradeDTO) {
        tradeRepository.save(merge(tradeDTO));
    }

    private Trade merge(TradeDTO tradeDTO) {
        Trade trade = findById(tradeDTO.getId());
        trade.setAccount(tradeDTO.getAccount());
        trade.setType(tradeDTO.getType());
        trade.setBuyQuantity(tradeDTO.getBuyQuantity());
        trade.setRevisionDate(new Timestamp(System.currentTimeMillis()));
        return trade;
    }

    public void add(Trade trade) {
        trade.setCreationDate(new Timestamp(System.currentTimeMillis()));
        tradeRepository.save(trade);
    }

}
