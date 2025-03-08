package com.poseidoncapitalsolutions.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poseidoncapitalsolutions.trading.model.Trade;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Integer> {
}
