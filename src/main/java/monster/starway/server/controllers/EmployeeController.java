package monster.starway.server.controllers;

import monster.starway.server.dto.EmployeesDTO;
import monster.starway.server.services.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {
    @Autowired
    private EmployeesService employeeService;

    @GetMapping(path="", produces = "application/json")
    public EmployeesDTO getEmployees() {
        return employeeService.getAllEmployees();
    }
}
