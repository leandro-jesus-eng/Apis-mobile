package br.edu.ifms.apis;

//importação das classes necessárias para o funcionamento do aplicativo
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	//variáveis que usaremos durante o processo
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
    private Button btDeitadoRuminando;
    private Button btEmPeRuminando;
    
    private Button btColarLaranja;
    private Button btColarVerde;
        
    public static final String ARQUIVO_LARANJA = "dadosApisLaranja.txt";
    public static final String ARQUIVO_VERDE = "dadosApisVerde.txt";
    
    public static boolean ARQUIVO_VERDE_SELECIONADO = false;
    
    
    private Timer timer;
    
    

	@Override // Método onCreate, iniciada quando o aplicativo é aberto
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        setupElements();
	}
	
	class RemindTask extends TimerTask {
        public void run() {
            vibrar();
        }
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	// Método usado para importar os elementos da classe R
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
            	mediaPlayer.start();
        }});
        
        btComendo = (Button) findViewById(R.id.btComendo);
        btComendo.setEnabled(false);
        btComendo.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
            	gravarDados("Pastando");
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
        
        btDeitadoRuminando = (Button) findViewById(R.id.btDeitadoRuminando);
        btDeitadoRuminando.setEnabled(false);
        btDeitadoRuminando.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
            	gravarDados("Deitado-Ruminando");
            	mediaPlayer.start();
        }});
        
        btEmPeRuminando = (Button) findViewById(R.id.btEmPeRuminando);
        btEmPeRuminando.setEnabled(false);
        btEmPeRuminando.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
            	gravarDados("EmPe-Ruminando");
            	mediaPlayer.start();
        }});
        
        btColarLaranja = (Button) findViewById(R.id.btColarLaranja);
        btColarLaranja.setEnabled(false);
        btColarLaranja.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
            	ARQUIVO_VERDE_SELECIONADO = false;
            	
            	btComendo.setEnabled(true);
            	btAndando.setEnabled(true);
            	btDeitado.setEnabled(true);
            	btEmPe.setEnabled(true);
            	btDeitadoRuminando.setEnabled(true);
            	btEmPeRuminando.setEnabled(true);
            	
            	btComendo.setTextColor(Color.parseColor("#FF8C00"));
            	btAndando.setTextColor(Color.parseColor("#FF8C00"));
            	btDeitado.setTextColor(Color.parseColor("#FF8C00"));
            	btEmPe.setTextColor(Color.parseColor("#FF8C00"));
            	btDeitadoRuminando.setTextColor(Color.parseColor("#FF8C00"));
            	btEmPeRuminando.setTextColor(Color.parseColor("#FF8C00"));
            	
            	mediaPlayer.start();
        }});
        
        btColarVerde = (Button) findViewById(R.id.btColarVerde);
        btColarVerde.setEnabled(false);
        btColarVerde.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
            	ARQUIVO_VERDE_SELECIONADO = true;
            	
            	btComendo.setEnabled(true);
            	btAndando.setEnabled(true);
            	btDeitado.setEnabled(true);
            	btEmPe.setEnabled(true);
            	btDeitadoRuminando.setEnabled(true);
            	btEmPeRuminando.setEnabled(true);
            	
            	btComendo.setTextColor(Color.parseColor("#008B00"));
            	btAndando.setTextColor(Color.parseColor("#008B00"));
            	btDeitado.setTextColor(Color.parseColor("#008B00"));
            	btEmPe.setTextColor(Color.parseColor("#008B00"));
            	btDeitadoRuminando.setTextColor(Color.parseColor("#008B00"));
            	btEmPeRuminando.setTextColor(Color.parseColor("#008B00"));
            	
            	mediaPlayer.start();
        }});
        
    }
    //Método que faz a leitura de fato dos valores recebidos do GPS
    public void startGPS(){
        LocationManager lManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        LocationListener lListener = new LocationListener() {
            public void onLocationChanged(Location locat) {

            	if(locationAtual == null) {                	
                	btColarLaranja.setEnabled(true);
                	btColarVerde.setEnabled(true);
                	mediaPlayer.start();
                	
                	timer = new Timer();
            		timer.scheduleAtFixedRate(new RemindTask(), 60000, 60000);
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
 
    //  Método que faz a atualização da tela para o usuário.
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
            	File f;
            	if(ARQUIVO_VERDE_SELECIONADO == true) {
            		f = new File(Environment.getExternalStorageDirectory()+"/"+ARQUIVO_VERDE);
            	} else {
            		f = new File(Environment.getExternalStorageDirectory()+"/"+ARQUIVO_LARANJA);
            	}
            	
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
    
    private void vibrar()
    {
        Vibrator rr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long milliseconds = 1000;//'30' é o tempo em milissegundos, é basicamente o tempo de duração da vibração. portanto, quanto maior este numero, mais tempo de vibração você irá ter
        rr.vibrate(milliseconds); 
    }
}	
