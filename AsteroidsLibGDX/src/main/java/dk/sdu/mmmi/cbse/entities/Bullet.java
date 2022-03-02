package dk.sdu.mmmi.cbse.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;

public class Bullet extends SpaceObject{

    private float lifeTime;
    private float lifeTimer;

    private boolean remove;
    private boolean isEnemy; 

    public Bullet(float x, float y, float radians, boolean enemy) {

        this.x = x;
        this.y = y;
        this.radians = radians;
        this.isEnemy = enemy;

        float speed = 600;
        if(isEnemy){
            speed = 500;
        }

        dx = MathUtils.cos(radians) * speed;
        dy = MathUtils.sin(radians) * speed;

        width = height = 2;

        lifeTimer = 0;
        lifeTime = 1;

    }

    public boolean shouldRemove() {
        return remove;
    }

    public void update(float dt) {

        x += dx * dt;
        y += dy * dt;

        wrap();

        lifeTimer += dt;
        if(lifeTimer > lifeTime) {
            remove = true;
        }

    }

    public void draw(ShapeRenderer sr) {

        //Trying to draw eclipse with newer LibGDX
        sr.begin(ShapeType.Filled);
        if(isEnemy){
            sr.setColor(1,0,0,1);
            sr.circle(x- width, y - height, width);
        } else {
            sr.setColor(1,1,1,1);
            sr.circle(x- width / 2, y - height /2, width/2);
        }

        sr.end();

    }

}
