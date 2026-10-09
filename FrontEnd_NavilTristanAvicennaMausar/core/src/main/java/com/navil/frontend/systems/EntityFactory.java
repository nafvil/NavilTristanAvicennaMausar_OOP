package com.navil.frontend.systems;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.navil.frontend.objects.Player;
import com.navil.frontend.objects.bullets.Bullet;
import com.navil.frontend.objects.bullets.BulletType;
import com.navil.frontend.objects.enemies.Boss;
import com.navil.frontend.objects.enemies.Fairy;
import com.navil.frontend.objects.items.Item;
import com.navil.frontend.objects.items.ItemType;

public class EntityFactory {

    // Create a Player and assign the 'player_idle' animation from AssetManager
    public static Player createPlayer(float x, float y, String name, int hp, int power, int spellCards) {
        Player player = new Player(x, y, name, hp, power, spellCards);
        player.setHeight(48); // player.png cells are 32x48; Player defaults to 32x32
        Animation<TextureRegion> anim = AssetManager.getInstance().getAnimation("player_idle");
        player.setAnimation(anim);
        return player;
    }

    // Create a Fairy and assign the 'fairy_idle' animation
    public static Fairy createFairy(float x, float y, String name, int hp) {
        Fairy fairy = new Fairy(x, y, name, hp);
        fairy.setWidth(32);
        fairy.setHeight(32); // matches the 32x32 cells registered in AssetManager.init()
        fairy.setAnimation(AssetManager.getInstance().getAnimation("fairy_idle"));
        return fairy;
    }

    // Create a Boss and assign the 'boss_idle' animation
    public static Boss createBoss(float x, float y, String name, int hp) {
        Boss boss = new Boss(x, y, name, hp);
        boss.setWidth(64);
        boss.setHeight(64); // rumia.png cells are 64x64; Boss defaults to 48x48
        boss.setAnimation(AssetManager.getInstance().getAnimation("boss_idle"));
        return boss;
    }

    // Create an Item and assign its sprite from AssetManager based on ItemType
    public static Item createItem(float x, float y, ItemType itemType) {
        Item item = new Item(x, y, itemType);
        String key = switch (itemType) {
            case POWER -> "item_power";
            case POINT -> "item_point";
            case BOMB  -> "item_bomb";
            case LIFE  -> "item_life";
        };
        TextureRegion sprite = AssetManager.getInstance().getTextureRegion(key);
        item.setSprite(sprite);
        return item;
    }

    // Create an enemy bullet (type DANMAKU, speed 0f, sprite bullet_danmaku)
    public static Bullet createEnemyBullet(float x, float y, int damage) {
        Bullet bullet = new Bullet(x, y, 0f, BulletType.DANMAKU, damage);
        bullet.setWidth(16);
        bullet.setHeight(16); // bullets_small.png cells are 16x16; Bullet defaults to 8x16
        TextureRegion sprite = AssetManager.getInstance().getTextureRegion("bullet_danmaku");
        bullet.setSprite(sprite);
        return bullet;
    }
}
}







