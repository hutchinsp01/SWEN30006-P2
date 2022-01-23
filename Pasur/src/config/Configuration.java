package config;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import gameLog.GameLog;
import gameLog.IObservable;
import gameLog.IObserver;

/**
 * Modified by Paul Hutchins, Kian Dsouza, and Jade Siang in regard to SWEN30006 project 2
 */


public class Configuration implements IObservable
{
    private static final String SEED_KEY = "Seed";
    private static final String ANIMATE_KEY = "Animate";
    private static final String PLAYER0_KEY = "Player0";
    private static final String PLAYER1_KEY = "Player1";

    private static Configuration configuration = null;

    private int seed;
    private boolean animate;
    private String player0class;
    private String player1class;
    
    private ArrayList<IObserver> observers = new ArrayList<IObserver>();
    

    public static Configuration getInstance()
    {
        if(configuration == null)
        {
            configuration = new Configuration();

            try {
                configuration.setUp();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return configuration;
    }

    private void setUp() throws IOException 
    {
        // Default properties
    	registerObserver(GameLog.getInstance());

        // Read properties
        Properties properties = new Properties();
        FileReader inStream = null;
        try {
            inStream = new FileReader("pasur.properties");
            properties.load(inStream);
        } finally {
            if (inStream != null) {
                inStream.close();
            }
        }
        

        // Seed
        seed = Integer.parseInt(properties.getProperty(SEED_KEY));
        notifyObserver("#Seed: " + seed);
        //System.out.println("#Seed: " + seed);

        // Animate
        animate = Boolean.parseBoolean(properties.getProperty(ANIMATE_KEY));
        notifyObserver("#Animate: " + animate);
        //System.out.println("#Animate: " + animate);

        // Player0
        player0class = properties.getProperty(PLAYER0_KEY);
        notifyObserver("#Player0: " + player0class);
        //System.out.println("#Player0: " + player0class);

        // Player1
        player1class = properties.getProperty(PLAYER1_KEY);
        notifyObserver("#Player1: " + player1class);
        //System.out.println("#Player1: " + player1class);

    }

    public int getSeed()
    {
        return seed;
    }

    public boolean isAnimate()
    {
        return animate;
    }

    public String getPlayer0class()
    {
        return player0class;
    }

    public String getPlayer1class()
    {
        return player1class;
    }

    @Override
	public void registerObserver(IObserver observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(IObserver observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObserver(String data) {
		observers.forEach(o -> o.update(data));	
	}

	
    
    
}
