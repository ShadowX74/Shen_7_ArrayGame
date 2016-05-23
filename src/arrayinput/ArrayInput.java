/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arrayinput;

import java.util.Scanner;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

//Remove add score in move
public class ArrayInput {
    
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    static Scanner scan = new Scanner(System.in);
    static Random rand = new Random();
    static boolean play = true;
    
    static int gameLevel = 1;
    static char[][] map = new char[51][51];
    static int[][] traps = new int[7][2];

    static BasicEnemy[] basicEnemies = new BasicEnemy[3]; //Array for tracking enemies 5 pts
    static TreasureChests[] treasures = new TreasureChests[5];
    static AdvancedEnemy[] advancedEnemies = new AdvancedEnemy[4]; //More enemies that are more difficult on second level 5 pts
    
    static Player player = new Player("Hero", 25, 25, 'U');
    
    static boolean enemyAlive = true;
    static boolean enemy2Alive = true;

    public static void main(String[] args) {
        game();
    }

    public static void game() {
        intro();
        while (play) {
            setup();
            while (play) {
                while (gameLevel == 1) {
                    try {
                        levelOne();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ArrayInput.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                while (gameLevel == 2) {
                    try {
                        levelTwo();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ArrayInput.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            playAgain();
        }
    }

    private static void setup() {
        makeTraps();
        makeChests();
        makeEnemies();
        betterEnemies();
    }
    
    private static void makeTraps() {
        for (int[] array : traps) {
            for (int i = 0; i < array.length; i++) {
                array[i] = rand.nextInt(49) + 1;
            }
        }
    }

    private static void makeChests() {
        for (int i = 0; i < treasures.length; i ++) {
            String a;
            int b = rand.nextInt(3);
            if (b == 2) {
                a = "Speed";
            } else if (b == 1) {
                a = "Damage";
            } else {
                a = "Points";
            }
            if (i == 0) {
                a = "Weapon";
            }
            treasures[i] = new TreasureChests(rand.nextInt(49) + 1,rand.nextInt(49) + 1, a);
        }
    }
    
    private static void makeEnemies() {
        for (int i = 0; i < basicEnemies.length; i ++) {
            basicEnemies[i] = new BasicEnemy(rand.nextInt(49) + 1,rand.nextInt(49) + 1, 'E');
        }
    }
    
    private static void betterEnemies() {
        for (int i = 0; i < advancedEnemies.length; i ++) {
            advancedEnemies[i] = new AdvancedEnemy(rand.nextInt(49) + 1,rand.nextInt(49) + 1, 'A');
        }
    }
    
    //Multiple levels 10 pts
    private static void levelOne() throws InterruptedException {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (i == player.x && j == player.y) {
                    map[i][j] = player.symbol;
                } else if (i == 0 || i == 50 || j == 0 || j == 50) {
                    map[i][j] = 'X';
                } else {
                    map[i][j] = '.';
                }

                for (TreasureChests e : treasures) {
                    if (i == e.x && j == e.y) {
                        if (e.x == 0 && e.y == 0) {
                            map[i][j] = 'X';
                        } else {
                            map[i][j] = 'T';
                        }
                    }
                }
                
                for (BasicEnemy e : basicEnemies) {
                    if (e.isAlive) {
                        if (i == e.x && j == e.y) {
                            map[i][j] = e.symbol;
                        }
                    }
                }
                
                for (int[] array : traps) {
                    if (i == array[0] && j == array[1]) {
                        map[i][j] = '*';
                    }
                }

                //defined level maps 5 pts
                if ((i % 10 == 0 && j < 12) || (i % 10 == 0 && j > 38)) {
                    map[i][j] = 'X';
                }
                
                System.out.print(map[i][j] + " ");
            }
            System.out.println("");
        }

        System.out.println("Exp: " + player.xp + "     Level: " + player.level);
        System.out.println("Score: " + player.score + "     Health: " + player.health);
        Thread.sleep(500);
        
        move();
        
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        
        isDead(player.x, player.y);

        isTrapped(player.x, player.y);

        if (healthDead()) {
            play = false;
            gameLevel = 0;
        }

        chestFound(player.x, player.y);

        checkLevel();
        
        if (isLevelWon(15)) {
            player.score = 0;
            gameLevel += 1;
            Thread.sleep(2000);
        }
    }
    
    private static void levelTwo() throws InterruptedException {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (i == player.x && j == player.y) {
                    map[i][j] = player.symbol;
                } else if (i == 0 || i == 50 || j == 0 || j == 50) {
                    map[i][j] = 'X';
                } else {
                    map[i][j] = '.';
                }
                
                for (TreasureChests e : treasures) {
                    if (i == e.x && j == e.y) {
                        if (e.x == 0 && e.y == 0) {
                            map[i][j] = 'X';
                        } else {
                            map[i][j] = 'T';
                        }
                    }
                }

                for (AdvancedEnemy e : advancedEnemies) {
                    if (e.isAlive) {
                        if (i == e.x && j == e.y) {
                            map[i][j] = e.symbol;
                        }
                    }
                }
                
                for (int[] array : traps) {
                    if (i == array[0] && j == array[1]) {
                        map[i][j] = '*';
                    }
                }
                
                if ((i % 8 < 6 && i % 8 > 3) && (j % 8 < 6 && j % 8 > 3)) {
                    map[i][j] = 'X';
                }
                
                System.out.print(map[i][j] + " ");
            }
            System.out.println("");
        }

        System.out.println("Exp: " + player.xp + "     Level: " + player.level);
        System.out.println("Score: " + player.score + "     Health: " + player.health);
        Thread.sleep(500);
        
        move();
        
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        
        isDead(player.x, player.y);

        isTrapped(player.x, player.y);

        if (healthDead()) {
            play = false;
            gameLevel = 0;
        }

        chestFound(player.x, player.y);

        checkLevel();
        
        if (isSecondWon(20)) {
            gameLevel += 1;
            play = false;
        }
    }

    private static void move() {
        movePlayer();
        //player.score += 5;
        for (BasicEnemy e : basicEnemies) {
            e.isAlive = enemyAlive(e.x, e.y);
        }
        
        moveEnemy();
    }

    private static void movePlayer() {
        System.out.println("What direction would you like to move? (N/E/S/W/NE/SE/NW/SW) Or (Q) to quit.");
        String input = scan.next().toUpperCase();
        if (input.contains("N")) {
            if (checkWalls(player.x - player.movementspeed, player.y)) {
                player.x -= player.movementspeed;
            }
        }
        if (input.contains("E")) {
            if (checkWalls(player.y + player.movementspeed, player.x)) {
                player.y += player.movementspeed;
            }
        }
        if (input.contains("S")) {
            if (checkWalls(player.x + player.movementspeed, player.y)) {
                player.x += player.movementspeed;
            }
        }
        if (input.contains("W")) {
            if (checkWalls(player.y - player.movementspeed, player.x)) {
                player.y -= player.movementspeed;
            }
        }
        if (input.contains("Q")) {
            System.out.println("Ok, bye!");
            play = false;
            gameLevel = 0;
        }
    }
    
    private static boolean checkWalls(int a, int b) {
        boolean valid = true;
        if (map[a][b] == 'X') {
            valid = false;
        }
        return valid;
    }

    private static void moveEnemy() {
        if (gameLevel == 1) {
            for (BasicEnemy e : basicEnemies) {
                if (e.isAlive) {
                    //walls block enemy movement 5 pts
                    if (e.movedirection(player.x, e.x)) {
                        if (checkWalls(e.x + e.movelength, e.y)) {
                            e.x = e.move(player.x, e.x);
                        }
                    } else if (!e.movedirection(player.x, e.x)) {
                        if (checkWalls(e.x - e.movelength, e.y)) {
                            e.x = e.move(player.x, e.x);
                        }
                    }
                    if (e.movedirection(player.y, e.y)) {
                        if (checkWalls(e.y + e.movelength, e.x)) {
                           e.y = e.move(player.y, e.y);
                        }
                    } else if (!e.movedirection(player.y, e.y)) {
                        if (checkWalls(e.y - e.movelength, e.x)) {
                            e.y = e.move(player.y, e.y);
                        }
                    }
                } else {
                    e.x = 0;
                    e.y = 0;
                }
            }
        } else if (gameLevel == 2) {
            for (AdvancedEnemy e : advancedEnemies) {
                if (e.isAlive) {
                    if (e.movedirection(player.x, e.x)) {
                        if (checkWalls(e.x + e.movelength, e.y)) {
                            e.x = e.move(player.x, e.x);
                        }
                    } else if (!e.movedirection(player.x, e.x)) {
                        if (checkWalls(e.x - e.movelength, e.y)) {
                            e.x = e.move(player.x, e.x);
                        }
                    }
                    if (e.movedirection(player.y, e.y)) {
                        if (checkWalls(e.y + e.movelength, e.x)) {
                           e.y = e.move(player.y, e.y);
                        }
                    } else if (!e.movedirection(player.y, e.y)) {
                        if (checkWalls(e.y - e.movelength, e.x)) {
                            e.y = e.move(player.y, e.y);
                        }
                    }
                } else {
                    e.x = 0;
                    e.y = 0;
                }
            }
        }
    }

    private static void isDead(int pX, int pY) {
        if (gameLevel == 1) {
            for (BasicEnemy e : basicEnemies) {
                if (e.x == pX && e.y == pY) {
                    System.out.println("You got attacked by an enemy! - 30 Health");
                    player.health -= 30;
                    e.isAlive = false;
                    e.x = 0;
                    e.y = 0;
                }
            }
        } else if (gameLevel == 2) {
            for (AdvancedEnemy e : advancedEnemies) {
                if (e.x == pX && e.y == pY) {
                    System.out.println("You got mauled by an advanced enemy! - 50 Health");
                    player.health -= 50;
                    e.isAlive = false;
                    e.x = 0;
                    e.y = 0;
                }
            }
        }
    }

    private static void isTrapped(int pX, int pY) {
        for (int[] trap : traps) {
            if (pX == trap[0] && pY == trap[1]) {
                System.out.println("Ouch! -20 health!");
                player.health -= 20;
            }
        }
    }

    private static boolean enemyAlive(int eX, int eY) {
        if (eX == 0 && eY == 0) {
            return false;
        }
        for (int[] trap : traps) {
            if (eX == trap[0] && eY == trap[1]) {
                System.out.println("You killed an enemy. + 5 Points");
                System.out.println("+ 100 Exp");
                player.score += 5;
                player.xp += 100;
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

    private static boolean isLevelWon(int points) {
        if (player.score == points) {
            System.out.println(" __   __            _                _     _   _     _       _                _ _ \n" +
" \\ \\ / /__  _   _  | |__   ___  __ _| |_  | |_| |__ (_)___  | | _____   _____| | |\n" +
"  \\ V / _ \\| | | | | '_ \\ / _ \\/ _` | __| | __| '_ \\| / __| | |/ _ \\ \\ / / _ \\ | |\n" +
"   | | (_) | |_| | | |_) |  __/ (_| | |_  | |_| | | | \\__ \\ | |  __/\\ V /  __/ |_|\n" +
"   |_|\\___/ \\__,_| |_.__/ \\___|\\__,_|\\__|  \\__|_| |_|_|___/ |_|\\___| \\_/ \\___|_(_)\n" +
"                                                                                  ");
            return true;
        }
        return false;
    }
    
    private static boolean isSecondWon(int points) {
        if (player.score == points) {
            System.out.println(" __   __            _                _     _   _     _       _                _ _ \n" +
" \\ \\ / /__  _   _  | |__   ___  __ _| |_  | |_| |__ (_)___  | | _____   _____| | |\n" +
"  \\ V / _ \\| | | | | '_ \\ / _ \\/ _` | __| | __| '_ \\| / __| | |/ _ \\ \\ / / _ \\ | |\n" +
"   | | (_) | |_| | | |_) |  __/ (_| | |_  | |_| | | | \\__ \\ | |  __/\\ V /  __/ |_|\n" +
"   |_|\\___/ \\__,_| |_.__/ \\___|\\__,_|\\__|  \\__|_| |_|_|___/ |_|\\___| \\_/ \\___|_(_)\n" +
"                                                                                  ");
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
        for (TreasureChests e : treasures) {
            if (pX == e.x && pY == e.y) {
                e.x = 0;
                e.y = 0;
                if (e.powerUp.equals("Points")) {
                    System.out.println("You found a chest. + 5 Points");
                    System.out.println("+ 100 Exp");
                    player.score += 5;
                    player.xp += 100;
                } else if (e.powerUp.equals("Speed")){
                    System.out.println("You found a chest. + 1 Speed");
                    System.out.println("+ 100 Exp");
                    player.xp += 100;
                    player.movementspeed += 1;
                } else if (e.powerUp.equals("Damage")) {
                    System.out.println("You found a trapped chest! - 20 health");
                    System.out.println("+ 50 Exp");
                    player.xp += 50;
                    player.health -= 20;
                } else if (e.powerUp.equals("Weapon")) {
                    System.out.println("You found a sword!");
                    System.out.println("+ 100 Exp");
                    player.weapon = "Sword";
                    player.xp += 100;
                }
            }
        }
    }

    private static void playAgain() {
        System.out.println("Score: " + player.score);
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
            player.xp = 0;
            player.level = 1;
            gameLevel = 1;
        }
    }

    private static void intro() {
        System.out.println(ANSI_RED + " __        __   _                            _          _   _            _____                                       _                         \n"
                + ANSI_RED +" \\ \\      / /__| | ___ ___  _ __ ___   ___  | |_ ___   | |_| |__   ___  |_   _| __ ___  __ _ ___ _   _ _ __ ___     / \\   _ __ _ __ __ _ _   _ \n"
                + ANSI_RED +"  \\ \\ /\\ / / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\ | __/ _ \\  | __| '_ \\ / _ \\   | || '__/ _ \\/ _` / __| | | | '__/ _ \\   / _ \\ | '__| '__/ _` | | | |\n"
                + ANSI_RED +"   \\ V  V /  __/ | (_| (_) | | | | | |  __/ | || (_) | | |_| | | |  __/   | || | |  __/ (_| \\__ \\ |_| | | |  __/  / ___ \\| |  | | | (_| | |_| |\n"
                + ANSI_RED +"    \\_/\\_/ \\___|_|\\___\\___/|_| |_| |_|\\___|  \\__\\___/   \\__|_| |_|\\___|   |_||_|  \\___|\\__,_|___/\\__,_|_|  \\___| /_/   \\_\\_|  |_|  \\__,_|\\__, |\n"
                + ANSI_RED +"                                                                                                                                         |___/ " + ANSI_RESET);
        System.out.println("You have been exploring the depths of the jungle, looking for ruins.");
        System.out.println("You came upon an overgrown tunnel, and stepped inside.");
        System.out.println("As soon as you emerged into a large open space, the tunnel opening collapses.");
        System.out.println("Perhaps the treasure might save you?");
        System.out.println("Would you like to play? (y/n)");
        String answer = scan.next();
        play = answer.toLowerCase().contains("y");
    }
    
    private static void checkLevel() {
        if (player.xp == 100 * player.level) {
            playerLevelUp();
        }
        if (player.level == 3) {
            System.out.println("New Icon Unlocked ('H')");
            player.symbol = 'H';
        }
        if (player.level == 5) {
            System.out.println("New Icon Unlocked ('@')");
            player.symbol = '@';
        }
    }
    
    //player levels up w/ avatar changes 10 pts
    private static void playerLevelUp() {
        player.xp = 0;
        player.level += 1;
        System.out.println("You leveled up! You are now level " + player.level + ".");
    }
}