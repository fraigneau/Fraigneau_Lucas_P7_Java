package com.poseidoncapitalsolutions.trading.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseidoncapitalsolutions.trading.dto.BidListDTO;
import com.poseidoncapitalsolutions.trading.mapper.BidListMapper;
import com.poseidoncapitalsolutions.trading.model.BidList;
import com.poseidoncapitalsolutions.trading.repository.BidListRepository;

@Service
public class BidListService implements GenericService<BidList> {

    private BidListRepository bidListRepository;
    private BidListMapper bidListMapper;

    public BidListService() {
    }

    @Autowired
    public BidListService(BidListRepository bidListRepository, BidListMapper bidListMapper) {
        this.bidListRepository = bidListRepository;
        this.bidListMapper = bidListMapper;
    }

    @Override
    public List<BidList> findAll() {
        return bidListRepository.findAll();
    }

    @Override
    public BidList findById(int id) {
        return bidListRepository.findById(id).get();
    }

    @Override
    public BidList save(BidList Object) {
        return bidListRepository.save(Object);
    }

    @Override
    public void delete(BidList Object) {
        bidListRepository.delete(Object);
    }

    public List<BidListDTO> getListResponseDTO(List<BidList> bidLists) {
        return bidLists.stream()
                .map(bidListMapper::toDto)
                .toList();
    }

}
