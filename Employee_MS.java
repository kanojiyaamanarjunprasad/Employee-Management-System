import java.util.*;
import java.io.*;



public class Employee_MS {

    
static boolean saveEmployees(ArrayList<Employee> emp) {

    File tempFile = new File("employees_temp.txt");
    File dataFile = new File("employees.txt");

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {

        for (int i = 0; i < emp.size(); i++) {

            Employee e = emp.get(i);

            if (e instanceof Manager) {
                Manager m = (Manager) e;

                bw.write("Manager," + m.getId() + "," +
                         m.getName() + "," + m.getSalary() + "," +
                         m.getTeamSize());

            } else if (e instanceof Developer) {
                Developer d = (Developer) e;

                bw.write("Developer," + d.getId() + "," +
                         d.getName() + "," + d.getSalary() + "," +
                         d.getProgrammingLanguage());
            }

            bw.newLine();
        }

    } catch (IOException e) {
        System.out.println("Error saving employees: " + e.getMessage());
        return false;
    }

    
    try {
        try {
            java.nio.file.Files.move(
                tempFile.toPath(),
                dataFile.toPath(),
                java.nio.file.StandardCopyOption.REPLACE_EXISTING,
                java.nio.file.StandardCopyOption.ATOMIC_MOVE
            );
        } catch (java.nio.file.AtomicMoveNotSupportedException e) {
            java.nio.file.Files.move(
                tempFile.toPath(),
                dataFile.toPath(),
                java.nio.file.StandardCopyOption.REPLACE_EXISTING
            );
        }

        System.out.println("Employees saved successfully.");
        return true;

    } catch (IOException e) {
        System.out.println("Error replacing employee file: " + e.getMessage());
        System.out.println("Check the employee files and try again.");
        return false;
    }

}

    




static void loadEmployees(ArrayList<Employee> emp) {

    File file = new File("employees.txt");

    if (!file.exists()) {
        System.out.println("No saved employee records found.");
        return;
    }

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {

        String line;
        int loaded = 0;
        int skipped = 0;

        while ((line = br.readLine()) != null) {

            try {
                String[] data = line.split(",", -1);

                if (data.length != 5) {
                    throw new IllegalArgumentException(
                        "Invalid number of fields."
                    );
                }

                String type = data[0];
                int id = Integer.parseInt(data[1]);
                String name = data[2];
                float salary = Float.parseFloat(data[3]);

                if (id <= 0 || !Float.isFinite(salary) || salary < 0) {
                    throw new IllegalArgumentException(
                        "Invalid employee ID or salary."
                    );
                }

                boolean duplicateID = false;

                for (int i = 0; i < emp.size(); i++) {
                    if (emp.get(i).getId() == id) {
                        duplicateID = true;
                        break;
                    }
                }

                if (duplicateID) {
                    System.out.println(
                        "Skipping duplicate employee ID: " + id
                    );
                    skipped++;
                    continue;
                }

                Employee e;

                if (type.equals("Manager")) {

                    int teamSize = Integer.parseInt(data[4]);

                    if (teamSize < 0) {
                        throw new IllegalArgumentException(
                            "Team size cannot be negative."
                        );
                    }

                    e = new Manager(id, name, salary, teamSize);

                } else if (type.equals("Developer")) {

                    String language = data[4];

                    e = new Developer(id, name, salary, language);

                } else {
                    throw new IllegalArgumentException(
                        "Unknown employee type: " + type
                    );
                }

                emp.add(e);
                loaded++;

            } catch (IllegalArgumentException ex) {
                System.out.println(
                    "Skipping invalid record: " + line
                    + " | Reason: " + ex.getMessage()
                );
                skipped++;
            }
        }

        System.out.println("Employees loaded successfully: " + loaded);
        System.out.println("Invalid or duplicate records skipped: " + skipped);

    } catch (IOException e) {
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
            if(name.trim().isEmpty()
        || name.matches("\\d+")
        || name.contains(",")) {
        throw new IllegalArgumentException(
            "Name cannot be empty, contain only numbers, or contain commas."
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
        a.nextLine();
        String programmingLanguage = a.nextLine();

        if (programmingLanguage.trim().isEmpty()
                || programmingLanguage.matches("\\d+")
                || programmingLanguage.contains(",")) {
            System.out.println(
                "Programming language cannot be empty, contain only numbers, or contain commas."
            );
            continue;
        }


        e = new Developer(ID, name, sallary, programmingLanguage);

    }

    emp.add(e);

    if(saveEmployees(emp)) {
        added++;
        System.out.println("Employee added successfully.");
    } else {
        emp.remove(e);
        System.out.println("Employee was not added because saving failed.");
    }

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
        || newName.matches("\\d+")
        || newName.contains(",")) {
    System.out.println("Invalid name. Commas are not allowed.");
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
        a.nextLine();
        newLanguage = a.nextLine();

       if(newLanguage.matches("\\d+") || newLanguage.contains(",")) {
       System.out.println(
        "Programming language cannot contain only numbers or commas."
       );
       return;
     }

        if(newLanguage.trim().isEmpty()) {
            System.out.println("Programming language cannot be empty.");
            return;
        }
    }



    
    // Store the original employee details.
    int oldID = current.getId();
    String oldName = current.getName();
    float oldSalary = current.getSalary();

    int oldTeamSize = 0;
    String oldLanguage = null;

    if(current instanceof Manager) {
        Manager m = (Manager) current;
        oldTeamSize = m.getTeamSize();
    }

    if(current instanceof Developer) {
        Developer d = (Developer) current;
        oldLanguage = d.getProgrammingLanguage();
    }

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

        if(saveEmployees(emp)) {
        System.out.println("Employee updated successfully.");
        } else {
        throw new IllegalArgumentException(
            "Update failed because saving failed."
        );
      }
    }
    catch(IllegalArgumentException ex) {
        // Restore the original employee details.
        current.setId(oldID);
        current.setName(oldName);
        current.setSalary(oldSalary);

        if(current instanceof Manager) {
            Manager m = (Manager) current;
            m.setTeamSize(oldTeamSize);
        }

        if(current instanceof Developer) {
            Developer d = (Developer) current;
            d.setProgrammingLanguage(oldLanguage);
        }

        System.out.println("Update failed: " + ex.getMessage());
        System.out.println("Original employee details restored.");
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

            Employee deletedEmployee = emp.remove(i);

            if(saveEmployees(emp)) {
                System.out.println("Employee deleted.");
            } else {
                emp.add(i, deletedEmployee);
                System.out.println("Employee was not deleted because saving failed.");
            }

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
