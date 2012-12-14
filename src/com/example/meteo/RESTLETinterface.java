package com.example.meteo;

import java.io.IOException;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

public class RESTLETinterface {

	/*LUMINOSITE*/
		//Récupération valeur luminosité intérieure
		public static String getLumInt() {
			ClientResource LumInt = new ClientResource("http://10.0.2.2:9000/intLight?method=getBrightness");
			String renvoiLum=null;
			try {
				renvoiLum = LumInt.get().getText();
			}
			catch (ResourceException e){
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			LumInt.release();
			return renvoiLum;
		}
		
		//Récupération valeur luminosité extérieure
		public static String getLumExt() {
			ClientResource LumExt = new ClientResource("http://10.0.2.2:9000/extLight?method=getBrightness");
			String renvoiLum=null;
			try {
				renvoiLum = LumExt.get().getText();
			}
			catch (ResourceException e){
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			LumExt.release();
			return renvoiLum;
		}
	
		
	/*TEMPERATURE*/	
		//Récupération valeur température intérieure
		public static String getTempInt() {
			ClientResource TempInt = new ClientResource("http://10.0.2.2:9000/intTemp?method=getTemperature");
			String renvoiTemp=null;
			try {
				renvoiTemp = TempInt.get().getText();
			}
			catch (ResourceException e){
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			TempInt.release();
			return renvoiTemp;
		}	
		
		//Récupération valeur température extérieure
		public static String getTempExt() {
			ClientResource TempExt = new ClientResource("http://10.0.2.2:9000/extTemp?method=getTemperature");
			String renvoiTemp=null;
			try {
				renvoiTemp = TempExt.get().getText();
			}
			catch (ResourceException e){
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			TempExt.release();
			return renvoiTemp;
		}
		
		
	/*METEO*/	
		//Récupération valeur météo
		public static Boolean getWeather() {
			ClientResource Temps = new ClientResource("http://10.0.2.2:9000/extWeather?method=isSunny");
			Boolean valTemps = null;
			try {
				valTemps = Boolean.parseBoolean(Temps.get().getText());
			}
			catch (ResourceException e){
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			Temps.release();
			return valTemps;
		}	
		
		
	/*LUMIERE*/	
		//Récupération valeur lumière	
		public static Integer getLamp() {
			ClientResource Lamp = new ClientResource("http://10.0.2.2:9000/light?method=getLevel");
			Integer renvoiLamp=null;
			try {
				renvoiLamp = Integer.parseInt(Lamp.get().getText());
			}
			catch (ResourceException e){
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			Lamp.release();
			return renvoiLamp;
		}
	
		
	/*VOLET*/	
		//Récupération position volet	
		public static Integer getVolet() {
			ClientResource Volet = new ClientResource("http://10.0.2.2:9000/shutter?method=getState");
			Integer renvoiVolet=null;
			try {
				renvoiVolet = Integer.parseInt(Volet.get().getText());
			}
			catch (ResourceException e){
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			Volet.release();
			return renvoiVolet;
		}
		
	public static void setValue(String device, String method, int value) throws ResourceException, IOException {
		ClientResource resource = new ClientResource("http://10.0.2.2:9000/"+device+"?method="+method+"&level="+value);
		resource.get();
		resource.release();
	}
	
}
