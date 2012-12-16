package com.example.meteo;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.*;
import android.widget.SeekBar.OnSeekBarChangeListener;

import java.io.IOException;

import org.restlet.resource.ResourceException;

public class MainActivity extends Activity {
	
	static String lumint, lumext, tempint, tempext;
	static TextView lumINT, lumEXT, tempsINT, tempsEXT, valtemp, Afflight, shutter;
	static Integer light, volet;
	static SeekBar seek_light, seek_shutter;
	static Boolean meteo;
	static int newProgressValue = 10, currentProgress;
	static SharedPreferences sharedPreferences;
	static String Key_PROGRESS = "key_progress", PREFERENCE_PROGRESS = "preference_progress";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(PREFERENCE_PROGRESS,Context.MODE_PRIVATE);
        currentProgress = sharedPreferences.getInt(Key_PROGRESS, 10);
        setContentView(R.layout.activity_main);
        
        //affichage texte
        TextView test = (TextView)findViewById(R.id.test);
        test.setText(R.string.des);   	
        
        /*LUMINOSITE*/
	        //affichage valeur luminosit� int�rieure
	        lumINT = (TextView)findViewById(R.id.LightInt);
	        //affichage valeur luminosit� ext�rieure
	        lumEXT = (TextView)findViewById(R.id.LightExt);
	        fonction_lumiere(); 
	        
	    /*TEMPERATURE*/
		    //affichage valeur temp�rature int�rieure
		    tempsINT = (TextView)findViewById(R.id.tempint);
		    //affichage valeur temp�rature ext�rieure
	        tempsEXT = (TextView)findViewById(R.id.tempext);
		    fonction_temperature();
		    
		/*METEO*/
	        //affichage valeur m�t�o
	        valtemp = (TextView)findViewById(R.id.weather);
	        fonction_meteo();
	        
	    /*LUMIERE*/    
	        //affichage valeur lampe
	        Afflight = (TextView)findViewById(R.id.lamp);
	        //affichage sur la seekbar
	        seek_light = (SeekBar)findViewById(R.id.seekBar_light);
	        seek_light.setMax(100);
			seek_light.setProgress(currentProgress);
			Afflight.setText("Lampe : " + String.valueOf(currentProgress) + " %");
			seek_light.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
	
				public void onStopTrackingTouch(SeekBar seekBar) {
	
					newProgressValue = seekBar.getProgress();
					currentProgress = newProgressValue;
					SharedPreferences.Editor editor = sharedPreferences.edit();
					editor.putInt(Key_PROGRESS, newProgressValue);
					editor.commit();
	
				}
	
				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
	
				}
	
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					Afflight.setText(String.valueOf(seekBar.getProgress()));
					try {
						RESTLETinterface.setValue("light", "setLevel",
								seekBar.getProgress());
					} catch (ResourceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	
				}
			});
  
	        	        	                	        
	    /*VOLET*/    
	        //affichage position volet
	        shutter = (TextView)findViewById(R.id.volet);   
	        //affichage sur la seekbar
	        seek_shutter = (SeekBar)findViewById(R.id.seekBar_stores);
	        fonction_volet();
    }
    
    
    
    /*FONCTION GERANT LA LUMINOSITE*/
    public void fonction_lumiere(){
    	new Thread(new Runnable() {
	    	public void run() {
	    		while (true){
	    			//luminosit� int�rieure
	    			lumint = RESTLETinterface.getLumInt();
	    	        handler.sendEmptyMessage(0);
	    	        //luminosit� ext�rieure
	    	        lumext = RESTLETinterface.getLumExt();
	    	        handler.sendEmptyMessage(1);
	    		}
    	    }
    	 }).start();
    }
    
    
    
    /*FONCTION GERANT LA TEMPERATURE*/
    public void fonction_temperature(){
    	new Thread(new Runnable() {
	    	public void run() {
	    		while (true){
	    			//temp�rature int�rieure
		    		tempint = RESTLETinterface.getTempInt();
		    		handler.sendEmptyMessage(2);
		    		//temp�rature ext�rieure
		    		tempext = RESTLETinterface.getTempExt();
		    		handler.sendEmptyMessage(3);
	    		}
    	    }
    	}).start();
    }
    
    
 
    /*FONCTION GERANT LA METEO*/
    public void fonction_meteo(){
    	new Thread(new Runnable() {
	    	public void run() {
	    		while (true){
	    			meteo = RESTLETinterface.getWeather();
		    		handler.sendEmptyMessage(4);
	    		}
    	    }
    	}).start();
    }
    
    
    
    /*FONCTION GERANT LA LUMIERE*/
   /* public void fonction_light(){
    	new Thread(new Runnable() {
	    	public void run() {	
	    		while (true){
	    			light = RESTLETinterface.getLamp();
		    		handler.sendEmptyMessage(5);
	    		}	
	    	 }
    	}).start();
    }*/
    
    
    
    /*FONCTION GERANT LA POSITION DU VOLET*/
    public void fonction_volet(){
    	new Thread(new Runnable() {
	    	public void run() {
	    		while (true){
	    			volet = RESTLETinterface.getVolet()*50;
		    	    handler.sendEmptyMessage(6);
	    		}
    	    }
    	}).start();
    }
    
    
    
    /*FONCTION GERANT LE TEMPS REEL POUR ACTUALISER NOS VALEURS*/
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
        	//luminosit� int�rieure
	        if(msg.what == 0){
	            lumINT.setText(" Lum INT : "+lumint+" lx ");	
	        }
	        //luminosit� ext�rieure
	        else if(msg.what == 1){
	            lumEXT.setText(" Lum EXT : "+lumext+" lx ");	
	        }
	        //temp�rature int�rieure
	        else if(msg.what == 2){
	        	tempsINT.setText(" Temp INT : "+tempint+" �C ");    
	        }
	        //temp�rature ext�rieure
	        else if(msg.what == 3){
	        	tempsEXT.setText(" Temp EXT : "+tempext+" �C ");    
	        }
	        //m�t�o
	        else if(msg.what == 4){
	        	String affichage = " Meteo : ";
		        if (meteo == true) affichage=affichage+"soleil ";
		        else affichage=affichage+"pluie ";
		        valtemp.setText(affichage);
	        }
	        //lumi�re
	       /* else if(msg.what == 5){
	        	Afflight.setText(" Lampe : "+light+" % ");
	        	seek_light.setProgress(light);
	        } */
	        //volet
	        else if(msg.what == 6){
    	        shutter.setText(" Volet : "+volet+" % ");    
    	        seek_shutter.setProgress(volet);    
	        }
        }
    };
    
    

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void pageSettings(View view){
    	Intent intent=new Intent(this, Settings.class);
    	startActivity(intent);
    }
    
    public void Menu (View view) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
    
    public void OK (View view) throws ResourceException, IOException {
    	RESTLETinterface.setValue("shutter", "pullUp()", RESTLETinterface.getVolet());
    	Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
    
}
