package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFrame;
import effects.Sons;
import entidades.Ceu;
import entidades.Entity;
import entidades.Inimigo;
import entidades.Player;
import entidades.Rifinha;
import graficos.Spritsheet;
import mundo.Camera;
import mundo.Mundo;
import ui.MenuHandler;

public class Game extends Canvas implements Runnable, KeyListener {

    private static final long serialVersionUID = 1L;
    public static JFrame jframe;
    public static int WIDTH = 260;
    public static int HEIGHT = 140;
    public static int SCALE = 5;
    private boolean isRunning = false; 
    private static Thread thread;
    
    private BufferedImage fundo;
    public static List<Entity> entidades;
    public static Spritsheet sprite;
    public static Player player; 
    
    public static List<Rifinha> rifinha;
    public static List<Inimigo> inimigo;
    public static List<Ceu> ceuvetor;
    public static Spritsheet ceu;
    
    public UserInterface ui;
    public static GameState gs = GameState.Menu;
    public MenuHandler menu;
    public static Mundo mundo;
    private Sons som;
    
    
    public static int level = 1;
	public int levelmaximo = 2;
    public static int coletadas = 0; // Adicionando contador de rifas coletadas
   
    public static int fps = 0;

    public Game() {
    	
        addKeyListener(this);
        this.setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
        initFrame();
        ui = new UserInterface();
        menu = new MenuHandler(this);
        fundo = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        ceuvetor = new ArrayList<Ceu>();
        entidades = new ArrayList<Entity>();
        inimigo = new ArrayList<Inimigo>();
        rifinha = new ArrayList<Rifinha>();
        sprite = new Spritsheet("/spritesheet.png");
        ceu = new Spritsheet("/ceunoite.png");
        player = new Player(0,0,16,16,sprite.getSprite(32, 0, 16, 16));
        entidades.add(player);
        mundo = new Mundo("/level"+level+".png");
    }

	public void initFrame() {
		jframe = new JFrame("JOGO");
		jframe.add(this);
		jframe.setResizable(false);
		jframe.pack();
		jframe.setLocationRelativeTo(null);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
	}

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }

    public synchronized void start() {
        thread = new Thread(this);
        isRunning = true; 
        thread.start();
    }

    public synchronized void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void tick() {
    	if(coletadas == 3) {
			level++;
			coletadas = 0;
			if(level > levelmaximo) {
				level = 1;
			}
			String Level = "level"+level+".png";
			Mundo.newlevel(Level);
		}
    	
    	
        if (gs == GameState.Menu) {
            menu.tick();
        } else if (gs == GameState.Game) {
            mundo.tick();
            for (int i = 0; i < entidades.size(); i++) {
                entidades.get(i).tick();
            }
            for (int i = 0; i < ceuvetor.size(); i++) {
                ceuvetor.get(i).tick();
            }
            for (int i = 0; i < rifinha.size(); i++) {
                rifinha.get(i).tick();
            }
        }
    }



    public void render() {
        BufferStrategy buffer = this.getBufferStrategy();
        if (buffer == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = fundo.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        if (gs == GameState.Menu) {
            menu.render(g);
       /* } else if (gs == GameState.GameOver) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            FontMetrics fontM = g.getFontMetrics();
            g.drawString("Fim de Jogo!", WIDTH / 2 - fontM.stringWidth("Fim de Jogo!") / 2, 75);
            g.setFont(new Font("Arial", Font.BOLD, 10));
            fontM = g.getFontMetrics();
            g.drawString("Restart?", WIDTH / 2 - fontM.stringWidth("Restart?") / 2, 100);
        */} else if (gs == GameState.Game) {
            mundo.render(g);
            for (Ceu ceuItem : ceuvetor) {
                ceuItem.render(g);
            }
            for (Entity entidade : entidades) {
                entidade.render(g);
            }
            for (Inimigo inimigoItem : inimigo) {
                inimigoItem.render(g);
            }
            for (Rifinha rifinhaItem : rifinha) {
            	rifinhaItem.render(g);
            }
            ui.render(g);
        }
        
        g.dispose();

        g = buffer.getDrawGraphics();
        g.drawImage(fundo, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        buffer.show();
    }


 
    public void run() {
		long lastTime  = System.nanoTime();
		double amountOfTicks = 60.0f;
		double ms = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ms;
			lastTime = now;
			if(delta > 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			if(System.currentTimeMillis() - timer >= 1000) {
				//System.out.println("FPS : " + frames );
				fps = frames;
				frames = 0;
				timer += 1000;
			}
		}
		stop();
		
	}

    public void tocarSom(String s) {
        som = new Sons(s);
        som.Tocar();
        som.Parar();
    }

    
    public void keyTyped(KeyEvent e) {}

    
    
    public void keyPressed(KeyEvent e) {
        if (gs == GameState.Game) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    player.up = true;
                    break;
                case KeyEvent.VK_A:
                    player.left = true;
                    break;
                case KeyEvent.VK_S:
                    player.down = true;
                    break;
                case KeyEvent.VK_D:
                    player.right = true;
                    break;
                case KeyEvent.VK_SPACE:
                    player.jump = true;
                    break;
                default:
                    break;
            }
        } else if (gs == GameState.Menu) {
            menu.KeyPress(e);
        }
    }

    
    public void keyReleased(KeyEvent e) {
        if (gs == GameState.Game) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    player.up = false;
                    break;
                case KeyEvent.VK_A:
                    player.left = false;
                    break;
                case KeyEvent.VK_S:
                    player.down = false;
                    break;
                case KeyEvent.VK_D:
                    player.right = false;
                    break;
                case KeyEvent.VK_SPACE:
                    player.jump = false;
                    break;
                default:
                    break;
            }
        } else if (gs == GameState.Menu) {
            menu.KeyPress(e);
        } else if (gs == GameState.GameOver) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                //resetGame();
                GotoGame();
            }
        }
    }

    public static void GotoGame() {
        gs = GameState.Game;
    }

    public static void EndGame() {
        gs = GameState.GameOver;
    }

   /* public void resetGame() {
        // Limpa listas de entidades
        entidades.clear();
        rifinha.clear();
        inimigo.clear();
        ceuvetor.clear();

        // Reinicializa o jogador
        player = new Player(0, 0, 16, 16, sprite.getSprite(32, 0, 16, 16));
        entidades.add(player);

        // Reinicializa o mundo
        mundo = new Mundo("/level1.png");

        // Reinicia o contador de itens coletados
        coletadas = 0;

        // Reinicia a interface do usuário, se necessário
        ui = new UserInterface();
        System.out.println("Posição do jogador: (" + player.getX() + ", " + player.getY() + ")");
        System.out.println("Posição da câmera: (" + Camera.x + ", " + Camera.y + ")");
        System.out.println("Coordenadas do sprite: (" + (player.getX() - Camera.x) + ", " + (player.getY() - Camera.y) + ")");


    }*/
}

