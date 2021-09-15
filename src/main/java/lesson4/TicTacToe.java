package lesson4;

import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    public static char[][] map;
    public static final int SIZE = 9;
    public static final int DOTS_TO_WIN = 6;
    public static final char DOT_EMPTY = '*';
    public static final char DOT_X = 'X';
    public static final char DOT_O = 'O';
    public static Scanner sc = new Scanner(System.in);
    public static Random rand = new Random();

    public static void main(String[] args) {
        initMap();
        printMap();
        while (true) {
            humanTurn();
            printMap();
            if (checkWin(DOT_X)) {
                System.out.println("Победил человек");
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья");
                break;
            }
            aiTurn();
            printMap();
            if (checkWin(DOT_O)) {
                System.out.println("Победил Искуственный Интеллект");
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья");
                break;
            }
        }
        System.out.println("Игра закончена");
    }

    public static void initMap() {
        map = new char[SIZE][SIZE];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    public static void printMap() {
        for (int i = 0; i <= SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void humanTurn() {
        int x, y;
        do {
            System.out.println("Введите координаты в формате [X Y]:");
            x = sc.nextInt() - 1;
            y = sc.nextInt() - 1;
        } while (!isCellValid(x, y));
        map[y][x] = DOT_X;
    }

    public static boolean isCellValid(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) return false;
        if (map[y][x] == DOT_EMPTY) return true;
        return false;
    }

    public static void aiTurn() {
        int x, y;
        do {
            x = rand.nextInt(SIZE);
            y = rand.nextInt(SIZE);
        } while (!isCellValid(x, y));
        System.out.println("Компьютер походил в точку " + (x + 1) + " " + (y + 1));
        map[y][x] = DOT_O;
    }

    public static boolean checkWin(char symb) {
        // проверяем по оси X
        for (char[] line : map) {
            char[] checkingLineX = new char[SIZE];
            for (int i = 0; i < SIZE; i++) {
                checkingLineX[i] = line[i];
            }
            if (checkLine(checkingLineX, symb)) return true;
        }
        // проверяем по оси Y
        for (int i = 0; i < SIZE; i++) {
            char[] checkingLineY = new char[SIZE];
            for (int j = 0; j < SIZE; j++) {
                checkingLineY[j] = map[j][i];
            }
            if (checkLine(checkingLineY, symb)) return true;
        }
        // проверяем по всем диагоналям
        for (int i = 0; i <= SIZE - DOTS_TO_WIN; i++) {
            char[] checkingLineYDown = new char[SIZE - i];
            char[] checkingLineYUp = new char[SIZE - i];
            char[] checkingLineXRight = new char[SIZE - i];
            char[] checkingLineXLeft = new char[SIZE - i];
            for (int j = 0; j < checkingLineYDown.length; j++) {
                checkingLineYDown[j] = map[i + j][j];
                checkingLineYUp[j] = map[i + j][SIZE - 1 - j];
                checkingLineXRight[j] = map[j][j + i];
                checkingLineXLeft[j] = map[j][SIZE - 1 - j - i];
            }
            if (checkLine(checkingLineYDown, symb)) return true;
            if (checkLine(checkingLineYUp, symb)) return true;
            if (checkLine(checkingLineXRight, symb)) return true;
            if (checkLine(checkingLineXLeft, symb)) return true;
        }

        return false;
    }

    public static boolean checkLine(char[] checkingLine, char symb) {
        int goodDots = 0;
        for (char dot : checkingLine) {
            if (dot == symb) goodDots++;
            else goodDots = 0;
            if (goodDots == DOTS_TO_WIN) return true;
        }
        return false;
    }

    public static boolean isMapFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) return false;
            }
        }
        return true;
    }
}
