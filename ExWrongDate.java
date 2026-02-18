public class ExWrongDate extends Exception {

    public ExWrongDate() {
        super("Wrong Date. Leave start date must not be earlier than the system date");

    }

    public ExWrongDate(String msg) {
        super(msg);
    }
    
}
