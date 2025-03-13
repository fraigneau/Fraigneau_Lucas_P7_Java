package com.poseidoncapitalsolutions.trading.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;

import com.poseidoncapitalsolutions.trading.dto.TradeDTO;
import com.poseidoncapitalsolutions.trading.exception.ResourceNotFoundException;
import com.poseidoncapitalsolutions.trading.mapper.TradeMapper;
import com.poseidoncapitalsolutions.trading.model.Trade;
import com.poseidoncapitalsolutions.trading.repository.TradeRepository;

/**
 * Service class responsible for handling operations related to Trades.
 * Provides methods for CRUD operations, mapping, and managing trade data.
 */
@Service
public class TradeService implements GenericService<Trade> {

    private TradeRepository tradeRepository;
    private TradeMapper tradeMapper;

    /**
     * Constructs a TradeService with the given repository and mapper.
     * 
     * @param tradeRepository The repository to interact with Trade data.
     * @param tradeMapper     The mapper to convert Trade entities to DTOs.
     */
    public TradeService(TradeRepository tradeRepository, TradeMapper tradeMapper) {
        this.tradeRepository = tradeRepository;
        this.tradeMapper = tradeMapper;
    }

    /**
     * Retrieves all Trades from the repository.
     * 
     * @return A list of all Trades.
     */
    @Override
    public List<Trade> findAll() {
        return tradeRepository.findAll();
    }

    /**
     * Retrieves a Trade by its ID.
     * 
     * @param id The ID of the Trade to retrieve.
     * @return The Trade with the specified ID.
     * @throws ResourceNotFoundException If no Trade with the given ID is found.
     */
    @Override
    public Trade findById(int id) {
        return tradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trade with id " + id + " not found"));
    }

    /**
     * Saves a given Trade entity.
     * 
     * @param Object The Trade entity to save.
     * @return The saved Trade entity.
     */
    @Override
    public Trade save(Trade Object) {
        return tradeRepository.save(Object);
    }

    /**
     * Deletes the specified Trade entity.
     * 
     * @param Object The Trade entity to delete.
     */
    @Override
    public void delete(Trade Object) {
        tradeRepository.delete(Object);
    }

    /**
     * Converts a list of Trade entities to a list of TradeDTOs.
     * 
     * @param trades The list of Trade entities to convert.
     * @return A list of TradeDTOs corresponding to the provided Trades.
     */
    public List<TradeDTO> getListResponseDTO(List<Trade> trades) {
        return trades.stream()
                .map(tradeMapper::toDto)
                .toList();
    }

    /**
     * Updates the Trade entity based on the given TradeDTO.
     * 
     * @param tradeDTO The TradeDTO containing updated information.
     */
    public void update(TradeDTO tradeDTO) {
        tradeRepository.save(merge(tradeDTO));
    }

    /**
     * Merges the data from a TradeDTO into an existing Trade entity.
     * 
     * @param tradeDTO The TradeDTO containing the updated data.
     * @return The updated Trade entity.
     */
    private Trade merge(TradeDTO tradeDTO) {
        Trade trade = findById(tradeDTO.getId());
        trade.setAccount(tradeDTO.getAccount());
        trade.setType(tradeDTO.getType());
        trade.setBuyQuantity(tradeDTO.getBuyQuantity());
        trade.setRevisionDate(new Timestamp(System.currentTimeMillis()));
        return trade;
    }

    /**
     * Adds a new Trade entity, setting its creation date.
     * 
     * @param trade The Trade entity to add.
     */
    public void add(Trade trade) {
        trade.setCreationDate(new Timestamp(System.currentTimeMillis()));
        tradeRepository.save(trade);
    }
}
