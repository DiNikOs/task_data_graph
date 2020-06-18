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
    public String index(Model model, @PathVariable(value = "id", required = false) Long id) {
        model.addAttribute("data", dataService.findAll());
        return "index";
    }

    @GetMapping("/data/{id}")
    public String idData(Model model, @PathVariable("id") Long id) {
        model.addAttribute("data", dataService.findById(id));
        return "/";
    }

    @GetMapping("/data/all")
    public String getAllData(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("data", dataService.findAll());
        return "/";
    }

    @GetMapping("/data/create")
    public String getCreateData(Model model) {
        model.addAttribute("data", new DataEntity());
        model.addAttribute("dataAll", dataService.findAll());
        model.addAttribute("action", "create");
        return "/create";
    }

    @PostMapping("/data/save")
    public String addData(@ModelAttribute("dataEntity") DataEntity dataEntity, Model model) {
        dataEntity.getCreated();
        dataEntity.getName();
        dataEntity.getCounts();
        model.addAttribute("time", dataEntity.getCreated());
        model.addAttribute("namedata",dataEntity.getName());
        model.addAttribute("counts", dataEntity.getCounts());
        dataService.save(dataEntity);
        return "redirect:/";
    }

    @GetMapping("/data/edit")
    public String updateData(@RequestParam(name = "id", required = false) Long id, Model model) {
        DataEntity dataEntity = dataService.findById(id);
        model.addAttribute("data", dataEntity);
        return "/create";
    }

    @GetMapping("/data/delete/{id}")
    public String deleteData(@PathVariable("id") Long id) {
        dataService.delete(id);
        return "redirect:/";
    }
}
