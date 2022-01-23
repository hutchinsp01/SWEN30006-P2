package gameLog;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Paul Hutchins, Kian Dsouza, and Jade Siang in regard to SWEN30006 project 2
 */

public class GameLog implements IObserver{
	
	private static GameLog instance = null;

	private FileWriter gameLog;
	
	// private constructor for singleton
	private GameLog() {
		try {
			gameLog = new FileWriter("pasur.log");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// getInstance either instantiates or returns current instance of GameLog
	public static GameLog getInstance() {
		if (instance == null) {
			instance = new GameLog();
		}
		return instance;
	}
	
	@Override
	// when given a string, add string to current gameLog
	public void update(String data) {
		try {
			if (data.equals("ENDLOG")) {
				endLog();
			}
			else {
				gameLog.write(data + "\n");
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	
	// Close file writer
	private void endLog() {
		try {
			gameLog.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
