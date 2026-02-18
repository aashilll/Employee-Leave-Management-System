public class CmdListLeaves implements Command {
    
    @Override
    public void execute(String[] cmdParts) {
        Company company = Company.getInstance();
        
        if (cmdParts.length == 1) {
            for (Employee e: company.getEmployeesList()) {
                System.out.println(e.getName() + ":");
                e.listLeaves();
            }
        } else {
            Employee e = company.searchEmployee(cmdParts[1]);
            e.listLeaves();
        }
        
    }
}