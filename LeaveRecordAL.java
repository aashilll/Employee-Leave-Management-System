public class LeaveRecordAL extends LeaveRecord {
    
    public LeaveRecordAL(Day start, Day end) {
        super(start, end);
    }

    @Override
    public String getLeaveType() {
        return "AL";
    }

    

}    
