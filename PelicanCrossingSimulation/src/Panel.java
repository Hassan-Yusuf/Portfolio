public class Panel {
    private Enums.PanelStates state;

    public Panel() {
        this.state = Enums.PanelStates.STANDBY;
    }

    public void pressButton() {
        if (state == Enums.PanelStates.STANDBY) {
            state = Enums.PanelStates.WAITING;
        }
    }

    public Enums.PanelStates getState() {
        return state;
    }

    public void updateState() {
        if (state == Enums.PanelStates.WAITING) {
            state = Enums.PanelStates.OFF;
        } else if (state == Enums.PanelStates.OFF) {
            state = Enums.PanelStates.STANDBY;
        }
    }
}
