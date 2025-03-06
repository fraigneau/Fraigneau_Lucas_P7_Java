package com.Poseidon_Capital_Solutions.Trading.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Poseidon_Capital_Solutions.Trading.model.Trade;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Integer> {
}
