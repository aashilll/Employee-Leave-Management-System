public class CmdHire extends RecordedCommand {
    
    private Employee e;

    @Override
    public void execute(String[] cmdParts) throws ExInsufficientArguments, ExEmployeeAlreadyExists, ExAnnualLeavesOutOfRange {
        Company company = Company.getInstance();
        
        if (cmdParts.length < 3) {
            throw new ExInsufficientArguments();
        }
        if (company.searchEmployee(cmdParts[1]) != null) {
            throw new ExEmployeeAlreadyExists();
        }

        int annualLeaves = Integer.parseInt(cmdParts[2]);
        if (annualLeaves < 0 || annualLeaves >300) {
            throw new ExAnnualLeavesOutOfRange();
        }

        e = new Employee(cmdParts[1], annualLeaves);
        company.addEmployee(e);

        //now adding to the undo list:
        addUndoCommand(this);
        //whenever u do a new command, clear the redo list
        clearRedoList();

        System.out.println("Done.");

        
    }

    @Override
    public void undoMe() {
        Company company = Company.getInstance();
        company.removeEmployee(e);
        
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        Company company = Company.getInstance();
        company.createEmployee(e.getName(), e.getAnnualLeaves());
        addUndoCommand(this);
    }
}
