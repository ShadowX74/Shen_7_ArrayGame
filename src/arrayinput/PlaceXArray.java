package arrayinput;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Scanner;

/**
 *
 * @author ShadowX
 */
public class PlaceXArray {

    static Scanner scan = new Scanner(System.in);
    static char[][] map = new char[10][10];
    static int x;
    static int y;
    public static void main(String[] args) {
        
        boolean notQuit = true;
        
        while (notQuit) {
            System.out.println("Insert an x value from 0 to 9");
            x = scan.nextInt();
            System.out.println("Insert a y value from 0 to 9");
            y = scan.nextInt();
                
            if (map[x][y] == 'x') {
                System.out.println("Not valid. Try again.");
            } else {
                map[x][y] = 'x';
            }
            
                    
            for (int i = 0; i <= map[0].length - 1; i++) {
                for (int j = 0; j <= map[1].length - 1; j++) {
                    if (j < map[1].length - 1) {
                        if (map[i][j] != 'x') {
                            System.out.print(".");
                        } else {
                            System.out.print(map[i][j]);
                        }
                    } else {
                        if (map[i][j] != 'x') {
                            System.out.println(".");
                        } else {
                            System.out.println(map[i][j]);
                        }
                    }
                }
            }
            
            System.out.println("Would you like to quit? (y/n)");
            String input = scan.next();
            if (input.equals("y")) {
                notQuit = false;
            }
        }
    }
}
