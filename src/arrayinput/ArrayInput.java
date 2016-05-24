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

//102 pts
//Give points when enemy runs into u
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
    static int counter;
    static int goal;

    static int gameLevel = 1;
    static char[][] map = new char[51][51];
    static int[][] traps = new int[7][2];

    static BasicEnemy[] basicEnemies = new BasicEnemy[3]; //Array for tracking enemies 5 pts
    static TreasureChests[] treasures = new TreasureChests[6];
    static AdvancedEnemy[] advancedEnemies = new AdvancedEnemy[4]; //More enemies that are more difficult on second level 5 pts

    static Player player = new Player("Hero", 25, 25, 'U');

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
        counter = 0;
        goal = rand.nextInt(6) + 10;
    }

    private static void makeTraps() {
        for (int[] array : traps) {
            for (int i = 0; i < array.length; i++) {
                array[i] = rand.nextInt(49) + 1;
            }
        }
    }

    private static void makeChests() {
        for (int i = 0; i < treasures.length; i++) {
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
            treasures[i] = new TreasureChests(rand.nextInt(49) + 1, rand.nextInt(49) + 1, a);
        }
    }

    private static void makeEnemies() {
        for (int i = 0; i < basicEnemies.length; i++) {
            basicEnemies[i] = new BasicEnemy(rand.nextInt(49) + 1, rand.nextInt(49) + 1, 'E');
            basicEnemies[i].isAlive = true;
        }
    }

    private static void betterEnemies() {
        for (int i = 0; i < advancedEnemies.length; i++) {
            advancedEnemies[i] = new AdvancedEnemy(rand.nextInt(49) + 1, rand.nextInt(49) + 1, 'A');
            advancedEnemies[i].isAlive = true;
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

        if (counter == goal) { // Random Event 10 pts
            System.out.println("Suddenly, you happen upon a gilded sword lying in the golden dust!");
            System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMmdhyssood\n" +
"MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNmhs+++++++odM\n" +
"MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNdyo+++++++++odMMM\n" +
"MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMmhso++oo++++++ydNMMMMM\n" +
"MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNdhs+++ooo+++++shmMMMMMMMMM\n" +
"MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNdyo+++ooooo+++oymMMMMMMMMMMMMM\n" +
"MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMmhs+++oooooo++++sdNMMMMMMMMMMMMMMMM\n" +
"MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNdyo+++oooooo++++shNMMMMMMMMMMMMMMMMMMMM\n" +
"MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMmhso+++oooooo++++oymMMMMMMMMMMMMMMMMMMMMMMMM\n" +
"MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMmhso+++ooosooo++/+ydNMMMMMMMMMMMMMMMMMMMMMMMMMMM\n" +
"MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNdyo++++ooosooo++++shNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n" +
"MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMdyo++++ooosooo+++/oymMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n" +
"MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMdy+++++++oooo+++/+sdNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n" +
"MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNdyo+++++o++++++///shNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n" +
"MMMMMMMMMMMMMMMMMMds+sMMMMMMMNNmdys+//+++oooo++++//oymMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n" +
"MMMMMMMMMMMMMMMMMMNo+oNMMMMms++++++++++ooo++++oyhdNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n" +
"MMMMMMMMMMMMMMMMMMMm+/syyso+oooo++++oo++++shmMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n" +
"MMMMMMMMMMMMMMMMMMMm++/++o++o+++ooo++/+ymMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n" +
"MMMMMMMMMMMMMMMMMMMNo+o+o++o+o++++/+ymMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n" +
"MMMMMMMMMMMMMMMMMNmhho++o++o++++oohMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n" +
"MMMMMMMMMMMMNNmdhhhhhds+o/o++dNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n" +
"MMMMMMMMMNmhhhhhhdmNMMmyoo++NMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n" +
"MyyhdhyohhhhhhdNMMMMMMMMMh+oomMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n" +
"Ny//:::/+dmNMMMMMMMMMMMMMMm++odMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n" +
"Myo://+hNMMMMMMMMMMMMMMMMMMmmMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n" +
"MNdo/oNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n" +
"MMMNdmMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
            player.weapon = "Sword";
        }

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

        //System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
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
            Thread.sleep(2000);
        }
    }

    private static void move() {
        movePlayer();

        for (BasicEnemy e : basicEnemies) {
            e.isAlive = enemyAlive(e.x, e.y);
        }
        
        for (AdvancedEnemy e : advancedEnemies) {
            e.isAlive = enemyAlive(e.x, e.y);
        }

        moveEnemy();
    }

    private static void movePlayer() {
        counter += 1;
        System.out.println("What direction would you like to move? (N/E/S/W/NE/SE/NW/SW) (B) to stun or (Q) to quit.");
        String input = scan.next().toUpperCase();
        if (input.contains("!")) {
            player.score += 5;
        }
        if (input.contains("B")) { // Ability player can use 5 pts
            if (gameLevel == 1) {
                for (BasicEnemy e : basicEnemies) {
                    if (((e.x == player.x + 1) || (e.x == player.x - 1) || (e.x == player.x)) && ((e.y == player.y + 1) || (e.y == player.y - 1) || (e.y == player.y))) {
                        e.stun = true;
                        e.stuncounter = 3;
                    }
                }
            } else if (gameLevel == 2) {
                for (AdvancedEnemy e : advancedEnemies) {
                    if (((e.x == player.x + 1) || (e.x == player.x - 1) || (e.x == player.x)) && ((e.y == player.y + 1) || (e.y == player.y - 1) || (e.y == player.y))) {
                        e.stun = true;
                        e.stuncounter = 2;
                    }
                }
            }
        }
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

    private static boolean checkTraps(int a, int b) {
        boolean valid = true;
        if (map[a][b] == '*') {
            valid = false;
        }
        return valid;
    }

    private static void moveEnemy() {
        if (gameLevel == 1) {
            for (BasicEnemy e : basicEnemies) {
                if (e.stun) {
                    e.stuncounter -= 1;
                    if (e.stuncounter == 0) {
                        e.stun = false;
                    }
                } else if (e.isAlive) {
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
                    if (((e.x == player.x + 1) || (e.x == player.x - 1) || (e.x == player.x)) && ((e.y == player.y + 1) || (e.y == player.y - 1) || (e.y == player.y))) {
                        if (player.weapon.equals("Sword")) {
                            System.out.println("You got attacked by an enemy, but fought him off with your sword!");
                            System.out.println("- 10 Health    + 100 Exp");
                            player.health -= 10;
                            player.xp += 100;
                            player.score += 5;
                            e.isAlive = false;
                            e.x = 0;
                            e.y = 0;
                        } else {
                            System.out.println("The enemy attacked you! - " + e.attack(e, gameLevel) + " Health");
                            player.health -= e.attack(e, gameLevel);
                        }
                    }
                } else {
                    e.x = 0;
                    e.y = 0;
                }
            }
        } else if (gameLevel == 2) {
            for (AdvancedEnemy e : advancedEnemies) {
                if (e.stun) {
                    e.stuncounter -= 1;
                    if (e.stuncounter == 0) {
                        e.stun = false;
                    }
                } else if (e.isAlive) {
                    //enemy avoids traps 5 pts
                    if (e.movedirection(player.x, e.x)) {
                        if (checkWalls(e.x + e.movelength, e.y)) {
                            if (checkTraps(e.x + e.movelength, e.y)) {
                                e.x = e.move(player.x, e.x);
                            }
                        }
                    } else if (!e.movedirection(player.x, e.x)) {
                        if (checkWalls(e.x - e.movelength, e.y)) {
                            if (checkTraps(e.x - e.movelength, e.y)) {
                                e.x = e.move(player.x, e.x);
                            }
                        }
                    }
                    if (e.movedirection(player.y, e.y)) {
                        if (checkWalls(e.y + e.movelength, e.x)) {
                            if (checkTraps(e.y + e.movelength, e.x)) {
                                e.y = e.move(player.y, e.y);
                            }
                        }
                    } else if (!e.movedirection(player.y, e.y)) {
                        if (checkWalls(e.y - e.movelength, e.x)) {
                            if (checkWalls(e.y - e.movelength, e.x)) {
                                e.y = e.move(player.y, e.y);
                            }
                        }
                    } // Enemy uses skills 5 pts
                    if (((e.x == player.x + 1) || (e.x == player.x - 1) || (e.x == player.x)) && ((e.y == player.y + 1) || (e.y == player.y - 1) || (e.y == player.y))) {
                        if (player.weapon.equals("Sword")) {
                            System.out.println("You got mauled by an advanced enemy, but fought him off with your sword!");
                            System.out.println("- 30 Health    + 150 Exp");
                            player.health -= 30;
                            player.xp += 100;
                            player.score += 5;
                            e.isAlive = false;
                            e.x = 0;
                            e.y = 0;
                        } else {
                            System.out.println("The enemy attacked you! - " + e.attack(e, gameLevel) + " Health");
                            player.health -= e.attack(e, gameLevel);
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
            System.out.println(" __   __                                          _            __   _                _ _   _     _ \n"
                    + " \\ \\ / /__  _   _   _ __ __ _ _ __     ___  _   _| |_    ___  / _| | |__   ___  __ _| | |_| |__ | |\n"
                    + "  \\ V / _ \\| | | | | '__/ _` | '_ \\   / _ \\| | | | __|  / _ \\| |_  | '_ \\ / _ \\/ _` | | __| '_ \\| |\n"
                    + "   | | (_) | |_| | | | | (_| | | | | | (_) | |_| | |_  | (_) |  _| | | | |  __/ (_| | | |_| | | |_|\n"
                    + "   |_|\\___/ \\__,_| |_|  \\__,_|_| |_|  \\___/ \\__,_|\\__|  \\___/|_|   |_| |_|\\___|\\__,_|_|\\__|_| |_(_)\n"
                    + "                                                                                                   ");
            System.out.println("");
            System.out.println("   ______             _            __                           _     _              __  \n"
                    + "  / / ___|  ___  _ __| |_    ___  / _|   __ _   _ __  _ __ ___ | |__ | | ___ _ __ ___\\ \\ \n"
                    + " | |\\___ \\ / _ \\| '__| __|  / _ \\| |_   / _` | | '_ \\| '__/ _ \\| '_ \\| |/ _ \\ '_ ` _ \\| |\n"
                    + " | | ___) | (_) | |  | |_  | (_) |  _| | (_| | | |_) | | | (_) | |_) | |  __/ | | | | | |\n"
                    + " | ||____/ \\___/|_|   \\__|  \\___/|_|    \\__,_| | .__/|_|  \\___/|_.__/|_|\\___|_| |_| |_| |\n"
                    + "  \\_\\                                          |_|                                   /_/ ");
            return true;
        }
        return false;
    }

    //story w/ ascii 5 pts
    private static boolean isLevelWon(int points) {
        if (player.score == points) {
            System.out.println(" __   __            _                _     _   _     _       _                _ _ \n"
                    + " \\ \\ / /__  _   _  | |__   ___  __ _| |_  | |_| |__ (_)___  | | _____   _____| | |\n"
                    + "  \\ V / _ \\| | | | | '_ \\ / _ \\/ _` | __| | __| '_ \\| / __| | |/ _ \\ \\ / / _ \\ | |\n"
                    + "   | | (_) | |_| | | |_) |  __/ (_| | |_  | |_| | | | \\__ \\ | |  __/\\ V /  __/ |_|\n"
                    + "   |_|\\___/ \\__,_| |_.__/ \\___|\\__,_|\\__|  \\__|_| |_|_|___/ |_|\\___| \\_/ \\___|_(_)\n"
                    + "                                                                                  ");
            System.out.println("Slowly, the ground begins to rumble.");
            System.out.println("You unsteadily walk to the side of the room, wary.");
            System.out.println("A staircase appears, and you descend.");
            System.out.println("yyyysyyssyyssssssssss+++++++++++ooooooosooshhhhhhhhhhhhhhhdmmmmdhhhhyyyysssyhyyssysssssooosoooooosos\n"
                    + "yyysssoooyyyssooooooss++++++oooossssyyyyhhdmNNNmmmmmmmmmmmmNmmmmmmmmddhhhhdmmmddhhyysyssssssssssysoo\n"
                    + "yyssssooossssoooo+oosso+ooooosyyyhhhhddddmmmNNNNNmmmmNNNNNNNmmNmmmmmmmNmmmmmmmmmmddhhyyyhyysosyssooo\n"
                    + "sssssssooo+sssoo+o+/+ooooossshddmmmmmNNNNNNNNNNNNmmmmNmNNNNmmmNmmmmmmmNNNmNmmmmmmmmmddhyhddhhhyyysso\n"
                    + "sssyysssoo++oso++//++osossyhhdNNNNNNNNNNNNNNNNNmmmmmmmmNNNNmmmmmmmmmmNNNNNmmmmmmmmmmmmmddddmmmmdyyys\n"
                    + "ssssyysoooo++oo+/++oosyhyhhdmmmNNNNNNNNNNNNNNNmmmmmmmmmNNNNmmmmmmmmmmNNNNmmmmmmmmmmmmmNNNmmmmmmmmdhy\n"
                    + "syyssssss++///+s+osyhdddmmmNNNmNNNNNNNNNNNNNNNmmmmmmmmNNNNmmmmmmmmmmNNNNmmmmmmmmmmmmmNNNNNmmmmmmmmmd\n"
                    + "sysssssoo+/////oshdmmNNNNNNNNNNNNNmNNNNmmNNNNNmmmmmmmmNNNNmmmmmmmmmmNNNmmmmmmmmmmmmmNNNNmmmmmmmmmmmm\n"
                    + "yssso++++oo+//+oydmNmNNNNNNNNNNNNNmmmmmmmNNNNNmmmmmmmmNNNmmmmmmmmmmmmmmmmmmmmmmmmmmNNNmmmmmmmmmmmmmm\n"
                    + "syyso++++ossooshddmNNNNNNNNNNNNNNNNmmmmmmmmNNNmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmhdmmmmmmmmm\n"
                    + "sssssooooooyyhddmNNNNNNNNNNNmmNNNNNmmmmmmmmNNNmmmddmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmdmddddmmmmmmm\n"
                    + "ssossssoosyhdmNNNNNNNNNNNNNNmmmmNNNNmmmmmmmmmmmmmmmmmmmmmmmmmmmmmNmmmmmmmmmmmmmmmmmmmmmmmmmdmmmmdmmm\n"
                    + "sosssssoyhdmNNNNNNNNNNNNNNNNmmmmmNNNmmmmmmmmmmmmmmmmmmNmmmdmmmmmNNmddmmmmmmmmNmmmdmmmmmmmmmmmmmmmmmm\n"
                    + "ossosoosdmNNNNNNNNNNNNNNNNNNmmmmmNNNNmmmmmmmmmmmmmmmmNNmddmmmmmNmddmmmmmmmmNmmmdddddddmmmmmmmmmmmmmm\n"
                    + "ooooooshdmNNNNNNNNNNNNNNNNNNmmmmmmmmmmmmmmmmNNmdmmmmNNmddmmmmmNmddmmmmmmmmmmddddddddddmmmmmmmmmmmmmm\n"
                    + "ooossyhdmNNNNNNNNNNNNNNNNNNNNNmmmmmmmmmmmmmNNNmdmmmmNmddmmmmmmddmmmmmmmmmdddddddddddmmmmmdddddddmmmm\n"
                    + "ossyhddmNNNNNNNNNNNNNNmmmmNNNNmmmmmmmmmdmmmmNmdmmmmmmdddmmmmdddmmmmmmmmdddddddddddmmmmddddddddddddmm\n"
                    + "ssyddmNNNNNNNNNNNNNNNNmmmmmmNmmmmmmmNNNdmmmmNddmdmmmddmdmmmdddddddmddddddddddddddddddddddddddddddddd\n"
                    + "syddNNNNNNNNNNNNNNNNNNmmmmmmmmmmmmNmNNNdmmmmmdmmmmmmmmmmmmmdddmmmmmddddddddddddddddddddddddddddddddd\n"
                    + "yydNNNNNNNNNNNNNNNNNNNNNmmdmmmmmmdNmNNNhmmmmmddhdhdddddsdddhhhdmmdmmmddddddddddddddddddddddddddddddd\n"
                    + "sydmmNNNNNNNNNNNNNNNmNNNmdddddmNNdmmmmmmmdhyhddddhNNmmmdNNmddhhhhhhhdmmmmdhhhhhhddddddddddddddddddmm\n"
                    + "shddmNNNNNNNNNNNNNNNNNmNmddhdmNmmdhmmdhyhhhhmNNNNNNNNNNNNNNNNmNNdddhdmdhdmdyyyhhddddddddddddmmmmmmmm\n"
                    + "hddmNNNNNNNNNNNNNNNNNNmmmmdmdmmmdmdys+omNNNNNNNNNNNNmmNNNNNNNNNNNNmmdhdhddmmhhhdddddddmmmmmmmmdddddd\n"
                    + "hdmNNNNNNNNNNNNNNNNNNNmmmdmmdhhddho/hmNNNNNNNNNmNNNmmNNNmmmNNNNNNNNNNddddmmmmmdddddddmmddddhhddddddh\n"
                    + "dmNNNNNNNNNNNNNNNNNNNNmmddmmmmyhh/odNNNNNNNNNNNNNNNmNNNmmmNNNNmmNNNNNNmddhddmmmhyyyyyhhhdddddhhhhhhh\n"
                    + "dNNNNNNNNNNNNNNNNNNNNmmmdddmhydy:yNNNNNmmmmmmmmmmmNmNNNNNNNmmmmNNNNNNNNNmhhhdmmmdyyyyhddddddhhdddddd\n"
                    + "mmNNNNNNNNNNNNNNNNNNNmmdddhyydy:omNNNNmmmmmmdmNmmmdmmmdmmNNmmNNNmmmmNNNNNddhhmmmmmdhhhhddddddddmmmmm\n"
                    + "dmmmNNNNNNNNNNNNNNNNNmmddmmdhy+oNNNNNmmhmmmdddddddddmddmddNmdmmmmmmmNNNNNmmdhmmddmmddddddmmmmmddmmmm\n"
                    + "dmmmNNNNNNNNNNNNNNNNNmdddmdhhy:yNNNmmdmdyyhdmmmmmmmhhhdhdmmmmdmmmNNNNNNNNNNdhmdhhdmNdyyyhddddddddddd\n"
                    + "dmmNNmNNNNNNNNNNNNNNNmdddyhyhh/mNNmdddyohddddhhhdddhddhhmddmmhymmmmmmmmNNNNdhmddddmmmyhyyhhhhhhhhhhh\n"
                    + "dmmNNNNNNNNNNNNNNNmNNmdddddyhy:mNmdmhy+ddhyyhhhyyyyhysdhdmmmmhsdmmmmmmmNNNNdhdhddddmmhyyhhhhdhhhhhhh\n"
                    + "ddNNNNNNNNNNNNNNNNNNNmdddhhyhy:mNmdhy+hhyysyhhhyyyyodyomhmmmmddmNmmmNNNNNNNNmdhddhhmddddhhhhdddddddd\n"
                    + "hmmNmNmNNNNNNNNNNNNNNNddddhyyy+oNmdhy+hyoososyyyhyhsyd+ddmmmmmmNmmmmmmmNNNNNNyhhyyyddddddddddddddddm\n"
                    + "ydmmNNNNNNNNNNNNNNNNNNmdddddyhs:dmdhsosyoso/+ss+yhhyhdhmdmNmmmmmmmmNNNNNNNNNdhhhhhhdmddyyyyhmmdddddd\n"
                    + "sddmNNNNNNNNNNNNmNNNNNNdddddhyss/yhhyy/oooo++o+-syhydddmdNmmmmmmmmmmmNNNNNNmhhhhdddmmddysooshdddddmm\n"
                    + "shddmNNNNmmmmNNNNNNNNmmmddddhyshs/oyhss+++o++oo:+yhddhddmmdddmmmmmmmmmNNNNNNmhyhdddddmdsososhhhhhhhh\n"
                    + "sshdmmmmmmmmmmNNNNNNNNmmmddddddhss+:shhsy+o++os+ohhhhhdmmdhyhmmmmmmmNNNNNNNNhyyyhddddddhyyshhhhhhhhh\n"
                    + "//+ydmmmmmmmmNmmmmmmmmmmmmddmdhyhyysoo+syyysssyyyyhhdmmmdhhdmmmmmmmmNNNNNNmhyhyyyyhmddhdhhddddhhhhhh\n"
                    + ":::ohmmmmNmmmmmmmmmmmmmmmmmmdmdddddsydhoooooyyyyhhdddmmdddmmmmNNmmmmmNNmNmhhhyhhhhdddhyyyydddddddddd\n"
                    + ":://oshhdmmmmmmmmmmmmmmmmmmmmddmdhhhhdmyyhhdhmddddddddddmmmmmmmmNNNNNNNNNmhyyhdddddddysssshhddhhhhhd\n"
                    + "::---:+sydmmmmmmmmmmmmmmmmmmmmmmdmdddhhhhhhdddddhddddmmmmmmmmmmmmNNNNNNNNdyyhddddhhdyssoosyydddddhhh\n"
                    + "-.----/oyhdmmmmmmmmmmmmmmmmmmmmmmmmmmmdddddddddmmmmNNNmmmmmmmmmmmmmNNNNmhyhhhyydhdddssooossyyhhddddd\n"
                    + "---:--:-oyhdddmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmNNNNmmmmmmmmmmmmmNNNdyyyhhhhhddddddhhyssssyyyyyyhdd\n"
                    + "/://::--./+oyhdmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmNNNmmmmmmmmmmmmmNNNNNdyssyhdhhhdddyhhhhhhhyyyyyyyyyh\n"
                    + "o+/:---:----/syhddmmmmmmmmmmmmmmmmmmmmmmmmmmmmmNNNmmmmmmmmmmmmmNmmmdyooosyhhhyhhdysssyyyhhddhhyyyyyy\n"
                    + "+//:----:--.--oyhdmmmddmmmmmmmmmmmmmmmmmmmmmmmNNNNmmmmmmmmmmmmmdhss+ooossyhdhhddhsssssoyhyhhdddddhhh\n"
                    + "////:-:::-...:::shdhhdmmmmmmmmmmmmmmmmmmmmmmmNNmmmmmmmmmmmmmmmdssssooo+++sdhhddhyyssssshddhhhhhddddd\n"
                    + "+//+++/:----:----//osyhmmmmmmmmNmmmmmmmmNmmmNNmmmmmmmmmmmmmmmmdysoo++oooooyhhhdhhyssssssyhddhyyyyhhd\n"
                    + "+ooo+//:/-:::+/:/+:-/shhhmmmmmmmmmmNmmmNmNNNNmmmmmmmmddhhyssoo++++o++ossosyhdhhhhhysssssssyhdhhyyyyy\n"
                    + "oo:://///::/+/:::::/+oosyhdmmhhdmmmmmNNmmNNNmmmmddddyooooooooo+++oo++ossyhhhyyyyhhyyyyssssssyhdhhyyy\n"
                    + "oo//:://++o+::::--:+///+oshhyyyhdhhdmNmmhsyyyhhdhyyyssoo++++////++++osyyhhysssyyssyyhhyyysssssyhddhy");
            return true;
        }
        return false;
    }

    private static boolean isSecondWon(int points) {
        if (player.score == points) {
            System.out.println(" __   __            _                _     _   _     _       _                _ _ \n"
                    + " \\ \\ / /__  _   _  | |__   ___  __ _| |_  | |_| |__ (_)___  | | _____   _____| | |\n"
                    + "  \\ V / _ \\| | | | | '_ \\ / _ \\/ _` | __| | __| '_ \\| / __| | |/ _ \\ \\ / / _ \\ | |\n"
                    + "   | | (_) | |_| | | |_) |  __/ (_| | |_  | |_| | | | \\__ \\ | |  __/\\ V /  __/ |_|\n"
                    + "   |_|\\___/ \\__,_| |_.__/ \\___|\\__,_|\\__|  \\__|_| |_|_|___/ |_|\\___| \\_/ \\___|_(_)\n"
                    + "                                                                                  ");
            System.out.println(" __   __           __        ___       _ \n"
                    + " \\ \\ / /__  _   _  \\ \\      / (_)_ __ | |\n"
                    + "  \\ V / _ \\| | | |  \\ \\ /\\ / /| | '_ \\| |\n"
                    + "   | | (_) | |_| |   \\ V  V / | | | | |_|\n"
                    + "   |_|\\___/ \\__,_|    \\_/\\_/  |_|_| |_(_)\n"
                    + "                                         ");
            System.out.println("As you finally conquer the second floor, a tile slides open on the ground with a rasp.");
            System.out.println("A groan issues from the space, and a platform arises.");
            System.out.println("On it, is a magnificent chest of ancient jewelry.");
            System.out.println("You hop on as a hole appears in the ceiling.");
            System.out.println("You soon reach the surface, and, not 20 feet away, is your camel.");
            System.out.println("Thanking the deities above for your luck, you load your treasure and ride away.");
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
                } else if (e.powerUp.equals("Speed")) {
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
            player.x = 25;
            player.y = 25;
            player.xp = 0;
            player.level = 1;
            player.movementspeed = 1;
            player.symbol = 'U';
            gameLevel = 1;
        }
    }

    private static void intro() {
        System.out.println(ANSI_RED + " __        __   _                            _          _   _            _____                                       _                         \n"
                + ANSI_RED + " \\ \\      / /__| | ___ ___  _ __ ___   ___  | |_ ___   | |_| |__   ___  |_   _| __ ___  __ _ ___ _   _ _ __ ___     / \\   _ __ _ __ __ _ _   _ \n"
                + ANSI_RED + "  \\ \\ /\\ / / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\ | __/ _ \\  | __| '_ \\ / _ \\   | || '__/ _ \\/ _` / __| | | | '__/ _ \\   / _ \\ | '__| '__/ _` | | | |\n"
                + ANSI_RED + "   \\ V  V /  __/ | (_| (_) | | | | | |  __/ | || (_) | | |_| | | |  __/   | || | |  __/ (_| \\__ \\ |_| | | |  __/  / ___ \\| |  | | | (_| | |_| |\n"
                + ANSI_RED + "    \\_/\\_/ \\___|_|\\___\\___/|_| |_| |_|\\___|  \\__\\___/   \\__|_| |_|\\___|   |_||_|  \\___|\\__,_|___/\\__,_|_|  \\___| /_/   \\_\\_|  |_|  \\__,_|\\__, |\n"
                + ANSI_RED + "                                                                                                                                         |___/ " + ANSI_RESET);
        System.out.println("You have been exploring throughout the desert, in search of treasure");
        System.out.println("You came upon an overgrown tunnel, and stepped inside.");
        System.out.println("As soon as you emerged into a large open space, the tunnel opening collapses.");
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
