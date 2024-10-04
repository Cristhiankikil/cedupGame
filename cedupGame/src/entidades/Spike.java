package entidades;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import effects.Sons;
import main.Game;
import mundo.Camera;

public class Spike extends Entity{
	 public int maskx = 0, masky = 0, maskw = 3, maskh = 16;
	 private Sons som;
	 private boolean somTocado = false; 
	 
	 

	public Spike(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
	}
	 private boolean colideComPlayer() {
	        Rectangle inimigoBounds = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
	        Rectangle playerBounds = new Rectangle(Game.player.getX() + Game.player.maskx, Game.player.getY() + Game.player.masky, Game.player.maskw, Game.player.maskh);
	        return inimigoBounds.intersects(playerBounds);
	    }

	    public void render(Graphics g) {
	    	if (colideComPlayer()) {
	            Game.player.life -= 10;
	            if (!somTocado) {
	                som.Tocar(); // Toca o som se ainda não foi tocado
	                somTocado = true; // Marca o som como tocado
	            }
	            Game.player.y -= 0.5;
	        } else {
	            somTocado = false; // Reseta a flag quando o jogador não está mais colidindo
	        }
	        
	    }

	    public boolean novo(int nextx, int nexty) {
	        Rectangle player = new Rectangle(nextx + maskx, nexty + masky, maskw, maskh);
	        for (int i = 0; i < Game.entidades.size(); i++) {
	            Entity entidade = Game.entidades.get(i);
	            if (entidade instanceof Player) {
	                Rectangle solido = new Rectangle(entidade.getX() + maskx, entidade.getY() + masky, maskw, maskh);    
	                if (player.intersects(solido)) {
	                    return true;
	                }
	            }
	        }
	        return false;
	    }

	   /* public boolean colisao(int nextx, int nexty) {
	        Rectangle player = new Rectangle(nextx + maskx, nexty + masky, maskw, maskh);
	        for (int i = 0; i < Game.entidades.size(); i++) {
	            Entity entidade = Game.entidades.get(i);
	            if (entidade instanceof Solido) {
	                Rectangle solido = new Rectangle(entidade.getX() + maskx, entidade.getY() + masky, maskw, maskh);    
	                if (player.intersects(solido)) {
	                    return true;
	                }
	            }
	        }
	        return false;
	    }*/

}
