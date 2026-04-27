package simulation;

public enum Parameters {
    MAP_ROWS(13),
    MAP_COLS(13),

    GRASS_QUANTITY(10),
    OBSTACLES_QUANTITY(16),

    HERBIVORE_HEALTH(4),
    HERBIVORE_SPEED(2),
    HERBIVORE_QUANTITY(5),

    PREDATOR_HEALTH(3),
    PREDATOR_SPEED(2),
    PREDATOR_ATTACK(2),
    PREDATOR_QUANTITY(4);

    private int parameter;

    Parameters(int parameter) {
        this.parameter = parameter;
    }

    public int getParameter() {
        return parameter;
    }
}
