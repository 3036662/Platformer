package com.mygdx.game;

import java.util.Date;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;	
	private Rabbit rabbit;
	private Platforms platf;
	private Enemies enemies;
	private Background bg;
	private Score score; 
	private MainMenu menu;

	@Override
	public void create () {
		batch = new SpriteBatch();	
		score=new Score();	//������� ���� ����
		menu=new MainMenu(score); 
		bg= new Background(menu.isLevelNigh()); //���
	}

	@Override
	public void render () {
		this.update();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();		
		bg.render(batch);
		score.render(batch);
		if (!score.isGameOver()  && !score.isFirstLaunch() ) {			
			platf.render(batch);
			enemies.render(batch);		
			rabbit.render(batch);
		}
		else  {
			// ���� ���� �������� 
			// � ����� ��������� GAME OVER ������ ������� ��� ������ ������
			// �������� ����
			Date date=new Date();
			if (date.getTime()-score.getGameOverScreenTime()>1000 || score.isFirstLaunch()) {							
				menu.render();				
			}
		}
	
		batch.end();
	}
	
	// ���������� ��������
	public void update() {	
		 if (!score.isGameOver()  && !score.isFirstLaunch() ) {
			updMove();// ���������� ����������� �����
			//������ ��������� 150 �����, ������ �������
			 if(score.getPoints()%150==0 && score.getPoints()>0) {
					   score.incrPoints();
					   menu.setLevelNight(!menu.isLevelNigh());
					   bg.dispose();
					   bg=new Background(menu.isLevelNigh());
					   this.enemies.dispose();
					   this.enemies=new Enemies();
					   rabbit.resurrect();
			 }
			enemies.update();
			platf.update();
			rabbit.update();
		 }
		 
		 if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			 score.gameOver();
		 }
		
		
		  // ���� ������ ������ ���� "����� ����"
		  if (menu.isNewGame()) {
			  menu.setNewGame(false);
			  if (!score.isFirstLaunch()) {
				  rabbit.dispose();
			  	  platf.dispose();
			  	  enemies.dispose();
			  }
			  bg.dispose();
			  bg=new Background(menu.isLevelNigh());
			  platf=new Platforms();
			  enemies=new Enemies();			
			  rabbit=new Rabbit(menu.isRabbitBlue());
			  rabbit.resurrect();
			  score.NewGame();			
		  }		  
		  bg.update();   
		  score.update();					
	}
			
	private void updMove() {
		rabbit.setHasGround(false);		   
		 if (rabbit.isAlive()) {
		   // ��� ������ ���������
		   for (int i = 0;i<platf.glds.length;i++) {
			   // ��������� ���� � ����� ����� ��� ������
			   // ���� ���� ������� ������ � ���������
			   if (rabbit.getY()>platf.glds[i].getY() && rabbit.getY()<platf.glds[i].getY()+64 && rabbit.getVY()<=0) {
				   if (rabbit.getX()+32>platf.glds[i].getX() && rabbit.getX()+32<platf.glds[i].getX()+platf.glds[i].getLen()*64) {
					   rabbit.setHasGround(true);
				   }
			   }
			   // �������� ���������� ����� � ������� ��������
			   for (int j=0;j<platf.glds[i].carrots.length;j++) {
				   if (platf.glds[i].carrots[j].getVisible()) {
					   // ����� 
					   if( rabbit.getX()+32>platf.glds[i].carrots[j].getX() && rabbit.getX()+32<platf.glds[i].carrots[j].getX()+32) {
						   if( rabbit.getY()+32>platf.glds[i].carrots[j].getY() && rabbit.getY()+32<platf.glds[i].carrots[j].getY()+64) {
							   // �������� ��������
							   platf.glds[i].carrots[j].setVisible(false);
							   // ���� �������������
							   score.incrPoints();
							 //  score.newLifeScore++;
						   }
					   }
				   }
			   }
		   }		   
		   // ������� ����
		   if (rabbit.getY()<50) {
			   rabbit.setHasGround(true);
		   }   			
		 // �������� �����
		 if (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)) {
			    rabbit.run_left();    
		   }
		   // �������� ������
		   if (Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)) {
			   if (!rabbit.run_right()) {
				   platf.moveLeft(rabbit.getSpeed());
				   enemies.moveLeft(rabbit.getSpeed());
			   }
		   }
		   // ������
		   if (Gdx.input.isKeyPressed(Keys.SPACE)||Gdx.input.isKeyPressed(Keys.UP)
				   || Gdx.input.isKeyPressed(Keys.W)){
			   rabbit.jump();
		   }	   
		  // ������ ����� �� ��� ������
		   for (int i=0;i<enemies.wolfs.length;i++) {
			   if (rabbit.getX()+32>enemies.wolfs[i].getX() && rabbit.getX()+32<enemies.wolfs[i].getX()+256){
				   if (rabbit.getY()+32>enemies.wolfs[i].getY() && rabbit.getY()+32<enemies.wolfs[i].getY()+100){					  
					   rabbit.die();
					   score.minusLife();
					   
				   }
			   }
		   }
		} 		 
		// ���������� ����� ���� ���� � ������ �����
		if (!rabbit.isAlive() && rabbit.getY()>700) {	
		   if (score.getLifes()>0) {
			   this.enemies.dispose();
			   this.enemies=new Enemies();
				   rabbit.resurrect();	// �����������		   
			   }
			   else { score.gameOver(); }
		}
		
	}
		
	@Override
	public void dispose () {
		batch.dispose();
		menu.dispose();
		bg.dispose();
		if(!score.isFirstLaunch()) {
			enemies.dispose();
			platf.dispose();
			rabbit.dispose();
		}
		score.dispose();
	}
}
