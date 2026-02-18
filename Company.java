import java.util.*;

public class Company {
    
    private ArrayList<Employee> allEmployees;
    private ArrayList<Team> allTeams;

    private static Company instance = new Company();

    private Company() {
        allEmployees = new ArrayList<>();
        allTeams = new ArrayList<>();
    }

    public static Company getInstance() {
        return instance;
    }

    public ArrayList<Employee> getEmployeesList() {
        return allEmployees;
    }

    public ArrayList<Team> getTeamsList() {
        return allTeams;
    }

    public void listTeams() {
        Team.list(allTeams);
    }


    public void listEmployees() {
        for (Employee e : allEmployees) {
            System.out.println(e);
        }
    }

    public Employee createEmployee(String n, int yle) {
        Employee e = new Employee(n, yle);
        allEmployees.add(e);
        Collections.sort(allEmployees); // allEmployees
        return e;
    }

    public void addEmployee(Employee e) {
        allEmployees.add(e);
        Collections.sort(allEmployees);
    }

    public void removeEmployee(Employee e) {
        allEmployees.remove(allEmployees.indexOf(e));
    }


    public Team createTeam(String n, String l) //See how it is called in main()
    {
        Employee e = Employee.searchEmployee(allEmployees,l);
        Team t = new Team(n, e);
        allTeams.add(t);
        Collections.sort(allTeams); // allTeams
        return t; // the return value is useful for later undoable command.
    }

    public void addTeam(Team t) {
        allTeams.add(t);
        Collections.sort(allTeams);
    }

    public void removeTeam(Team t) {
        allTeams.remove(allTeams.indexOf(t));
    }

    public Employee searchEmployee(String name) {
        for (Employee e : allEmployees) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        return null;
    }

    public Team searchTeam(String teamName) {
        for (Team t: allTeams) {
            if (teamName.equals(t.getName())) {
                return t;
            }
        }
        return null;
    }

    
   
}
