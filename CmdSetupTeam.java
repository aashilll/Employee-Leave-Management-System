public class CmdSetupTeam extends RecordedCommand {
    
    private Team t;

    @Override
    public void execute(String[] cmdParts)  throws ExInsufficientArguments, ExTeamAlreadyExists, ExEmployeeNotFound{
        
        Company company = Company.getInstance();

        if (cmdParts.length < 3) {
            throw new ExInsufficientArguments();
        }

        if (company.searchEmployee(cmdParts[2]) == null) {
            throw new ExEmployeeNotFound();
        }

        if (company.searchTeam(cmdParts[1]) != null) {
            throw new ExTeamAlreadyExists();
        }

        t = new Team(cmdParts[1], company.searchEmployee(cmdParts[2]));

        
        company.addTeam(t);
        //now adding to the undo list:
        addUndoCommand(this);
        //whenever u do a new command, clear the redo list
        clearRedoList();

        System.out.println("Done.");
       
    }

    @Override
    public void undoMe() {
        Company company = Company.getInstance();
        company.removeTeam(t);
        
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        Company company = Company.getInstance();
        company.createTeam(t.getName(), t.getHead().getName());
        addUndoCommand(this);
    }
}
