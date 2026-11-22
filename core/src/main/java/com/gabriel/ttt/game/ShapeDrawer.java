package com.gabriel.ttt.game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ShapeDrawer {
    private ShapeRenderer shape;
    public void circle() {
        shape =  new ShapeRenderer();
        shape.begin(ShapeRenderer.ShapeType.Line);

        shape.setColor(Color.WHITE);
        shape.circle(300,400,50);
    }
}