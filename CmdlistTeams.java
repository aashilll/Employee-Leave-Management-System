public class CmdlistTeams implements Command{
    
    @Override
    public void execute(String[] cmdParts) {
        Company company = Company.getInstance();
        company.listTeams();
    }
}
