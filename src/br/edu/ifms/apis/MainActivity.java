package br.edu.ifms.apis;

//importa��o das classes necess�rias para o funcionamento do aplicativo
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	//vari�veis que usaremos durante o processo
    private EditText edLatitude;
    private EditText edLongitude;
    private EditText edAcuracia;
    private EditText edAltitude;
    private EditText edBearing;
    private EditText edVelocidade;
    private EditText edTime;
    private Button btLocalizar;
    private MediaPlayer mediaPlayer;
    private Location locationAtual;
    
    private Button btComendo;
    private Button btAndando;
    private Button btDeitado;
    private Button btEmPe;
        
    public static final String ARQUIVO = "dadosApis.txt";

	@Override // M�todo onCreate, iniciada quando o aplicativo � aberto
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        setupElements();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	// M�todo usado para importar os elementos da classe R
    public void setupElements(){
        edLatitude = (EditText) findViewById(R.id.edLatitude);
        edLongitude = (EditText) findViewById(R.id.edLongitude);
        edAcuracia = (EditText) findViewById(R.id.edAcuracia);
        edAltitude = (EditText) findViewById(R.id.edAltitude);
        edBearing = (EditText) findViewById(R.id.edBearing);
        edVelocidade = (EditText) findViewById(R.id.edVelocidade);
        edTime = (EditText) findViewById(R.id.edTime);
        
        mediaPlayer = MediaPlayer.create(this, R.raw.clicksom);
        mediaPlayer.start();
                        
        btLocalizar = (Button) findViewById(R.id.btLocalizar);
        btLocalizar.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
                startGPS();
        }});
        
        btComendo = (Button) findViewById(R.id.btComendo);
        btComendo.setEnabled(false);
        btComendo.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
            	gravarDados("Comendo");
            	mediaPlayer.start();
            	
        }});
        
        btAndando = (Button) findViewById(R.id.btAndando);
        btAndando.setEnabled(false);
        btAndando.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
            	gravarDados("Andando");
            	mediaPlayer.start();
        }});
        
        btDeitado = (Button) findViewById(R.id.btDeitado);
        btDeitado.setEnabled(false);
        btDeitado.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
            	gravarDados("Deitado");
            	mediaPlayer.start();
        }});
        
        btEmPe = (Button) findViewById(R.id.btEmPe);
        btEmPe.setEnabled(false);
        btEmPe.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
            	gravarDados("EmPe");
            	mediaPlayer.start();
        }});
        
    }
    //M�todo que faz a leitura de fato dos valores recebidos do GPS
    public void startGPS(){
        LocationManager lManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        LocationListener lListener = new LocationListener() {
            public void onLocationChanged(Location locat) {

            	if(locationAtual == null) {
            		btComendo.setEnabled(true);
                	btAndando.setEnabled(true);
                	btDeitado.setEnabled(true);
                	btEmPe.setEnabled(true);
            	}
            	locationAtual = locat;
            	
                updateView(locat);
            }
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {}
        };
        lManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, lListener);
    }
 
    //  M�todo que faz a atualiza��o da tela para o usu�rio.
    public void updateView(Location locat){
    	    	
        Double latitude = locat.getLatitude();
        Double longitude = locat.getLongitude();
        
        edLatitude.setText(latitude.toString());
        edLongitude.setText(longitude.toString());
        
        Float acuracia = locat.getAccuracy();
        edAcuracia.setText(acuracia.toString());
                
        Double altitude = locat.getAltitude();
        edAltitude.setText(altitude.toString());
        
        Float bearing = locat.getBearing();
        edBearing.setText(bearing.toString());
                
        Float velocidade = locat.getSpeed();
        velocidade = (float) (velocidade * 3.6); // m/s para km/h
        edVelocidade.setText(velocidade.toString());
        
        Long time = locat.getTime();        
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zZ");
        edTime.setText(format1.format(new Date(time)));
    }
    
    
    public void gravarDados(String acao){
        
    	String conteudo= "";
        
        Long time = locationAtual.getTime();        
        conteudo += time.toString() +";";                
        
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zZ");
        conteudo += format1.format(new Date(time)) +";";
        
        conteudo += acao +";";
        		
        gravarArquivo(conteudo);
    }
    
    private void gravarArquivo(String conteudo) {
        try {
            try {
                File f = new File(Environment.getExternalStorageDirectory()+"/"+ARQUIVO);
                if(!f.exists()){
        			f.createNewFile();
        		}
                FileOutputStream out = new FileOutputStream(f, true);
                out.write(conteudo.getBytes());
                out.write('\n');
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}