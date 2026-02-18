public class CmdAddTeamMember extends RecordedCommand{
    private String teamName;
    private String employeeName;

    @Override
    public void execute(String[] cmdParts) throws Exception {
        Company company = Company.getInstance();
        
        if (cmdParts.length < 3) {
            throw new ExInsufficientArguments();
        }

        Team team = company.searchTeam(cmdParts[1]);
        if (team == null) {
            throw new ExTeamNotFound();
        }

        Employee employee = company.searchEmployee(cmdParts[2]);
        if (employee == null) {
            throw new ExEmployeeNotFound();}

        
        if (team.searchMember(employee.getName()) != null) {
            throw new ExEmployeeAlreadyInTeam();
        }

        teamName = cmdParts[1];
        employeeName = cmdParts[2];

        team.addMember(employee);



        //now adding to the undo list:
        addUndoCommand(this);
        //whenever u do a new command, clear the redo list
        clearRedoList();

        System.out.println("Done.");

        
    }

    @Override
    public void undoMe() {
        Company company = Company.getInstance();
        Team team = company.searchTeam(teamName);
        Employee employee = company.searchEmployee(employeeName);
        team.removeMember(employee);

        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        Company company = Company.getInstance();
        Team team = company.searchTeam(teamName);
        Employee employee = company.searchEmployee(employeeName);
        team.addMember(employee);
        
        addUndoCommand(this);
    }
}
