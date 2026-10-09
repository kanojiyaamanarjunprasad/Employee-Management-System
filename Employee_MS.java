import java.util.*;

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
        if(id > 0) {
            this.id = id;
        } 
        else {
            System.out.println("Invalid ID. ID must be greater than 0.");
        }
    }

    public void setName(String name) {
        if(name != null && name.length() > 0) {
            this.name = name;
        } 
        else {
            System.out.println("Invalid name. Name cannot be null or empty.");
        }
    }

    public void setSalary(float sallary) {
        if(sallary >= 0) {
            this.sallary = sallary;
        } 
        else {
            System.out.println("Invalid salary. Salary must be non-negative.");
        }
    }

    public abstract void displayRole();
}


// Manager inherits Employee
class Manager extends Employee implements Bonus {

    private int teamSize;

    Manager(int id, String name, float sallary, int teamSize) {

        super(id, name, sallary);

        this.teamSize = teamSize;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(int teamSize) {
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

        this.programmingLanguage = programmingLanguage;
    }

    public String getProgrammingLanguage() {
        return programmingLanguage;
    }

    public void setProgrammingLanguage(String programmingLanguage) {
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

   static void addemployee(ArrayList<Employee> emp, int choice, Scanner a) {

    for(int i = 0; i < choice; i++) {

      
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


        
        System.out.println("Enter the employee name:");

        String name = a.next();

        if(name.matches("\\d+")) {
            System.out.println("Name cannot contain only numbers.");
            continue;
        }

        if(name.trim().isEmpty()) {
            System.out.println("Name cannot be empty.");
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

        if(sallary < 0) {
            System.out.println("Salary cannot be negative.");
            continue;
        }


        Employee e;

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
    else if(type == 2) {

        System.out.println("Enter programming language:");  
        String programmingLanguage = a.next();

        e = new Developer(ID, name, sallary, programmingLanguage);

    }
    else {

        System.out.println("Invalid employee type");
        continue;
    }

    emp.add(e);

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
        }
    }

    if(found == 1) {
        System.out.println("Employee not exist");
    }
}


    static void updateemployee(ArrayList<Employee> emp, Scanner a) {

    System.out.println("Enter the employee id which we want to update: ");
    int ID = a.nextInt();

    for(int i = 0; i < emp.size(); i++) {

        if(emp.get(i).getId() == ID) {

            System.out.println("Enter the new id:");
            emp.get(i).setId(a.nextInt());

            System.out.println("Enter the new name: ");
            emp.get(i).setName(a.next());

            System.out.println("Enter the new salary:");
            emp.get(i).setSalary(a.nextFloat());


            if(emp.get(i) instanceof Manager) {

                Manager m = (Manager) emp.get(i);

                System.out.println("Enter the new team size:");
                m.setTeamSize(a.nextInt());
            }


            if(emp.get(i) instanceof Developer) {

                Developer d = (Developer) emp.get(i);

                System.out.println("Enter the new programming language:");
                d.setProgrammingLanguage(a.next());
            }


            System.out.println("Employee updated");
        }
    }
}


    static void deleteemployee(ArrayList<Employee> emp, Scanner a) {

        System.out.println("Enter the Employee id which we want to delete:");
        int ID = a.nextInt();

        for(int i = 0; i < emp.size(); i++) {

            if(emp.get(i).getId() == ID) {

                emp.remove(i);

                System.out.println("Employee deleted");

                break;
            }
        }
    }


    public static void main(String[] args) {

        ArrayList<Employee> emp = new ArrayList<>();

        Scanner a = new Scanner(System.in);

        int choice, op=0;

        do {

            System.out.println("\nEnter the operations which we want to select:");
            System.out.println("1. Add employee");
            System.out.println("2. View employee");
            System.out.println("3. Update employee");
            System.out.println("4. Delete employee");
            System.out.println("5. Exit");


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

                    System.out.println("Exit");

                    break;


                default:

                    System.out.println("Invalid operation");

                    break;
            }

        } while(op != 5);
    }
}
