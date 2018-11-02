package br.edu.ifms.apis;

//importação das classes necessárias para o funcionamento do aplicativo
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	// variáveis que usaremos durante o processo
	private MediaPlayer mediaPlayer;
	private Location locationAtual;

	private ToggleButton togglePastej;
	private ToggleButton toggleOP;
	private ToggleButton toggleOD;
	private ToggleButton toggleRumPe;
	private ToggleButton toggleRumDeit;
	private ToggleButton toggleAcMonta;
	private ToggleButton toggleMontaOutra;
	private ToggleButton toggleInquiet;
	private ToggleButton toggleSol;
	private ToggleButton toggleSombra;
	
	private EditText editObs;
	

	private Button btColarVermelho;
	private Button btColarVerde;
	private Button btColarAmarelo;

	private Button btSalvar;

	public static final String ARQUIVO_VERMELHO = "_dadosApisVermelho.txt";
	public static final String ARQUIVO_VERDE = "_dadosApisVerde.txt";
	public static final String ARQUIVO_AMARELO = "_dadosApisAmarelo.txt";

	public static boolean ARQUIVO_VERMELHO_SELECIONADO = true;
	public static boolean ARQUIVO_VERDE_SELECIONADO = false;
	public static boolean ARQUIVO_AMARELO_SELECIONADO = false;

	private Timer timer;

	@Override
	// Método onCreate, iniciada quando o aplicativo é aberto
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
	public void setupElements() {
		togglePastej = (ToggleButton) findViewById(R.id.togglePastej);
		toggleOP = (ToggleButton) findViewById(R.id.togglePastej);
		toggleOD = (ToggleButton) findViewById(R.id.togglePastej);
		toggleRumPe = (ToggleButton) findViewById(R.id.togglePastej);
		toggleRumDeit = (ToggleButton) findViewById(R.id.togglePastej);
		toggleAcMonta = (ToggleButton) findViewById(R.id.togglePastej);
		toggleMontaOutra = (ToggleButton) findViewById(R.id.togglePastej);
		toggleInquiet = (ToggleButton) findViewById(R.id.togglePastej);
		
		toggleSol = (ToggleButton) findViewById(R.id.toggleSol);
		toggleSombra = (ToggleButton) findViewById(R.id.toggleSombra);
		
		//togglePastej.setChecked(true);
		
		editObs = (EditText) findViewById(R.id.editObs);

		mediaPlayer = MediaPlayer.create(this, R.raw.clicksom);
		mediaPlayer.start();

		btSalvar = (Button) findViewById(R.id.btSalvar);
		btSalvar.setEnabled(false);
		btSalvar.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				gravarDados();
				mediaPlayer.start();

			}
		});

		btColarVermelho = (Button) findViewById(R.id.btColarVermelho);
		btColarVermelho.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				btSalvar.setEnabled(true);

				ARQUIVO_VERMELHO_SELECIONADO = true;
				ARQUIVO_VERDE_SELECIONADO = false;
				ARQUIVO_AMARELO_SELECIONADO = false;

				btSalvar.setBackgroundResource(R.drawable.button_vermelho);
				mediaPlayer.start();
				
				System.out.println("Click rápido");
			}
		});		
		btColarVermelho.setOnLongClickListener(new OnLongClickListener (){
			@Override
			public boolean onLongClick(View v) {
				
				final EditText taskEditText;
				taskEditText = new EditText(v.getContext());
				taskEditText.setText(btColarVermelho.getText());
				AlertDialog dialog = new AlertDialog.Builder(v.getContext())
				        .setTitle("Identificação do Animal")
				        .setMessage("Este identificador será utilizado como nome do arquivo de saída.")
				        .setView(taskEditText)
				        .setPositiveButton("Alterar", new DialogInterface.OnClickListener() {
				            @Override
				            public void onClick(DialogInterface dialog, int which) {
				                String task = String.valueOf(taskEditText.getText());
				                btColarVermelho.setText(task);
				            }
				        })
				        .setNegativeButton("Cancel", null)
				        .create();
				dialog.show();
				
				return false;
			}
		});

		btColarVerde = (Button) findViewById(R.id.btColarVerde);
		btColarVerde.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				btSalvar.setEnabled(true);

				ARQUIVO_VERMELHO_SELECIONADO = false;
				ARQUIVO_VERDE_SELECIONADO = true;
				ARQUIVO_AMARELO_SELECIONADO = false;

				btSalvar.setBackgroundResource(R.drawable.button_verde);
				mediaPlayer.start();
			}
		});
		btColarVerde.setOnLongClickListener(new OnLongClickListener (){
			@Override
			public boolean onLongClick(View v) {
				
				final EditText taskEditText;
				taskEditText = new EditText(v.getContext());
				taskEditText.setText(btColarVerde.getText());
				AlertDialog dialog = new AlertDialog.Builder(v.getContext())
				        .setTitle("Identificação do Animal")
				        .setMessage("Este identificador será utilizado como nome do arquivo de saída.")
				        .setView(taskEditText)
				        .setPositiveButton("Alterar", new DialogInterface.OnClickListener() {
				            @Override
				            public void onClick(DialogInterface dialog, int which) {
				                String task = String.valueOf(taskEditText.getText());
				                btColarVerde.setText(task);
				            }
				        })
				        .setNegativeButton("Cancel", null)
				        .create();
				dialog.show();
				
				return false;
			}
		});

		btColarAmarelo = (Button) findViewById(R.id.btColarAmarelo);
		btColarAmarelo.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				btSalvar.setEnabled(true);

				ARQUIVO_VERMELHO_SELECIONADO = false;
				ARQUIVO_VERDE_SELECIONADO = false;
				ARQUIVO_AMARELO_SELECIONADO = true;

				btSalvar.setBackgroundResource(R.drawable.button_amarelo);
				mediaPlayer.start();
			}
		});
		btColarAmarelo.setOnLongClickListener(new OnLongClickListener (){
			@Override
			public boolean onLongClick(View v) {
				
				final EditText taskEditText;
				taskEditText = new EditText(v.getContext());
				taskEditText.setText(btColarAmarelo.getText());
				AlertDialog dialog = new AlertDialog.Builder(v.getContext())
				        .setTitle("Identificação do Animal")
				        .setMessage("Este identificador será utilizado como nome do arquivo de saída.")
				        .setView(taskEditText)
				        .setPositiveButton("Alterar", new DialogInterface.OnClickListener() {
				            @Override
				            public void onClick(DialogInterface dialog, int which) {
				                String task = String.valueOf(taskEditText.getText());
				                btColarAmarelo.setText(task);
				            }
				        })
				        .setNegativeButton("Cancel", null)
				        .create();
				dialog.show();
				
				return false;
			}
		});

		togglePastej = (ToggleButton) findViewById(R.id.togglePastej);
		togglePastej
				.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener() {
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked == true) {
							togglePastej.setChecked(true);
							toggleOP.setChecked(false);
							toggleOD.setChecked(false);
							toggleRumPe.setChecked(false);
							toggleRumDeit.setChecked(false);
						}/* else {
							if( comportamentoFisiologicoSelecionado() == null )
								togglePastej.setChecked(true);
						}*/
					}
				});

		toggleOP = (ToggleButton) findViewById(R.id.toggleOP);
		toggleOP.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked == true) {
					togglePastej.setChecked(false);
					toggleOP.setChecked(true);
					toggleOD.setChecked(false);
					toggleRumPe.setChecked(false);
					toggleRumDeit.setChecked(false);
				}
			}
		});

		toggleOD = (ToggleButton) findViewById(R.id.toggleOD);
		toggleOD.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked == true) {
					togglePastej.setChecked(false);
					toggleOP.setChecked(false);
					toggleOD.setChecked(true);
					toggleRumPe.setChecked(false);
					toggleRumDeit.setChecked(false);
				}
			}
		});

		toggleRumPe = (ToggleButton) findViewById(R.id.toggleRumPe);
		toggleRumPe
				.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener() {
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked == true) {
							togglePastej.setChecked(false);
							toggleOP.setChecked(false);
							toggleOD.setChecked(false);
							toggleRumPe.setChecked(true);
							toggleRumDeit.setChecked(false);
						}

						System.out.println("carai 2");
					}
				});

		toggleRumDeit = (ToggleButton) findViewById(R.id.toggleRumDeit);
		toggleRumDeit
				.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener() {
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked == true) {
							togglePastej.setChecked(false);
							toggleOP.setChecked(false);
							toggleOD.setChecked(false);
							toggleRumPe.setChecked(false);
							toggleRumDeit.setChecked(true);
						}

						System.out.println("carai 1");
					}
				});

		/*
		 * togglePastej toggleOP toggleOD toggleRumPe toggleRumDeit
		 * toggleAcMonta toggleMontaOutra toggleInquiet
		 */
		
		
		toggleAcMonta = (ToggleButton) findViewById(R.id.toggleAcMonta);
		toggleAcMonta
				.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener() {
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked == true) {
							toggleAcMonta.setChecked(true);
							toggleMontaOutra.setChecked(false);
							toggleInquiet.setChecked(false);							
						}
					}
				});
		
		
		toggleMontaOutra = (ToggleButton) findViewById(R.id.toggleMontaOutra);
		toggleMontaOutra
				.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener() {
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked == true) {
							toggleAcMonta.setChecked(false);
							toggleMontaOutra.setChecked(true);
							toggleInquiet.setChecked(false);							
						}
					}
				});
		
		
		toggleInquiet = (ToggleButton) findViewById(R.id.toggleInquiet);
		toggleInquiet
				.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener() {
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked == true) {
							toggleAcMonta.setChecked(false);
							toggleMontaOutra.setChecked(false);
							toggleInquiet.setChecked(true);							
						}
					}
				});
		
		
		toggleSol = (ToggleButton) findViewById(R.id.toggleSol);
		toggleSol
				.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener() {
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked == true) {
							toggleSol.setChecked(true);
							toggleSombra.setChecked(false);														
						}
					}
				});
		
		toggleSombra = (ToggleButton) findViewById(R.id.toggleSombra);
		toggleSombra
				.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener() {
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked == true) {
							toggleSol.setChecked(false);
							toggleSombra.setChecked(true);														
						}
					}
				});

	}
	
	protected String comportamentoFisiologicoSelecionado() {
		
		if( togglePastej.isChecked() )
			return "Pastej";
				
		if( toggleOP.isChecked() )
			return "OP";
		
		if( toggleOD.isChecked() )
			return "OD";
		
		if( toggleRumPe.isChecked() )
			return "RumPe";
		
		if( toggleRumDeit.isChecked() )
			return "RumDeit";
		
		return null;
	}

	// Método que faz a leitura de fato dos valores recebidos do GPS
	public void startGPS() {

		int permissionCheck = ContextCompat.checkSelfPermission(this,
				Manifest.permission.ACCESS_FINE_LOCATION);

		ActivityCompat.requestPermissions(this,
				new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, 1);

		Toast.makeText(
				getApplicationContext(),
				"GPS permission allows us to access location data. Please allow in App Settings for additional functionality.",
				Toast.LENGTH_LONG).show();

		LocationManager lManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		LocationListener lListener = new LocationListener() {
			public void onLocationChanged(Location locat) {
				if (locationAtual == null) {
					btColarVermelho.setEnabled(true);
					btColarVerde.setEnabled(true);
					mediaPlayer.start();

					timer = new Timer();
					timer.scheduleAtFixedRate(new RemindTask(), 60000, 60000);
				}
				locationAtual = locat;

				updateView(locat);
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}

			public void onProviderEnabled(String provider) {
			}

			public void onProviderDisabled(String provider) {
			}
		};
		lManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				lListener);
	}

	// Método que faz a atualização da tela para o usuário.
	public void updateView(Location locat) {

		Double latitude = locat.getLatitude();
		Double longitude = locat.getLongitude();
	}

	final static String[] PERMISSIONS_STORAGE = {
        Manifest.permission.VIBRATE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
	};
	public void gravarDados() {
		
	
		int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
	    if (permission != PackageManager.PERMISSION_GRANTED) {
	        // We don't have permission so prompt the user
	        ActivityCompat.requestPermissions(
	                this,
	                PERMISSIONS_STORAGE,
	                1
	        );
	    }
		
		//ActivityCompat.requestPermissions(arg0, arg1, arg2);
		
		String comportamentoFisiologico = "--- Comportamento Fisiológico ---\n";		
		if (togglePastej.isChecked() )
			comportamentoFisiologico += "Pastej\n";		
		if (toggleOP.isChecked() )
			comportamentoFisiologico += "OP\n";		
		if (toggleOD.isChecked() )
			comportamentoFisiologico += "OD\n";		
		if (toggleRumPe.isChecked() )
			comportamentoFisiologico += "RumPe\n";		
		if (toggleRumDeit.isChecked() )
			comportamentoFisiologico += "RumDeit\n";
		
		comportamentoFisiologico += "\n";
		
		String comportamentoReprodutivo = "--- Comportamento Reprodutivo ---\n";
		if (toggleAcMonta.isChecked() )
			comportamentoReprodutivo += "AcMonta\n";
		if (toggleMontaOutra.isChecked() )
			comportamentoReprodutivo += "MontaOutra\n";
		if (toggleInquiet.isChecked() )
			comportamentoReprodutivo += "Inquit\n";
		
		comportamentoReprodutivo += "\n";
		
		String utilizacaoSombra = "--- Utilização da Sombra ---\n";
		if (toggleSol.isChecked() )
			utilizacaoSombra += "Sol\n";		 
		
		if (toggleSombra.isChecked() )
			utilizacaoSombra += "Sombra\n";
		
		if( editObs.getText().toString().trim().length() > 0) {
			utilizacaoSombra += editObs.getText().toString().trim();			
		}
		
		new AlertDialog.Builder(this)
	    .setTitle("Confira os dados")
	    .setMessage(comportamentoFisiologico+comportamentoReprodutivo+utilizacaoSombra)
	    .setPositiveButton("sim",
	        new DialogInterface.OnClickListener() {
	        @Override
	        public void onClick(DialogInterface dialogInterface, int i) {
	        	String conteudo = "";	        	
	    		if (togglePastej.isChecked() )
	    			conteudo += "Sim,";		
	    		else
	    			conteudo += "Nao,";
	    		
	    		if (toggleOP.isChecked() )
	    			conteudo += "Sim,";		
	    		else
	    			conteudo += "Nao,";
	    		
	    		if (toggleOD.isChecked() )
	    			conteudo += "Sim,";		
	    		else
	    			conteudo += "Nao,";
	    		
	    		if (toggleRumPe.isChecked() )
	    			conteudo += "Sim,";		
	    		else
	    			conteudo += "Nao,";
	    		
	    		if (toggleRumDeit.isChecked() )
	    			conteudo += "Sim,";		
	    		else
	    			conteudo += "Nao,";
	    		
	    		
	    		if (toggleAcMonta.isChecked() )
	    			conteudo += "Sim,";		
	    		else
	    			conteudo += "Nao,";
	    		
	    		if (toggleMontaOutra.isChecked() )
	    			conteudo += "Sim,";		
	    		else
	    			conteudo += "Nao,";
	    		
	    		if (toggleInquiet.isChecked() )
	    			conteudo += "Sim,";		
	    		else
	    			conteudo += "Nao,";
	    		
	    		if (toggleSol.isChecked() )
	    			conteudo += "Sim,";		
	    		else
	    			conteudo += "Nao,";
	    		
	    		if (toggleSombra.isChecked() )
	    			conteudo += "Sim,";		
	    		else
	    			conteudo += "Nao,";
	    		
	    		
	    		if( editObs.getText().toString().trim().length() > 0) {
	    			conteudo += editObs.getText().toString().trim();			
	    		}
	        	
	    		gravarArquivo(conteudo);
	        }
	    })
	    .setNegativeButton("não", null)
	    .show();
		

		
	}

	private void gravarArquivo(String conteudo) {
		try {
			try {
				File f;
				if (ARQUIVO_VERMELHO_SELECIONADO == true) {
					f = new File(Environment.getExternalStorageDirectory()
							+ "/" + btColarVermelho.getText().toString().trim()+"_"+ARQUIVO_VERMELHO);
					
				} else if (ARQUIVO_VERDE_SELECIONADO == true) {
					f = new File(Environment.getExternalStorageDirectory()
							+ "/" + btColarVermelho.getText().toString().trim()+"_"+ARQUIVO_VERDE);
					
				} else {
					f = new File(Environment.getExternalStorageDirectory()
							+ "/" + btColarVermelho.getText().toString().trim()+"_"+ARQUIVO_AMARELO);
				}

				if (!f.exists()) {
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

	private void vibrar() {
		Vibrator rr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		long milliseconds = 1000;// '30' é o tempo em milissegundos, é
									// basicamente o tempo de duração da
									// vibração. portanto, quanto maior este
									// numero, mais tempo de vibração você irá
									// ter
		rr.vibrate(milliseconds);
	}
}
