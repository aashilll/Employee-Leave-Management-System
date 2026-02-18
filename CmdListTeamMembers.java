import java.util.ArrayList;

public class CmdListTeamMembers  implements Command{

    @Override
    public void execute(String[] cmdParts) {
        
        Company company = Company.getInstance();

        for (Team t: company.getTeamsList()) {
            System.out.println(t.getName() + ":");
            ArrayList<Employee> teamMembers = t.getTeamMembers();
            for (Employee e: teamMembers) {
                if (t.getHead().getName().equals(e.getName())) {
                    System.out.println(e.getName() + " (Head of Team)");
                } else {
                    System.out.println(e.getName());
                }
            }
            
            ArrayList<ActingHeadRecord> actingHeads = t.getActingHeads();
                if (actingHeads.size() != 0) {
                System.out.println("Acting heads:");
                for (ActingHeadRecord actingHead : actingHeads) {
                    System.out.println(String.format("%s to %s: %s", actingHead.getStartDate(), actingHead.getEndDate(), actingHead.getActingHead().getName()));
                }
            }
            System.out.println();
        }

        
    }
    
}
