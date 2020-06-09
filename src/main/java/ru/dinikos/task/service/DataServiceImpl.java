/**
 * @author Ostrovskiy Dmitriy
 * @created 09.06.2020
 * DataServiceImpl
 * @version v1.0
 */

package ru.dinikos.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.dinikos.task.entity.DataEntity;
import ru.dinikos.task.repository.DataTestRepository;

import java.util.List;

public class DataServiceImpl implements DataService {

    private DataTestRepository repository;

    @Autowired
    public DataServiceImpl(DataTestRepository repository) {
        this.repository = repository;
    }

    @Override
    public DataEntity findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public List<DataEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public DataEntity save(DataEntity dataEntity) {
        return repository.save(dataEntity);
    }

    @Override
    public void delete(Long id) {
        repository.delete(findById(id));
    }

}
