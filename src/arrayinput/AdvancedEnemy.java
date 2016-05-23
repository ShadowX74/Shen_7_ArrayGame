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
public class AdvancedEnemy extends Enemy{ //Usage of subclass 7 pts

    public AdvancedEnemy(int x, int y, char symbol) {
        super(x, y, symbol);
        this.movelength = 2;
        this.isAlive = true;
    }
}