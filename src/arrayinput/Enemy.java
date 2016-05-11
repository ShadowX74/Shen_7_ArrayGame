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
public class Enemy {
    int x, y;
    int health = 100;
    boolean isALive = true;
    char symbol = 'P';
    
    Enemy(int x, int y, char symbol) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
    }
}
