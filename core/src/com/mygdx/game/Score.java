package com.mygdx.game;



import java.util.Date;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Score implements Layer{
	private int lifes=3; // кол жизней
	private int points=0; // кол очков
	private int newLifeScore=0;
	private BitmapFont bmf,bmfGameOver;// шрифт
	private Texture img; 
	private boolean gameOver,gameOverScreen,firstLaunch;
	private long gameOverScreenTime;
	
	public Score() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(new FileHandle("Calibri.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 40;
		parameter.color=Color.WHITE;
		bmf= generator.generateFont(parameter);
		parameter.color=Color.RED;
		parameter.size = 100;
		bmfGameOver= generator.generateFont(parameter);
		generator.dispose();
		img=new Texture("Score.png");
		gameOver=false;
		firstLaunch=true;
	}
	
	public void render(SpriteBatch batch) {		
		batch.draw(img,50,530);
		bmf.draw(batch, Integer.toString(points), 230, 570);
		bmf.draw(batch, Integer.toString(lifes), 140, 570);
		if (gameOver) {
			Date date=new Date();
			// если экран уже отрисован и  не прошла секунда то отрисововать
			if (gameOverScreen && date.getTime()-gameOverScreenTime<1000 &&!firstLaunch) {
				bmfGameOver.draw(batch, "GAME OVER", 150, 500);
			}
			// если не отрисован еще GAME OVER
			// запоменить время первой отрисовки GAME OVER
			if (!gameOverScreen) {
				gameOverScreenTime=date.getTime();
				gameOverScreen=true;
			}
			
		}
	}
	
	public void update() {
		if 	(newLifeScore==100) {
			lifes++;
			newLifeScore=0;
		}
	}
	
	// конец игры
	public void gameOver(){
		this.gameOverScreen=false;
		this.gameOver=true;
	}
	
	// новая игра
	public void NewGame() {
		this.gameOver=false;
		lifes=3;
		points=0;
		newLifeScore=0;
		firstLaunch=false;
		
	}
	
	public void dispose() {
		bmf.dispose();
		bmfGameOver.dispose();
		img.dispose();		
	}
	
	// вернет количество жизней
	public int getLifes() {
		return this.lifes;
	}
	
	// минус одна жизнь
	public void minusLife() {
		this.lifes--;
	}
	
	// вернет количество очков 
	public int getPoints() {
		return this.points;
	}
	
	// увеличиь кол-во очков на единицу
	public void incrPoints() {
		this.points++;
		this.newLifeScore++;
	}
	
	// истина, если конец игры
	public boolean isGameOver() {
		return this.gameOver;
	}
	
	// истина, если первый запуск в этом окне
	public boolean isFirstLaunch() {
		return this.firstLaunch;
	}
	
	// вернет время начала показа надписи GAME OVER
	public long getGameOverScreenTime() {
		return this.gameOverScreenTime;
	}
	
}
