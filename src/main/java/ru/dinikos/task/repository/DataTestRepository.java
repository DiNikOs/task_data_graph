/**
 * @author Ostrovskiy Dmitriy
 * @created 09.06.2020
 * DataTestRepository
 * @version v1.0
 */

package ru.dinikos.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.dinikos.task.entity.DataEntity;

@Repository
public interface DataTestRepository extends JpaRepository<DataEntity, Long>, JpaSpecificationExecutor<DataEntity> {

}
