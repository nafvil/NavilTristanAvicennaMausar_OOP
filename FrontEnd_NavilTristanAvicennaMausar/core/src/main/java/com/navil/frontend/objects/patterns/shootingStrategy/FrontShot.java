package com.navil.frontend.objects.patterns.shootingStrategy;

import com.navil.frontend.objects.GameObject;
import com.navil.frontend.objects.bullets.Bullet;
import com.navil.frontend.objects.patterns.ShootingPattern;
import com.navil.frontend.objects.patterns.bulletStrategy.BulletMovementPattern;
import com.navil.frontend.objects.patterns.bulletStrategy.LinearBulletMovement;
import com.navil.frontend.systems.BulletManager;

public class FrontShot implements ShootingPattern {
    private float bulletSpeed;
    private int damage;
    private BulletMovementPattern bulletMovementPattern;
    private GameObject target;
    private String spriteKey;
    private float offsetX = 0f;
    private float offsetY = 0f;

    public FrontShot(float bulletSpeed, int damage, float offsetX, float offsetY, BulletMovementPattern bulletMovementPattern, GameObject target, String spriteKey) {
        this.bulletSpeed = bulletSpeed;
        this.damage = damage;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.bulletMovementPattern = bulletMovementPattern;
        this.target = target;
        this.spriteKey = spriteKey;
    }

    public FrontShot(float bulletSpeed, int damage, BulletMovementPattern bulletMovementPattern, GameObject target, String spriteKey) {
        this(bulletSpeed, damage, 0f, 0f, bulletMovementPattern, target, spriteKey);
    }

    public FrontShot(float bulletSpeed, int damage, BulletMovementPattern bulletMovementPattern, String spriteKey) {
        this(bulletSpeed, damage, 0f, 0f, bulletMovementPattern, null, spriteKey);
    }

    public FrontShot(float bulletSpeed, int damage, BulletMovementPattern bulletMovementPattern) {
        this(bulletSpeed, damage, 0f, 0f, bulletMovementPattern, null, null);
    }

    public FrontShot(float bulletSpeed, int damage) {
        this(bulletSpeed, damage, 0f, 0f, new LinearBulletMovement(), null, null);
    }

    @Override
    public void execute(float originX, float originY, BulletManager bulletManager, boolean isPlayer) {
        if (bulletManager == null) return;

        float spawnX = originX + offsetX;
        float spawnY = originY + (isPlayer ? offsetY : -offsetY);

        // Angle: Aim at target if present (ZUN FAN_AIMED N=1), otherwise 90 deg (Player) / 270 deg (Enemy)
        float angle;
        if (target != null) {
            float targetCenterX = target.getX() + target.getWidth() / 2f;
            float targetCenterY = target.getY() + target.getHeight() / 2f;
            angle = (float) Math.toDegrees(Math.atan2(targetCenterY - spawnY, targetCenterX - spawnX));
        } else {
            angle = isPlayer ? 90f : 270f;
        }

        float rad = (float) Math.toRadians(angle);
        float vx = (float) (Math.cos(rad) * bulletSpeed);
        float vy = (float) (Math.sin(rad) * bulletSpeed);

        Bullet bullet;
        if (isPlayer) {
            bullet = bulletManager.spawnPlayerBullet(spawnX, spawnY, vx, vy, damage, spriteKey);
        } else {
            bullet = bulletManager.spawnEnemyBullet(spawnX, spawnY, vx, vy, damage);
        }

        if (bullet != null && bulletMovementPattern != null) {
            bullet.setMovementPattern(bulletMovementPattern);
        }
    }

    public float getBulletSpeed() { return bulletSpeed; }
    public void setBulletSpeed(float bulletSpeed) { this.bulletSpeed = bulletSpeed; }

    public int getDamage() { return damage; }
    public void setDamage(int damage) { this.damage = damage; }

    public BulletMovementPattern getBulletMovementPattern() { return bulletMovementPattern; }
    public void setBulletMovementPattern(BulletMovementPattern bulletMovementPattern) { this.bulletMovementPattern = bulletMovementPattern; }

    public GameObject getTarget() { return target; }
    public void setTarget(GameObject target) { this.target = target; }

    public String getSpriteKey() { return spriteKey; }
    public void setSpriteKey(String spriteKey) { this.spriteKey = spriteKey; }

    public float getOffsetX() { return offsetX; }
    public void setOffsetX(float offsetX) { this.offsetX = offsetX; }

    public float getOffsetY() { return offsetY; }
    public void setOffsetY(float offsetY) { this.offsetY = offsetY; }
}
