/**
 * @author Ostrovskiy Dmitriy
 * @created 09.06.2020
 * DataService
 * @version v1.0
 */

package ru.dinikos.task.service;

import ru.dinikos.task.entity.DataEntity;

import java.util.List;

public interface DataService {

    DataEntity findById(Long id);
    DataEntity save(DataEntity dataEntity);
    void delete(Long id);

    List<DataEntity> findAll();
}
