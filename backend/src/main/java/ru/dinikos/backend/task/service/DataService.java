/**
 * @author Ostrovskiy Dmitriy
 * @created 09.06.2020
 * DataServiceImpl
 * @version v1.0
 */

package ru.dinikos.backend.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import ru.dinikos.backend.task.entity.DataEntity;
import ru.dinikos.backend.task.repository.DataTestRepository;


import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class DataService implements DataServiceInterface {

    private DataTestRepository repository;

    @Autowired
    public DataService(DataTestRepository repository) {
        this.repository = repository;
    }

    @Override
    public DataEntity findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalStateException("Data not found! -" + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DataEntity> findAll() {
       return repository.findAll();
    }

    @Override
    // Защита от проскакивания двойной записи, видимо из за быстрой памяти
    @Transactional(isolation= Isolation.SERIALIZABLE, timeout=500)
    public DataEntity save(DataEntity dataEntity) {
        return repository.save(dataEntity);
    }

    @Override
    public void delete(Long id) {
        repository.delete(findById(id));
    }

}
