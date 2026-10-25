public class Manager extends Employee implements Bonus {

    private int teamSize;

    public Manager(int id, String name, float sallary, int teamSize) {
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