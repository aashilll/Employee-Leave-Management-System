import java.util.*;

public class CmdListAllRoles implements Command{

    @Override
    public void execute(String[] cmdParts) throws ExInsufficientArguments, ExEmployeeNotFound {
        
        if (cmdParts.length < 2) {throw new ExInsufficientArguments();}

        Company company = Company.getInstance();
        Employee e = company.searchEmployee(cmdParts[1]);
        if (e == null) {
            throw new ExEmployeeNotFound();
        }




        // Find all the teams this employee belongs to and adding it to an ArrayList
        ArrayList<Team> teamsEmployeeIn = new ArrayList<>();
        for (Team t : company.getTeamsList()) {
            if (t.searchMember(e.getName()) != null) {
                teamsEmployeeIn.add(t);
            }
        }

        Collections.sort(teamsEmployeeIn); // as the roles need to be listed sorted according to the teams names, and we already have impelmented comparable for team list sorting using teams' names

        if (teamsEmployeeIn.size() == 0) {
            System.out.println("No role");
        } else {
            for (Team t: teamsEmployeeIn) {
               if (t.getHead().getName().equals(e.getName())) {
                System.out.println(t.getName() + " (Head of Team)");
               } else {
                System.out.println(t.getName());
               }
            }
        }

        
    }
    
}
