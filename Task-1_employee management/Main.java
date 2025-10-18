public class Main {
    public static void main(String[] args) {
        EmployeeManager manager = new EmployeeManager();

        // Add employees
        manager.addEmployee(new Employee(1, "Alice", 50000));
        manager.addEmployee(new Employee(2, "Bob", 60000));

        // View employees
        manager.viewEmployees();

        // Update employee
        manager.updateEmployee(1, "Alice Smith", 55000);

        // Delete employee
        manager.deleteEmployee(2);

        // View employees after update and delete
        manager.viewEmployees();
    }
}
