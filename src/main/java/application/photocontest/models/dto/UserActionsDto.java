package application.photocontest.models.dto;

public class UserActionsDto {

    private boolean isJury;

    private boolean isParticipant;

    private boolean hasImageUploaded;

    public UserActionsDto() {
    }

    public boolean isJury() {
        return isJury;
    }

    public void setJury(boolean jury) {
        isJury = jury;
    }

    public boolean isParticipant() {
        return isParticipant;
    }

    public void setParticipant(boolean participant) {
        isParticipant = participant;
    }

    public boolean isHasImageUploaded() {
        return hasImageUploaded;
    }

    public void setHasImageUploaded(boolean hasImageUploaded) {
        this.hasImageUploaded = hasImageUploaded;
    }
}
