package dk.sdu.mmmi.cbse.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.cbse.main.Game;

import java.util.Random;

public class Enemy extends SpaceObject {

    private Random random = new Random();

    private boolean[] enemyPattern = {true, false, false, false, false};

    private float maxSpeed;
    private float acceleration;
    private float deceleration;

    public Enemy() {

        x = Game.WIDTH / 2;
        y = Game.HEIGHT / 2;

        maxSpeed = 400;
        acceleration = 50;
        deceleration = 10;

        shapex = new float[4];
        shapey = new float[4];

        radians = 3.1415f / 2;
        rotationSpeed = 4;

    }

    private void setShape() {
        shapex[0] = x + MathUtils.cos(radians) * 16;
        shapey[0] = y + MathUtils.sin(radians) * 16;

        shapex[1] = x + MathUtils.cos(radians - 4 * 3.1415f / 5) * 16;
        shapey[1] = y + MathUtils.sin(radians - 4 * 3.1445f / 5) * 16;

        shapex[2] = x + MathUtils.cos(radians + 3.1415f) * 10;
        shapey[2] = y + MathUtils.sin(radians + 3.1415f) * 10;

        shapex[3] = x + MathUtils.cos(radians + 4 * 3.1415f / 5) * 16;
        shapey[3] = y + MathUtils.sin(radians + 4 * 3.1415f / 5) * 16;
    }

    public void update(float dt) {

        // accelerating
        dx += MathUtils.cos(radians) * acceleration * dt;
        dy += MathUtils.sin(radians) * acceleration * dt;

        // turning
        if (enemyPattern[random.nextInt(enemyPattern.length)]) {
            radians += rotationSpeed * dt;
        } else if (enemyPattern[random.nextInt(enemyPattern.length)]) {
            radians -= rotationSpeed * dt;
        }

        // deceleration
        float vec = (float) Math.sqrt(dx * dx + dy * dy);
        if (vec > 0) {
            dx -= (dx / vec) * deceleration * dt;
            dy -= (dy / vec) * deceleration * dt;
        }
        if (vec > maxSpeed) {
            dx = (dx / vec) * maxSpeed;
            dy = (dy / vec) * maxSpeed;
        }

        // set position
        x += dx * dt;
        y += dy * dt;

        // set shape
        setShape();

        // screen wrap
        wrap();

    }

    public void draw(ShapeRenderer sr) {

        sr.setColor(1, 0, 0, 1);

        sr.begin(ShapeType.Line);

        for (int i = 0, j = shapex.length - 1;
             i < shapex.length;
             j = i++) {

            sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);

        }

        sr.end();

    }

}
