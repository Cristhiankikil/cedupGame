	package ui;

	import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.sql.SQLException;

import database.QuestionarioBd;
import effects.Sons;
import main.Game;


	
	    
public class TelaFim {

	 public String nomeJogador = "";
	 public String pontuacaoJogador = "";
	 public String qtdPerguntas = "";
    private Game game;
    private byte selectores = 0;
    private int selectorX = 0;
    private int selectorY = 0;
    private int selectorLeftBuffer = 10; // Ajuste na distância do seletor à esquerda
    private int selectorWidth = 4;
    private Sons som;
    private int delay = 20; // Define um valor para o delay
    private int delayCounter = 0; // Contador para controlar o delay
    public BufferedImage[] playert;
    public BufferedImage[] inimigot;
    public int frames = 0, maxFrames = 6, index = 0, maxIndex = 3;
    private boolean condition = true; 
    boolean agoraSim = false;
	
	    public TelaFim(Game game) {
	        this.game = game;
	    }

	    public void render(Graphics g, int cod_jogadas) {
	        	
	    	tela1(g, cod_jogadas);
	    }
	    
	    public void tela1(Graphics g, int cod_jogadas) {
	    	
	        g.setColor(Color.BLACK);
	        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT); // Preenche o fundo com preto

	        g.setColor(Color.WHITE);
	        String titulo = "Fim de Jogo!";
	        
	       
	        
	        
	        String continuar = "Sair";
	        String voltar = "Restart";
	        
	        QuestionarioBd quest = new QuestionarioBd();
	        if (nomeJogador.equals("")) {
	        	try {
					nomeJogador = quest.buscaJogador(cod_jogadas);
					pontuacaoJogador = quest.buscaPontos(cod_jogadas);
					
					qtdPerguntas = quest.buscaQtdPerguntas(cod_jogadas)+"";
					
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
	        }
	        
	
	        
	        g.setFont(new Font("Arial", Font.BOLD, 10)); 
	        g.drawString(titulo, 100, 25);
	        g.drawString("Jogador:  "+nomeJogador, 100, 50);
	        g.drawString("Pontuação:", 100, 65);
	        g.drawString(pontuacaoJogador+"/"+qtdPerguntas, 115, 80);
	       
	        g.setFont(new Font("", Font.BOLD, 10));
	        

	        // Desenha as opções
	        g.drawString(voltar, 19, 130);
	        g.drawString(continuar, 200, 130);
	       

	        // Posiciona o seletor para a opção 'Voltar'
	        if (selectores == 0) {
	            selectorX = 19 - selectorLeftBuffer;
	            selectorY = 130 - selectorWidth;
	        }

	      
	        if (selectores == 1) {
	            selectorX = 200  - selectorLeftBuffer;
	            selectorY = 130 - selectorWidth;
	        }

	        // Desenha o seletor
	        g.setColor(Color.RED);
	        if (selectorX != 0) g.fillRect(selectorX, selectorY, selectorWidth, selectorWidth);
	    }
	    public void tick() {
			 if (delayCounter > 0) {
		            delayCounter--; // Decrementa o contador do delay
		        }
		 }
	  
	    public void KeyPress(KeyEvent e) {
	       

	        if (delayCounter == 0) { 
	        	 int keyCode = e.getKeyCode();// Só permite o movimento se o contador do delay for 0
	            if (keyCode == KeyEvent.VK_D) {
	                selectores++;  // Move para a próxima opção
	               
	                tocarSom("/icone.wav");
	                //System.out.println("Tecla D pressionada");
	            } else if (keyCode == KeyEvent.VK_A) {
	                selectores--;  // Move para a opção anterior
	                
	                tocarSom("/icone.wav");
	                //System.out.println("Tecla A pressionada");
	            } else if (keyCode == KeyEvent.VK_ENTER) {
	                selectOption(); // Seleciona a opção atual
	            }
	            
	            
	            if (selectores < 0) {
	                selectores = 1; // Vai para a última opção
	            } else if (selectores > 1) {
	                selectores = 0; // Vai para a primeira opção
	            }
	         

	            delayCounter = delay; // Reinicia o contador do delay
	        }
	    }

	    private void tocarSom(String s) {
	        // Cria uma nova instância de Sons para tocar o som toda vez que o seletor muda
	        som = new Sons(s);
	        som.Tocar();
	    }

	    private void selectOption() {
	        
	        if (selectores == 0) {
	            System.out.println("Voltar!");
	            
	            try {
	            	
					Game.inicioJogo();
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            agoraSim = false;
	        } else if (selectores == 1) {
	   
	        	System.exit(1);
	        
	    }
	        }
	}