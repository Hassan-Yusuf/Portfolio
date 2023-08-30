public class TrafficLight {
    private Enums.TLStates state;

    public TrafficLight() {
        this.state = Enums.TLStates.GREEN;
    }

    public Enums.TLStates getState() {
        return state;
    }

    public void updateState() {
        if (state == Enums.TLStates.GREEN) {
            state = Enums.TLStates.AMBER;
        } else if (state == Enums.TLStates.AMBER) {
            state = Enums.TLStates.RED;
        } else if (state == Enums.TLStates.RED) {
            state = Enums.TLStates.AMBER_FLASHING;
        } else if (state == Enums.TLStates.AMBER_FLASHING) {
            state = Enums.TLStates.GREEN;
        }
    }

}
