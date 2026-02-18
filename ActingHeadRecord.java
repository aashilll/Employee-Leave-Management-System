public class ActingHeadRecord implements Comparable<ActingHeadRecord>{
    
    private Team team;
    private Employee actingHead;
    private Day startDate;
    private Day endDate;

    public ActingHeadRecord(Team team, Employee actingHead, Day startDate, Day endDate) {
        this.team = team;
        this.actingHead = actingHead;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Day getStartDate() {
        return startDate;
    }


    public Day getEndDate() {
        return endDate;
    }
    
    public Employee getActingHead() {
        return actingHead;
    }

    public Team getTeam() {
        return team;
    }

    @Override
    public int compareTo(ActingHeadRecord another) {
        return startDate.compareTo(another.getStartDate());
    }


}
