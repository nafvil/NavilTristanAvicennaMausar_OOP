package com.navil.frontend.objects.patterns.entityStrategy;

import com.navil.frontend.objects.GameObject;

public interface EntityMovementPattern {
    void move(GameObject entity, float delta);
}
