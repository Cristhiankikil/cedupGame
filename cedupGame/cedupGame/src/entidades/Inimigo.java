package entidades;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import effects.Sons;
import main.Game;
import mundo.Camera;

public class Inimigo extends Entity {
    
    public double speed = 0.5;
    public int movimentacao = 1;
    public int frames = 0, maxFrames = 7, index = 0, maxIndex = 3;
    public int maskx = 0, masky = 0, maskw = 16, maskh = 16;
    public BufferedImage[] inimigoDireita;
    public BufferedImage[] inimigoEsquerda;
    private Sons som;
    private boolean somTocado = false; // Flag para verificar se o som foi tocado

    public Inimigo(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        
        inimigoDireita = new BufferedImage[4];
        inimigoEsquerda = new BufferedImage[4];
        
        for (int i = 0; i < 4; i++) {
        	inimigoEsquerda[i] = Game.sprite.getSprite(96 + (i * 16), 0, 16, 16);
        }
        for (int i = 0; i < 4; i++) {
        	inimigoDireita[i] = Game.sprite.getSprite(144 - (i * 16), 16, 16, 16);
        }
        
        som = new Sons("/dano.wav"); // Inicializa o som
    }

    public void tick() {
        if (!colisao((int)x, (int)(y + 1))) {
            y += 2; // Aplica a gravidade ao inimigo
        }
        
        if (Game.player.getX() < this.getX() && !colisao((int)(x - speed), this.getY())) {
            x -= speed;
        }
        if (Game.player.getX() > this.getX() && !colisao((int)(x + speed), this.getY())) {
            x += speed;
        }
        
        if (movimentacao == 1) {
            frames++;
            if (frames == maxFrames) {
                index++;
                frames = 0;
                if (index > maxIndex)
                    index = 0;
            }
        }
        
        // Verifica se o inimigo colide com o jogador
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

    private boolean colideComPlayer() {
        Rectangle inimigoBounds = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
        Rectangle playerBounds = new Rectangle(Game.player.getX() + Game.player.maskx, Game.player.getY() + Game.player.masky, Game.player.maskw, Game.player.maskh);
        return inimigoBounds.intersects(playerBounds);
    }

    public void render(Graphics g) {
    	if(movimentacao == 1 && Game.player.getX() > this.getX()) {
    		 g.drawImage(inimigoDireita[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
    	}
    	if(movimentacao == 1 && Game.player.getX() < this.getX()) {
   		 g.drawImage(inimigoEsquerda[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
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

    public boolean colisao(int nextx, int nexty) {
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
    }
}
