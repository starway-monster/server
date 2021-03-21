package monster.starway.server.services;

import monster.starway.server.dao.EmployeeDAO;
import monster.starway.server.dto.EmployeesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeesService {
    @Autowired
    private EmployeeDAO employeeDao;

    public EmployeesDTO getAllEmployees()
    {
        EmployeesDTO employeesDTO = new EmployeesDTO(employeeDao.getAllEmployees());
        return employeesDTO;
    }
}
