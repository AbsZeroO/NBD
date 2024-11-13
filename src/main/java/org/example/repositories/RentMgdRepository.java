package org.example.repositories;

import org.example.model.Rent;

import java.util.List;

public class RentMgdRepository extends AbstractMongoRepository implements IRepo<Rent> {

    @Override
    public boolean add(Rent entity) {
        return false;
    }

    @Override
    public Rent findById(int id) {
        return null;
    }

    @Override
    public List<Rent> findAll() {
        return null;
    }

    @Override
    public boolean update(Rent entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
