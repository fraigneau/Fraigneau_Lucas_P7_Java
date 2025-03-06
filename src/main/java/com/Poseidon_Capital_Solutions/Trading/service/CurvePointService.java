package com.Poseidon_Capital_Solutions.Trading.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.Poseidon_Capital_Solutions.Trading.Repository.CurvePointRepository;
import com.Poseidon_Capital_Solutions.Trading.model.CurvePoint;

public class CurvePointService implements GenericService<CurvePoint> {

    private CurvePointRepository curvePointRepository;

    public CurvePointService() {
    }

    @Autowired
    public CurvePointService(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
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

}
