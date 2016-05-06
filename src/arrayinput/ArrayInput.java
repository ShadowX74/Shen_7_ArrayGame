/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arrayinput;

import java.util.Scanner;
import java.util.Random;

/**
 *
 * @author ShadowX
 */
public class ArrayInput {

    static Scanner scan = new Scanner(System.in);
    static Random rand = new Random();
    static boolean notWinorLose = true;
    //creating array map
    static char[][] map = new char[21][21];
//    static char[][] map = new char[501][501];
    
    public static void main(String[] args) {
        boolean play = true;
        while (play) {
            game();
            System.out.println("Would you like to play again? (y/n)");
            String answer = scan.next();
            if (answer.toLowerCase().contains("n")) {
                play = false;
            }
            notWinorLose = true;
        }
    }
    
    public static void game() {
        //player x and y
        int x = 10;
        int y = 10;

        //defining random trap x and y
        int trapX = rand.nextInt(20);
        int trapY = rand.nextInt(20);
        int trapTwoX = rand.nextInt(20);
        int trapTwoY = rand.nextInt(20);
        
        //defining random chest x and y
        int chestX = rand.nextInt(20);
        int chestY = rand.nextInt(20);
        
        //defining random enemy x and y
        int enemyX = rand.nextInt(20);
        int enemyY = rand.nextInt(20);
        
        String player = x + "," + y;
        String trap1 = trapX + "," + trapY;
        String trap2 = trapTwoX + "," + trapTwoY;
        String chest = chestX + "," + chestY;
        String enemy = enemyX + "," + enemyY;
        
        //checking to be sure they don't overlap
        while ((player.equals(trap1)) || (player.equals(trap2)) || (player.equals(chest)) || (player.equals(enemy)) || (trap1.equals(trap2)) || (trap1.equals(chest)) || (trap1.equals(enemy)) || (trap2.equals(chest)) || (trap2.equals(enemy)) || (enemy.equals(chest))) {
            trapX = rand.nextInt(20);
            trapY = rand.nextInt(20);
            trapTwoX = rand.nextInt(20);
            trapTwoY = rand.nextInt(20);
            chestX = rand.nextInt(20);
            chestY = rand.nextInt(20);
            enemyX = rand.nextInt(20);
            enemyY = rand.nextInt(20);
        }
        
        map[chestX][chestY] = 'T';
        map[trapX][trapY] = '*';
        map[trapTwoX][trapTwoY] = '*';
        
        //Start of loop
        while (notWinorLose) {
            map[x][y] = '@';
            map[enemyX][enemyY] = 'E';
            
            for (int i = 0; i <= map[0].length - 1; i++) {
                for (int j = 0; j <= map[1].length - 1; j++) {
                    if (j < map[1].length - 1) {
                        if (map[i][j] != '@' && map[i][j] != '*' && map[i][j] != 'T' && map[i][j] != 'E') {
                            System.out.print(". ");
                        } else {
                            System.out.print(map[i][j]  + " ");
                        }
                    } else {
                        if (map[i][j] != '@' && map[i][j] != '*' && map[i][j] != 'T' && map[i][j] != 'E') {
                            System.out.println(". ");
                        } else {
                            System.out.println(map[i][j] + " ");
                        }
                    }
                }
            }
            
//            for (int i = x - 10; i <= map[0].length - 1 && i < x + 10 && i < x - 10; i++) {
//                for (int j = y - 10; j <= map[1].length - 1 && j < y + 10 && j < y - 10; j++) {
//                    if (j < map[1].length - 1 || j < y + 10) {
//                        if (map[i][j] != '@' && map[i][j] != '*' && map[i][j] != 'T' && map[i][j] != 'E') {
//                            System.out.print(". ");
//                        } else {
//                            System.out.print(map[i][j]  + " ");
//                        }
//                    } else {
//                        if (map[i][j] != '@' && map[i][j] != '*' && map[i][j] != 'T' && map[i][j] != 'E') {
//                            System.out.println(". ");
//                        } else {
//                            System.out.println(map[i][j] + " ");
//                        }
//                    }
//                }
//            }

            map[x][y] = '.';
            map[enemyX][enemyY] = '.';
            
            System.out.println("What direction would you like to move? (N/E/S/W/NE/SE/NW/SW) Or (Q) to quit.");
            String input = scan.next();
            if (input.toUpperCase().contains("N")) {
                x -= 1;
            }
            if (input.toUpperCase().contains("E")) {
                y += 1;
            }
            if (input.toUpperCase().contains("S")) {
                x += 1;
            }
            if (input.toUpperCase().contains("W")) {
                y -= 1;
            }

            if (enemyX < x) {
                enemyX += 1;
            } else if (enemyX > x){
                enemyX -= 1;
            }
            if (enemyY < y) {
                enemyY += 1;
            } else if (enemyY > y){
                enemyY -= 1;
            }
            
            if (x > 20) {
                x = 0;
            } else if (x < 0) {
                x = 20;
            }
            if (y >= 20) {
                y = 0;
            } else if (y < 0) {
                y = 20;
            }
            
            player = x + "," + y;
            enemy = enemyX + "," + enemyY;
            
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            
            if (input.toUpperCase().contains("Q")) {
                System.out.println("Ok, bye!");
                notWinorLose = false;
            }
            
            if (player.equals(trap1) || player.equals(trap2)) {
                System.out.println("You stepped on a trap. GAME OVER");
                notWinorLose = false;
            } else if (player.equals(enemy)) {
                System.out.println("You got caught by the enemy. YOU LOST");
                notWinorLose = false;
            } else if (player.equals(chest)) {
                System.out.println("You found the chest. YOU WON");
                notWinorLose = false;
            }
        }
    }
}