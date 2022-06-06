package store;

public class Curency {

    private final int curcode;
    private final String curTybe;

    public Curency(int curcode, String curTybe) {
        this.curcode = curcode;
        this.curTybe = curTybe;

    }

    @Override
    public String toString() {
        return String.format("%04d-%s", this.curcode, this.curTybe);
    }
}
