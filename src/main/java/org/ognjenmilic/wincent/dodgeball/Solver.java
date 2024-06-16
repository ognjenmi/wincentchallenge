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
        do {
            System.out.println("Current player: " + currentPlayer);
            Line line = new Line(direction.coefficient, direction.getN.apply(currentPlayer));
            ArrayList<Position> positionsInLine = lineMap.get(line);

            for (Position nextPlayer : positionsInLine) {
                System.out.println("Position: " + nextPlayer);
                if (removed.contains(nextPlayer.index())) {
                    System.out.println("Removed");
                    continue;
                }

                if (direction.sign == currentPlayer.direction(nextPlayer)) {
                    System.out.println("Current player: " + currentPlayer + " next player " + nextPlayer + " direction " + direction);
                    if (closestPlayer == null || currentPlayer.distance(nextPlayer) < currentPlayer.distance(closestPlayer)) {
                        System.out.println("Closest player: " + nextPlayer);
                        closestPlayer = nextPlayer;
                        nextPlayerIndex = nextPlayer.index();
                    }
                }
            }
            if (closestPlayer == null) {
                System.out.println("No closest player in direction " + direction);
                direction = direction.next();
                System.out.println("Next direction " + direction);
                continue;
            }

            hits++;
            System.out.println("Hits " + hits);
            initialDirection = direction.opposite();
            System.out.println("Next player direction " + initialDirection);
            removed.add(nextPlayerIndex);
            direction = initialDirection.next();
            System.out.println("Next player starts from " + direction);
            currentPlayer = closestPlayer;
            closestPlayer = null;
        } while (direction != initialDirection);

        return new Result(hits, nextPlayerIndex);
    }

    private void addLineToMap(Position position, Line lineNE) {
        ArrayList<Position> linePositions = lineMap.computeIfAbsent(lineNE, k -> new ArrayList<>());
        linePositions.add(position);
    }
}
