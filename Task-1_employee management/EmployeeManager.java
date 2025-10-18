import java.util.ArrayList;
import java.util.Iterator;

public class EmployeeManager {
    private ArrayList<Employee> employees = new ArrayList<>();

    // Create
    public void addEmployee(Employee emp) {
        employees.add(emp);
    }

    // Read
    public void viewEmployees() {
        for (Employee emp : employees) {
            System.out.println(emp);
        }
    }

    // Update
    public boolean updateEmployee(int id, String newName, double newSalary) {
        for (Employee emp : employees) {
            if (emp.getId() == id) {
                emp.setName(newName);
                emp.setSalary(newSalary);
                return true;
            }
        }
        return false;
    }

    // Delete
    public boolean deleteEmployee(int id) {
        Iterator<Employee> iterator = employees.iterator();
        while (iterator.hasNext()) {
            Employee emp = iterator.next();
            if (emp.getId() == id) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }
}
