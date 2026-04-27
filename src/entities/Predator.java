package entities;

import actions.turnActions.ActionPathFinder;
import map.Coordinates;
import map.Map;

import java.util.Deque;

public class Predator extends Creature {
    private int attackPower;

    public Predator(int speed, int health, int attackPower) {
        super(speed, health);
        this.attackPower = attackPower;
    }

    @Override
    public void makeMove(Map map) {

        Deque<Coordinates> pathToVictim = ActionPathFinder.findFoodPath(map, this, Herbivore.class);

        if (!pathToVictim.isEmpty()) {

            Coordinates predatorPos = pathToVictim.poll();
            if (predatorPos == null) {
                return;
            }

            for (int i = 0; i < super.getSpeed() && !pathToVictim.isEmpty(); i++) {
                Coordinates nextStep = pathToVictim.peek();
                Entity entityAhead = map.get(nextStep);

                if (entityAhead instanceof Herbivore) {
                    attackHerbivore(map, nextStep);
                    System.out.printf("\tАтаковано %s [%s,%s] хищником %s [%s, %s].\n",
                            entityAhead.toString(), nextStep.getRow(), nextStep.getCol(),
                            this.toString(), predatorPos.getRow(), predatorPos.getCol());
                    break;
                } else {
                    pathToVictim.poll();
                    predatorPos = nextStep;
                }
            }

            map.remove(map.get(this));
            map.put(predatorPos, this);

        }
    }


    public void attackHerbivore(Map map, Coordinates victimCoordinates) {
        Herbivore victim = (Herbivore) map.get(victimCoordinates);
        victim.decreaseHealth(attackPower);
        if (!victim.checkHealth(map)) {
            map.remove(victimCoordinates);
        }

    }
}
