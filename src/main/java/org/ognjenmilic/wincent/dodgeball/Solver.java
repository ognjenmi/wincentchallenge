package org.ognjenmilic.wincent.dodgeball;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Solver {
    private final ArrayList<Position> positions;

    private final HashMap<Line, ArrayList<Position>> lineMap = new HashMap<>();


    public Solver(ArrayList<Position> positions) {
        this.positions = positions;
    }



    public Result solve(SideVector initialDirection, int playerIndex) {
        if (playerIndex < 0 || playerIndex >= positions.size()) {
            throw new IndexOutOfBoundsException("Player index out of range");
        }

        for(Position position : positions) {
            Line lineNE = new Line(1, position.y() - position.x());
            addLineToMap(position, lineNE);
            Line lineNW = new Line(-1, position.y() + position.x());
            addLineToMap(position, lineNW);
            Line lineN = new Line(-2, position.x());
            addLineToMap(position, lineN);
            Line lineS = new Line(0, position.y());
            addLineToMap(position, lineS);
        }

        Position currentPlayer = positions.get(playerIndex);


        int hits = 0;
        int nextPlayerIndex = playerIndex;
        SideVector direction = initialDirection.next();
        HashSet<Integer> removed = new HashSet<>(positions.size());
        removed.add(playerIndex);
        Position closestPlayer = null;
        int rounds = 0;
        do {
            Line line = new Line(direction.coefficient, direction.getN.apply(currentPlayer));
            ArrayList<Position> positionsInLine = lineMap.get(line);

            for (Position nextPlayer : positionsInLine) {
                if (removed.contains(nextPlayer.index())) {
                    continue;
                }

                if (direction.sign == currentPlayer.direction(nextPlayer)) {
                    if (closestPlayer == null || currentPlayer.distance(nextPlayer) < currentPlayer.distance(closestPlayer)) {
                        closestPlayer = nextPlayer;
                        nextPlayerIndex = nextPlayer.index();
                    }
                }
            }
            if (closestPlayer == null) {
                rounds++;
                direction = direction.next();
                continue;
            }

            hits++;
            rounds = 0;
            initialDirection = direction.opposite();
            System.out.println("Player: " + nextPlayerIndex + ", direction: " + direction );
            removed.add(nextPlayerIndex);
            direction = initialDirection.next();
            currentPlayer = closestPlayer;
            closestPlayer = null;
        } while (rounds < 8);

        return new Result(hits, nextPlayerIndex);
    }

    private void addLineToMap(Position position, Line lineNE) {
        ArrayList<Position> linePositions = lineMap.computeIfAbsent(lineNE, k -> new ArrayList<>());
        linePositions.add(position);
    }
}
