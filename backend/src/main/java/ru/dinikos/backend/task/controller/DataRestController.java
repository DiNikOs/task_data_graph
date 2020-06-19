/**
 * @author Ostrovskiy Dmitriy
 * @created 09.06.2020
 * DataController
 * @version v1.0
 */

package ru.dinikos.backend.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.dinikos.backend.task.entity.DataEntity;
import ru.dinikos.backend.task.exception.DataErrorResponse;
import ru.dinikos.backend.task.exception.DataNotFoundException;
import ru.dinikos.backend.task.service.DataService;

import javax.servlet.http.HttpServlet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/api/v0")
public class DataRestController extends HttpServlet {

    private DataService dataService;

    @Autowired
    public DataRestController(DataService dataService){
        this.dataService = dataService;
    }

    @GetMapping("/data/{id}")
    public DataEntity getIdData(Model model, @PathVariable Long id) {
        return dataService.findById(id);
    }

    @GetMapping("/data")
    public ResponseEntity<List<DataEntity>> getAllData() {
        return new ResponseEntity<List<DataEntity>>(dataService.findAll(), HttpStatus.OK);
    }

    @PostMapping(path = "/data", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<DataEntity>> addData(@RequestBody List<DataEntity> entities) {
        List<DataEntity> listAll = dataService.findAll();
        if (entities.size()==1){
            DataEntity entity = entities.get(0);
            System.out.println("Entyty one:" + entity);
            ResponseEntity<List<DataEntity>> response= stopDuplicates(listAll, entity);
            if (response!=null) return response;
            dataService.save(entity);
        } else if (entities.size() > 1){
            List<DataEntity> entities_tmp = new ArrayList<>();
            ResponseEntity<List<DataEntity>> response;
            for (DataEntity entity: entities) {
                response= stopDuplicates(listAll, entity);
                if (response==null) {
                    entities_tmp.add(entity);
                }
            }
            dataService.saveList(entities_tmp);
        } else {
            return new ResponseEntity<List<DataEntity>>(listAll, HttpStatus.BAD_REQUEST);
        }
        listAll = dataService.findAll();
        return new ResponseEntity<List<DataEntity>>(listAll, HttpStatus.OK);
    }

    @PutMapping(path = "/data/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<DataEntity>> updateData( @RequestBody DataEntity entity,
                                                        @PathVariable(value = "id", required = false) Long id) {
        List<DataEntity> listAll = dataService.findAll();
        if (entity.getId()!=null && entity.getId() == id) {
            //Защита от записи объектов с одинаковыми данными под разными индексами
            DataEntity result = listAll.stream()
                    .filter((p) -> entity.getName().equals(p.getName()) &&
                            entity.getCreated().toLocalDate().equals(p.getCreated().toLocalDate())&&
                            entity.getCounts().equals(p.getCounts()))
                    .findAny().orElse(null);
            if (result==null) {
                dataService.save(entity);
                listAll = dataService.findAll();
                return new ResponseEntity<List<DataEntity>>(listAll, HttpStatus.OK);
            }
        }
        return new ResponseEntity<List<DataEntity>>(listAll, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/data/{id}")
    public ResponseEntity<List<DataEntity>> deleteData(@PathVariable Long id) {
        dataService.delete(id);
        return new ResponseEntity<List<DataEntity>>(dataService.findAll(), HttpStatus.ACCEPTED);
    }

    @ExceptionHandler
    public ResponseEntity<DataErrorResponse>
    handleException(DataNotFoundException exc) {
        DataErrorResponse errResp = new DataErrorResponse();
        errResp.setStatus(HttpStatus.NOT_FOUND.value());
        errResp.setMessage(exc.getMessage());
        errResp.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(errResp, HttpStatus.NOT_FOUND);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataService);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataRestController that = (DataRestController) o;
        return dataService.equals(that.dataService);
    }

    // DataEntity result поиск дубликатов по названию и времени(запрет записи дубликатов).
    // Можно только изменить и удалить элемент с одинаковым названием и временем.
    private ResponseEntity<List<DataEntity>> stopDuplicates(List<DataEntity> listAll, DataEntity entity){
        DataEntity result = listAll.stream()
        .filter((p) -> entity.getName().equals(p.getName()) &&
                entity.getCreated().toLocalDate().equals(p.getCreated().toLocalDate()))
        .findAny().orElse(null);
        if (result==null)  return null;
        return new ResponseEntity<List<DataEntity>>(listAll, HttpStatus.NOT_ACCEPTABLE);
    }
}

