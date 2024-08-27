package mundo;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entidades.*;
import graficos.Spritsheet;
import main.Game;
import main.UserInterface;
import ui.MenuHandler;

public class Mundo {

	public static int WIDTH;
	public static int HEIGHT;
	public Tile[] tiles;
	
	
	public Mundo(String path) {
	    try {
	        BufferedImage level = ImageIO.read(getClass().getResource(path));
	        int[] pixels = new int[level.getWidth() * level.getHeight()];
	        tiles = new Tile[level.getWidth() * level.getHeight()];
	        WIDTH = level.getWidth();
	        HEIGHT = level.getHeight();
	        
	        level.getRGB(0, 0, level.getWidth(), level.getHeight(), pixels, 0, level.getWidth());
	        
	        for(int x = 0; x < level.getWidth();x++) {
				for(int y = 0; y < level.getHeight();y++) {
					int pixelAtual = pixels[x + (y*level.getWidth())];
					tiles[x + (y*WIDTH)] = new Empty(x*16,y*16,Entity.empty);
	                if(pixelAtual == 0xFF99e550) {
	                    // player
	               
	                    Game.player.setX(x*16);
	                    Game.player.setY(y*16);
	                } else if(pixelAtual == 0xFF696a6a) {
	                    // chão
	                	Solido solido = new Solido(x*16,y*16,16,16,Entity.chao);
						Game.entidades.add(solido);
	                }else if(pixelAtual == 0xFFff0000) {
	                	// Inimigo
						Inimigo a = new Inimigo(x*16,y*16,16,16,Entity.inimigo);
						Game.entidades.add(a);
					}else if(pixelAtual == 0xFFfbf236) {
	                	// Rifinhas
						Rifinha r = new Rifinha(x*16,y*16,16,16,Entity.rifinha);
						Game.entidades.add(r);
					}else if(pixelAtual == 0xFFffffff) {
						//Ceu
						Ceu b = new Ceu(x*16,y*16,16,16,Entity.ceu);
						Game.ceuvetor.add(b);
				}else if(pixelAtual == 0xFF1600ff) {
					Check a = new Check(x*16,y*16,16,16,Entity.save);
					Game.entidades.add(a);
				}
	              
	                
	         
	            }
	        }
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public  void render(Graphics g) {
		int xi = Camera.x/16;
		int yi = Camera.y/16;
		int xf = xi + (Game.WIDTH/16);
		int yf = yi + (Game.HEIGHT/16);
		for(int x = xi; x <= xf; x++) {
			for(int y = yi; y <= yf; y++) {
				if(x < 0|| y < 0|| x >= WIDTH|| y >= HEIGHT)
					continue;
				Tile tile = tiles[x + (y*WIDTH)];
				tile.render(g);
			}
		}
		}

	public static void  newlevel(String level) {
		 Game.ceuvetor = new ArrayList<Ceu>();
		 Game. entidades = new ArrayList<Entity>();
		 Game.inimigo = new ArrayList<Inimigo>();
		 Game.rifinha = new ArrayList<Rifinha>();
		 Game.sprite = new Spritsheet("/spritesheet.png");
		 Game.ceu = new Spritsheet("/ceunoite.png");
        Game.player = new Player(0,0,16,16, Game.sprite.getSprite(32, 0, 16, 16));
        Game.entidades.add(Game.player);
        Game.mundo = new Mundo("/"+level);
	}
	public  void tick() {
		// TODO Auto-generated method stub
		
	}
	}