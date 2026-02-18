import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {

		Scanner in = new Scanner(System.in);

		System.out.print("Please input the file pathname: ");
		String filepathname = in.nextLine();
		
		Scanner inFile = new Scanner(new File(filepathname));
		
		//The first command in the file must be to set the system date 
		//(eg. "startNewDay|01-Jan-2025"); and it cannot be undone
		String cmdLine1 = inFile.nextLine();
		String[] cmdLine1Parts = cmdLine1.split("\\|");
		System.out.println("\n> "+cmdLine1);
		SystemDate.createTheInstance(cmdLine1Parts[1]);
		
		while (inFile.hasNext())		
		{
			String cmdLine = inFile.nextLine().trim();
			
			//Blank lines exist in data file as separators.  Skip them.
			if (cmdLine.equals("")) continue;  

			System.out.println("\n> "+cmdLine);
			

			String[] cmdParts = cmdLine.split("\\|"); 
			
			try {
			if (cmdParts[0].equals("hire"))
				(new CmdHire()).execute(cmdParts);
			if (cmdParts[0].equals("startNewDay"))
				(new CmdStartNewDay()).execute(cmdParts);
			if (cmdParts[0].equals("listEmployees"))
				(new CmdListEmployees()).execute(cmdParts);
			if (cmdParts[0].equals("setupTeam"))
				(new CmdSetupTeam()).execute(cmdParts);
			if (cmdParts[0].equals("listTeams"))
				(new CmdlistTeams()).execute(cmdParts);
			if (cmdParts[0].equals("addTeamMember"))
				(new CmdAddTeamMember()).execute(cmdParts);
			if (cmdParts[0].equals("listAllRoles"))
				(new CmdListAllRoles()).execute(cmdParts);
			if (cmdParts[0].equals("listTeamMembers"))
				(new CmdListTeamMembers()).execute(cmdParts);
			if (cmdParts[0].equals("applyLeave"))
				(new CmdApplyLeave()).execute(cmdParts);
			if (cmdParts[0].equals("listLeaves"))
				(new CmdListLeaves()).execute(cmdParts);
			if (cmdParts[0].equals("undo"))
				RecordedCommand.undoOneCommand();
			if (cmdParts[0].equals("redo"))
				RecordedCommand.redoOneCommand();
			} catch (Exception e) {
    			System.out.println(e.getMessage());
			} 
			

		}
		inFile.close();
			
		in.close();
	}
}