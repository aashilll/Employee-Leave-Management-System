public abstract class LeaveRecord implements Comparable<LeaveRecord>{
    private Day startDate;
    private Day endDate;

    public LeaveRecord(Day start, Day end) {
        startDate = start;
        endDate = end;
    }

    public abstract String getLeaveType();

    @Override
    public String toString() {
        return String.format("%s to %s [%s]", startDate, endDate, getLeaveType());
    } 

    @Override
    public int compareTo(LeaveRecord another) {
        return this.startDate.compareTo(another.startDate);
    }

    public Day getStartDay() {return startDate;}
    public Day getEndDay() {return endDate;}

    public int getLeaveDays() {
        int startTotalDays = convertToTotalDays(startDate);
        int endTotalDays = convertToTotalDays(endDate);
        return (endTotalDays - startTotalDays) + 1;
    }

    public int convertToTotalDays(Day date) {
        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();

        int totalDays = year * 365; 
        
        totalDays += countLeapYears(year);
        
        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        for (int i = 0; i < month - 1; i++) {
            totalDays += daysInMonth[i];
        }
        
        if (month > 2 && Day.isLeapYear(year)) {
            totalDays += 1;
        }
        
        totalDays += day;
        
        return totalDays;

    }

    private int countLeapYears(int year) {
        return (year / 4) - (year / 100) + (year / 400);
    }

    



}