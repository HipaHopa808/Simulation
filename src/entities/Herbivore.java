package entities;

import actions.turnActions.ActionPathFinder;
import map.Coordinates;
import map.Map;
import simulation.Parameters;

import java.util.Deque;
import java.util.HashMap;
import java.util.Queue;

public class Herbivore extends Creature {


    public Herbivore(int speed, int health) {
        super(speed, health);
    }

    @Override
    public void makeMove(Map map) {
        if (checkHealth(map)) {
            Deque<Coordinates> foodPath = ActionPathFinder.findFoodPath(map, this, Grass.class);
            if (!foodPath.isEmpty()) {
                // Первый элемент — текущая позиция, пропускаем её
                Coordinates nextStep = foodPath.poll();
                int stepsMade = 0;
                // Двигаемся не более чем на speed шагов
                while (stepsMade < super.getSpeed() && !foodPath.isEmpty()) {
                    nextStep = foodPath.poll();
                    stepsMade++;
                }
                // Перемещаем существо
                map.remove(map.get(this));
                map.put(nextStep, this);
            } else {
                // Путь не найден — можно добавить случайное движение
            }
        }
    }


    public boolean checkHealth(Map map) {
        return super.getHealth() > 0;
    }

    public void decreaseHealth(int attack) {
        super.setHealth(super.getHealth() - attack);
    }

    private void heal() {
        if (super.getHealth() < Parameters.HERBIVORE_HEALTH.getParameter()) {
            super.setHealth(super.getHealth() + 1);
        }
    }

}
