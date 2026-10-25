import java.util.*;
import java.io.*;

interface Bonus {

    void calculateBonus();
}


interface Work {

    void work();
}



abstract class Employee {

    private int id;
    private String name;
    private float sallary; 

    Employee(int id, String name, float sallary) {
        setId(id);
        setName(name);
        setSalary(sallary);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getSalary() {
        return sallary;
    }

    
    public void setId(int id) {
        if(id <= 0) {
            throw new IllegalArgumentException(
                "ID must be greater than 0."
            );
        }
        this.id = id;
    }

    public void setName(String name) {
        if(name == null || name.trim().isEmpty()
                || name.matches("\\d+")) {
            throw new IllegalArgumentException(
                "Name cannot be empty or contain only numbers."
            );
        }
        this.name = name;
    }

    public void setSalary(float sallary) {
        if(!Float.isFinite(sallary) || sallary < 0) {
            throw new IllegalArgumentException(
                "Salary must be a finite, non-negative number."
            );
        }
        this.sallary = sallary;
    }


    public abstract void displayRole();
}


// Manager inherits Employee
class Manager extends Employee implements Bonus {

    private int teamSize;

    Manager(int id, String name, float sallary, int teamSize) {
    super(id, name, sallary);
    setTeamSize(teamSize);
   }

    public int getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(int teamSize) {
    if(teamSize < 0) {
        throw new IllegalArgumentException(
            "Team size cannot be negative."
        );
    }
    this.teamSize = teamSize;
}

    @Override
    public void displayRole() {
        System.out.println("I am a Manager");
    }

    @Override
    public void calculateBonus() {

        System.out.println("Manager bonus = " + (getSalary() * 0.20));
    }
}


class Developer extends Employee implements Bonus, Work {

    private String programmingLanguage;

    Developer(int id, String name, float sallary, String programmingLanguage) {

        super(id, name, sallary);

        setProgrammingLanguage(programmingLanguage);
    }

    public String getProgrammingLanguage() {
        return programmingLanguage;
    }

    
public void setProgrammingLanguage(String programmingLanguage) {
    if(programmingLanguage == null
            || programmingLanguage.trim().isEmpty()
            || programmingLanguage.matches("\\d+")) {
        throw new IllegalArgumentException(
            "Programming language cannot be empty or contain only numbers."
        );
    }

    this.programmingLanguage = programmingLanguage;
}


    @Override
    public void displayRole() {
        System.out.println("I am a Developer");
    }

    @Override
    public void calculateBonus() {

        System.out.println("Developer bonus = " + (getSalary() * 0.10));
    }

    @Override
    public void work() {

    System.out.println("Developer is writing code");
    }
}


public class Employee_MS {


    static void saveEmployees(ArrayList<Employee> emp) {

    try {
        FileWriter fw = new FileWriter("employees.txt");
        BufferedWriter bw = new BufferedWriter(fw);

        for(int i = 0; i < emp.size(); i++) {

            Employee e = emp.get(i);

            if(e instanceof Manager) {
                Manager m = (Manager) e;

                bw.write("Manager," + m.getId() + "," +
                         m.getName() + "," + m.getSalary() + "," +
                         m.getTeamSize());
            }
            else if(e instanceof Developer) {
                Developer d = (Developer) e;

                bw.write("Developer," + d.getId() + "," +
                         d.getName() + "," + d.getSalary() + "," +
                         d.getProgrammingLanguage());
            }

            bw.newLine();
        }

        bw.close();

        System.out.println("Employees saved successfully.");

    }
    catch(IOException e) {
        System.out.println("Error saving employees: " + e.getMessage());
    }
}


    
static void loadEmployees(ArrayList<Employee> emp) {

    File file = new File("employees.txt");

    if(!file.exists()) {
        System.out.println("No saved employee records found.");
        return;
    }

    try {
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line;

        while((line = br.readLine()) != null) {

            String[] data = line.split(",", -1);

            if(data.length != 5) {
                System.out.println("Invalid employee record: " + line);
                continue;
            }

            String type = data[0];
            int id = Integer.parseInt(data[1]);
            String name = data[2];
            float salary = Float.parseFloat(data[3]);

            if(type.equals("Manager")) {

                int teamSize = Integer.parseInt(data[4]);

                emp.add(new Manager(id, name, salary, teamSize));
            }
            else if(type.equals("Developer")) {

                String language = data[4];

                emp.add(new Developer(id, name, salary, language));
            }
            else {
                System.out.println("Unknown employee type: " + type);
            }
        }

        br.close();

        System.out.println("Employees loaded successfully.");
    }
    catch(IOException | IllegalArgumentException e) {
        System.out.println("Error loading employees: " + e.getMessage());
    }
}








   static void addemployee(ArrayList<Employee> emp, int choice, Scanner a) {

    int added = 0;

    while(added < choice) {

      
        System.out.println("Enter employee type:");
        System.out.println("1. Manager");
        System.out.println("2. Developer");

        int type;

        try {
            type = a.nextInt();
        }
        catch(InputMismatchException e) {
            System.out.println("Please enter a number.");
            a.nextLine();
            continue;
        }

        if(type != 1 && type != 2) {
            System.out.println("Invalid employee type.");
            continue;
        }


        System.out.println("Enter the employee id:");

        int ID;

        try {
            ID = a.nextInt();
        }
        catch(InputMismatchException e) {
            System.out.println("Employee ID must be a number.");
            a.nextLine();
            continue;
        }
        
        if(ID <= 0) {
            System.out.println("Employee ID must be greater than 0.");
            continue;
        }

         
        boolean duplicateID = false;

        for(int j = 0; j < emp.size(); j++) {
            if(emp.get(j).getId() == ID) {
                duplicateID = true;
                break;
            }
        }

        if(duplicateID) {
            System.out.println("Employee ID already exists.");
            continue;
        }


        
       System.out.println("Enter the employee name:");

        a.nextLine();
        String name = a.nextLine();

        
        try {
            if(name.trim().isEmpty() || name.matches("\\d+")) {
                throw new IllegalArgumentException(
                    "Name cannot be empty or contain only numbers."
                );
            }
        }
        catch(IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            continue;
        }



       
        System.out.println("Enter the employee salary:");

        float sallary;

        try {
            sallary = a.nextFloat();
        }
        catch(InputMismatchException e) {
            System.out.println("Salary must be a number.");
            a.nextLine();
            continue;
        }

        if(!Float.isFinite(sallary) || sallary < 0) {
        System.out.println("Salary must be finite and non-negative.");
        continue;
        }


        Employee e;

      
      try {
      if(type == 1) {

        System.out.println("Enter team size:");

        int teamSize;

        try {
            teamSize = a.nextInt();
        }
        catch(InputMismatchException ex) {
            System.out.println("Team size must be a number.");
            a.nextLine();
            continue;
        }

        if(teamSize < 0) {
            System.out.println("Team size cannot be negative.");
            continue;
        }

        e = new Manager(ID, name, sallary, teamSize);
    }
    else {

       
        System.out.println("Enter programming language:");
        String programmingLanguage = a.next();

        if(programmingLanguage.matches("\\d+")) {
            System.out.println("Programming language cannot contain only numbers.");
            continue;
        }

        e = new Developer(ID, name, sallary, programmingLanguage);

    }

    emp.add(e);
    added++;
    saveEmployees(emp);
    System.out.println("Employee added successfully.");

    }
    catch(IllegalArgumentException ex) {
        System.out.println(ex.getMessage());
    }


        }
    }




static void viewemployee(ArrayList<Employee> emp, Scanner a) {
   
        System.out.println("Enter the employee id which we want to view:");

        int ID;

        try {
            ID = a.nextInt();
        }
        catch(InputMismatchException e) {
        System.out.println("Employee ID must be a number.");
        a.nextLine();
        return;
        }

        if(ID <= 0) {
            System.out.println("Employee ID must be greater than 0.");
            return;
        }

        int found = 1;

        for(int i = 0; i < emp.size(); i++) {

        if(emp.get(i).getId() == ID) {

            System.out.println("Employee exist");
            System.out.println("Employee id = " + emp.get(i).getId());
            System.out.println("Employee name = " + emp.get(i).getName());
            System.out.println("Employee salary = " + emp.get(i).getSalary());

            emp.get(i).displayRole();

            if(emp.get(i) instanceof Bonus) {
             Bonus b = (Bonus) emp.get(i);
             b.calculateBonus();
           }

           if(emp.get(i) instanceof Work) {
           Work w = (Work) emp.get(i);
           w.work();
          }

           if(emp.get(i) instanceof Manager) {
            Manager m = (Manager) emp.get(i);
           System.out.println("Manager Team Size = " + m.getTeamSize());
           }

           if(emp.get(i) instanceof Developer) {
           Developer d = (Developer) emp.get(i);
         System.out.println("Programming Language = " + d.getProgrammingLanguage());
         }

            found = 0;
            break;
        }
    }

    if(found == 1) {
        System.out.println("Employee not exist");
    }
}



static void viewAllEmployees(ArrayList<Employee> emp) {

        if(emp.isEmpty()) {
            System.out.println("No employees available.");
            return;
        }

        System.out.println("All Employee Details:");

        for(int i = 0; i < emp.size(); i++) {

            System.out.println("----------------------");
            System.out.println("Employee ID: " + emp.get(i).getId());
            System.out.println("Employee Name: " + emp.get(i).getName());
            System.out.println("Employee Salary: " + emp.get(i).getSalary());

            emp.get(i).displayRole();

            
            if(emp.get(i) instanceof Bonus) {
                Bonus b = (Bonus) emp.get(i);
                b.calculateBonus();
            }

            if(emp.get(i) instanceof Work) {
                Work w = (Work) emp.get(i);
                w.work();
            }


            if(emp.get(i) instanceof Manager) {
                Manager m = (Manager) emp.get(i);
                System.out.println("Team Size: " + m.getTeamSize());
            }

            if(emp.get(i) instanceof Developer) {
                Developer d = (Developer) emp.get(i);
                System.out.println("Programming Language: " + d.getProgrammingLanguage());
            }
        }
    }



    
static void updateemployee(ArrayList<Employee> emp, Scanner a) {

    System.out.println("Enter the employee id which we want to update:");

    int ID;

    try {
        ID = a.nextInt();
    }
    
    catch(InputMismatchException e) {
        System.out.println("Employee ID must be a number.");
        a.nextLine();
        return;
    }

    if(ID <= 0) {
        System.out.println("Employee ID must be greater than 0.");
        return;
    }

    Employee current = null;


    for(int i = 0; i < emp.size(); i++) {
    if(emp.get(i).getId() == ID) {
        current = emp.get(i);
        break;
    }
}

    if(current == null) {
        System.out.println("Employee does not exist.");
        return;
    }

    System.out.println("Enter the new id:");

    int newID;

    try {
        newID = a.nextInt();
    }
    catch(InputMismatchException e) {
        System.out.println("Employee ID must be a number.");
        a.nextLine();
        return;
    }

    if(newID <= 0) {
        System.out.println("Employee ID must be greater than 0.");
        return;
    }

    for(int i = 0; i < emp.size(); i++) {
    if(emp.get(i).getId() == newID && emp.get(i) != current) {
        System.out.println("Employee ID already exists.");
        return;
    }
}

    System.out.println("Enter the new name:");
    a.nextLine();
    String newName = a.nextLine();

    if(newName == null || newName.trim().isEmpty()
        || newName.matches("\\d+")) {
    System.out.println("Invalid name.");
    return;
    }

    System.out.println("Enter the new salary:");

    float newSalary;

    try {
        newSalary = a.nextFloat();
    }
    catch(InputMismatchException e) {
        System.out.println("Salary must be a number.");
        a.nextLine();
        return;
    }

    if(!Float.isFinite(newSalary) || newSalary < 0) {
        System.out.println("Salary must be finite and non-negative.");
        return;
    }

    int newTeamSize = 0;
    String newLanguage = null;

    if(current instanceof Manager) {
        System.out.println("Enter the new team size:");

        try {
            newTeamSize = a.nextInt();
        }
        catch(InputMismatchException e) {
            System.out.println("Team size must be a number.");
            a.nextLine();
            return;
        }

        if(newTeamSize < 0) {
            System.out.println("Team size cannot be negative.");
            return;
        }
    }

    
    
    if(current instanceof Developer) {
        System.out.println("Enter the new programming language:");
        newLanguage = a.next();

        if(newLanguage.matches("\\d+")) {
            System.out.println("Programming language cannot contain only numbers.");
            return;
        }

        if(newLanguage.trim().isEmpty()) {
            System.out.println("Programming language cannot be empty.");
            return;
        }
    }



    // Apply changes only after validation succeeds.
    
    try {
        current.setId(newID);
        current.setName(newName);
        current.setSalary(newSalary);

        if(current instanceof Manager) {
            Manager m = (Manager) current;
            m.setTeamSize(newTeamSize);
        }

        if(current instanceof Developer) {
            Developer d = (Developer) current;
            d.setProgrammingLanguage(newLanguage);
        }

        saveEmployees(emp);
        System.out.println("Employee updated successfully.");
    }
    catch(IllegalArgumentException ex) {
        System.out.println("Update failed: " + ex.getMessage());
    }

    }



    
static void sortEmployeesBySalary(ArrayList<Employee> emp) {

    if(emp.isEmpty()) {
        System.out.println("No employees available.");
        return;
    }

    for(int i = 0; i < emp.size() - 1; i++) {

        for(int j = i + 1; j < emp.size(); j++) {

            if(emp.get(i).getSalary() > emp.get(j).getSalary()) {

                Employee temp = emp.get(i);

                emp.set(i, emp.get(j));

                emp.set(j, temp);
            }
        }
    }

    System.out.println("Employees sorted by salary.");

    viewAllEmployees(emp);
}




static void countEmployees(ArrayList<Employee> emp) {

    System.out.println("Total number of employees: " + emp.size());

}



static void highestPaidEmployee(ArrayList<Employee> emp) {

    if(emp.isEmpty()) {
        System.out.println("No employees available.");
        return;
    }

    Employee highest = emp.get(0);

    for(int i = 1; i < emp.size(); i++) {

        if(emp.get(i).getSalary() > highest.getSalary()) {
            highest = emp.get(i);
        }
    }

    System.out.println("Highest Paid Employee:");
    System.out.println("Employee ID: " + highest.getId());
    System.out.println("Employee Name: " + highest.getName());
    System.out.println("Employee Salary: " + highest.getSalary());
    highest.displayRole();
}



static void lowestPaidEmployee(ArrayList<Employee> emp) {

    if(emp.isEmpty()) {
        System.out.println("No employees available.");
        return;
    }

    Employee lowest = emp.get(0);

    for(int i = 1; i < emp.size(); i++) {

        if(emp.get(i).getSalary() < lowest.getSalary()) {
            lowest = emp.get(i);
        }
    }

    System.out.println("Lowest Paid Employee:");
    System.out.println("Employee ID: " + lowest.getId());
    System.out.println("Employee Name: " + lowest.getName());
    System.out.println("Employee Salary: " + lowest.getSalary());
    lowest.displayRole();
}






    
static void deleteemployee(ArrayList<Employee> emp, Scanner a) {

    System.out.println("Enter the Employee id which we want to delete:");

    int ID;

    try {
        ID = a.nextInt();
    }
    
    catch(InputMismatchException e) {
    System.out.println("Employee ID must be a number.");
    a.nextLine();
    return;
    }

    if(ID <= 0) {
        System.out.println("Employee ID must be greater than 0.");
        return;
    }

    int found = 0;


    for(int i = 0; i < emp.size(); i++) {

        if(emp.get(i).getId() == ID) {

            emp.remove(i);

            saveEmployees(emp);

            System.out.println("Employee deleted");

            found = 1;
            break;
        }
    }

    if(found == 0) {
        System.out.println("Employee does not exist.");
    }
}



    public static void main(String[] args) {

        ArrayList<Employee> emp = new ArrayList<>();

        loadEmployees(emp);

        Scanner a = new Scanner(System.in);

        int choice, op=0;

        do {

            System.out.println("\nEnter the operations which we want to select:");
            System.out.println("1. Add employee");
            System.out.println("2. View employee");
            System.out.println("3. Update employee");
            System.out.println("4. Delete employee");
            System.out.println("5. View all employees");
            System.out.println("6. Exit");
            System.out.println("7. Sort employees by salary");
            System.out.println("8. Count employees");
            System.out.println("9. Highest paid employee");
            System.out.println("10. Lowest paid employee");
           


           try {
           op = a.nextInt();
            }
            catch(InputMismatchException e) {
            System.out.println("Please enter a number.");
            a.nextLine();
            continue;
           }

           
            switch(op) {

                case 1:

                    
                System.out.println("How many employees we want to enter:");

                try {
                    choice = a.nextInt();
                }
                catch(InputMismatchException e) {
                    System.out.println("Please enter a valid number.");
                    a.nextLine();
                    break;
                }

                if(choice <= 0) {
                    System.out.println("Employee count must be greater than 0.");
                    break;
                }

                addemployee(emp, choice, a);


                    break;


                case 2:

                    viewemployee(emp, a);

                    break;


                case 3:

                    updateemployee(emp, a);

                    break;


                case 4:

                    deleteemployee(emp, a);

                    break;



                case 5:

                    viewAllEmployees(emp);

                    break;


                case 6:
                
                saveEmployees(emp);
                System.out.println("Exit");

                break;


                case 7:

                sortEmployeesBySalary(emp);

                break;


                case 8:

                countEmployees(emp);

                break;


                case 9:

                highestPaidEmployee(emp);

                break;


                case 10:
                lowestPaidEmployee(emp);
                break;
                            

                default:
                    System.out.println("Invalid operation");

                    break;
            }

        } while(op != 6);
    }
}
