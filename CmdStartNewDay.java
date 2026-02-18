public class CmdStartNewDay extends RecordedCommand {
    Day oldDay;
    Day newDay;

    @Override
    public void execute(String[] cmdParts) throws ExInsufficientArguments{
        
        if (cmdParts.length < 2) {
            throw new ExInsufficientArguments();
        }
        

        SystemDate sysDate = SystemDate.getInstance();
        oldDay = sysDate.clone();
        newDay = new Day(cmdParts[1]);
        sysDate.set(cmdParts[1]);

        //now adding to the undo list:
        addUndoCommand(this);
        //whenever u do a new command, clear the redo list
        clearRedoList();

        System.out.println("Done.");
    }

    @Override
    public void undoMe() {
        SystemDate sysDate = SystemDate.getInstance();
        sysDate.set(oldDay.toString());
        Day temp = newDay;
        newDay = oldDay;
        oldDay = temp;

        
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        SystemDate sysDate = SystemDate.getInstance();
        sysDate.set(newDay.toString());
        Day temp = newDay;
        newDay = oldDay;
        oldDay = temp;


        addUndoCommand(this);
    }
}
