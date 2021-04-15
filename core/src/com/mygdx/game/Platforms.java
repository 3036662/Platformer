package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

// ����� � �����������
public class Platforms implements Layer {
    // �������
	class Glade extends Item{
    	private int len; //�����
    	Carrot[] carrots;
    	// �����������
    	public Glade(Vector2 pos,int length) {
    		super(pos);
    		this.len=length;
    		Random rd=new Random();
    		// ������� ��������
    		carrots=new Carrot[len];
    		for (int i=0;i<len;i++) {
    			carrots[i]=new Carrot((int)(pos.x+64*i+18),(int)(pos.y+40),rd.nextBoolean());
    		}
    	}    
    	public int getLen() {
    		return this.len;
    	}
    }
	
	// ��������
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
	
	private Texture img; // �������� ������
	private Texture ground; // �������� ��� �����
	private Texture img_carrot;	
	
	Carrot[] carrots; // ������ �������� ��������
	Glade[] glds; // ������ �������
	Glade[] grounds;// ������ �����
	
	// �����������
	// ��������� ������� ����������� ��������� �� ����
	public Platforms(){
		img= new Texture("Glade.png"); // ��������� �������� 
		ground=new Texture("Ground.png");// �������� ��� �����
		img_carrot=new Texture("Carrot.png");
		
		
		glds= new Glade[10]; // ������ ������ 5 �������� �������
		grounds=new Glade[2]; // ������ ��� �����
		// ��������� ���������� ��� ��������� � ����� �������
		int x,y,ln;
		/* ������ ������� ��������� � ���������
		 * X= 100....400;y=10....500; */
		x=(int)( Math.random()*(400-100)+100);
		y=(int)( Math.random()*(500-0)+10);	
	    ln=(int)(Math.random()*(5-1)+1);
	    glds[0]=new Glade(new Vector2(x,y),ln);
	    
	    /* ������ �����������  �� 0 �� 200 ��� �� �������� �� ��������.
	     * � �� y=10 .... 500; */
	    for (int i=1;i<glds.length;i++) {
			x=(int)(Math.random()*((200+x)-(ln*64+x))+(ln*64+x));
			y=(int)( Math.random()*(500-0)+10);	
			ln=(int)(Math.random()*(5-1)+1);
			glds[i]=new Glade(new Vector2(x,y),ln);
			//java.lang.System.out.println(x);
		}
	    
	    // ��������� ��� ��������� � ������, ������ ������� ���� ������
	    x=0;y=0;		       
	    ln=800/64;
	    java.lang.System.out.println(ln*64);
	    for (int i=0;i<grounds.length;i++) {
	    	grounds[i]=new Glade(new Vector2(x,y),ln);
	    	x+=ln*64;
	    }
		
	}
	
	
	public void render(SpriteBatch batch){
		// ��������� ��������
		for (int i=0;i<glds.length;i++) { // ��� ������ ��������� 					
			for (int j=0;j<glds[i].len;j++) { // ���������� ����� ������� ����� ������� �� �����
				batch.draw(img,glds[i].getX()+j*66,glds[i].getY());
				if (glds[i].carrots[j].visible) {
					batch.draw(img_carrot,glds[i].carrots[j].getX(),glds[i].carrots[j].getY());
				}
			}			
		}
		// ��������� �����
		for (int i=0;i<grounds.length;i++) {
			for (int j=0;j<grounds[i].len;j++) { // ���������� ����� ������� ����� ������� �� �����
				batch.draw(ground,grounds[i].getX()+j*64,grounds[i].getY());
			}
		}
		
	}
	
	// ���� ���� ��������� �����
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
	
	
	/* ����� ����� ���������� ��������� ������� ���
	 * �� ����� � ��������� �����.
	*/
	public void update() {		
		int x,y,ln;
		// ���� ������� � �������� 0 ������ �� ������������� �� ������� ���������		
		if (glds[0].getX()+glds[0].len*64<0) {
			  // ������ ���������� �����
			  for(int i=0;i<glds.length-1;i++) {
				  glds[i]=glds[i+1];
			  }
			  // � ��������� ������� ���������� �����
			  x=(int)glds[glds.length-2].getX();
			  y=(int)glds[glds.length-2].getY();
			  ln=glds[glds.length-2].len;
			  x=(int)(Math.random()*((200+x)-(ln*64+x))+(ln*64+x));
			  y=(int)( Math.random()*(500-0)+10);	
  			  ln=(int)(Math.random()*(5-1)+1);
			  glds[glds.length-1]=new Glade(new Vector2(x,y),ln);
		}
		
		// ���� �����  ������  ����� �� 0 ������ �� ������������� �� ������� ���������
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
