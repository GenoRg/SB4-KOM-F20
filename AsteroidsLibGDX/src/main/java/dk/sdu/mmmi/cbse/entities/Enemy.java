package dk.sdu.mmmi.cbse.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.cbse.main.Game;

import java.util.ArrayList;
import java.util.Random;

public class Enemy extends SpaceObject {

    private Random random = new Random();

    private boolean[] enemyPattern = {true, false, false, false, false};

    private final int MAX_BULLETS = 2;
    private ArrayList<Bullet> bullets;

    private float[] flamex;
    private float[] flamey;

    private float maxSpeed;
    private float acceleration;
    private float deceleration;
    private float acceleratingTimer;

    public Enemy(ArrayList<Bullet> bullets) {

        this.bullets = bullets;

        x = Game.WIDTH / 4;
        y = Game.HEIGHT / 4;

        maxSpeed = 400;
        acceleration = 50;
        deceleration = 10;

        shapex = new float[4];
        shapey = new float[4];
        flamex = new float[3];
        flamey = new float[3];

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

    private void setFlame(){
        flamex[0] = x + MathUtils.cos(radians - 5 * 3.1314f / 6) * 12;
        flamey[0] = y + MathUtils.sin(radians - 5 * 3.1314f / 6) * 12;

        flamex[1] = x + MathUtils.cos(radians - 3.1415f) * (6 + acceleratingTimer * 100) * 2;
        flamey[1] = y + MathUtils.sin(radians - 3.1415f) * (6 + acceleratingTimer * 100) * 2;

        flamex[2] = x + MathUtils.cos(radians + 5 * 3.1314f / 6) * 12;
        flamey[2] = y + MathUtils.sin(radians + 5 * 3.1314f / 6) * 12;
    }

    public void shoot(){
        if(bullets.size() == MAX_BULLETS) return;
        bullets.add(new Bullet(x, y, radians, true));
    }

    public void update(float dt) {

        // accelerating
        dx += MathUtils.cos(radians) * acceleration * dt;
        dy += MathUtils.sin(radians) * acceleration * dt;
        acceleratingTimer += dt;
        if(acceleratingTimer > 0.1f) {
            acceleratingTimer = 0;
        }

        // turning
        if (enemyPattern[random.nextInt(enemyPattern.length)]) {
            radians += rotationSpeed * dt;
        } else if (enemyPattern[random.nextInt(enemyPattern.length)]) {
            radians -= rotationSpeed * dt;
        } else if (enemyPattern[random.nextInt(enemyPattern.length)]) {
            shoot();
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

        // set flames
        setFlame();

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

        for (int i = 0, j = flamex.length - 1;
             i < flamex.length;
             j = i++) {

            sr.line(flamex[i], flamey[i], flamex[j], flamey[j]);

        }

        sr.end();

    }

}
