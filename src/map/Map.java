package map;

import entities.Entity;

import java.util.HashMap;
import java.util.Set;

public class Map {
    private int rows;
    private int cols;
    private HashMap<Coordinates, Entity> mapEntities;
    private HashMap<Entity, Coordinates> mapCoordinates;


    public Map(int rows, int cols) {
        this.mapEntities = new HashMap<>();
        this.mapCoordinates = new HashMap<>();
        this.rows = rows;
        this.cols = cols;
    }

    public Entity get(Coordinates coordinates) {
        validateCoordinates(coordinates);
        return mapEntities.get(coordinates);
    }

    public Coordinates get(Entity entity) {
        return mapCoordinates.get(entity);
    }

    public boolean put(Coordinates coordinates, Entity entity) {
        validateCoordinates(coordinates);
        mapCoordinates.remove(mapEntities.get(coordinates));
        mapEntities.put(coordinates, entity);
        mapCoordinates.put(entity, coordinates);
        return true;
    }

    public boolean remove(Coordinates coordinates) {
        validateCoordinates(coordinates);
        Entity entity = mapEntities.remove(coordinates);
        mapCoordinates.remove(entity);
        return true;
    }

    public Set<Coordinates> getAllCoordinates() {
        return mapEntities.keySet();
    }

    public Set<Entity> getAllEntities() {
        return mapCoordinates.keySet();
    }


    public void validateCoordinates(Coordinates coordinates) {
        if (coordinates.getRow() < 0 || coordinates.getCol() < 0) {
            throw new IndexOutOfBoundsException("map.Coordinates can't be negative");
        } else if (coordinates.getCol() >= cols || coordinates.getRow() >= rows) {
            throw new IndexOutOfBoundsException("map.Coordinates out of bounds");
        }
    }

    public boolean contains(Entity entity){
        return mapCoordinates.containsKey(entity);
    }

    public boolean contains(Coordinates coordinates){
        return mapEntities.containsKey(coordinates);
    }

    public int getRows(){
        return this.rows;
    }

    public int getCols(){
        return this.cols;
    }

}