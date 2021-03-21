package monster.starway.server.services;

import monster.starway.server.data.repository.EmployeeRepository;
import monster.starway.server.dto.EmployeesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeesService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeesDTO getAllEmployees()
    {
        EmployeesDTO employeesDTO = new EmployeesDTO(employeeRepository.getAllEmployees());
        return employeesDTO;
    }
}
