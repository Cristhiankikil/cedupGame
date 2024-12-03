package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import effects.Sons;
import entidades.Entity;
import main.Game;

public class Instrucoes {
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
    int z = 0;
    
    
    public Instrucoes(Game game) {
        this.game = game;
    }

    public void render(Graphics g) {
        	
    	tela1(g);
        if(agoraSim == true) {
        	tela2(g);
        	}
    }
    
    public void tela1(Graphics g) {
    	
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT); // Preenche o fundo com preto

        g.setColor(Color.WHITE);
        String continuar = "Jogar";
        String voltar = "Voltar";
        String proximo = "Próximo";
        
        g.setFont(new Font("Arial", Font.BOLD, 10)); 
        g.drawString("Como Jogar: ", 15, 25);
        
        g.setFont(new Font("Arial", Font.BOLD, 9)); 
        g.drawString("Ultilize as teclas (A) e (D), para se movimentar ", 18, 50);
        
        g.drawImage(Entity.controles, 18, 60, null);
        
        playert = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            playert[i] = Game.sprite.getSprite(32 + (i * 16), 0, 16, 16);
        }
        if (condition == true) {
            frames++;
            if (frames == maxFrames) {
                index++;
                frames = 0;
                if (index > maxIndex)
                    index = 0;
            }
        }
        
        g.setFont(new Font("Arial", Font.BOLD, 9)); 
        g.drawString("Para pular use a tecla (Space)", 18, 90);
        
        g.drawImage(Entity.espaco, 18, 100, null);
        
        g.drawImage(playert[index], 60, 60, null);
        //g.drawImage(Game.sprite.getSprite(32, 0, 16, 16), 80, 60, null);

        // Configura a fonte e o tamanho
        g.setFont(new Font("", Font.BOLD, 10));
        

        // Desenha as opções
        g.drawString(voltar, 19, 130);
        g.drawString(continuar, 111, 130);
        g.drawString(proximo, 200, 130);

        // Posiciona o seletor para a opção 'Voltar'
        if (selectores == 0) {
            selectorX = 19 - selectorLeftBuffer;
            selectorY = 130 - selectorWidth;
        }

        // Posiciona o seletor para a opção 'Jogar'
        if (selectores == 2) {
            selectorX = 200  - selectorLeftBuffer;
            selectorY = 130 - selectorWidth;
        }
        if (selectores == 1) {
            selectorX = 111  - selectorLeftBuffer;
            selectorY = 130 - selectorWidth;
        }

        // Desenha o seletor
        g.setColor(Color.RED);
        if (selectorX != 0) g.fillRect(selectorX, selectorY, selectorWidth, selectorWidth);
    }
    
    public void tela2(Graphics g) {
    	
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT); // Preenche o fundo com preto

        g.setColor(Color.WHITE);
        String continuar = "Jogar";
        String voltar = "Voltar";
        String proximo = "Próximo";
        
        g.setFont(new Font("Arial", Font.BOLD, 10)); 
        g.drawString("Como Jogar: ", 15, 25);
        
        g.setFont(new Font("Arial", Font.BOLD, 9)); 
        g.drawString("Colete as perguntas e responda elas ", 18, 50);
        g.drawString("corretamente para avançar de nível. ", 16, 60);
     
        g.drawString("Evite inimigos para não perder a paciência! ", 18, 80);

        inimigot = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
        	inimigot[i] = Game.sprite.getSprite(96 + (i * 16), 0, 16, 16);
        }
        
        g.drawImage(inimigot[index], 100, 95, null);
        
        g.setFont(new Font("", Font.BOLD, 10));
        

        // Desenha as opções
        g.drawString(voltar, 19, 130);
        
        g.drawString(continuar, 200, 130);

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

    public void tick() {
        if (delayCounter > 0) {
            delayCounter--; // Decrementa o contador do delay
        }
    }

    public void KeyPress(KeyEvent ee) {
       

        if (delayCounter == 0) { 
        	 int keyCodee = ee.getKeyCode();// Só permite o movimento se o contador do delay for 0
            if (keyCodee == KeyEvent.VK_D) {
                selectores++;  // Move para a próxima opção
               
                tocarSom("/icone.wav");
                //System.out.println("Tecla D pressionada");
            } else if (keyCodee == KeyEvent.VK_A) {
                selectores--;  // Move para a opção anterior
                
                tocarSom("/icone.wav");
                //System.out.println("Tecla A pressionada");
            } else if (keyCodee == KeyEvent.VK_ENTER) {
            	z ++;
            	if (z > 0) {
            		 selectOption();
            	}
              // Seleciona a opção atual
                
            }
            
            
            if (selectores < 0 && agoraSim == true) {
                selectores = 1; // Vai para a última opção
            } else if (selectores > 1 && agoraSim == true) {
                selectores = 0; // Vai para a primeira opção
            }
            if (selectores < 0) {
                selectores = 2; // Vai para a última opção
            } else if (selectores > 2) {
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
            Game.BackToMenu();
            agoraSim = false;
        } else if (selectores == 1) {
        	System.out.println("Iniciar o jogo!");
             Game.GotoGame();
        }else if (selectores == 2) {
        	System.out.println("Mais instruções!");
            
            agoraSim = true;
       }
    }
}
