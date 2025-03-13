package com.poseidoncapitalsolutions.trading.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;

import com.poseidoncapitalsolutions.trading.dto.CurvePointDTO;
import com.poseidoncapitalsolutions.trading.exception.ResourceNotFoundException;
import com.poseidoncapitalsolutions.trading.mapper.CurvepointMapper;
import com.poseidoncapitalsolutions.trading.model.CurvePoint;
import com.poseidoncapitalsolutions.trading.repository.CurvePointRepository;

/**
 * Service class responsible for handling operations related to CurvePoints.
 * Provides methods for CRUD operations, mapping, and managing curve point data.
 */
@Service
public class CurvePointService implements GenericService<CurvePoint> {

    private CurvePointRepository curvePointRepository;
    private CurvepointMapper curvepointMapper;

    /**
     * Constructs a CurvePointService with the given repository and mapper.
     * 
     * @param curvePointRepository The repository to interact with CurvePoint data.
     * @param curvepointMapper     The mapper to convert CurvePoint entities to
     *                             DTOs.
     */
    public CurvePointService(CurvePointRepository curvePointRepository, CurvepointMapper curvepointMapper) {
        this.curvePointRepository = curvePointRepository;
        this.curvepointMapper = curvepointMapper;
    }

    /**
     * Retrieves all CurvePoints from the repository.
     * 
     * @return A list of all CurvePoints.
     */
    @Override
    public List<CurvePoint> findAll() {
        return curvePointRepository.findAll();
    }

    /**
     * Retrieves a CurvePoint by its ID.
     * 
     * @param id The ID of the CurvePoint to retrieve.
     * @return The CurvePoint with the specified ID.
     * @throws ResourceNotFoundException If no CurvePoint with the given ID is
     *                                   found.
     */
    @Override
    public CurvePoint findById(int id) {
        return curvePointRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CurvePoint with id " + id + " not found"));
    }

    /**
     * Saves a given CurvePoint entity.
     * 
     * @param Object The CurvePoint entity to save.
     * @return The saved CurvePoint entity.
     */
    @Override
    public CurvePoint save(CurvePoint Object) {
        return curvePointRepository.save(Object);
    }

    /**
     * Deletes the specified CurvePoint entity.
     * 
     * @param Object The CurvePoint entity to delete.
     */
    @Override
    public void delete(CurvePoint Object) {
        curvePointRepository.delete(Object);
    }

    /**
     * Converts a list of CurvePoint entities to a list of CurvePointDTOs.
     * 
     * @param curvePoints The list of CurvePoint entities to convert.
     * @return A list of CurvePointDTOs corresponding to the provided CurvePoints.
     */
    public List<CurvePointDTO> getListResponseDTO(List<CurvePoint> curvePoints) {
        return curvePoints.stream()
                .map(curvepointMapper::toDto)
                .toList();
    }

    /**
     * Updates the CurvePoint entity based on the given CurvePointDTO.
     * 
     * @param curvePointDTO The CurvePointDTO containing updated information.
     */
    public void update(CurvePointDTO curvePointDTO) {
        curvePointRepository.save(merge(curvePointDTO));
    }

    /**
     * Merges the data from a CurvePointDTO into an existing CurvePoint entity.
     * 
     * @param curvePointDTO The CurvePointDTO containing the updated data.
     * @return The updated CurvePoint entity.
     */
    private CurvePoint merge(CurvePointDTO curvePointDTO) {
        CurvePoint curve = findById(curvePointDTO.getId());
        curve.setTerm(curvePointDTO.getTerm());
        curve.setValue(curvePointDTO.getValue());
        return curve;
    }

    /**
     * Adds a new CurvePoint entity, setting its creation date.
     * 
     * @param curvePoint The CurvePoint entity to add.
     */
    public void add(CurvePoint curvePoint) {
        curvePoint.setCreationDate(new Timestamp(System.currentTimeMillis()));
        curvePointRepository.save(curvePoint);
    }
}
