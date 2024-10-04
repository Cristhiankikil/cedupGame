package entidades;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.sql.SQLException;

import database.*;
import java.util.*;

import effects.Sons;
import main.Game;

public class PerguntasTela {
	
	private Game game;
    private byte selectores = 0;
    private int selectorX = 0;
    private int selectorY = 0;
    private int selectorLeftBuffer = 10; // Ajuste na distância do seletor à esquerda
    private int selectorWidth = 4;
    private Sons som;
    private int delay = 20; // Define um valor para o delay
    private int delayCounter = 0; 
    boolean agoraSim = false;
	private String resposta = "";
	
	public Perguntas xx = new Perguntas();
	public PerguntasTela(Game game){
		this.game = game;
	}
	
	 public void render(Graphics g, ArrayList<?> lista) {
		 
		 telaPergunta(g, lista);
	    }
	 
	 public void tick() {
		 if (delayCounter > 0) {
	            delayCounter--; // Decrementa o contador do delay
	        }
	 }
	 
	 public void telaPergunta(Graphics g, ArrayList<?> lista ) {
	    	
	        g.setColor(Color.BLACK);
	        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT); // Preenche o fundo com preto

	        g.setColor(Color.WHITE);
	        String sim = "Verdadeiro!";
	        String nao = "Falso!";
	       
	        
	        g.setFont(new Font("Arial", Font.BOLD, 10)); 
	        g.drawString("Pergunta número "+Game.coletadas+":", 15, 25);
	        
	        g.setFont(new Font("Arial", Font.BOLD, 9)); 
	        
	       
	        int ponteiro = 0;
	     	
	        
	        while ( ponteiro < lista.size()) {
	        	
	        	 xx = (Perguntas)lista.get(ponteiro);
	        	
	        	
	        	
	        	ponteiro++;
	        }
	        
	        g.drawString(xx.getPergunta(), 18, 50);
	        
	       
	        //g.drawString("corretamente para avançar de nível. ", 16, 60);
	     
	        

	        
	        
	        g.setFont(new Font("", Font.BOLD, 10));
	        

	        // Desenha as opções
	        g.drawString(sim, 19, 130);
	        
	        g.drawString(nao, 200, 130);

	        // Posiciona o seletor para a opção 'Voltar'
	        if (selectores == 0) {
	            selectorX = 19 - selectorLeftBuffer;
	            selectorY = 130 - selectorWidth;
	        }

	        // Posiciona o seletor para a opção 'Jogar'
	        if (selectores == 1) {
	            selectorX = 200  - selectorLeftBuffer;
	            selectorY = 130 - selectorWidth;
	        }
	        

	        // Desenha o seletor
	        g.setColor(Color.RED);
	        if (selectorX != 0) g.fillRect(selectorX, selectorY, selectorWidth, selectorWidth);
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
	            
	            
	            if (selectores < 0 ) {
	                selectores = 1; // Vai para a última opção
	            } else if (selectores > 1 ) {
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
	        
	    	QuestionarioBd q = new QuestionarioBd();
	    	
	    	
	    	
	        if (selectores == 0 ) {
	        	 
	            Game.GotoGame();
	        	if("t".equals(xx.getResposta_esperada())){
	        		System.out.println("Acertou");
	        		q.RespondePerguntas(game.RetornaCod(), xx.getCod_pergunta(),"true","true");
	        	
	        }  else {
	        	 System.out.println("Errou"+selectores+" "+xx.getResposta());
	        	 q.RespondePerguntas(game.RetornaCod(), xx.getCod_pergunta(),"true","false");
	        } 
	        	}
	        	
	        if (selectores == 1 ) {
	            
	        	
	        	
	            Game.GotoGame();
	        	if("f".equals(xx.getResposta_esperada())){
	        		System.out.println("Acertou");
	        		q.RespondePerguntas(game.RetornaCod(), xx.getCod_pergunta(),"false","true");
	        	
	        }  else {
	        	 System.out.println("Errou"+selectores+" "+xx.getResposta());
	        	 q.RespondePerguntas(game.RetornaCod(), xx.getCod_pergunta(),"false","false");
	        } 
	        	}
	    }
}
