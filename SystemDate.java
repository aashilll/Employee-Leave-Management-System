public class SystemDate extends Day {
    
    private static SystemDate instance;

    private SystemDate(String sDay) {
        super(sDay);
    }

    public static SystemDate getInstance() {
        return instance;
    }

    //static method to crate the instance through main directly;
    public static void createTheInstance(String sDay) {
        if (instance == null) {
            instance = new SystemDate(sDay);
        }
        else {
            System.out.println("Cannot create one more sysem date instance");
        }
    }
}
