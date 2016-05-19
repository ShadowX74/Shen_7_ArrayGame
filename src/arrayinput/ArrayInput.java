/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arrayinput;

import java.util.Scanner;
import java.util.Random;

public class ArrayInput {
    //ADD FAKE TRAP THAT LOOKS LIKE A CHEST

    static Scanner scan = new Scanner(System.in);
    static Random rand = new Random();
    static boolean play = true;
    static Enemy E1, E2, E3;
    
    static char[][] map = new char[51][51];
    static int[][] traps = new int[7][2];
    static int[][] chests = new int[3][2];
    static Enemy[] enemies = {E1, E2, E3};

    static int trappedChestX = 25;//rand.nextInt(50);
    static int trappedChestY = 25;//rand.nextInt(50);
    static Player player= new Player("Hero", 25, 25, 'U');
    
    static boolean enemyAlive = true;
    static boolean enemy2Alive = true;

    public static void main(String[] args) {
        game();
    }

    public static void game() {
        intro();
        while (play) {
            traps();
            chests();
            enemies();
            while (play) {
                playGame();
            }
            playAgain();
        }
    }

    private static void traps() {
        for (int[] array : traps) {
            for (int i = 0; i < array.length; i++) {
                array[i] = rand.nextInt(49) + 1;
            }
        }
    }

    private static void chests() {
        for (int[] array : chests) {
            for (int i = 0; i < array.length; i++) {
                array[i] = rand.nextInt(49) + 1;
            }
        }
    }
    
    private static void enemies() {
        for (int i = 0; i < enemies.length; i ++) {
            enemies[i] = new Enemy(rand.nextInt(49) + 1,rand.nextInt(49) + 1, 'E');
        }
    }
    
    public static void playGame() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (i == player.x - 1 && j == player.y - 1) {
                    map[i][j] = player.symbol;
                } else if (i == 0 || i == 50 || j == 0 || j == 50) {
                    map[i][j] = 'X';
                } else if (i == trappedChestX && j == trappedChestY) {
                    map[i][j] = 'C';
                } else {
                    map[i][j] = '.';
                }
                
                for (int[] array : chests) {
                    if (i == array[0] && j == array[1]) {
                        if (0 == array[0] && 0 == array[1]) {
                            map[i][j] = 'X';
                        } else {
                            map[i][j] = 'T';
                        }
                    }
                }

                for (int[] array : traps) {
                    if (i == array[0] && j == array[1]) {
                        map[i][j] = '*';
                    }
                }
                
                for (Enemy e : enemies) {
                    if (e.isAlive) {
                        if (i == e.x && j == e.y) {
                            map[i][j] = 'E';
                        }
                    }
                }
                
                System.out.print(map[i][j] + " ");
            }
            System.out.println("");
        }

        move();
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

        if (isDead(player.x, player.y) == false) {
            play = false;
        }

        if (isDead(player.x, player.y) == false) {
            play = false;
        }

        isTrapped(player.x, player.y);

        if (healthDead()) {
            play = false;
        }

        chestFound(player.x, player.y);

        if (isWon()) {
            play = false;
        }
    }

    private static void move() {
        movePlayer();
        
        for (Enemy e : enemies) {
            e.isAlive = enemyAlive(e.x, e.y);
        }
        
        moveEnemy();
    }

    private static void movePlayer() {
        System.out.println("What direction would you like to move? (N/E/S/W/NE/SE/NW/SW) Or (Q) to quit.");
        String input = scan.next().toUpperCase();
        if (input.contains("N")) {
            if (projected(player.x - 2, player.y)) {
                player.x -= 1;
            }
        }
        if (input.contains("E")) {
            if (projected(player.y + 2, player.x)) {
                player.y += 1;
            }
        }
        if (input.contains("S")) {
            if (projected(player.x + 2, player.y)) {
                player.x += 1;
            }
        }
        if (input.contains("W")) {
            if (projected(player.y - 2, player.x)) {
                player.y -= 1;
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
        for (Enemy e : enemies) {
            if (e.isAlive) {
                e.x = checkEnemyPosition(player.x, e.x);
                e.y = checkEnemyPosition(player.y, e.y);
            } else {
                e.x = 0;
                e.y = 0;
            }
        }
    }

    private static boolean isDead(int pX, int pY) {
        for (Enemy e : enemies) {
            if (e.x == pX && e.y == pY) {
                System.out.println(" __   __                        _                           _     _     _             _   _                                               __   _____  _   _   _     ___  ____ _____ \n"
                    + " \\ \\ / /__  _   _    __ _  ___ | |_    ___ __ _ _   _  __ _| |__ | |_  | |__  _   _  | |_| |__   ___    ___ _ __   ___ _ __ ___  _   _    \\ \\ / / _ \\| | | | | |   / _ \\/ ___|_   _|\n"
                    + "  \\ V / _ \\| | | |  / _` |/ _ \\| __|  / __/ _` | | | |/ _` | '_ \\| __| | '_ \\| | | | | __| '_ \\ / _ \\  / _ \\ '_ \\ / _ \\ '_ ` _ \\| | | |    \\ V / | | | | | | | |  | | | \\___ \\ | |  \n"
                    + "   | | (_) | |_| | | (_| | (_) | |_  | (_| (_| | |_| | (_| | | | | |_  | |_) | |_| | | |_| | | |  __/ |  __/ | | |  __/ | | | | | |_| |_    | || |_| | |_| | | |__| |_| |___) || |  \n"
                    + "   |_|\\___/ \\__,_|  \\__, |\\___/ \\__|  \\___\\__,_|\\__,_|\\__, |_| |_|\\__| |_.__/ \\__, |  \\__|_| |_|\\___|  \\___|_| |_|\\___|_| |_| |_|\\__, (_)   |_| \\___/ \\___/  |_____\\___/|____/ |_|  \n"
                    + "                    |___/                             |___/                   |___/                                              |___/                                              ");
                return false;
            }
        }
        return true;
    }

    private static void isTrapped(int pX, int pY) {
        for (int[] trap : traps) {
            if (pX == trap[0] + 1 && pY == trap[1] + 1) {
                System.out.println("Ouch! -50 health!");
                player.health -= 50;
            } 
            if (pX == trappedChestX + 1 && pY == trappedChestY + 1) {
                System.out.println("Ouch! -50 health!");
                player.health -= 50;
            }
        }
    }

    private static boolean enemyAlive(int eX, int eY) {
        for (int[] trap : traps) {
            if (eX == trap[0] + 1 && eY == trap[1] + 1) {
                System.out.println("You killed an enemy. + 5 Points");
                player.score += 5;
                return false;
            }
        }
        return true;
    }

    private static boolean healthDead() {
        if (player.health <= 0) {
            System.out.println(" __   __                _                            _                         _                       ____    _    __  __ _____    _____     _______ ____  \n"
                    + " \\ \\ / /__  _   _   ___| |_ ___ _ __  _ __   ___  __| |   ___  _ __     __ _  | |_ _ __ __ _ _ __     / ___|  / \\  |  \\/  | ____|  / _ \\ \\   / / ____|  _ \\ \n"
                    + "  \\ V / _ \\| | | | / __| __/ _ \\ '_ \\| '_ \\ / _ \\/ _` |  / _ \\| '_ \\   / _` | | __| '__/ _` | '_ \\   | |  _  / _ \\ | |\\/| |  _|   | | | \\ \\ / /|  _| | |_) |\n"
                    + "   | | (_) | |_| | \\__ \\ ||  __/ |_) | |_) |  __/ (_| | | (_) | | | | | (_| | | |_| | | (_| | |_) |  | |_| |/ ___ \\| |  | | |___  | |_| |\\ V / | |___|  _ < \n"
                    + "   |_|\\___/ \\__,_| |___/\\__\\___| .__/| .__/ \\___|\\__,_|  \\___/|_| |_|  \\__,_|  \\__|_|  \\__,_| .__(_)  \\____/_/   \\_\\_|  |_|_____|  \\___/  \\_/  |_____|_| \\_\\\n"
                    + "                               |_|   |_|                                                    |_|                                                             ");
            return true;
        }
        return false;
    }

    private static boolean isWon() {
        if (player.score == 15) {
            System.out.println(" __   __             __                       _         _ _   _   _                 _               _                         _                                      _ _ \n"
                    + " \\ \\ / /__  _   _   / _| ___  _   _ _ __   __| |   __ _| | | | |_| |__   ___    ___| |__   ___  ___| |_ ___    __ _ _ __   __| |   ___  ___  ___ __ _ _ __   ___  __| | |\n"
                    + "  \\ V / _ \\| | | | | |_ / _ \\| | | | '_ \\ / _` |  / _` | | | | __| '_ \\ / _ \\  / __| '_ \\ / _ \\/ __| __/ __|  / _` | '_ \\ / _` |  / _ \\/ __|/ __/ _` | '_ \\ / _ \\/ _` | |\n"
                    + "   | | (_) | |_| | |  _| (_) | |_| | | | | (_| | | (_| | | | | |_| | | |  __/ | (__| | | |  __/\\__ \\ |_\\__ \\ | (_| | | | | (_| | |  __/\\__ \\ (_| (_| | |_) |  __/ (_| |_|\n"
                    + "   |_|\\___/ \\__,_| |_|  \\___/ \\__,_|_| |_|\\__,_|  \\__,_|_|_|  \\__|_| |_|\\___|  \\___|_| |_|\\___||___/\\__|___/  \\__,_|_| |_|\\__,_|  \\___||___/\\___\\__,_| .__/ \\___|\\__,_(_)\n"
                    + "                                                                                                                                                     |_|                 ");
            System.out.println(" __   __           __        ___       _ \n"
                    + " \\ \\ / /__  _   _  \\ \\      / (_)_ __ | |\n"
                    + "  \\ V / _ \\| | | |  \\ \\ /\\ / /| | '_ \\| |\n"
                    + "   | | (_) | |_| |   \\ V  V / | | | | |_|\n"
                    + "   |_|\\___/ \\__,_|    \\_/\\_/  |_|_| |_(_)\n"
                    + "                                         ");
            return true;
        }
        return false;
    }

    private static void chestFound(int pX, int pY) {
        for (int[] chest : chests) {
            if (pX == chest[0] + 1 && pY == chest[1] + 1) {
                chest[0] = 0;
                chest[1] = 0;
                System.out.println("You found a chest. + 5 Points");
                player.score += 5;
            }
        }
    }

    private static int checkEnemyPosition(int player, int enemy) {
        if (enemy < player) {
            enemy += 1;
        } else if (enemy > player) {
            enemy -= 1;
        }
        return enemy;
    }

    private static void playAgain() {
        System.out.println("Score:" + player.score);
        System.out.println("Would you like to play again? (y/n)");
        String answer = scan.next();
        if (answer.toLowerCase().contains("y")) {
            play = true;
            player.score = 0;
            player.health = 100;
            enemyAlive = true;
            enemy2Alive = true;
            player.x = 25;
            player.y = 25;
        }
    }

    private static void intro() {
        System.out.println(" __        __   _                            _          _   _            _____                                       _                         \n"
                + " \\ \\      / /__| | ___ ___  _ __ ___   ___  | |_ ___   | |_| |__   ___  |_   _| __ ___  __ _ ___ _   _ _ __ ___     / \\   _ __ _ __ __ _ _   _ \n"
                + "  \\ \\ /\\ / / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\ | __/ _ \\  | __| '_ \\ / _ \\   | || '__/ _ \\/ _` / __| | | | '__/ _ \\   / _ \\ | '__| '__/ _` | | | |\n"
                + "   \\ V  V /  __/ | (_| (_) | | | | | |  __/ | || (_) | | |_| | | |  __/   | || | |  __/ (_| \\__ \\ |_| | | |  __/  / ___ \\| |  | | | (_| | |_| |\n"
                + "    \\_/\\_/ \\___|_|\\___\\___/|_| |_| |_|\\___|  \\__\\___/   \\__|_| |_|\\___|   |_||_|  \\___|\\__,_|___/\\__,_|_|  \\___| /_/   \\_\\_|  |_|  \\__,_|\\__, |\n"
                + "                                                                                                                                         |___/ ");
        System.out.println("You have been exploring the depths of the jungle, looking for ruins.");
        System.out.println("You came upon an overgrown tunnel, and stepped inside.");
        System.out.println("As soon as you emerged into a large open space, the tunnel opening collapses.");
        System.out.println("Perhaps the treasure might save you?");
        System.out.println("Would you like to play? (y/n)");
        String answer = scan.next();
        play = answer.toLowerCase().contains("y");
    }
}
