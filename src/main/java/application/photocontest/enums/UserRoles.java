package application.photocontest.enums;

public enum UserRoles {
    JUNKIE,ENTHUSIAST,MASTER,DICTATOR,ORGANIZER,ADMIN;


    @Override
    public String toString() {
        return super.name().toLowerCase();
    }
}
