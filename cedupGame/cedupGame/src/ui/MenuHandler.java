package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import effects.Sons;
import main.Game;

public class MenuHandler {

    private Game game;
    private byte selector = 0;
    private int selectorX = 0;
    private int selectorY = 0;
    private int selectorLeftBuffer = 16;
    private int selectorWidth = 4;
     // Caminho relativo
    private Sons som;
    private int delay = 10; // Define um valor para o delay
    private int delayCounter = 0; // Contador para controlar o delay
    private Instrucoes instrucoes;

    public MenuHandler(Game game) {
        this.game = game;
    }

    public void render(Graphics g) {
        // Configura a cor de fundo do menu
    
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT); // Preenche o fundo com preto

        g.setColor(Color.WHITE);

        String iniciarJogo = "Iniciar o jogo!";
        String opcao = "Opções";
        String sair = "Sair";
        String titulo = "Cedup";
        String sub = "the game";

        // Configura a fonte e o tamanho
        g.setFont(new Font("", Font.BOLD, 20));
        FontMetrics fontM = g.getFontMetrics(g.getFont());

        // Desenha o texto centralizado
        int menuWidth = Game.WIDTH;
        g.drawString(titulo, menuWidth / 2 - fontM.stringWidth(titulo) / 2, 20);

        g.setFont(new Font("", Font.BOLD, 10));
        fontM = g.getFontMetrics(g.getFont());
        g.drawString(sub, menuWidth / 2 - fontM.stringWidth(sub) / 2, 30);

        g.drawString(iniciarJogo, menuWidth / 2 - fontM.stringWidth(iniciarJogo) / 2, 55);
        if (selector == 0) {
            selectorX = menuWidth / 2 - (fontM.stringWidth(iniciarJogo) / 2) - selectorWidth - selectorLeftBuffer;
            selectorY = 55 - selectorWidth / 2;
        }

        g.drawString(opcao, menuWidth / 2 - fontM.stringWidth(opcao) / 2, 75);
        if (selector == 1) {
            selectorX = menuWidth / 2 - (fontM.stringWidth(opcao) / 2) - selectorWidth - selectorLeftBuffer;
            selectorY = 75 - selectorWidth / 2;
        }

        g.drawString(sair, menuWidth / 2 - fontM.stringWidth(sair) / 2, 95);
        if (selector == 2) {
            selectorX = menuWidth / 2 - (fontM.stringWidth(sair) / 2) - selectorWidth - selectorLeftBuffer;
            selectorY = 95 - selectorWidth / 2;
        }

        // Selector
        g.setColor(Color.RED);
        if (selectorX != 0) g.fillRect(selectorX, selectorY, selectorWidth, selectorWidth);
    }

    public void tick() {
        if (delayCounter > 0) {
            delayCounter--; // Decrementa o contador do delay
        }
    }

    
    public void KeyPress(KeyEvent e) {
        if (delayCounter == 0) { // Só permite o movimento se o contador do delay for 0
            int keyCode = e.getKeyCode();

            if (keyCode == KeyEvent.VK_S) {
                selector += 1;
                tocarSom("/icone.wav");
            } else if (keyCode == KeyEvent.VK_W) {
                selector -= 1;
                tocarSom("/icone.wav");
            } else if (keyCode == KeyEvent.VK_ENTER) {
                selectOption();
            }

            // Garante que o seletor fique dentro dos limites das opções
            if (selector < 0) {
                selector = 2; // Última opção
            } else if (selector > 2) {
                selector = 0; // Primeira opção
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
        // Adicione a lógica para lidar com a opção selecionada
        if (selector == 0) {
            System.out.println("Iniciar o jogo!");
            //Game.GotoGame();
            Game.instructions(); 
        } else if (selector == 1) {
            System.out.println("Opções!");
            // Adicione a lógica para abrir o menu de opções
            Game.instructions(); 
        } else if (selector == 2) {
            System.out.println("Sair!");
            // Adicione a lógica para sair do jogo
            System.exit(1);
        }
    }
}
