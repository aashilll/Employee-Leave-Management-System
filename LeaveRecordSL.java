public class LeaveRecordSL extends LeaveRecord {

    public LeaveRecordSL(Day start, Day end) {
        super(start, end);
    }

    @Override
    public String getLeaveType() {
        return "SL";
    }
    
}
