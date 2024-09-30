package com.projeto;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog.ModalityType;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



public class Screen extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textCod;
	private JButton btnYPositivo;
	private JButton btnZNegativo;
	private JButton btnZPositivo;
	private JButton btnXNegativo;
	private JButton btnXPositivo;
	private JButton btnDel;
	private JButton btnHomePosition;
	private JButton btnApaga;
	private JComboBox cmbBaudRate;
	private JButton btnDesconectar;
	static boolean incrementandoXp = false;
	static boolean incrementandoXn = false;
	static boolean incrementandoYn = false;
	static boolean incrementandoYp = false;
	static boolean incrementandoZn = false;
	static boolean incrementandoZp = false;
	static JLabel lblX = new JLabel("0.000");
	static JLabel lblY = new JLabel("0.000");
	static JLabel lblZ = new JLabel("0.000");
	boolean escrita = true;


	 
	// flag para sinalizar status da porta
	 boolean conectado = false;
	 
	// String para preenchimento do Baud Rate
	String[] baudRate = { "110", "300", "600", "1200", "2400", "4800", "9600", "14400", "19200", "38400", "57600",
			 "115200", "128000", "256000" };
	
	// cria objeto para comunica��o serial
	Serial com = new Serial();


	

	 
	 
	 
	 static float posX;
	 static float posY;
	 static float posZ;
	 
	 //cria objeto para usar função de outra classe
	 Funcoes ver = new Funcoes();
	//cria objeto de comunicação com a classe de execução de incremento
	 Movimento mov = new Movimento();
			

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		 
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Screen frame = new Screen();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			} 
		});
	}
	
	
	public Screen() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(conectado) {
					com.fechaCom();
				}
				
			}
		});
	        initialize();
	    }

	/**
	 * Create the frame.
	 */
	 private void initialize() {

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 837, 571);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		//JScrollPane scroolPane = new JScrollPane(textComandList);
		
		textCod = new JTextField();
		textCod.setBounds(249, 483, 356, 39);
		textCod.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(textCod);
		textCod.setColumns(10);
		
		JButton btnEnter = new JButton("ENTER");
		btnEnter.setBounds(609, 483, 100, 36);
		
		
		btnEnter.setFont(new Font("Arial Narrow", Font.PLAIN, 20));
		contentPane.add(btnEnter);
		
		btnDel = new JButton("DEL");
		btnDel.setBounds(714, 483, 89, 37);
		
		btnDel.setFont(new Font("Arial Narrow", Font.PLAIN, 20));
		contentPane.add(btnDel);
		
		btnApaga = new JButton("Apagar");
		
		btnApaga.setBounds(714, 452, 89, 23);
		contentPane.add(btnApaga);
		
		JTextArea textComandList = new JTextArea();
		JScrollPane textComandListS = new JScrollPane ( textComandList );
		contentPane.add(textComandListS);
		textComandListS.setBounds(249, 112, 564, 329);
		
		JPanel pnlComunicacao = new JPanel();
		pnlComunicacao.setBounds(21, 11, 782, 90);
		pnlComunicacao.setBackground(new Color(192, 192, 192));
		pnlComunicacao.setBorder(UIManager.getBorder("InternalFrame.border"));
		contentPane.add(pnlComunicacao);
		pnlComunicacao.setLayout(null);
		
		JComboBox cmbPortas = new JComboBox(com.listaCom());
		cmbPortas.setBounds(10, 44, 206, 22);
		pnlComunicacao.add(cmbPortas);
		
		cmbBaudRate = new JComboBox(baudRate);
		cmbBaudRate.setSelectedIndex(11);
		cmbBaudRate.setBounds(246, 44, 232, 22);
		pnlComunicacao.add(cmbBaudRate);
		
		JButton btnConectar = new JButton("CONECTAR");
		btnConectar.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		btnConectar.setBackground(new Color(128, 128, 128));
		btnConectar.setBounds(530, 44, 106, 23);
		pnlComunicacao.add(btnConectar);
		
		btnDesconectar = new JButton("DESCONECTAR");
		btnDesconectar.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnDesconectar.setEnabled(false);
		btnDesconectar.setBackground(new Color(128, 128, 128));
		btnDesconectar.setBounds(646, 44, 126, 23);
		pnlComunicacao.add(btnDesconectar);
		
		JPanel pnlButtons = new JPanel();
		pnlButtons.setBorder(UIManager.getBorder("Menu.border"));
		pnlButtons.setBackground(new Color(192, 192, 192));
		pnlButtons.setBounds(21, 112, 224, 422);
		contentPane.add(pnlButtons);
		pnlButtons.setLayout(null);
		
		
		btnXNegativo = new JButton("X-");
		btnXNegativo.setBounds(10, 36, 99, 59);
		pnlButtons.add(btnXNegativo);
		btnXNegativo.setFont(new Font("Arial Black", Font.PLAIN, 34));
		
		JButton btnStart = new JButton("START");
		
		btnStart.setBounds(123, 356, 98, 56);
		pnlButtons.add(btnStart);
		btnStart.setFont(new Font("Arial Black", Font.BOLD, 15));
		
		btnHomePosition = new JButton("Home");
		
		btnHomePosition.setBounds(10, 354, 99, 56);
		pnlButtons.add(btnHomePosition);
		btnHomePosition.setFont(new Font("Arial Black", Font.PLAIN, 20));
		
		btnZNegativo = new JButton("Z-");
		btnZNegativo.setBounds(11, 229, 98, 61);
		pnlButtons.add(btnZNegativo);
		btnZNegativo.setFont(new Font("Arial Black", Font.PLAIN, 34));
		
		btnXPositivo = new JButton("X+");
		btnXPositivo.setBounds(123, 35, 98, 61);
		pnlButtons.add(btnXPositivo);
		btnXPositivo.setFont(new Font("Arial Black", Font.PLAIN, 34));
		
		JButton btnYNegativo = new JButton("Y-");
		btnYNegativo.setBounds(11, 132, 98, 61);
		pnlButtons.add(btnYNegativo);
		
		btnYNegativo.setFont(new Font("Arial Black", Font.PLAIN, 34));
		
		btnYPositivo = new JButton("Y+");
		btnYPositivo.setBounds(123, 132, 98, 61);
		pnlButtons.add(btnYPositivo);
		btnYPositivo.setFont(new Font("Arial Black", Font.PLAIN, 34));
		
		lblY.setBounds(123, 107, 79, 14);
		pnlButtons.add(lblY);
		lblY.setHorizontalAlignment(SwingConstants.LEFT);
		lblY.setForeground(new Color(255, 255, 255));
		lblY.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JLabel lblX_2 = new JLabel("Y");
		lblX_2.setBounds(30, 106, 79, 14);
		pnlButtons.add(lblX_2);
		lblX_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblX_2.setForeground(Color.WHITE);
		lblX_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblX.setBounds(123, 11, 79, 14);
		pnlButtons.add(lblX);
		
		
		lblX.setHorizontalAlignment(SwingConstants.LEFT);
		lblX.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblX.setForeground(new Color(255, 255, 255));
		
		JLabel lblX_1 = new JLabel("X");
		lblX_1.setBounds(30, 11, 79, 14);
		pnlButtons.add(lblX_1);
		lblX_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblX_1.setForeground(Color.WHITE);
		lblX_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		btnZPositivo = new JButton("Z+");
		btnZPositivo.setBounds(123, 229, 98, 61);
		pnlButtons.add(btnZPositivo);
		btnZPositivo.setFont(new Font("Arial Black", Font.PLAIN, 34));
		
		lblZ.setBounds(123, 204, 79, 14);
		pnlButtons.add(lblZ);
		lblZ.setHorizontalAlignment(SwingConstants.LEFT);
		lblZ.setForeground(Color.WHITE);
		lblZ.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JLabel lblZ_1 = new JLabel("Z");
		lblZ_1.setBounds(30, 204, 79, 14);
		pnlButtons.add(lblZ_1);
		lblZ_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblZ_1.setForeground(Color.WHITE);
		lblZ_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		// Comando de movimento negativo eixo X
		btnXNegativo.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(conectado) {
					if(escrita) {
						escrita = false;
						incrementandoXn = true;
					}
				}
				
				
			}
			public void mouseReleased(MouseEvent e ) {
				if(conectado) {
					if(!escrita) {
					escrita = true;
					incrementandoXn = false;
					}
				}
		}});
		
		//Comando de movimento positivo do eixo X
		btnXPositivo.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(conectado) {
					if(escrita) {
						escrita = false;
						incrementandoXp = true;
					}
				}
				
				
			}
			public void mouseReleased(MouseEvent e ) {
				if(conectado) {
					if(!escrita) {
						escrita = true;
						incrementandoXp = false;
					}	
				}
			}
		});
		
		
		//Comando de movimento negativo eixo Y
		btnYNegativo.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(conectado) {
					if(escrita) {
						escrita = false;
						incrementandoYn = true;
					}
				}
			}
			public void mouseReleased(MouseEvent e ) {
				if(conectado) {
					if(!escrita) {
						escrita = true;
						incrementandoYn = false;
					}
				}
			}				
		});
		
		//Comando de movimento positivo eixo Y
		btnYPositivo.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(conectado) {
					if(escrita) {
						escrita = false;
						incrementandoYp = true;
					}
				}
			}
			public void mouseReleased(MouseEvent e ) {
				if(conectado) {
					if(!escrita) {
						escrita = true;
						incrementandoYp = false;
					}
				}
			}
		});
		
		//Comando movimento negativo eixo Z
		btnZNegativo.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(conectado) {	
					if(escrita) {
						escrita = false;
						incrementandoZn = true;
					}
				}
			}
			public void mouseReleased(MouseEvent e ) {
				if(conectado) {
					if(!escrita) {
						escrita = true;
						incrementandoZn = false;
					}
				}
			}
		});
		
		
		//Comando movimento positivo eixo Z
		btnZPositivo.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(conectado) {
					if(escrita) {
						escrita = false;
						incrementandoZp = true;
					}
				}
			}
			public void mouseReleased(MouseEvent e ) {
				if(conectado) {
					if(!escrita) {
						escrita = true;
						incrementandoZp = false;
					}
				}
			}
		});
		
		
		//INICIA COMANDO DE VERIFICA��O DE CODIGOS DIGITADOS
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cod = textCod.getText();
				cod  = cod.toUpperCase();
				if(Funcoes.isValidGCode(cod)) {
					textComandList.append("    " +cod +"\n");
					textCod.setText("");
					
				}
				else {
					ErroCod erro = new ErroCod();
					erro.setModalityType(ModalityType.APPLICATION_MODAL);
					erro.setVisible(true);
					erro.setLocationRelativeTo(null);
				
					
				}
			}

	
		});
		
		textCod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnEnter.doClick();
			}
		});
		
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dados = textComandList.getText();
				com.enviaDados(dados.concat("\r\n"));
							
			}
		});

		
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				textComandList.setText("");
				
			}
		});
		
		btnApaga.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  String content = textComandList.getText();
	                if (!content.isEmpty()) {
	                    String[] lines = content.split("\n");
	                    if (lines.length > 0) {
	                        StringBuilder newContent = new StringBuilder();
	                        for (int i = 0; i < lines.length - 1; i++) {
	                            newContent.append(lines[i]).append("\n");
	                        }
	                        textComandList.setText(newContent.toString());
	                    }
	                }
			}
		});
		
		setPanelEnabled(pnlButtons, false);
		
		JButton btnNewButton = new JButton("Home \r\nposition");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(conectado) {
					com.enviaDados("G90 X0 Y0 Z0 \r\n");
					lblX.setText("0.000");
					lblY.setText("0.000");
					lblZ.setText("0.000");
				}
			}
		});
		btnNewButton.setFont(new Font("Arial Black", Font.PLAIN, 14));
		btnNewButton.setBounds(30, 295, 172, 54);
		pnlButtons.add(btnNewButton);
		btnNewButton.setEnabled(false);
		
		btnHomePosition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				com.enviaDados("G10 P0 L20 X0 Y0 Z0 \r\n");
				lblX.setText("0.000");
				lblY.setText("0.000");
				lblZ.setText("0.000");
			}
		}); 
		
		

		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean resultado = com.abreCom(cmbPortas.getSelectedItem().toString(),
						Integer.parseInt(cmbBaudRate.getSelectedItem().toString()));
				if (resultado) {
					conectado = true;
					cmbPortas.setEnabled(false);
					cmbBaudRate.setEnabled(false);
					btnConectar.setEnabled(false);
					btnDesconectar.setEnabled(true);
					setPanelEnabled(pnlButtons, true);
					com.enviaDados("G21 G91 G94;\r\n"+"G10 P0 L20 X0 Y0 Z0 \r\n"+"M30\r\n");
				}
			
			}
		});
		btnDesconectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				conectado = false;
				com.fechaCom();
				cmbPortas.setEnabled(true);
				cmbBaudRate.setEnabled(true);
				btnConectar.setEnabled(true);
				btnDesconectar.setEnabled(false);
				setPanelEnabled(pnlButtons, false);
			}
		});
		
		
		
		
		// Cria um servi�o agendado com uma �nica Thread
				ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

				// Tarefa a ser executada periodicamente
				Runnable task = new Runnable() {
					@Override
					public void run() {
						if (conectado) {
							com.enviaDados("?\r\n");
							
							
													}
					}
				};
				//Tarefa roda em segundo plano movimentaçao dos eixos e label
				Runnable tarefa2 = new Runnable() {
				    @Override
				    public void run() {
				        if (incrementandoXn) {
				        	posX = Float.parseFloat(lblX.getText());
						    lblX.setText("" + mov.mover(-1, posX));
						    com.enviaDados(mov.moverXn());		
				        }
				        else if (incrementandoXp) {
				        	posX = Float.parseFloat(lblX.getText());
				            lblX.setText("" + mov.mover(1, posX));
				            com.enviaDados(mov.moverXp());
				        }
				        else if (incrementandoYn) {
				        	posY = Float.parseFloat(lblY.getText());
				            lblY.setText("" + mov.mover(-1, posY));
				            com.enviaDados(mov.moverYn(Float.parseFloat(lblY.getText())));
				        }
				        else if(incrementandoYp) {
				        	posY = Float.parseFloat(lblY.getText());
				            lblY.setText("" + mov.mover( 1, posY));
				            com.enviaDados(mov.moverYp(Float.parseFloat(lblY.getText())));
				        }
				        else if(incrementandoZn) {
				        	posZ = Float.parseFloat(lblZ.getText());
				            lblZ.setText("" + mov.mover(-1, posZ));
				            com.enviaDados(mov.moverZn(Float.parseFloat(lblZ.getText())));
				        }
				        else if(incrementandoZp) {
				        	posZ = Float.parseFloat(lblZ.getText());
				            lblZ.setText("" + mov.mover( 1, posZ));
				            com.enviaDados(mov.moverZp(Float.parseFloat(lblZ.getText())));
				        }
				        
				    }};

				// Agenda a tarefa para ser executada a cada 100 milissegundos
				scheduler.scheduleAtFixedRate(task, 0,100, TimeUnit.MILLISECONDS);
				scheduler.scheduleAtFixedRate(tarefa2, 0,400, TimeUnit.MILLISECONDS);

				

				// Encerra o servi�o agendado de forma adequada quando necess�rio
				Runtime.getRuntime().addShutdownHook(new Thread() {
					@Override
					public void run() {
						scheduler.shutdown();
						try {
							if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) {
								scheduler.shutdownNow();
							}
						} catch (InterruptedException e) {
							scheduler.shutdownNow();
						}
					}
				});
			}

		
	 
	// m�todo para habilitar ou desabilitar um JPanel
		void setPanelEnabled(JPanel panel, Boolean isEnabled) {
			panel.setEnabled(isEnabled);

			Component[] components = panel.getComponents();

			for (Component component : components) {
				if (component instanceof JPanel) {
					setPanelEnabled((JPanel) component, isEnabled);
				}
				component.setEnabled(isEnabled);
			}
		}
}
