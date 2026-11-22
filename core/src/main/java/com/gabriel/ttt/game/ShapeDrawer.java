package com.gabriel.ttt.game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ShapeDrawer {
    private ShapeRenderer shape;
    public void renderCircle() {
        shape =  new ShapeRenderer();
        shape.begin(ShapeRenderer.ShapeType.Line);

        shape.setColor(Color.WHITE);
        shape.circle(300,400,50);

        shape.end();
    }

    public void renderX() {
        shape = new ShapeRenderer();
        shape.begin(ShapeRenderer.ShapeType.Line);

        shape.setColor(Color.WHITE);

        shape.line(265, 365, 335, 435);
        shape.line(335, 365, 265, 435);

        shape.end();
    }
}