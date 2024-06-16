package org.ognjenmilic.wincent.dodgeball;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("inp.txt");
        Scanner s = new Scanner(fileInputStream);

        if (!s.hasNextLine()) {
            throw new IllegalArgumentException("Invalid input");
        }
        String line = s.nextLine();
        int testCases = Integer.parseInt(line.trim());
        ArrayList<Result> results = new ArrayList<>(testCases);
        for (int i = 0; i < testCases; i++) {
            int numberOfPLayers = Integer.parseInt(s.nextLine().trim());
            ArrayList<Position> positions = new ArrayList<>(new ArrayList<>());
            for (int j = 0; j < numberOfPLayers; j++) {
                String[] coordinates = s.nextLine().trim().split(" ");
                if (coordinates.length != 2) {
                    throw new IllegalArgumentException("Invalid input invalid player coordinates, test case " + i + " coordinate " + j);
                }
                positions.add(new Position(j, Integer.parseInt(coordinates[0].trim()), Integer.parseInt(coordinates[1].trim())));

            }
            SideVector initialDirection = SideVector.valueOf(s.nextLine().trim().toUpperCase());
            int playerIndex = Integer.parseInt(s.nextLine().trim()) - 1;
            Solver solver = new Solver(positions);
            System.out.println("Test case " + i + "starts");
            results.add(solver.solve(initialDirection, playerIndex));
            System.out.println("Test case " + i + " finished");

        }
        FileWriter myWriter = new FileWriter("out.txt");

        for (Result result : results) {
            myWriter.append(String.valueOf(result.hits())).append(" ").append(String.valueOf(result.lastPlayer() + 1)).append(System.lineSeparator());
        }
        myWriter.close();
    }
}