package entidades;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import effects.Sons;
import main.Game;
import mundo.Camera;

public class Projetil extends Entity {

    public double speed = 2.0; // Aumenta a velocidade do projétil
    public int frames = 0, maxFrames = 7, index = 0, maxIndex = 3;
    public int maskx = 0, masky = 0, maskw = 16, maskh = 16;
    public BufferedImage[] projetil;
   
    private Sons som;
    private boolean somTocado = false; // Flag para verificar se o som foi tocado

    public Projetil(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
         
        projetil = new BufferedImage[3];
        
        for (int i = 0; i < 3; i++) {
            projetil[i] = Game.sprite.getSprite(32 + (i * 16), 64, 16, 16);
        }
        
        som = new Sons("/dano.wav"); // Inicializa o som
    }

    public void tick() {
        // Movimento do projétil (movendo-se horizontalmente para a esquerda)
        x -= speed;

        // Atualização da animação do projétil
        frames++;
        if (frames == maxFrames) {
            index++;
            frames = 0;
            if (index >= projetil.length) {
                index = 0; // Reinicia o índice para evitar acesso fora dos limites
            }
        }

        // Verifica se o projétil colide com o jogador
        if (colideComPlayer()) {
            Game.player.life -= 10;
            if (!somTocado) {
                som.Tocar(); // Toca o som se ainda não foi tocado
                somTocado = true; // Marca o som como tocado
            }
            Game.entidades.remove(this); // Remove o projétil após a colisão
        }

        // Verifica colisão com qualquer objeto sólido e remove o projétil se colidir
        if (colisao((int) x, (int) y)) {
            Game.entidades.remove(this);
        }
    }


    private boolean colideComPlayer() {
        Rectangle projBounds = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
        Rectangle playerBounds = new Rectangle(Game.player.getX() + Game.player.maskx, Game.player.getY() + Game.player.masky, Game.player.maskw, Game.player.maskh);
        return projBounds.intersects(playerBounds);
    }

    public void render(Graphics g) {
        g.drawImage(projetil[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
    }

    public boolean colisao(int nextx, int nexty) {
        Rectangle projBounds = new Rectangle(nextx + maskx, nexty + masky, maskw, maskh);
        for (int i = 0; i < Game.entidades.size(); i++) {
            Entity entidade = Game.entidades.get(i);
            if (entidade instanceof Solido) {
                Rectangle solido = new Rectangle(entidade.getX() + maskx, entidade.getY() + masky, maskw, maskh);    
                if (projBounds.intersects(solido)) {
                    return true;
                }
            }
        }
        return false;
    }
}
