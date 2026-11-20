package com.navil.frontend.objects.patterns.shootingStrategy;

import com.navil.frontend.objects.GameObject;
import com.navil.frontend.objects.bullets.Bullet;
import com.navil.frontend.objects.patterns.ShootingPattern;
import com.navil.frontend.objects.patterns.bulletStrategy.BulletMovementPattern;
import com.navil.frontend.systems.BulletManager;

public class SpreadShot implements ShootingPattern {
    private float speed;
    private int numBullets;
    private float spreadAngleDegrees;
    private int damage;
    private float horizontalSpacing = 0f;
    private BulletMovementPattern bulletMovementPattern;
    private GameObject target;
    private String spriteKey;

    // Master Constructor: Menampung seluruh atribut dan konfigurasi
    public SpreadShot(float speed, int numBullets, float spreadAngleDegrees, int damage, float horizontalSpacing,
                      BulletMovementPattern bulletMovementPattern, GameObject target, String spriteKey) {
        this.speed = speed;
        this.numBullets = numBullets;
        this.spreadAngleDegrees = spreadAngleDegrees;
        this.damage = damage;
        this.horizontalSpacing = horizontalSpacing;
        this.bulletMovementPattern = bulletMovementPattern;
        this.target = target;
        this.spriteKey = spriteKey;
    }

    // Convenience Constructors (Mendelegasikan ke Master Constructor)
    public SpreadShot(float speed, int numBullets, float spreadAngleDegrees) {
        this(speed, numBullets, spreadAngleDegrees, 15, 0f, null, null, null);
    }

    public SpreadShot(float speed, int numBullets, float spreadAngleDegrees, int damage) {
        this(speed, numBullets, spreadAngleDegrees, damage, 0f, null, null, null);
    }

    public SpreadShot(float speed, int numBullets, float spreadAngleDegrees, int damage, BulletMovementPattern bulletMovementPattern) {
        this(speed, numBullets, spreadAngleDegrees, damage, 0f, bulletMovementPattern, null, null);
    }

    public SpreadShot(float speed, int numBullets, float spreadAngleDegrees, int damage, GameObject target, String spriteKey) {
        this(speed, numBullets, spreadAngleDegrees, damage, 0f, null, target, spriteKey);
    }

    @Override
    public void execute(float originX, float originY, BulletManager bulletManager, boolean isPlayer) {
        if (bulletManager == null) return;

        // Base direction angle: Aim at target if present (ZUN FAN_AIMED), otherwise default 90 deg (Player) / 270 deg (Enemy)
        float centerAngle;
        if (target != null) {
            float targetCenterX = target.getX() + target.getWidth() / 2f;
            float targetCenterY = target.getY() + target.getHeight() / 2f;
            centerAngle = (float) Math.toDegrees(Math.atan2(targetCenterY - originY, targetCenterX - originX));
        } else {
            centerAngle = isPlayer ? 90f : 270f;
        }

        float startAngle = centerAngle - (spreadAngleDegrees / 2f);
        float angleStep = (numBullets > 1) ? (spreadAngleDegrees / (numBullets - 1)) : 0;
        int bulletDamage = this.damage;
        float startX = (horizontalSpacing > 0f && numBullets > 1) ? (originX - ((numBullets - 1) * horizontalSpacing / 2f)) : originX;

        for (int i = 0; i < numBullets; i++) {
            float angleDeg = startAngle + (i * angleStep);
            float angleRad = (float) Math.toRadians(angleDeg);

            float vx = (float) (speed * Math.cos(angleRad));
            float vy = (float) (speed * Math.sin(angleRad));
            float spawnX = (horizontalSpacing > 0f && numBullets > 1) ? (startX + i * horizontalSpacing) : originX;

            Bullet bullet;
            if (isPlayer) {
                bullet = bulletManager.spawnPlayerBullet(spawnX, originY, vx, vy, bulletDamage, spriteKey);
            } else {
                bullet = (spriteKey != null)
                    ? bulletManager.spawnEnemyBullet(spawnX, originY, vx, vy, bulletDamage, spriteKey)
                    : bulletManager.spawnEnemyBullet(spawnX, originY, vx, vy, bulletDamage);
            }

            if (bullet != null && bulletMovementPattern != null) {
                bullet.setMovementPattern(bulletMovementPattern);
            }
        }
    }

    public float getSpeed() { return speed; }
    public void setSpeed(float speed) { this.speed = speed; }

    public int getNumBullets() { return numBullets; }
    public void setNumBullets(int numBullets) { this.numBullets = numBullets; }

    public float getSpreadAngleDegrees() { return spreadAngleDegrees; }
    public void setSpreadAngleDegrees(float spreadAngleDegrees) { this.spreadAngleDegrees = spreadAngleDegrees; }

    public int getDamage() { return damage; }
    public void setDamage(int damage) { this.damage = damage; }

    public float getHorizontalSpacing() { return horizontalSpacing; }
    public void setHorizontalSpacing(float horizontalSpacing) { this.horizontalSpacing = horizontalSpacing; }

    public BulletMovementPattern getBulletMovementPattern() { return bulletMovementPattern; }
    public void setBulletMovementPattern(BulletMovementPattern bulletMovementPattern) { this.bulletMovementPattern = bulletMovementPattern; }

    public GameObject getTarget() { return target; }
    public void setTarget(GameObject target) { this.target = target; }

    public String getSpriteKey() { return spriteKey; }
    public void setSpriteKey(String spriteKey) { this.spriteKey = spriteKey; }
}
