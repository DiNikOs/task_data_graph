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
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import ru.dinikos.backend.task.entity.DataEntity;
import ru.dinikos.backend.task.exception.DataErrorResponse;
import ru.dinikos.backend.task.exception.DataNotFoundException;
import ru.dinikos.backend.task.service.DataService;

import javax.servlet.http.HttpServlet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public ResponseEntity<List<DataEntity>> addData(@RequestBody DataEntity entity) {

        List<DataEntity> list = dataService.findAll();
        DataEntity result1 = list.stream()
                .filter((p) -> entity.getName().equals(p.getName()) &&
                        entity.getCreated().toLocalDate().equals(p.getCreated().toLocalDate()))
                .findAny().orElse(null);
        if (result1==null) {
            dataService.save(entity);
            return new ResponseEntity<List<DataEntity>>(list, HttpStatus.OK);
        }
        return new ResponseEntity<List<DataEntity>>(list, HttpStatus.NOT_ACCEPTABLE);
//        list.sort((o1,o2) -> o1.getCreated().compareTo(o2.getCreated()));
    }
    //@PathVariable для защиты от записи одинаковых значений с разными индексами
    @PutMapping(path = "/data/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<DataEntity>> updateData( @RequestBody DataEntity entity,
                                                        @PathVariable(value = "id", required = false) Long id) {
        List<DataEntity> list = dataService.findAll();
        if (entity.getId()!=null && entity.getId() == id) {
            //Защита от записи одинаковых объектов под разными индексами
            DataEntity result1 = list.stream()
                    .filter((p) -> entity.getName().equals(p.getName()) &&
                            entity.getCreated().toLocalDate().equals(p.getCreated().toLocalDate())&&
                            entity.getCounts().equals(p.getCounts()))
                    .findAny().orElse(null);
            if (result1==null) {
                dataService.save(entity);
                return new ResponseEntity<List<DataEntity>>(list, HttpStatus.OK);
            }
//            list.sort((o1,o2) -> o1.getCreated().compareTo(o2.getCreated()));
        }
        return new ResponseEntity<List<DataEntity>>(list, HttpStatus.NOT_FOUND);
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
}

