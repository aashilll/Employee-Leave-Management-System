public class LeaveRecordNL extends LeaveRecord {
    
    public LeaveRecordNL(Day start, Day end) {
        super(start, end);
    }

    @Override
    public String getLeaveType() {
        return "NL";
    }

}    
