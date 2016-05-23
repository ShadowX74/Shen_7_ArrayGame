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
public class Enemy { //Created Enemy Class 5 pts
    int x, y;
    int health = 100;
    boolean isAlive;
    char symbol = 'E';
    int movelength;
    
    Enemy(int x, int y, char symbol) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        isAlive = true;
    }
    public int move(int player, int enemy) {
        if (enemy < player) {
            enemy += movelength;
        } else if (enemy > player) {
            enemy -= movelength;
        }
        return enemy;
    }
    
    public boolean movedirection(int player, int enemy) {
        if (enemy < player) {
            return true;
        } else if (enemy > player) {
            return false;
        }
        return true;
    }
}
