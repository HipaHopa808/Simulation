package actions.turnActions;

import entities.*;

public enum EntityRender {
    ROCK("🪨", "gray"),
    GRASS("🌱", "#ADFF2F"),
    TREE("🌳", "green"),
    HERBIVORE("🐏", "white"),
    PREDATOR("🐅","orange"),
    TILE("🔸", "#2b2b2b");

    private String symbol;
    private String color;

    EntityRender(String symbol, String color){
        this.symbol = symbol;
        this.color = color;
    }

    public String getSymbol(){
        return symbol;
    }

    public String getColor() {
        return color;
    }

    public static EntityRender getEntityRender(Entity entity){
        if(entity == null){
            return EntityRender.TILE;
        } else if (entity instanceof Grass) {
            return EntityRender.GRASS;
        }else if (entity instanceof Rock) {
            return EntityRender.ROCK;
        }else if (entity instanceof Tree) {
            return EntityRender.TREE;
        }else if (entity instanceof Predator) {
            return EntityRender.PREDATOR;
        }else {
            return EntityRender.HERBIVORE;
        }

    }
}
