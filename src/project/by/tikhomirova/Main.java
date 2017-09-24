package project.by.tikhomirova;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static Scanner sc = new Scanner(System.in);
    public static Random rand = new Random();

    public static  int SIZE = 3;
    public static  int DOTS_TO_WIN = 3;
    public static final char DOT_X = 'X';
    public static final char DOT_O = 'O';
    public static final char DOT_EMPTY = '*';
    public static char[][] map;

    public static void main(String[] args) {
        menu();
        initMap();
        printMap();

        while (true) {
            humanTurn();
            printMap();
            if (checkWin(DOT_X)) {
                System.out.println("Победил человек. Человек-царь природы)");
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья.");
                break;
            }
            aiTurn();
            printMap();
            if (checkWin(DOT_O)) {
                System.out.println("Победил ИИ. Эти машины начинают умнеть-_-");
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья.");
                break;
            }
        }
        System.out.println("Игра окончена.");
    }
    public static void menu() {
        System.out.println("Выберите режим игры:\n 1 - Поле 3х3: Победа: 3\n 2 - Поле 5х5: Победа: 4");
        int x;
        do{
            while(!sc.hasNextInt()){
                System.out.println("Это не число. Введите,пожалуйста, 1 или 2. ");
                sc.nextLine();
            }x = sc.nextInt();
        }while(x<1 || x>2);
        switch(x){
            case 1:
                SIZE = 3;
                DOTS_TO_WIN = 3;
                break;
            case 2:
                SIZE = 5;
                DOTS_TO_WIN = 4;
                break;
        }
    }
    public static void initMap(){
        map = new char[SIZE][SIZE];
        for (int i=0; i<SIZE; i++){
            for (int j=0; j<SIZE; j++){
                map[i][j] = DOT_EMPTY;
            }
        }
    }
    public static void printMap(){
        for(int i=0; i<=SIZE; i++){
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i=0; i<SIZE; i++){
            System.out.print((i+1) + " ");
            for (int j=0; j<SIZE; j++){
                System.out.print(map[i][j] + " ");
            }System.out.println();
        }System.out.println();

    }
    public static void humanTurn() {
        int x, y;
        do {
            System.out.println("Введите координаты в формате x y ");
            x = sc.nextInt() - 1;
            y = sc.nextInt() - 1;
        }while(!isCellValid(x,y)); map[y][x] = DOT_X;
    }
    public static boolean isCellValid(int x, int y){
        if(x<0||x>=SIZE||y<0||y>=SIZE) return false;
        if(map[y][x] == DOT_EMPTY) return true;
        return false;
    }
    public static void aiTurn() {
        int x = -1, y = -1;                 // блокировка хода
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (isCellValid(j, i)) {
                    map[i][j] = DOT_X;
                    if (checkWin(DOT_X)) {
                        x = j;
                        y = i;
                    }
                    map[i][j] = DOT_EMPTY;
                }
            }
        }

        if (x == -1 && y == -1) {
            do {
                x = rand.nextInt(SIZE);
                y = rand.nextInt(SIZE);
            } while (!isCellValid(x, y));
        }
        System.out.println("Компьютер походил в точку  " + (x + 1) + " " + (y + 1));
        map[y][x] = DOT_O;
    }
    public static boolean checkWin(char symb){//экзотичный метод проверки. хотела написать попроще, но не успела довести до ума
        for (int i=0; i<SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if(checkLine(i, j, 1,0, symb)) return true;//проверка горизонтали
                if(checkLine(i, j, 1,1, symb)) return true;//проверка диагонали  -> '\'
                if(checkLine(i, j, 0,1, symb)) return true;//проверка вертикали
                if(checkLine(i, j, 1,-1, symb)) return true;//проверка диагонали -> '/'
            }
        }return false;
    }
    public static boolean checkLine(int x0, int y0, int vx, int vy, char symb){
        if(x0 + DOTS_TO_WIN * vx > SIZE || y0 + DOTS_TO_WIN * vy > SIZE || y0 + DOTS_TO_WIN * vy < -1) return false;//проверка на выход за границы поля
        for(int i = 0; i < DOTS_TO_WIN; i++){
            if(map[y0 + i * vy][x0 + i * vx]!=symb) return false;//проверка на достаточную для выигрыша последовательность
        }return true;
    }
    public static boolean isMapFull(){
        for(int i = 0; i<SIZE; i++){
            for(int j = 0; j<SIZE; j++){
                if(map[i][j] == DOT_EMPTY)return false;
            }
        }return true;
    }

}

