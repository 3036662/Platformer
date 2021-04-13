package com.mygdx.game;

import java.util.Date;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Enemies implements Layer {
	private Texture wolf_left1,wolf_left2,wolf_right1,wolf_right2 ;
	
	public class Wolf extends Item {		
		private Vector2 pos_start;
		private int vx; // скорость
		private int pass_len; // Длина пути
		private long lastframe_time; // время последнего обн кадра
		private int frame_numb; // номер кадра
		
		
		// парам: нач положение, длина пробежки, начальная скорость
		public Wolf(int x,int y,int len,int vx) {
			super(x,y);		
			this.pos_start=new Vector2(x,y);
			this.pass_len=len;
			this.vx=vx;
		}
		
		private void run() {			
			moveX(vx);
		}
		
		private void animate_run() {
			Date date=new Date();
			if (vx!=0) {
				if (date.getTime()-lastframe_time>120) {
					   if (frame_numb==0 ) {
						   frame_numb++;
					   } else {frame_numb--;}
					   lastframe_time=date.getTime();
				   
				   }
			}
			
		}
		
		public void update() {
			animate_run();
			// разворот волка
			if (getX()>=pos_start.x+pass_len || getX()<pos_start.x) {vx=-vx;}
			run();
			// если заяц убежал от него вперед, телепортируем волка вперед)
			if (pos_start.x+pass_len< -150) {
				pos_start.x+=1200;
				setX(pos_start.x);
			}
		}
	
	}
		
	
	
	Wolf[] wolfs;
	
	// конструктор
	public Enemies(){	
		wolf_left1=new Texture("wolf_left1.png");
		wolf_left2=new Texture("wolf_left2.png");
		wolf_right1=new Texture("wolf_right1.png");
		wolf_right2=new Texture("wolf_right2.png");
		
		wolfs=new Wolf[2];
		wolfs[0]=new Wolf(100,50,200,1);
		wolfs[1]=new Wolf(510,50,200,2);
	}
	
	
	
	
	
	public void render(SpriteBatch batch) {
		for (int i=0;i<wolfs.length;i++) {
			if (wolfs[i].vx>0 && wolfs[i].frame_numb==0) {
				batch.draw(wolf_right1,wolfs[i].getX() ,wolfs[0].getY());
			}
			if  (wolfs[i].vx>0 && wolfs[i].frame_numb==1) {
				batch.draw(wolf_right2,wolfs[i].getX() ,wolfs[0].getY());
			}
			if (wolfs[i].vx<0 && wolfs[i].frame_numb==0) {
				batch.draw(wolf_left1,wolfs[i].getX() ,wolfs[0].getY());
			}
			if  (wolfs[i].vx<0 && wolfs[i].frame_numb==1) {
				batch.draw(wolf_left2,wolfs[i].getX() ,wolfs[0].getY());
			}
		}
	}
	
	public void moveLeft(int speed) {
		for (int i=0;i<wolfs.length;i++) {
			wolfs[i].moveX(-speed);	
			wolfs[i].pos_start.x-=speed;	
		}
	}
	
	public void update() {
		for (int i=0;i<wolfs.length;i++) {
			wolfs[i].update();
		}		
	}
	
	public void dispose() {
		wolf_left1.dispose();
		wolf_left2.dispose();
		wolf_right1.dispose();
		wolf_right2.dispose();
	}
}
