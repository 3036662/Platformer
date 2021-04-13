package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
public class Background extends Item implements Layer{
    private Texture img;
    //public Vector2 pos1;
    
    public Background(boolean levelNight) {
    	 super(0,0);
    	 if (levelNight) {
    		 img=new Texture("Night.png");
    	 }
    	 else {
    		 img=new Texture("Background.png");
    	 }
    	
    	 
    }
    
    public void render(SpriteBatch batch) {
    	batch.draw(img,getX(),getY());
    }
    
    public void update() {
    	
    }
    
    public void dispose() {
    	img.dispose();
    }
}
