package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

public abstract class Item {
	
	private Vector2 pos; // позиция
	
	public Item(float x,float y) {
		this.pos=new Vector2(x,y);
	}
	public Item(Vector2 pos) {
		this.pos=pos;
	}
	
	public void setX(int x) {
		this.pos.x=(int)x;
	}
	public void setX(float x) {
		this.pos.x=x;
	}
	
	
	public void setY(int y) {
		this.pos.y=(int)y;
	}
	public void setY(float y) {
		this.pos.y=y;
	}
	
	public void setPos(Vector2 pos) {
		this.pos=pos;
	}
	
	public int getY() {
		return (int)this.pos.y;
	}
	public int getX() {
		return (int)this.pos.x;
	}
	
	public void  moveX(int speed) {
		this.pos.x+=speed;
	}
	public void  moveY(int speed) {
		this.pos.y+=speed;
	}

}
