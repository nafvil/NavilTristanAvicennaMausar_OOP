package com.navil.frontend.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.navil.frontend.objects.bullets.Bullet;
import com.navil.frontend.objects.patterns.ShootingPattern;
import com.navil.frontend.objects.items.Item;
import com.navil.frontend.objects.items.ItemType;
import com.navil.frontend.objects.enemies.Boss;
import com.navil.frontend.objects.enemies.Enemy;
import com.navil.frontend.objects.enemies.Fairy;
import com.navil.frontend.systems.AssetManager;
import com.navil.frontend.systems.BulletManager;
import com.navil.frontend.systems.EntityFactory;

public class Player extends GameObject {
    private String name;
    private int hp;
    private int power;
    private int spellCards;
    private long score;
    private int currentDir = 0; // -1: Left, 0: Idle, 1: Right
    // TODO: Declare a private field named shootingPattern of type ShootingPattern.
    //       No need to initialize it right away.
    private ShootingPattern shootingPattern;

    public Player(String name, int hp, int power, int spellCards) {
        super(280, 40, 32, 48, 200f, Color.RED);
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
        this.score = 0;
    }

    public Player(float x, float y, String name, int hp, int power, int spellCards) {
        super(x, y, 32, 48, 200f, Color.RED);
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
        this.score = 0;
    }

    @Override
    public void update(float delta) {
        super.update(delta); // Advances stateTime for idle animations

        // Player movement handling (LibGDX input)
        float dx = 0;
        if (Gdx.input != null) {
            if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
                y += speed * delta;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                y -= speed * delta;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                x -= speed * delta;
                dx -= 1;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                x += speed * delta;
                dx += 1;
            }
        }
        updateAnimationState(dx);
    }

    // Updates character animation state based on horizontal movement direction (dx)
    public void updateAnimationState(float dx) {
        AssetManager assets = AssetManager.getInstance();
        if (dx < 0) { // Moving Left
            if (currentDir != -1) {
                currentDir = -1;
                Animation<TextureRegion> anim = assets.getAnimation("player_left");
                if (anim != null) setAnimation(anim);
            }
        } else if (dx > 0) { // Moving Right
            if (currentDir != 1) {
                currentDir = 1;
                Animation<TextureRegion> anim = assets.getAnimation("player_right");
                if (anim != null) setAnimation(anim);
            }
        } else { // Idle / Stationary
            if (currentDir != 0) {
                currentDir = 0;
                Animation<TextureRegion> anim = assets.getAnimation("player_idle");
                if (anim != null) setAnimation(anim);
            }
        }
    }

    // TODO: Update shootBullet() to shootBullet(BulletManager bulletManager)!
    public void shootBullet(BulletManager bulletManager) {
        // - If bulletManager is null, return immediately
        // - If shootingPattern is not null, call execute() from the player's muzzle coordinates (x + width / 2f - 8f, y + height)
        // - If shootingPattern is null, call bulletManager.spawnPlayerBullet() directly
        if (bulletManager == null) return;

        float muzzleX = x + width / 2f - 8f;
        float muzzleY = y + height;

        if (shootingPattern != null) {
            shootingPattern.execute(muzzleX, muzzleY, bulletManager, true);
        } else {
            int damage = 10 + power;
            System.out.println(name + " shoots bullet dealing " + damage + " DMG!");
            bulletManager.spawnPlayerBullet(muzzleX, muzzleY, 0f, 400f, damage);
        }
    }

    @Override
    public Rectangle getCoreHitbox() {
        // TODO: Return a new 8x8 Rectangle centered right in the middle of the sprite:
        // - X coordinate: x + (width / 2f) - 4f
        // - Y coordinate: y + (height / 2f) - 4f
        // - width: 8f, height: 8f
        return new Rectangle(x + (width / 2f) - 4f, y + (height / 2f) - 4f, 8f, 8f);
    }

    @Override
    public Rectangle getGrazeHitbox() {
        // TODO: Return a new Rectangle at position coordinates (x, y)
        //       covering the entire visual sprite dimensions (width, height)
        return new Rectangle(x, y, width, height);
    }

    public void moveUp(float delta) { this.y += speed * delta; }
    public void moveDown(float delta) { this.y -= speed * delta; }
    public void moveLeft(float delta) { this.x -= speed * delta; }
    public void moveRight(float delta) { this.x += speed * delta; }

    @Override
    public void onCollision(Collidable other) {
        if (other instanceof Fairy) {
            System.out.println("Player touches fairy");
        } else if (other instanceof Boss) {
            System.out.println("Player touches boss");
        } else if (other instanceof Item item) {
            System.out.println("Player touches items");
            collectItem(item);
        }
    }

    public void shoot(Enemy target) {
        int damage = 10 + power;
        System.out.println(name + " shoots " + target.getName() + " dealing " + damage + " DMG!");
        boolean defeated = target.takeDamage(damage);
        if (defeated) {
            addScore(target.getScoreValue());
        }
    }

    public void collectItem(Item item) {
        if (item.isDestroyed()) return;

        ItemType type = item.getItemTypeEnum();
        if (type != null) {
            switch (type) {
                case POWER -> {
                    this.power += type.getPowerBonus();
                    addScore(item.getScoreValue());
                    System.out.println(name + " collected POWER item! Power increased to " + power);
                }
                case POINT -> {
                    addScore(item.getScoreValue());
                    System.out.println(name + " collected POINT item!");
                }
                case BOMB -> {
                    this.spellCards++;
                    addScore(item.getScoreValue());
                    System.out.println(name + " collected BOMB item! SpellCards: " + spellCards);
                }
                case LIFE -> {
                    this.hp += 20;
                    addScore(item.getScoreValue());
                    System.out.println(name + " collected LIFE item! HP: " + hp);
                }
            }
        } else {
            addScore(item.getScoreValue());
            System.out.println(name + " collected " + item.getItemType() + "!");
        }

        item.destroy();
    }

    public void takeDamage(int damage) {
        this.hp -= damage;
        if (this.hp < 0) {
            this.hp = 0;
        }
        System.out.println(name + " took " + damage + " damage! Remaining HP: " + this.hp);
        if (this.hp == 0) {
            System.out.println(name + " was defeated (Pichuun~)! ");
        }
    }

    public void addScore(long points) {
        if (points > 0) {
            this.score += points;
            System.out.println(name + " gained " + points + " pts! Total Score: " + this.score);
        }
    }

    public boolean isAlive() {
        return this.hp > 0;
    }

    // Encapsulation getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = Math.max(0, hp); }

    public int getPower() { return power; }
    public void setPower(int power) { this.power = power; }

    public int getSpellCards() { return spellCards; }
    public void setSpellCards(int spellCards) { this.spellCards = spellCards; }

    public long getScore() { return score; }

    // TODO: Provide a getter and setter for shootingPattern
    public ShootingPattern getShootingPattern() { return shootingPattern; }
    public void setShootingPattern(ShootingPattern shootingPattern) { this.shootingPattern = shootingPattern; }
}
