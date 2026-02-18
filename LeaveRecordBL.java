public class LeaveRecordBL extends LeaveRecord {

    public LeaveRecordBL(Day start, Day end) {
        super(start, end);
    }

    @Override
    public String getLeaveType() {
        return "BL";
    }

    
}

