package com.navil.frontend.commands;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.navil.frontend.objects.GameObject;

import java.util.ArrayList;
import java.util.List;

public class InputHandler {

    // 1. Key Configuration
    private int keyShoot = Input.Keys.Z;
    private int keyBomb = Input.Keys.X;
    private int keyFocus = Input.Keys.SHIFT_LEFT;

    // 2. Pre-allocated Command Slots (Zero-GC Invoker)
    private ShootCommand shootCommand = new ShootCommand();
    private BombCommand bombCommand = new BombCommand();
    private FocusCommand focusOn = new FocusCommand(true);
    private FocusCommand focusOff = new FocusCommand(false);
    private final MoveCommand moveCommand = new MoveCommand(0, 0);

    private final List<Command> activeCommands = new ArrayList<>();

    public InputHandler() {}

    public InputHandler(List<GameObject> entities) {
        this.bombCommand.setEntities(entities); // Make sure this does not cause an error!
    }

    public void setEntities(List<GameObject> entities) {
        this.bombCommand.setEntities(entities); // Make sure this does not cause an error!
    }

    // Getters and Setters for Key Rebinding
    public int getKeyShoot() { return keyShoot; }
    public void setKeyShoot(int keyShoot) { this.keyShoot = keyShoot; }

    public int getKeyBomb() { return keyBomb; }
    public void setKeyBomb(int keyBomb) { this.keyBomb = keyBomb; }

    public int getKeyFocus() { return keyFocus; }
    public void setKeyFocus(int keyFocus) { this.keyFocus = keyFocus; }

    public List<Command> handleInput(float delta) {
        activeCommands.clear();
        if (Gdx.input == null) return activeCommands;

        // 1. Focus Mode Toggle (Left or Right Shift) (3 points)
        // TODO:
        boolean isFocusHeld = Gdx.input.isKeyPressed(keyFocus) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT);
        activeCommands.add(isFocusHeld ? focusOn : focusOff);
        // - Check whether keyFocus or SHIFT_RIGHT is currently pressed (isKeyPressed).
        // - Store the result in a boolean variable `isFocusHeld`.
        // - Add to activeCommands: if isFocusHeld then focusOn, otherwise focusOff.

        // 2. 8-Directional Movement (WASD or Arrow Keys) (5 points)
        float dx = 0;
        float dy = 0;
        // TODO:
        // - If W or UP is pressed (isKeyPressed), increase dy by 1
        // - If S or DOWN is pressed (isKeyPressed), decrease dy by 1
        // - If A or LEFT is pressed (isKeyPressed), decrease dx by 1
        // - If D or RIGHT is pressed (isKeyPressed), increase dx by 1
        // - Calculate float speedMult = isFocusHeld ? 0.45f : 1.0f;
        // - Update displacement: moveCommand.set(dx * delta * speedMult, dy * delta * speedMult);
        // - Add moveCommand to activeCommands.
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) dy += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S))  dy -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))  dx -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) dx += 1;

        float speedMult = isFocusHeld ? 0.45f : 1.0f;

        moveCommand.set(dx * delta * speedMult, dy * delta * speedMult);
        activeCommands.add(moveCommand);

        // 3. Shoot Command (Hold key for continuous shooting) (3 points)
        // TODO:
        // - Check whether keyShoot is currently pressed (isKeyPressed).
        // - If YES: add shootCommand to activeCommands.
        if (Gdx.input.isKeyPressed(keyShoot)) {
            activeCommands.add(shootCommand);
        }

        // 4. Bomb / Spell Card Command (Single press) (3 points)
        // TODO:
        // - Check whether keyBomb was just pressed (isKeyJustPressed).
        // - If YES: add bombCommand to activeCommands.
        if (Gdx.input.isKeyJustPressed(keyBomb)) {
            activeCommands.add(bombCommand);
        }

        return activeCommands;
    }
}
