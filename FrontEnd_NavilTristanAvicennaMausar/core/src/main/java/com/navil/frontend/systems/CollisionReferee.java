package com.navil.frontend.systems;

import com.navil.frontend.objects.GameObject;
import com.navil.frontend.objects.Player;
import com.navil.frontend.objects.bullets.Bullet;
import com.navil.frontend.objects.enemies.Enemy;
import com.navil.frontend.objects.items.ItemType;

import java.util.ArrayList;
import java.util.List;

public class CollisionReferee {

    public void resolveCollisions(Player player, List<GameObject> entities, BulletManager bulletManager) {
        if (player == null || entities == null || bulletManager == null) return;

        List<GameObject> newDrops = new ArrayList<>();

        for (Bullet bullet : bulletManager.getActivePlayerBullets()) {
            if (bullet.isDestroyed()) continue;

            for (GameObject entity : entities) {
                if (entity instanceof Enemy enemy && !enemy.isDestroyed()) {
                    if (bullet.getCoreHitbox().overlaps(enemy.getCoreHitbox())) {

                        newDrops.add(EntityFactory.createItem(enemy.getX(), enemy.getY(), ItemType.POWER));
                    }
                }
            }
        }

        for (Bullet bullet : bulletManager.getActiveEnemyBullets()Bullets()) {
            if (bullet.isDestroyed()) continue;

            for (GameObject entity : entities) {
                if (entity instanceof Enemy enemy && !enemy.isDestroyed()) {
                    if (bullet.getCoreHitbox().overlaps(enemy.getCoreHitbox())) {
                        // Core Business Logic:
                        // Enemy takes damage, player gets score, etc.
                        // Bullet gets marked to be destroyed
                        newDrops.add(EntityFactory.createItem(enemy.getX(), enemy.getY(), ItemType.POWER));
                    }
                }
            }
        }
        entities.addAll(newDrops);
    }

}
