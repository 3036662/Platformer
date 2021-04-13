package com.mygdx.game;


import java.util.Date;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

// ����� ��� �������� ����� 
public class Rabbit extends Item implements Layer {
   private Texture img,img2,img_dead,img_dead2; // �������� �������� �����
  // Vector2 pos; //���������� ����������
   private int speed=4; // �������� ���������� � ����/������
   private int vy=0; // ������� �� ���������
   private int frame_numb;// ����� ����� � ������
  // private Date date;
   private long lastframe_time=0;// 
   private Boolean hasGround=false;
   private Boolean isAlive;

   
   // ����������� 
   public Rabbit(boolean rabbitIsBlue) {
	   super(50,250);
	   if (rabbitIsBlue) {
		   img=new Texture("Rabbit_blue1.png");
		   img2=new Texture("Rabbit_blue2.png");
		   img_dead=new Texture("Rabbit_blueDead1.png");
		   img_dead2=new Texture("Rabbit_blueDead2.png");
	   }
	   else {
		   img=new Texture("Rabbit_pink.png");
		   img2=new Texture("Rabbit_pink_2.png");
		   img_dead=new Texture("pink_dead.png");
		   img_dead2=new Texture("pink_dead2.png");
	   }

	   isAlive=true;
	   
   }
   
  
   
   // ��������� �������
   public void render(SpriteBatch batch) {
	   Texture tmp;
	   if (isAlive) {		   
		   if (frame_numb==1) {tmp=img;}else {tmp=img2;}
		   batch.draw(tmp, getX(), getY());
	   }
	// �������
	   else {
		   if (frame_numb==1) {tmp=img_dead;}else {tmp=img_dead2;}
		   batch.draw(tmp, getX(), getY());
	   }
	   
	   
	   
	   
   }
   
   //����������� �������
   public void update(){
	// ��������� ��� �������� ������ �� ��������� �������
	   if (!isAlive) {die_animate();}
	   phisics();
	   
   }
   
   private void run_animate() {
	   Date date=new Date();
	   if (date.getTime()-lastframe_time>80) {
		   if (frame_numb==0 ) {
			   frame_numb++;
		   } else {frame_numb--;}
		   lastframe_time=date.getTime();
	   
	   }
   }
   
   public void run_left() {
	   run_animate();
	   if (getX()>10) {
		   moveX(-speed);
	   }
   }
   
   // ��������� ������ �� x=350; ������ ������ ��������� ���
   // ���������� true ���� ���� ���������
   public boolean run_right() {
	   run_animate();
	   if (getX()<350) {
		   moveX(speed);		   		   
		   return true;
	   }	   	  
	   return false;
   }
   
   // ������
   public void jump() {	  
	    if (hasGround()) {
		   vy=33; // ������� �������� �� ���������
	    }  
   }
   
   // ������ 
   private void phisics() {
	   //���������� - ������������� ��������� ,���� ��� ����� ��� ������
	   if (!hasGround() && isAlive) {
		   vy-=1;
	   }
	   // ���� ������,��������� ����� �� ����� ���� �� �������� ������ �� ����
	   if(hasGround()&& vy<0) {
		   vy=0;
	   }	   
	   // ����������� �� ���������
	  moveY(vy);
	   
   }
   
   //���� �� �����  � ����� ��� ������
   public boolean hasGround() {	   	  
	   return this.hasGround;	   
   }
   
   public void setHasGround(boolean hasGround) {
	   this.hasGround=hasGround;
   }
   
   public boolean isAlive() {
	   return this.isAlive;
   }
   
   public int getVY() {
	   return this.vy;
   }
   
   public int getSpeed() {
	   return this.speed;
   }
   
   
   public void die() {
	   vy=6;
	   isAlive=false;
	   Date date=new Date();
	   lastframe_time= date.getTime();
   }
   
   public void resurrect() {
	   setPos(new Vector2(50,250));	   
	   isAlive=true;
   }
   
   private void die_animate() {
	   Date date=new Date();
	   if (date.getTime()-lastframe_time>80) {
		   if (frame_numb==0 ) {
			   frame_numb++;
		   } else {frame_numb--;}
		   lastframe_time=date.getTime();
	   
	   }
   }
   
   
   
   public void dispose(){
	   img.dispose();
	   img2.dispose();
	   img_dead.dispose();
	   img_dead2.dispose();	   
   }
}
