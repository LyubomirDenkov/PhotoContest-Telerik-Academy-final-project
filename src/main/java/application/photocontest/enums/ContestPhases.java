package application.photocontest.enums;

public enum ContestPhases {
    PREPARING, VOTING, FINISHED;

    @Override
    public String toString() {
        return super.name().toLowerCase();
    }
}
