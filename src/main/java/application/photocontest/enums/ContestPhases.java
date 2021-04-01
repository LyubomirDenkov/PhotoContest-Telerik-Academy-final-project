package application.photocontest.enums;

public enum ContestPhases {
    PHASEONE, PHASETWO, FINISHED;

    @Override
    public String toString() {
        return super.name().toLowerCase();
    }
}
