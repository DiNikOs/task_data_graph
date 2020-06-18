/**
 * @author Ostrovskiy Dmitriy
 * @created 09.06.2020
 * DataService
 * @version v1.0
 */

package ru.dinikos.backend.task.service;

import ru.dinikos.backend.task.entity.DataEntity;

import java.util.List;

public interface DataServiceInterface {

    DataEntity findById(Long id);
    DataEntity save(DataEntity dataEntity);
    void delete(Long id);

    List<DataEntity> findAll();
}
