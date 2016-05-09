/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arrayinput;

import java.util.Scanner;
import java.util.Random;

public class ArrayInput {

    static Scanner scan = new Scanner(System.in);
    static Random rand = new Random();
    static boolean play = true;
    static char[][] map = new char[51][51];
    static int enemyX = rand.nextInt(50);
    static int enemyY = rand.nextInt(50);
    static int enemy2X = rand.nextInt(50);
    static int enemy2Y = rand.nextInt(50);
    static int x = 25;
    static int y = 25;
    static int chestX = rand.nextInt(50);
    static int chestY = rand.nextInt(50);
    static int[][] traps = new int[5][2];
    static int[][] chests = new int[3][2];
    static boolean enemyAlive = true;
    static boolean enemy2Alive = true;
    static int points = 0;
    static int health = 100;
    
    public static void main(String[] args) {
        game();
    }
    
    public static void game() {
        intro();
        traps();
        chests();
        while (play) {
            while (play) {
                playGame();
            }
            playAgain();
        }
    }
    
    private static void traps() {
        for (int[] array : traps) {
            for (int i = 0; i < array.length; i++) {
                array[i] = rand.nextInt(50) + 1;
            }
        }
    }
    
    private static void chests() {
        for (int[] array : chests) {
            for (int i = 0; i < array.length; i++) {
                array[i] = rand.nextInt(50) + 1;
            }
        }
    }
    
    public static void playGame() {
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++) {
                    if (i == x - 1 && j == y - 1) {
                        map[i][j] = '@';
                    } else if ((i == enemyX - 1 && j == enemyY - 1) || (i == enemy2X - 1 && j == enemy2Y - 1)) {
                        map[i][j] = 'E';
                    } else if (i == 0 || i == 50 || j == 0 || j == 50) {
                        map[i][j] = 'X';
                    } else {
                        map[i][j] = '.';
                    }
                    
                    for (int[] array : chests) {
                        if (i == array[0] && j == array[1]) {
                            map[i][j] ='T';
                        }
                    }
                    
                    for (int[] array : traps) {
                        if (i == array[0] && j == array[1]) {
                            map[i][j] ='*';
                        }
                    }
                    System.out.print(map[i][j] + " ");
                }
                System.out.println("");
            }
            
            move();
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            
            if (isDead(x, enemyX, y, enemyY) == false) {
                play = false;
            }
            
            if (isDead(x, enemy2X, y, enemy2Y) == false) {
                play = false;
            }
            
            if (healthDead()) {
                play = false;
            }
            
            chestFound(x, y);
            
            if (isWon()) {
                play = false;
            }
    }
    
    private static void move() {
        movePlayer();
        enemyAlive();
        if (enemyAlive) {
            moveEnemy();
        } else {
            enemyX = 0;
            enemyY = 0;
        }
        enemy2Alive();
        if (enemy2Alive) {
            moveEnemy2();
        } else {
            enemy2X = 0;
            enemy2Y = 0;
        }
    }
    
    private static void movePlayer() {
        System.out.println("What direction would you like to move? (N/E/S/W/NE/SE/NW/SW) Or (Q) to quit.");
        String input = scan.next().toUpperCase();
        if (input.contains("N")) {
            if (projected(x - 2, y)) {
                x -= 1;
            }
        }
        if (input.contains("E")) {
            if (projected(y + 2, x)) {
                y += 1;
            }
        }
        if (input.contains("S")) {
            if (projected(x + 2, y)) {
                x += 1;
            }
        }
        if (input.contains("W")) {
            if (projected(y - 2, x)) {
                y -= 1;
            }
        }
        if (input.contains("Q")) {
            System.out.println("Ok, bye!");
            play = false;
        }
    }
    
    private static boolean projected(int a, int b) {
        boolean valid = true;
        if (a <= 0 || a > 51) {
        	valid = false;
    	}
    	if (b <= 0 || b > 51) {
        	valid = false;
    	}
    	return valid;
    }
    
    private static void moveEnemy() {
        enemyX = checkEnemyPosition(x, enemyX);
        enemyY = checkEnemyPosition(y, enemyY);
    }
    
    private static void moveEnemy2() {
        enemy2X = checkEnemyPosition(x, enemy2X);
        enemy2Y = checkEnemyPosition(y, enemy2Y);
    }
    
    private static boolean isDead(int pX, int eX, int pY, int eY) {
    	boolean live = true;
    	if (pX == eX && pY == eY) {
            live = false;
            System.out.println(" __   __                        _                           _     _     _             _   _                                               __   _____  _   _   _     ___  ____ _____ \n" +
" \\ \\ / /__  _   _    __ _  ___ | |_    ___ __ _ _   _  __ _| |__ | |_  | |__  _   _  | |_| |__   ___    ___ _ __   ___ _ __ ___  _   _    \\ \\ / / _ \\| | | | | |   / _ \\/ ___|_   _|\n" +
"  \\ V / _ \\| | | |  / _` |/ _ \\| __|  / __/ _` | | | |/ _` | '_ \\| __| | '_ \\| | | | | __| '_ \\ / _ \\  / _ \\ '_ \\ / _ \\ '_ ` _ \\| | | |    \\ V / | | | | | | | |  | | | \\___ \\ | |  \n" +
"   | | (_) | |_| | | (_| | (_) | |_  | (_| (_| | |_| | (_| | | | | |_  | |_) | |_| | | |_| | | |  __/ |  __/ | | |  __/ | | | | | |_| |_    | || |_| | |_| | | |__| |_| |___) || |  \n" +
"   |_|\\___/ \\__,_|  \\__, |\\___/ \\__|  \\___\\__,_|\\__,_|\\__, |_| |_|\\__| |_.__/ \\__, |  \\__|_| |_|\\___|  \\___|_| |_|\\___|_| |_| |_|\\__, (_)   |_| \\___/ \\___/  |_____\\___/|____/ |_|  \n" +
"                    |___/                             |___/                   |___/                                              |___/                                              ");
    	}
        for (int[] trap : traps) {
            if (pX == trap[0] + 1 && pY == trap[1] + 1) {
                System.out.println("Ouch! -50 health!");
                health -= 50;
            }
    	}
    	return live;
    }
        
    private static void enemyAlive() {
        for (int[] trap : traps) {
            if (enemyX == trap[0] + 1 && enemyY == trap[1] + 1) {
                enemyAlive = false;
            }
    	}
    }
    
    private static void enemy2Alive() {
        for (int[] trap : traps) {
            if (enemy2X == trap[0] + 1 && enemy2Y == trap[1] + 1) {
                enemy2Alive = false;
            }
    	}
    }
    
    private static boolean healthDead() {
        if (health <= 0) {
        System.out.println(" __   __                _                            _                         _                       ____    _    __  __ _____    _____     _______ ____  \n" +
" \\ \\ / /__  _   _   ___| |_ ___ _ __  _ __   ___  __| |   ___  _ __     __ _  | |_ _ __ __ _ _ __     / ___|  / \\  |  \\/  | ____|  / _ \\ \\   / / ____|  _ \\ \n" +
"  \\ V / _ \\| | | | / __| __/ _ \\ '_ \\| '_ \\ / _ \\/ _` |  / _ \\| '_ \\   / _` | | __| '__/ _` | '_ \\   | |  _  / _ \\ | |\\/| |  _|   | | | \\ \\ / /|  _| | |_) |\n" +
"   | | (_) | |_| | \\__ \\ ||  __/ |_) | |_) |  __/ (_| | | (_) | | | | | (_| | | |_| | | (_| | |_) |  | |_| |/ ___ \\| |  | | |___  | |_| |\\ V / | |___|  _ < \n" +
"   |_|\\___/ \\__,_| |___/\\__\\___| .__/| .__/ \\___|\\__,_|  \\___/|_| |_|  \\__,_|  \\__|_|  \\__,_| .__(_)  \\____/_/   \\_\\_|  |_|_____|  \\___/  \\_/  |_____|_| \\_\\\n" +
"                               |_|   |_|                                                    |_|                                                             ");
            return true;
        }
        return false;
    }
    
    private static boolean isWon() {
        if (points == 15) {
            System.out.println(" __   __             __                       _         _ _   _   _                 _               _                         _                                      _ _ \n" +
" \\ \\ / /__  _   _   / _| ___  _   _ _ __   __| |   __ _| | | | |_| |__   ___    ___| |__   ___  ___| |_ ___    __ _ _ __   __| |   ___  ___  ___ __ _ _ __   ___  __| | |\n" +
"  \\ V / _ \\| | | | | |_ / _ \\| | | | '_ \\ / _` |  / _` | | | | __| '_ \\ / _ \\  / __| '_ \\ / _ \\/ __| __/ __|  / _` | '_ \\ / _` |  / _ \\/ __|/ __/ _` | '_ \\ / _ \\/ _` | |\n" +
"   | | (_) | |_| | |  _| (_) | |_| | | | | (_| | | (_| | | | | |_| | | |  __/ | (__| | | |  __/\\__ \\ |_\\__ \\ | (_| | | | | (_| | |  __/\\__ \\ (_| (_| | |_) |  __/ (_| |_|\n" +
"   |_|\\___/ \\__,_| |_|  \\___/ \\__,_|_| |_|\\__,_|  \\__,_|_|_|  \\__|_| |_|\\___|  \\___|_| |_|\\___||___/\\__|___/  \\__,_|_| |_|\\__,_|  \\___||___/\\___\\__,_| .__/ \\___|\\__,_(_)\n" +
"                                                                                                                                                     |_|                 ");
            System.out.println(" __   __           __        ___       _ \n" +
" \\ \\ / /__  _   _  \\ \\      / (_)_ __ | |\n" +
"  \\ V / _ \\| | | |  \\ \\ /\\ / /| | '_ \\| |\n" +
"   | | (_) | |_| |   \\ V  V / | | | | |_|\n" +
"   |_|\\___/ \\__,_|    \\_/\\_/  |_|_| |_(_)\n" +
"                                         ");
            return true;
        }
        return false;
    }
    
    private static void chestFound(int pX, int pY) {
        for (int[] chest : chests) {
            if (pX == chest[0] + 1 && pY == chest[1] + 1) {
                System.out.println("You found a chest. + 5 Points");
                points += 5;
            }
    	}
    }
    
    private static int checkEnemyPosition(int player, int enemy) {
        if (enemy < player) {
            enemy += 1;
        } else if (enemy > player){
            enemy -= 1;
        }
        return enemy;
    }
    
    private static void playAgain() {
        System.out.println("Would you like to play again? (y/n)");
        String answer = scan.next();
        if (answer.toLowerCase().contains("y")) {
            play = true;
            points = 0;
        }
    }
    
    private static void intro() {
        System.out.println(" __        __   _                            _          _   _            _____                                       _                         \n" +
" \\ \\      / /__| | ___ ___  _ __ ___   ___  | |_ ___   | |_| |__   ___  |_   _| __ ___  __ _ ___ _   _ _ __ ___     / \\   _ __ _ __ __ _ _   _ \n" +
"  \\ \\ /\\ / / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\ | __/ _ \\  | __| '_ \\ / _ \\   | || '__/ _ \\/ _` / __| | | | '__/ _ \\   / _ \\ | '__| '__/ _` | | | |\n" +
"   \\ V  V /  __/ | (_| (_) | | | | | |  __/ | || (_) | | |_| | | |  __/   | || | |  __/ (_| \\__ \\ |_| | | |  __/  / ___ \\| |  | | | (_| | |_| |\n" +
"    \\_/\\_/ \\___|_|\\___\\___/|_| |_| |_|\\___|  \\__\\___/   \\__|_| |_|\\___|   |_||_|  \\___|\\__,_|___/\\__,_|_|  \\___| /_/   \\_\\_|  |_|  \\__,_|\\__, |\n" +
"                                                                                                                                         |___/ ");
        System.out.println("You have been exploring the depths of the jungle, looking for ruins.");
        System.out.println("You came upon an overgrown tunnel, and stepped inside.");
        System.out.println("As soon as you emerged into a large open space, the tunnel opening collapses.");
        System.out.println("Perhaps the treasure might save you?");
        System.out.println("Would you like to play? (y/n)");
        String answer = scan.next();
        play = answer.toLowerCase().contains("y");
    }
}