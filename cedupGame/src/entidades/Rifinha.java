package entidades;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.Game;



public class Rifinha extends Entity {
	
	public BufferedImage[] rifinha;
    public int maskx = 0, masky = 0, maskw = 16, maskh = 16;

    public Rifinha(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        rifinha = new BufferedImage[4]; 
        for (int i = 0; i < 4; i++) {
        	//System.out.println("o contador esta em:" + i);
        	rifinha[i] = Game.sprite.getSprite(80 + (i * 16), 32, 16, 16);
        }
    }

    @Override
    public void tick() {
        // Verifica se o item está colidindo com o jogador
        if (isCollidingWithPlayer()) {
            System.out.println("Colidiu com o jogador!");
            Game.coletadas++;  // Incrementa o contador de rifas coletadas
            Game.entidades.remove(this); // Remove a rifa após coleta
        }
    }

    public boolean isCollidingWithPlayer() {
        // Converta para int se necessário
        Rectangle itemBounds = new Rectangle((int) (this.x + this.maskx), (int) (this.y + this.masky), this.maskw, this.maskh);
        Rectangle playerBounds = new Rectangle((int) (Game.player.getX() + Game.player.maskx), (int) (Game.player.getY() + Game.player.masky), Game.player.maskw, Game.player.maskh);
        return itemBounds.intersects(playerBounds);
    }
}
