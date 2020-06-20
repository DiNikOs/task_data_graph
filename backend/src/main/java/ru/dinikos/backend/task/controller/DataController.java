/**
 * @author Ostrovskiy Dmitriy
 * @created 12.06.2020
 * DataController
 * @version v1.0
 */

package ru.dinikos.backend.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.dinikos.backend.task.entity.DataEntity;
import ru.dinikos.backend.task.service.DataService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class DataController {

    private DataService dataService;

    @Autowired
    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("data", dataService.findAll());
        return "index";
    }

    @GetMapping("/cat")
    public String cat() {
        return "/fragments/cat";
    }
}