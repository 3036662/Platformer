package com.mygdx.game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.TreeMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class SaveRecords {
	private String fileName;
	private TreeMap<Integer, String> map;
	private FileHandle file;

	@SuppressWarnings("unchecked")
	public SaveRecords() {
		this.fileName="records.dat";	
		 map=new TreeMap<Integer, String>();
		if (Gdx.files.isLocalStorageAvailable()) {		
			//если файл не существует - создать и записать пустой TreeMap
			 file =Gdx.files.local(fileName);			 
			 if (!file.exists()) {
				try {
					OutputStream os=file.write(false);
					ObjectOutputStream oos = new ObjectOutputStream(os);					
					 oos.writeObject(map);
					 oos.close();
					 os.close();
					 (file.write(false)).close();
				} catch (IOException e) {					
					 System.out.println(e.getMessage());
				}				 
				
				// System.out.print("OK");
				 
			 }
			 // файл есть, значить считать из него TreeMap
			 else {
				try {
					ObjectInputStream ois=new ObjectInputStream(file.read());					
				     map =(TreeMap<Integer,String>)ois.readObject();
					 System.out.print("OK");
					 ois.close();
					 file.read().close();
				} catch (IOException | ClassNotFoundException e) {
					System.out.println(e.getMessage());
				}
			 } 
		}
	}
	
	public void NewRec(String name,Integer score){
		this.map.put(score,name);
		 if (file.exists()) {
			 try {
				OutputStream os=file.write(false); 
				ObjectOutputStream oos = new ObjectOutputStream(os);
				oos.writeObject(map);
				oos.close();
				os.close();
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		 }
	}
	
	
	public TreeMap<Integer, String> getRecs() {
		return this.map;
	}
	
}
