package entidades;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.Game;
import mundo.Camera;

public class Merendeira extends Entity {
    private int frameIndex = 0;
    private int maxFrames = 5; // 60 ticks para aproximadamente 1 segundo
    private int frameCounter = 0;
    public BufferedImage[] merenda;
    public int maskx = 0, masky = 0, maskw = 16, maskh = 16;

    private int shootCooldown = 180; // Tempo entre disparos
    private int shootTimer = 0;
    private boolean projectileInAir = false; // Verifica se o projétil está no ar

    public Merendeira(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        merenda = new BufferedImage[3];
        for (int i = 0; i < 3; i++) {
            merenda[i] = Game.sprite.getSprite(32 + (i * 16), 48, 16, 16);
        }
    }

    @Override
    public void tick() {
        // Apenas troca os frames se o projétil não estiver no ar
        if (!projectileInAir) {
            frameCounter++;
            if (frameCounter >= maxFrames) {
                frameCounter = 0;
                frameIndex++;
                if (frameIndex >= 3) {
                    frameIndex = 0;
                    shoot(); // Atira somente após completar o ciclo de animação
                }
            }
        }

        // Controle do tempo entre disparos
        shootTimer++;
        if (shootTimer >= shootCooldown) {
            projectileInAir = false; // Permite trocar de frame novamente
            shootTimer = 0;
        }
        
        // Verifica colisão com o jogador
        if (isCollidingWithPlayer()) {
            System.out.println("Colidiu com o jogador!");
        }
    }

    private void shoot() {
        // Cria um novo projétil e dispara
        int projX = this.getX();
        int projY = this.getY();
        int projWidth = 16;
        int projHeight = 16;

        Projetil proj = new Projetil(projX, projY, projWidth, projHeight, null);
        Game.entidades.add(proj); // Adiciona o projétil à lista de entidades do jogo
        projectileInAir = true; // Marca que o projétil está no ar

        // Retorna ao frame inicial enquanto o projétil estiver no ar
        frameIndex = 0;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(merenda[frameIndex], this.getX() - Camera.x, this.getY() - Camera.y, null);
    }

    // Mantém o método de colisão com o jogador
    public boolean isCollidingWithPlayer() {
        Rectangle itemBounds = new Rectangle((int) (this.x + this.maskx), (int) (this.y + this.masky), this.maskw, this.maskh);
        Rectangle playerBounds = new Rectangle((int) (Game.player.getX() + Game.player.maskx), (int) (Game.player.getY() + Game.player.masky), Game.player.maskw, Game.player.maskh);
        return itemBounds.intersects(playerBounds);
    }
}
