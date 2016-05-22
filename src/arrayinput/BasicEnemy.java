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
public class BasicEnemy extends Enemy{

    
    public BasicEnemy(int x, int y, char symbol, int move) {
        super(x, y, symbol);
        this.movelength = move;
        this.isAlive = true;
    }
}