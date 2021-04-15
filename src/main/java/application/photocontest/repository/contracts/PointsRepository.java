package application.photocontest.repository.contracts;

import application.photocontest.models.Points;

public interface PointsRepository {

    void createPoints(Points points);

    void updatePoints(Points points);
}