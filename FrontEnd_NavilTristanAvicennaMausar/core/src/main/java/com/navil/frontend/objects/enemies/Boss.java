package com.navil.frontend.objects.enemies;

import com.badlogic.gdx.graphics.Color;
import com.navil.frontend.objects.patterns.ShootingPattern;
import com.navil.frontend.objects.patterns.shootingStrategy.SpreadShot;
import com.navil.frontend.systems.BulletManager;

public class Boss extends Enemy {
    // TODO: Declare a private field named `shootingPattern` of type ShootingPattern
    //       and immediately initialize it with the `SpreadShot` we just created.
    //       Make sure speed is 200f, spread angle is 30f, and 3 bullets (num) are fired.
    private ShootingPattern shootingPattern = new SpreadShot(200f, 3, 30f);

    // Existing Boss constructors you already made before. (Don't touch these!)
    public Boss(String name, int hp) {
        super(380, 400, 64, 64, Color.BLUE, name, hp, 5000L);
    }

    public Boss(float x, float y, String name, int hp) {
        super(x, y, 64, 64, Color.BLUE, name, hp, 5000L);
    }

    // TODO: Create the shootBullet(BulletManager bulletManager) method:
    //       If shootingPattern is not null and the boss hasn't been destroyed,
    //       execute shootingPattern at the boss's center cone coordinates (x + width / 2 - 8, y).
    public void shootBullet(BulletManager bulletManager) {
        if (shootingPattern != null && !isDestroyed()) {
            shootingPattern.execute(x + width / 2 - 8, y, bulletManager, false);
        }
    }

    // TODO: Provide a getter and setter for shootingPattern
    public ShootingPattern getShootingPattern() { return shootingPattern; }
    public void setShootingPattern(ShootingPattern shootingPattern) { this.shootingPattern = shootingPattern; }
}
