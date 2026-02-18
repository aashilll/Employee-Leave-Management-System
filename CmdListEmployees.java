public class CmdListEmployees implements Command {
    
    @Override
    public void execute(String[] cmdParts) {
        Company company = Company.getInstance();
        company.listEmployees();
    }
}