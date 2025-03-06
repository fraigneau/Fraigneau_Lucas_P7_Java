package com.Poseidon_Capital_Solutions.Trading.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Poseidon_Capital_Solutions.Trading.model.BidList;

@Repository
public interface BidListRepository extends JpaRepository<BidList, Integer> {

}
