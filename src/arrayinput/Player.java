/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arrayinput;

/**
 *
 * @author ShadowX
 */
public class Player { //Converted Player into Class. 10 pts

    int health = 100;
    int score = 0;
    int level = 1;
    int xp = 0;
    int movementspeed = 1;

    int x, y, projectedX, projectedY;
    String name;
    String weapon;
    char symbol = 'U';

    Player(String name, int x, int y, char symbol) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        this.weapon = "Fists";
    }
}
