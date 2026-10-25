public abstract class Employee {

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
