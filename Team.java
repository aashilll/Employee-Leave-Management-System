import java.util.*;

public class Team implements Comparable<Team>{
    private String teamName;
    private Employee head;
    private ArrayList<Employee> teamMembers;
    private ArrayList<ActingHeadRecord> actingHeads;
    private Day dateSetup; //the date this team was setup


    public Team(String n, Employee hd) {
        teamName = n;
        head = hd;
        dateSetup = SystemDate.getInstance().clone();
        teamMembers = new ArrayList<>();
        teamMembers.add(hd);
        actingHeads = new ArrayList<>();
       
    }

    public static void list(ArrayList<Team> list) {
        System.out.printf("%-30s%-10s%-13s\n", "Team Name", "Leader", "Setup Date");
        
        for (Team t : list) {
            System.out.printf("%-30s%-10s%-13s\n", t.getName(), t.getHead().getName(), t.getDate());
            
        }
    }

    public Employee searchMember(String employeeName) {
        for (Employee e: teamMembers) {
            if (e.getName().equals(employeeName)) {
                return e; 
            }
        }
        return null;
    }

    public String getName() {return teamName;}
    
    public Employee getHead() {return head;}
    
    public Day getDate() {return dateSetup;}

    public void addMember(Employee employee) {
        
        teamMembers.add(employee);
        Collections.sort(teamMembers);
    }

    public void addActingHead(ActingHeadRecord actingHead) {
        actingHeads.add(actingHead);
        Collections.sort(actingHeads);
    }

    public void removeActingHead(ActingHeadRecord actingHead) {
        actingHeads.remove(actingHead);
       
    }

    public void removeMember(Employee employee) {
        teamMembers.remove(employee);
    }

    public ArrayList<Employee> getTeamMembers() {
        return teamMembers;
    }

    public ArrayList<ActingHeadRecord> getActingHeads() {
        return actingHeads;
    }

    @Override
    public int compareTo(Team another) {
        return this.teamName.compareTo(another.getName()); // this calls the compareTo method for string to compare the names of the two teams.
    }

}


