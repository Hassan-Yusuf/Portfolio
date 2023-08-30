public class PedestrianSignal {
    private Enums.PedestrianSState state;

    public PedestrianSignal() {
        this.state = Enums.PedestrianSState.RED_PERSON_ILLUMINATED;
    }

    public Enums.PedestrianSState getState() {
        return state;
    }

    public void updateState() {
        if (state == Enums.PedestrianSState.RED_PERSON_ILLUMINATED) {
            state = Enums.PedestrianSState.GREEN_PERSON_ILLUMINATED;
        } else if (state == Enums.PedestrianSState.GREEN_PERSON_ILLUMINATED) {
            state = Enums.PedestrianSState.GREEN_PERSON_FLASHING;
        } else if (state == Enums.PedestrianSState.GREEN_PERSON_FLASHING) {
            state = Enums.PedestrianSState.RED_PERSON_ILLUMINATED;
        }
    }

}
