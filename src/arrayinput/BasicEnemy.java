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
public class BasicEnemy extends Enemy{ //second Enemy Class 5 pts

    
    public BasicEnemy(int x, int y, char symbol) {
        super(x, y, symbol);
        this.movelength = 1;
        this.isAlive = true;
    }
}