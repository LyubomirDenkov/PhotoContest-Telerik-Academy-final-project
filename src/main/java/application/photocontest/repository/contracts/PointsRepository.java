package application.photocontest.repository.contracts;

import application.photocontest.models.Points;

public interface PointsRepository {

    Points getById(int id);

    Points createPoints(Points points);

    void update(Points points);
}