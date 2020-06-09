/**
 * @author Ostrovskiy Dmitriy
 * @created 09.06.2020
 * DataController
 * @version v1.0
 */

package ru.dinikos.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.dinikos.task.entity.DataEntity;
import ru.dinikos.task.exception.DataErrorResponse;
import ru.dinikos.task.exception.DataNotFoundException;
import ru.dinikos.task.service.DataService;

import java.util.List;

@RestController
@RequestMapping("/api/v0")
public class DataController {

    private DataService dataService;

    @Autowired
    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/data/{id}")
    public DataEntity getIdData(Model model, @PathVariable Long id) {
        return dataService.findById(id);
    }

    @GetMapping("/data")
    public List<DataEntity> getAllData() {
        return dataService.findAll();
    }

    @PostMapping("/data")
    public DataEntity addData(@RequestBody DataEntity entity) {
        return dataService.save(entity);
    }

    @PutMapping(path = "/data", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public DataEntity updateData(@RequestBody DataEntity entity) {
        return dataService.save(entity);
    }

    @DeleteMapping("/data/{id}")
    public int deleteData(@PathVariable Long id) {
        dataService.delete(id);
        return HttpStatus.OK.value();
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
}

