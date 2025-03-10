package com.poseidoncapitalsolutions.trading.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.poseidoncapitalsolutions.trading.dto.CurvePointDTO;
import com.poseidoncapitalsolutions.trading.mapper.CurvepointMapper;
import com.poseidoncapitalsolutions.trading.model.CurvePoint;
import com.poseidoncapitalsolutions.trading.repository.CurvePointRepository;

@Service
public class CurvePointService implements GenericService<CurvePoint> {

    private CurvePointRepository curvePointRepository;
    private CurvepointMapper curvepointMapper;

    public CurvePointService(CurvePointRepository curvePointRepository, CurvepointMapper curvepointMapper) {
        this.curvePointRepository = curvePointRepository;
        this.curvepointMapper = curvepointMapper;
    }

    @Override
    public List<CurvePoint> findAll() {
        return curvePointRepository.findAll();
    }

    @Override
    public CurvePoint findById(int id) {
        return curvePointRepository.findById(id).get();
    }

    @Override
    public CurvePoint save(CurvePoint Object) {
        return curvePointRepository.save(Object);
    }

    @Override
    public void delete(CurvePoint Object) {
        curvePointRepository.delete(Object);
    }

    public List<CurvePointDTO> getListResponseDTO(List<CurvePoint> curvePoints) {
        return curvePoints.stream()
                .map(curvepointMapper::toDto)
                .toList();
    }

    public void update(CurvePointDTO curvePointDTO) {
        curvePointRepository.save(merge(curvePointDTO));
    }

    private CurvePoint merge(CurvePointDTO curvePointDTO) {
        CurvePoint curve = findById(curvePointDTO.getId());
        curve.setTerm(curvePointDTO.getTerm());
        curve.setValue(curvePointDTO.getValue());
        return curve;
    }

}
