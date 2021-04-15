package com.mygdx.game;

import java.util.Map;
import java.util.TreeMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MainMenu {
	private Stage stage;
	private Skin skin;
	private Texture arrBack;
	private Score score;
	private boolean newGame;
	private boolean  rabbit_blue=false;
	private boolean isLevelNigh=false;

	// конструктор
	public MainMenu(Score score) {
		this.newGame=false;
		this.score=score;
		stage = new Stage();
		Gdx.input.setInputProcessor(stage); // устанавл. обработчик ввода, вызывается перед рендер каждый кадр
		skin = new Skin(Gdx.files.internal("comic-ui.json")); // skin хранит ресурсы для UI
		arrBack= new Texture("ArrowBack.png");
		mainMenuLaunch();
		
	}
	
	public void render () {
		stage.act(Gdx.graphics.getDeltaTime()); // вызывает act для всех дочерних актеров
		stage.draw(); 
	}
	
	public void dispose() {
		stage.dispose();
		skin.dispose();
		arrBack.dispose();
	}
	
	//  запуск главного меню
	private void mainMenuLaunch() {
		stage.clear();
		final TextButton newGameBtn = new TextButton("Игра", skin);
		newGameBtn.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {						
				newGame=true;
			}
		});		
		final TextButton exitBtn = new TextButton("Выход", skin);
		exitBtn.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.exit(0);
			}
		});		
		
		final TextButton aboutBtn = new TextButton("О программе", skin);
		aboutBtn.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				aboutMenu();
			}
		});	
		
		final TextButton recsBtn = new TextButton("Рекорды", skin);
		recsBtn.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				showRecords();
			}
		});	
		
		newGameBtn.setPosition(200, 400);
		exitBtn.setPosition(200, 320);
		aboutBtn.setPosition(200, 240);
		recsBtn.setPosition(200, 160);
		
		stage.addActor(newGameBtn);
		stage.addActor(exitBtn);
		stage.addActor(aboutBtn);
		stage.addActor(recsBtn);
		
		//Radio переключать цвета зайта
		CheckBox[] radioArr1= new CheckBox[2]; 
		radioArr1[0]=new CheckBox("Синий",skin);
		radioArr1[0].addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
			   CheckBox box=(CheckBox)actor;
			   if (box.isChecked() ) {
						rabbit_blue=true;
			   }
			   else {
				   rabbit_blue=false;
			   }
			}
		});	
		radioArr1[1]=new CheckBox("Красный",skin);		
		@SuppressWarnings("unused")
		ButtonGroup<CheckBox> colorRadioGroup=new  ButtonGroup<CheckBox>(radioArr1);		
		radioArr1[0].setPosition(500, 400);
		radioArr1[1].setPosition(500, 360);			
		stage.addActor(radioArr1[0]);
		stage.addActor(radioArr1[1]);
		Label lbl=new Label("Цвет зайца:",skin);
		lbl.setPosition(500,440);
		stage.addActor(lbl);
		
		// уровень
		CheckBox[] radioArr2= new CheckBox[2];
		radioArr2[0]=new CheckBox("День",skin);
		radioArr2[1]=new CheckBox("Ночь",skin);
		radioArr2[1].addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
			   CheckBox box=(CheckBox)actor;
			   if (box.isChecked() ) {
				   isLevelNigh=true;
			   }
			   else {
				   isLevelNigh=false;
			   }
			}
		});	
		
		@SuppressWarnings("unused")
		ButtonGroup<CheckBox> levelRadioGroup=new  ButtonGroup<CheckBox>(radioArr2);
		radioArr2[0].setPosition(500, 280);
		radioArr2[1].setPosition(500, 240);	
		Label lbl2=new Label("Уровень:",skin);
		lbl2.setPosition(500,320);
		stage.addActor(radioArr2[0]);
		stage.addActor(radioArr2[1]);
		stage.addActor(lbl2);
		
		final TextField txtName=new TextField("Имя",skin);
		txtName.setPosition(500, 70);
		
		 // сохранить результат
		final TextButton saveBtn = new TextButton("Сохранить", skin);
		saveBtn.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				SaveRecords sv=new SaveRecords();
				sv.NewRec(txtName.getText(),score.getPoints());
			}
		});	
		
		saveBtn.setPosition(200,70);
		stage.addActor(txtName);
		stage.addActor(saveBtn);
	}
	
	private void aboutMenu(){
		stage.clear();
		final TextButton helpBtn = new TextButton("Справка", skin);
		helpBtn.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				helpScreen(1);
			}
		});	
		final TextButton devInfoBtn = new TextButton("О разработчике", skin);
		devInfoBtn.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				helpScreen(2);
			}
		});
		helpBtn.setPosition(320,300);
		devInfoBtn.setPosition(280, 230);		
		stage.addActor(helpBtn);
		stage.addActor(devInfoBtn);
		ImageButton backBtn= new ImageButton(new TextureRegionDrawable(arrBack));
		backBtn.setPosition(350, 150);
		backBtn.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				mainMenuLaunch();
			}
		});	
		stage.addActor(backBtn);	
	}
	
	// показать экран помощи
	private void helpScreen(int type) {
		stage.clear();
		String str1="Управление- кнопки WASD или LEFT,RIGHT,UP"
				+ "\n Прыжок - пробел илл UP или W\nНужно собрать 150 морковок чтобы пройти уровень\n"
				+ "100 собранных морковок добавляют жизнь.";
		String str2="Студент ТУСУР 2021";
		if(type==2) {
			str1=str2;
		}
		Label helpTxt=new Label(str1,skin);
		helpTxt.setPosition(200, 300);
		ImageButton backBtn= new ImageButton(new TextureRegionDrawable(arrBack));
		backBtn.setPosition(350, 150);
		backBtn.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				aboutMenu();
			}
		});	
		stage.addActor(helpTxt);
		stage.addActor(backBtn);
	}
	
	// показать рекорды
	private void showRecords() {
		stage.clear();
		Table table=new Table(skin);
		table.setFillParent(true);
		SaveRecords sv=new SaveRecords();
		TreeMap<Integer, String> map=sv.getRecs();
		if (!map.isEmpty()) {
			int i=0;
			Map.Entry<Integer, String> e=map.lastEntry();
			do {
			table.add(e.getKey().toString()).left().space(10);
			table.add(e.getValue().toString()).left().space(5);
			table.row();
			e=map.lowerEntry((int)(e.getKey()));
			i++;
			}
			while(e!=null && i<15);
		}
		ImageButton backBtn= new ImageButton(new TextureRegionDrawable(arrBack));
		backBtn.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				mainMenuLaunch();
			}
		});
		
		table.add(backBtn);		
		stage.addActor(table);		
	}
	// вернет истину если выбаран пункт "новая игра"
	public boolean isNewGame(){
		return this.newGame;
	}
	
	// установить флаг "Новая игра" в положение истина
	public void setNewGame(boolean ng) {
		this.newGame=ng;
	}
	// вернет истину , если выбран синий цвет героя
	public boolean isRabbitBlue() {
		return this.rabbit_blue;
	}
	
	// вернет истину, если выбран уровень "ночь"
	public boolean isLevelNigh() {
		return this.isLevelNigh;
	}
	
	// установит флаг выбранного уровня
	public void setLevelNight(boolean ln) {
		this.isLevelNigh=ln;
	}
	
}
