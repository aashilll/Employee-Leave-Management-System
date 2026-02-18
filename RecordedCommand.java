import java.util.*;

public abstract class RecordedCommand implements Command{
    
    private static ArrayList<RecordedCommand> undoList = new ArrayList<>();
    private static ArrayList<RecordedCommand> redoList = new ArrayList<>();

    // these commands are to be implemented by subclasses, and they will act on RecordedCommand object, so they are non-static methods
    public abstract  void undoMe();
    public abstract  void redoMe();

    // to add a specific RecordedCommand comnmand to the appropriate lists 
    protected static void addUndoCommand(RecordedCommand cmd) {undoList.add(cmd);}
    protected static void addRedoCommand(RecordedCommand cmd) {redoList.add(cmd);}

    // clear redo List, to do when a new command is given
    protected static void clearRedoList() {redoList.clear();}

    // Carry out undo command
    public static void undoOneCommand() {
        if (undoList.size() == 0) {
            System.out.println("Nothing to undo.");
        } else {undoList.remove(undoList.size()-1).undoMe();}
        }
    public static void redoOneCommand() {
        if (redoList.size() == 0) {System.out.println("Nothing to redo.");} 
        else {redoList.remove(redoList.size()-1).redoMe();}
        }

}
