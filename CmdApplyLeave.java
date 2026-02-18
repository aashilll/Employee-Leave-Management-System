import java.util.*;

public class CmdApplyLeave extends RecordedCommand{
    
    String employeeName;
    LeaveRecord leave;
    ArrayList<Team> teamsLed = new ArrayList<>();
    ArrayList<ActingHeadRecord> actingHeadRecords = new ArrayList<>();

    @Override
    public void execute(String[] cmdParts) throws Exception {

        if (cmdParts.length < 5) {
            throw new ExInsufficientArguments();
        }


        Company company = Company.getInstance();
        employeeName = cmdParts[1];
        Employee e = company.searchEmployee(employeeName);

        Day start = new Day(cmdParts[3]);
        Day end = new Day(cmdParts[4]);


        Day systemDate = SystemDate.getInstance(); 
        if (start.compareTo(systemDate) < 0) {
            String msg = String.format("Wrong Date. Leave start date must not be earlier than the system date (%s)!", systemDate);
            throw new ExWrongDate(msg);
        }


        if (e == null) {
            throw new ExEmployeeNotFound();
        }

        
        for (Team team : company.getTeamsList()) {
            if (team.getHead().getName().equals(employeeName)) {
                teamsLed.add(team);
            }
        }

        for (LeaveRecord existingLeave: e.getLeavesRecord()) {
            
            boolean overlap = (start.compareTo(existingLeave.getEndDay()) <= 0) && (end.compareTo(existingLeave.getStartDay()) >= 0);
            
            if (overlap) {
                throw new ExLeaveOverlap(String.format("Leave overlapped: The leave period %s to %s [%s] is found!", existingLeave.getStartDay(), existingLeave.getEndDay(), existingLeave.getLeaveType()));
            }
        }

        if (teamsLed.size() != 0) {
            if (cmdParts.length < 5 + (teamsLed.size() * 2)) {
                for (int i=0; i<teamsLed.size(); i++) {
                    
                    boolean teamfound = false;
                    String thisTeamName = teamsLed.get(i).getName();
                    for (int j=0; j<cmdParts.length; j++) {
                        if (thisTeamName.equals(cmdParts[j])) {
                            teamfound = true;
                            break;
                        }
                    }
                    if (!teamfound) {
                        throw new ExMissingActingHead(String.format("Missing input:  Please provide the acting head for %s", thisTeamName));
                    }
                    
                }

            }
        }

        for (Team team: company.getTeamsList()) {
            for (ActingHeadRecord actingHeadRecord : team.getActingHeads()) {
                if (actingHeadRecord.getActingHead().getName().equals(employeeName)) {
                    //if name matches then check if there is overlap with already actinng head record
                    boolean overlap = (start.compareTo(actingHeadRecord.getEndDate()) <= 0) && 
                             (end.compareTo(actingHeadRecord.getStartDate()) >= 0);
                    if (overlap) {
                        throw new ExLeaveOverlap(String.format("Cannot take leave.  %s is the acting head of %s during %s to %s!", employeeName, team.getName(), actingHeadRecord.getStartDate(), actingHeadRecord.getEndDate()));
                    }
                }
            }
        }

        
        Boolean exceptionThrown = false;
        for (int i=0; i<teamsLed.size(); i++) {
            String providedTeamName = cmdParts[5+ 2*i];
            String actingHeadName = cmdParts[6+ 2*i];
            

            Team team = null;
            for (Team t : teamsLed) {
                if (t.getName().equals(providedTeamName)) {
                    team = t;
                    break;
                }
            }

            if (team == null) {
                exceptionThrown = true;
                throw new ExTeamNotFound();
            }

            Employee actingHead = company.searchEmployee(actingHeadName);

            if (team.searchMember(actingHeadName) == null) {
                
                String msg = String.format("Employee (%s) not found for %s!", actingHeadName, providedTeamName);
                exceptionThrown = true;
                throw new ExEmployeeNotFoundForTeam(msg);
            }

            for (LeaveRecord existingLeave: actingHead.getLeavesRecord()) {
                
                boolean overlap = (start.compareTo(existingLeave.getEndDay()) <= 0) && (end.compareTo(existingLeave.getStartDay()) >= 0);
                if (overlap) {
                    exceptionThrown = true;
                    throw new ExLeaveOverlap(String.format("%s is on leave during %s to %s [%s]!", actingHead.getName(), existingLeave.getStartDay(), existingLeave.getEndDay(), existingLeave.getLeaveType()));
                }


            }  

            ActingHeadRecord tempRecord = new ActingHeadRecord(team, actingHead, start, end);
            actingHeadRecords.add(tempRecord);            
            
        }

        if (!exceptionThrown) {
            for (ActingHeadRecord tempRecord : actingHeadRecords) {
                tempRecord.getTeam().addActingHead(tempRecord);
            }
        }


        
        


        //if not exceptions occur till here then just check against the type of leave
        if (cmdParts[2].equals("AL")) {
            leave = new LeaveRecordAL(start, end);
            if (leave.getLeaveDays() >= 7) {
                throw new ExUseBlockLeave();
            }

            if (!e.hasTakenBlockLeave()) {
                int maxAllowedAL = e.getRemainingAnnualLeaveDays() - 7; // this is to reserve 7 days for the potential block leave
                if (maxAllowedAL < 0) {
                    maxAllowedAL = 0;
                }

                if (leave.getLeaveDays() > maxAllowedAL) {
                    String msg = String.format("The annual leave is invalid.\nThe current balance is %d days only.\nThe employee has not taken any block leave yet.\nThe employee can take at most %d days of non-block annual leave\nbecause of the need to reserve 7 days for a block leave.", e.getRemainingAnnualLeaveDays(), maxAllowedAL);
                    
                    throw new ExBlockLeaveReservation(msg);
                }
            }

            int remainingAnnualDays = e.getRemainingAnnualLeaveDays();
            if (remainingAnnualDays < leave.getLeaveDays()) {
                throw new ExInsufficientAnnualLeavesBalance(String.format("Insufficient balance of annual leaves. %d days left only!", remainingAnnualDays));
            }

            e.addLeave(leave);
        } else if (cmdParts[2].equals("BL")) {
            leave = new LeaveRecordBL(start, end);
            if (leave.getLeaveDays() <= 6) {
                throw new ExUseAnnualLeave();
            }

            int remainingAnnualDays = e.getRemainingAnnualLeaveDays();
            if (remainingAnnualDays < leave.getLeaveDays()) {
                throw new ExInsufficientAnnualLeavesBalance(String.format("Insufficient balance of annual leaves. %d days left only!", remainingAnnualDays));
            }
            e.addLeave(leave);
        } else if (cmdParts[2].equals("NL")) {
            leave = new LeaveRecordNL(start, end);
            e.addLeave(leave);
        } else if (cmdParts[2].equals("SL")) {
            leave = new LeaveRecordSL(start, end);
            int remainingSickDays = e.getRemainingSickLeaveDays();
            if (remainingSickDays < leave.getLeaveDays()) {
                throw new ExInsufficientAnnualLeavesBalance(String.format("Insufficient balance of sick leaves. %d days left only!", remainingSickDays));
            }
            
            
            e.addLeave(leave);
        } else {
            throw new ExInvalidLeaveType();
        }

        //now adding to the undo list:
        addUndoCommand(this);
        //whenever u do a new command, clear the redo list
        clearRedoList();

        System.out.println("Done.");
    }

    @Override
    public void undoMe() {
        Company company = Company.getInstance();
        Employee e = company.searchEmployee(employeeName);
        e.removeLeave(leave);
        
        for (ActingHeadRecord thisActingHeadRecord : actingHeadRecords) {
            thisActingHeadRecord.getTeam().removeActingHead(thisActingHeadRecord);
        }
        addRedoCommand(this);

    }

    @Override
    public void redoMe() {
        Company company = Company.getInstance();
        Employee e = company.searchEmployee(employeeName);
        e.addLeave(leave);

        for (ActingHeadRecord thisActingHeadRecord : actingHeadRecords) {
            thisActingHeadRecord.getTeam().addActingHead(thisActingHeadRecord);
           
        }
        addUndoCommand(this);
        
    }


}
