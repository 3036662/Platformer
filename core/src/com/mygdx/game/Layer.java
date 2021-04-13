package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Layer {
	 public void render(SpriteBatch batch);
     
     public void update();
     
     public void dispose();
     
}
