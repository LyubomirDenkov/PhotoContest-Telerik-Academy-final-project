package application.photocontest.repository.contracts;

import application.photocontest.models.Phase;

public interface PhaseRepository {

    Phase getPhaseByName(String phaseName);
}
