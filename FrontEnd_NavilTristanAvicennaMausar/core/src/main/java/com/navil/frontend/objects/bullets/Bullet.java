package com.navil.frontend.objects.bullets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.navil.frontend.objects.Collidable;
import com.navil.frontend.objects.GameObject;
import com.navil.frontend.objects.enemies.Enemy;

public class Bullet extends GameObject {
    private BulletType bulletType;
    private int damage;

    // TODO: Declare the following private attributes:
    // 1. float vx and vy (2D velocity vector components)
    // 2. float angle initialized to 90f (visual rotation angle in degrees)
    // 3. boolean isPlayerBullet (flag indicating whether this bullet belongs to the player or an enemy)
    // 4. boolean grazed (flag indicating whether this bullet has already been grazed by the player)
    private float vx;
    private float vy;
    private float angle = 90f;
    private boolean isPlayerBullet;
    private boolean grazed;

    // Constructor with default speed (speed = 400f)
    public Bullet(float x, float y, BulletType bulletType, int damage) {
        super(x, y, 16, 16, 400f, Color.YELLOW);
        // TODO:
        // 1. Initialize bulletType and damage from the parameters
        // 2. Set initial velocity: vx to 0 and vy to 400f (moves straight upward)
        // 3. Set the visual rotation angle to 90 degrees upward
        // 4. Mark this bullet as belonging to the player (isPlayerBullet), and set graze status to inactive
        this.bulletType = bulletType;
        this.damage = damage;
        this.vx = 0f;
        this.vy = 400f;
        this.angle = 90f;
        this.isPlayerBullet = true;
        this.grazed = false;
    }

    // Overloaded constructor with custom speed
    public Bullet(float x, float y, float speed, BulletType bulletType, int damage) {
        super(x, y, 16, 16, speed, Color.YELLOW);
        // TODO:
        // 1. Initialize bulletType and damage from the parameters
        // 2. Set initial velocity: vx to 0 and vy to speed
        // 3. Set visual rotation angle: if speed is negative use 270f (facing down), otherwise use 90f
        // 4. Mark this bullet as belonging to the player (isPlayerBullet), and set graze status to inactive
        this.bulletType = bulletType;
        this.damage = damage;
        this.vx = 0f;
        this.vy = speed;
        this.angle = (speed < 0) ? 270f : 90f;
        this.isPlayerBullet = true;
        this.grazed = false;
    }

    // Pool Re-initialization for the Object Pool Pattern
    public void init(float x, float y, float vx, float vy, BulletType bulletType, int damage, boolean isPlayerBullet) {
        // TODO:
        // 1. Update position (x, y) and velocity vector components (vx, vy) from the argument parameters
        // 2. Calculate the visual rotation angle using Math.atan2 as explained above, then convert it to degrees
        // 3. Update speed by calculating the resultant length of both velocity vectors using Math.hypot as explained above
        // 4. Save the bullet's identity attributes (bulletType, damage, and isPlayerBullet) from the parameters
        // 5. Reactivate this bullet and reset its animation state time from 0
        // 6. Make sure the grazed status is reset to inactive so this recycled bullet can be grazed again
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.angle = (float) Math.toDegrees(Math.atan2(vy, vx));
        this.speed = (float) Math.hypot(vx, vy);
        this.bulletType = bulletType;
        this.damage = damage;
        this.isPlayerBullet = isPlayerBullet;
        this.active = true;
        this.stateTime = 0f;
        this.grazed = false;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        // Bullet moves upwards linearly
        // TODO:
        // 1. Update position x by adding velocity component vx multiplied by delta
        // 2. Update position y by adding velocity component vy multiplied by delta
        this.x += vx * delta;
        this.y += vy * delta;
    }

    @Override
    public void render(SpriteBatch batch) {
        if (batch != null && active) {
            if (sprite != null) {
                // TODO: Draw the bullet sprite using batch.draw() with the following parameters:
                // sprite, position (x, y), rotation pivot origin at center (width / 2f, height / 2f),
                // dimensions (width, height), scale (1f, 1f), and rotation angle (angle - 90f)
                batch.draw(sprite, x, y, width / 2f, height / 2f, width, height, 1f, 1f, angle - 90f);
            } else {
                super.render(batch);
            }
        }
    }

    @Override
    public void onCollision(Collidable other) {
        if (other instanceof Enemy enemy) {
            System.out.println("Bullet hit " + enemy.getName() + " for " + damage + " DMG!");
            boolean defeated = enemy.takeDamage(damage);
            this.destroy(); // Bullet is destroyed on collision
        }
    }

    public BulletType getBulletType() { return bulletType; }
    public int getDamage() { return damage; }

    // TODO:
    // 1. Create getters for isPlayerBullet, vx, and vy
    // 2. Create a getter and setter for angle
    // 3. Create a getter hasBeenGrazed() that returns the grazed attribute
    // 4. Create a setter setGrazed(boolean grazed)
    public boolean isPlayerBullet() { return isPlayerBullet; }
    public float getVx() { return vx; }
    public float getVy() { return vy; }

    public float getAngle() { return angle; }
    public void setAngle(float angle) { this.angle = angle; }

    public boolean hasBeenGrazed() { return grazed; }
    public void setGrazed(boolean grazed) { this.grazed = grazed; }
}
