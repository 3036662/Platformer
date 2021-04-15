package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Layer {
	 // отрисовка
	 public void render(SpriteBatch batch);
     // обновление оьъектов
     public void update();
     // уничтожение объектов
     public void dispose();
     
}
