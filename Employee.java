import java.util.*;

public class Employee implements Comparable<Employee> {
    
    private String name;
    private int annualLeaves;
    private ArrayList<LeaveRecord> leavesRecord;

    public Employee(String n, int yle) {
        name = n;
        annualLeaves = yle;
        leavesRecord = new ArrayList<>();

    }

    public String getName() {return name;}

    public int getAnnualLeaves() {return annualLeaves;}

    public static Employee searchEmployee(ArrayList<Employee> list, String nameToSearch) {
        for (Employee e : list) {
            if (e.getName().equals(nameToSearch)) {
                return e;
            }
        }
        return null;
    }

    public void addLeave(LeaveRecord leave) {
        leavesRecord.add(leave);
        Collections.sort(leavesRecord);
    }

    public void removeLeave(LeaveRecord leave) {
        leavesRecord.remove(leave);
    }

    public void listLeaves() {
        if (leavesRecord.size() == 0) {
            System.out.println("No leave record");
        } else {
            for (LeaveRecord leave: leavesRecord) {
                System.out.println(leave);
            }
        }
    }

    public ArrayList<LeaveRecord> getLeavesRecord(){
        return leavesRecord;
    }

    public int getRemainingAnnualLeaveDays() {
        int usedAnnualDays = 0;
        for (LeaveRecord leave : leavesRecord) {
            if (leave.getLeaveType().equals("AL") || leave.getLeaveType().equals("BL")) {
                usedAnnualDays += leave.getLeaveDays();
            }
        }
        return annualLeaves-usedAnnualDays;
    }

    public int getRemainingSickLeaveDays() {
        int usedSickDays = 0;
        for (LeaveRecord leave : leavesRecord) {
            if (leave.getLeaveType().equals("SL")) {
                usedSickDays += leave.getLeaveDays();
            }
        }
        return 135-usedSickDays;
    }

    public boolean hasTakenBlockLeave() {
        for (LeaveRecord leave : leavesRecord) {
            if (leave.getLeaveType().equals("BL")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int compareTo(Employee another) {
        return this.name.compareTo(another.getName());
    }

    @Override
    public String toString() {
        return String.format("%s (Entitled Annual Leaves: %s days)", name, annualLeaves);
    }
}
