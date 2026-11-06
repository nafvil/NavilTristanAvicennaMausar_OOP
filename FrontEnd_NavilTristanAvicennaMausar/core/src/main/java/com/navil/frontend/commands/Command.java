package com.navil.frontend.commands;

import com.navil.frontend.objects.Player;
import com.navil.frontend.systems.BulletManager;

public interface Command {
    void execute(Player player, BulletManager bulletManager);
}
