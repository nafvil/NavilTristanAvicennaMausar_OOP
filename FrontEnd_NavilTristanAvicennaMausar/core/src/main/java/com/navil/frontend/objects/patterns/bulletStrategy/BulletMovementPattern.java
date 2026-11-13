package com.navil.frontend.objects.patterns.bulletStrategy;

import com.navil.frontend.objects.bullets.Bullet;

public interface BulletMovementPattern {
    void move(Bullet bullet, float delta);
}
