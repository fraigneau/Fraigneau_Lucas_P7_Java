package com.poseidoncapitalsolutions.trading.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;

import com.poseidoncapitalsolutions.trading.dto.BidListDTO;
import com.poseidoncapitalsolutions.trading.exception.ResourceNotFoundException;
import com.poseidoncapitalsolutions.trading.mapper.BidListMapper;
import com.poseidoncapitalsolutions.trading.model.BidList;
import com.poseidoncapitalsolutions.trading.repository.BidListRepository;

/**
 * Service class responsible for handling operations related to BidLists.
 * Provides methods for CRUD operations, mapping, and managing bid list data.
 */
@Service
public class BidListService implements GenericService<BidList> {

    private BidListRepository bidListRepository;
    private BidListMapper bidListMapper;

    /**
     * Constructs a BidListService with the given repository and mapper.
     * 
     * @param bidListRepository The repository to interact with BidList data.
     * @param bidListMapper     The mapper to convert BidList entities to DTOs.
     */
    public BidListService(BidListRepository bidListRepository, BidListMapper bidListMapper) {
        this.bidListRepository = bidListRepository;
        this.bidListMapper = bidListMapper;
    }

    /**
     * Retrieves all BidLists from the repository.
     * 
     * @return A list of all BidLists.
     */
    @Override
    public List<BidList> findAll() {
        return bidListRepository.findAll();
    }

    /**
     * Retrieves a BidList by its ID.
     * 
     * @param id The ID of the BidList to retrieve.
     * @return The BidList with the specified ID.
     * @throws ResourceNotFoundException If no BidList with the given ID is found.
     */
    @Override
    public BidList findById(int id) {
        return bidListRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BidList with id " + id + " not found"));
    }

    /**
     * Saves a given BidList entity.
     * 
     * @param Object The BidList entity to save.
     * @return The saved BidList entity.
     */
    @Override
    public BidList save(BidList Object) {
        return bidListRepository.save(Object);
    }

    /**
     * Deletes the specified BidList entity.
     * 
     * @param Object The BidList entity to delete.
     */
    @Override
    public void delete(BidList Object) {
        bidListRepository.delete(Object);
    }

    /**
     * Converts a list of BidList entities to a list of BidListDTOs.
     * 
     * @param bidLists The list of BidList entities to convert.
     * @return A list of BidListDTOs corresponding to the provided BidLists.
     */
    public List<BidListDTO> getListResponseDTO(List<BidList> bidLists) {
        return bidLists.stream()
                .map(bidListMapper::toDto)
                .toList();
    }

    /**
     * Updates the BidList entity based on the given BidListDTO.
     * 
     * @param bidListDTO The BidListDTO containing updated information.
     */
    public void update(BidListDTO bidListDTO) {
        bidListRepository.save(merge(bidListDTO));
    }

    /**
     * Merges the data from a BidListDTO into an existing BidList entity.
     * 
     * @param bidListDTO The BidListDTO containing the updated data.
     * @return The updated BidList entity.
     */
    private BidList merge(BidListDTO bidListDTO) {
        BidList bid = findById(bidListDTO.getId());
        bid.setAccount(bidListDTO.getAccount());
        bid.setType(bidListDTO.getType());
        bid.setBidQuantity(bidListDTO.getBidQuantity());
        bid.setRevisionDate(new Timestamp(System.currentTimeMillis()));
        return bid;
    }

    /**
     * Adds a new BidList entity, setting its creation date.
     * 
     * @param object The BidList entity to add.
     */
    public void add(BidList object) {
        object.setCreationDate(new Timestamp(System.currentTimeMillis()));
        bidListRepository.save(object);
    }
}
