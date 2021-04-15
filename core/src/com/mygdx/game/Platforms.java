package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

// Класс с платформами
public class Platforms implements Layer {
    // полянка
	class Glade extends Item{
    	private int len; //длина
    	Carrot[] carrots;
    	// конструктор
    	public Glade(Vector2 pos,int length) {
    		super(pos);
    		this.len=length;
    		Random rd=new Random();
    		// создать мокровки
    		carrots=new Carrot[len];
    		for (int i=0;i<len;i++) {
    			carrots[i]=new Carrot((int)(pos.x+64*i+18),(int)(pos.y+40),rd.nextBoolean());
    		}
    	}    
    	public int getLen() {
    		return this.len;
    	}
    }
	
	// морковки
	public class Carrot extends Item {
		private boolean visible;		
		public Carrot(int x,int y,boolean vis) {
			super(x,y);
			visible=vis;
		}		
		public boolean  getVisible() {
			return this.visible;
		}
		public void setVisible(boolean vis) {
		    this.visible=vis;	
		}	
	}
	
	private Texture img; // Текстура поляны
	private Texture ground; // Текстура для земли
	private Texture img_carrot;	
	
	Carrot[] carrots; // массив объектов морковка
	Glade[] glds; // массив полянок
	Glade[] grounds;// массив земля
	
	// конструктор
	// случайным образом раскидывает платформы по миру
	public Platforms(){
		img= new Texture("Glade.png"); // Загружена текстура 
		ground=new Texture("Ground.png");// текстура для земли
		img_carrot=new Texture("Carrot.png");
		
		
		glds= new Glade[10]; // создан массив 5 объектов лужайка
		grounds=new Glade[2]; // массив для земли
		// локальные переменные для координат и длины лужайки
		int x,y,ln;
		/* первая лужайка создается в диапазоне
		 * X= 100....400;y=10....500; */
		x=(int)( Math.random()*(400-100)+100);
		y=(int)( Math.random()*(500-0)+10);	
	    ln=(int)(Math.random()*(5-1)+1);
	    glds[0]=new Glade(new Vector2(x,y),ln);
	    
	    /* Каждая последующая  от 0 до 200 пкс по горизонт от предыдущ.
	     * и от y=10 .... 500; */
	    for (int i=1;i<glds.length;i++) {
			x=(int)(Math.random()*((200+x)-(ln*64+x))+(ln*64+x));
			y=(int)( Math.random()*(500-0)+10);	
			ln=(int)(Math.random()*(5-1)+1);
			glds[i]=new Glade(new Vector2(x,y),ln);
			//java.lang.System.out.println(x);
		}
	    
	    // создается две платформы с землей, каждая шириной шире экрана
	    x=0;y=0;		       
	    ln=800/64;
	    java.lang.System.out.println(ln*64);
	    for (int i=0;i<grounds.length;i++) {
	    	grounds[i]=new Glade(new Vector2(x,y),ln);
	    	x+=ln*64;
	    }
		
	}
	
	
	public void render(SpriteBatch batch){
		// отрисовка платформ
		for (int i=0;i<glds.length;i++) { // для каждой платформы 					
			for (int j=0;j<glds[i].len;j++) { // нарисовать колич кубиков травы равноее ее длине
				batch.draw(img,glds[i].getX()+j*66,glds[i].getY());
				if (glds[i].carrots[j].visible) {
					batch.draw(img_carrot,glds[i].carrots[j].getX(),glds[i].carrots[j].getY());
				}
			}			
		}
		// Отрисовка земли
		for (int i=0;i<grounds.length;i++) {
			for (int j=0;j<grounds[i].len;j++) { // нарисовать колич кубиков земли равноее ее длине
				batch.draw(ground,grounds[i].getX()+j*64,grounds[i].getY());
			}
		}
		
	}
	
	// весь слой двигается влево
	public void moveLeft(int speed) {
		for(int i=0;i<glds.length;i++) {
			glds[i].moveX(-speed);
			for(int j=0;j<glds[i].len;j++) {
				glds[i].carrots[j].moveX(-speed);				
			}
		}
		for(int i=0;i<grounds.length;i++) {
			grounds[i].moveX(-speed);			
		}
	}	
	
	
	/* Здесь будут уничтожены платформы которые уже
	 * не нужны и созданные новые.
	*/
	public void update() {		
		int x,y,ln;
		// если лужайка с индексом 0 уехала по горизонтально за пределы видимости		
		if (glds[0].getX()+glds[0].len*64<0) {
			  // массив сдвигается влево
			  for(int i=0;i<glds.length-1;i++) {
				  glds[i]=glds[i+1];
			  }
			  // а последняя лужайка заменяется новой
			  x=(int)glds[glds.length-2].getX();
			  y=(int)glds[glds.length-2].getY();
			  ln=glds[glds.length-2].len;
			  x=(int)(Math.random()*((200+x)-(ln*64+x))+(ln*64+x));
			  y=(int)( Math.random()*(500-0)+10);	
  			  ln=(int)(Math.random()*(5-1)+1);
			  glds[glds.length-1]=new Glade(new Vector2(x,y),ln);
		}
		
		// если земля  уехала  влево от 0 уехала по горизонтально за пределы видимости
		if (grounds[0].getX()+grounds[0].len*64<0) {
			Glade tmp=grounds[0];
			//tmp.pos.x+=tmp.len*64*2;
			tmp.moveX(tmp.len*64*2);
			grounds[0]=grounds[1];
			grounds[1]=tmp;
		}
	}
	
	public void dispose() {
		img_carrot.dispose();
		img.dispose();
		ground.dispose();		
	}
}
