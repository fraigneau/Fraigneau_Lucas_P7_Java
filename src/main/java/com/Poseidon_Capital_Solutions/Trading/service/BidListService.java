package com.Poseidon_Capital_Solutions.Trading.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Poseidon_Capital_Solutions.Trading.Repository.BidListRepository;
import com.Poseidon_Capital_Solutions.Trading.model.BidList;

@Service
public class BidListService implements GenericService<BidList> {

    private BidListRepository bidListRepository;

    public BidListService() {
    }

    @Autowired
    public BidListService(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
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

}
