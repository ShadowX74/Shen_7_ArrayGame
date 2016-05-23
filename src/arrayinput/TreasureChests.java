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
public class TreasureChests { //Treasure chest class with vaired loot
    int x, y;
    boolean isLost;
    String powerUp;
    
    TreasureChests(int a, int b, String power) {
        this.x = a;
        this.y = b;
        this.powerUp = power;
        this.isLost = true;
    }
}
