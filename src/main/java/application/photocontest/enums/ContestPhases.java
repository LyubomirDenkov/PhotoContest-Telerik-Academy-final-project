package application.photocontest.enums;

public enum ContestPhases {
    ONGOING, VOTING, FINISHED;

    @Override
    public String toString() {
        return super.name().toLowerCase();
    }
}
