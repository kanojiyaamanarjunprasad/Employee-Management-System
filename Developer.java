public class Developer extends Employee implements Bonus, Work {

    private String programmingLanguage;

    public Developer(int id, String name, float sallary, String programmingLanguage) {

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

