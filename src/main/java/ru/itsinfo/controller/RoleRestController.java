package ru.itsinfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.itsinfo.model.Role;
import ru.itsinfo.service.AppService;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleRestController {

    private final AppService appService;

    @Autowired
    public RoleRestController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping
    public List<Role> getAllRoles() {
        return appService.findAllRoles();
    }
}