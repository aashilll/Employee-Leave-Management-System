public class ExAnnualLeavesOutOfRange extends Exception {
    public ExAnnualLeavesOutOfRange() {
        super("Out of range (Entitled Annual Leaves should be within 0-300)!");
    }
}
