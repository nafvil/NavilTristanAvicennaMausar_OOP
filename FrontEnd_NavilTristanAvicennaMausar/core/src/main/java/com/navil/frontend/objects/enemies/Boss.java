package com.navil.frontend.objects.enemies;

import com.badlogic.gdx.graphics.Color;
import com.navil.frontend.objects.patterns.ShootingPattern;
import com.navil.frontend.objects.patterns.entityStrategy.EntityMovementPattern;
import com.navil.frontend.objects.patterns.shootingStrategy.SpreadShot;
import com.badlogic.gdx.graphics.Color;
import com.navil.frontend.objects.patterns.shootingStrategy.SpreadShot;
import com.navil.frontend.objects.patterns.entityStrategy.FixedMovement;


public class Boss extends Enemy {
    // Make sure the shootingPattern attribute, shootBullet() method, and legacy getters/setters from Module 8
    // have been deleted so Boss inherits the Strategy Pattern implementation from EntityShooter! (2 points)

    public Boss(String name, int hp) {
        super(380, 400, 64, 64, Color.BLUE, name, hp, 5000L);
        // TODO (2 points): Inject default Boss strategies via setters
        // 1. Set Shooting Pattern as SpreadShot with speed 200f, 3 bullets, and 30f spreadAngleDegrees.
        // 2. Set Movement Pattern as FixedMovement.
        setShootingPattern(new SpreadShot(200f, 3, 30f));
        setMovementPattern(new FixedMovement());
    }

    public Boss(float x, float y, String name, int hp) {
        super(x, y, 64, 64, Color.BLUE, name, hp, 5000L);
        // TODO (2 points): Perform the same strategy injection in the custom coordinate constructor
        setShootingPattern(new SpreadShot(200f, 3, 30f));
        setMovementPattern(new FixedMovement());
    }
}
