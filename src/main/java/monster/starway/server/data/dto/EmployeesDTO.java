package monster.starway.server.data.dto;

import java.util.Objects;

public class EmployeesDTO extends Object{
    private Object list;

    public EmployeesDTO() {
    }

    public EmployeesDTO(Object list) {
        this.list = list;
    }

    public Object getList() {
        return list;
    }

    public void setList(Object list) {
        this.list = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeesDTO that = (EmployeesDTO) o;
        return Objects.equals(list, that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }
}
