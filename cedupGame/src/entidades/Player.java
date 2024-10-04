package entidades;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Iterator;

import effects.Sons;
import main.Game;
import mundo.Camera;

public class Player extends Entity {
    public int maskx = 0, masky = 0, maskw = 16, maskh = 16;
    public boolean right, left, down, up;
    public double speed = 1.5;
    
    public int direita = 1, esquerda = 0;
    public int direcaoAtual = direita;
    public boolean jump = false;
    public boolean isJump = false;
    public int jumpHeigth = 17;
    public int jumpFrames = 0;
    public Inimigo pl;
    private int itemCount;
    
    private Sons som;
    private boolean inAir = false;
    private float airSpeed = 0f;
    private float gravidade = 0.1f; 
    private float jumpSpeed = -3f; 
    private float fallSpeedAfterCollision = 0.3f;
    
    public double life = 100, maxLife = 100;
    public Rifinha coleta;
    public int movimentacao = 0;
    public int frames = 0, maxFrames = 6, index = 0, maxIndex = 3;
    
    public BufferedImage[] playerRight;
    public BufferedImage[] playerLeft;
    public BufferedImage playerParadoLeft;
    public BufferedImage playerParadoRight;
    public int posx, posy;

    public Player(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        playerRight = new BufferedImage[4];
        playerLeft = new BufferedImage[4];
        playerParadoLeft = Game.sprite.getSprite(16, 0, 16, 16);
        playerParadoRight = Game.sprite.getSprite(16, 16, 16, 16);
        
        for (int i = 0; i < 4; i++) {
            playerRight[i] = Game.sprite.getSprite(32 + (i * 16), 0, 16, 16);
        }
        for (int i = 0; i < 4; i++) {
            playerLeft[i] = Game.sprite.getSprite(80 - (i * 16), 16, 16, 16); 
        }
    }
    
    public void tick() {
        movimentacao = 0;
        // Movimentação horizontal
        if (right && !colisao((int)(x + speed), this.getY())) {
            x += speed;
            movimentacao = 1;
            direcaoAtual = direita;
        }
        if (left && !colisao((int)(x - speed), this.getY())) {
            x -= speed;
            movimentacao = 1;
            direcaoAtual = esquerda;
        }

        // Animação de movimento
        if (movimentacao == 1) {
            frames++;
            if (frames == maxFrames) {
                index++;
                frames = 0;
                if (index > maxIndex)
                    index = 0;
            }
        }

        // Lógica de pulo e gravidade
        if (!inAir) {
            if (jump) {
                tocarSom("/pulo.wav");
                inAir = true;
                airSpeed = jumpSpeed;
                jump = false;
            } else if (!colisao(this.getX(), (int)(this.getY() + 1))) {
                inAir = true;
                airSpeed = 0;
            }
        } else {
            airSpeed += gravidade;
            if (colisao(this.getX(), (int)(this.getY() + airSpeed))) {
                y -= airSpeed;
                inAir = false;
                airSpeed = fallSpeedAfterCollision;
            } else {
                y += airSpeed;
            }
        }
        
        if (damage(this.getX(), this.getY()-8)) {
            life -= 0.5;
        }
        if(check(this.getX(), this.getY())){
			posx = this.getX();
			posy = this.getY();
		}
        if(life <= 0) {
			setX(posx);
			setY(posy);
			life = 100;
		}

        // Ajuste da câmera
        Camera.x = Camera.Clamp(this.getX() - (Game.WIDTH / 2), 0, mundo.Mundo.WIDTH * 16 - Game.WIDTH);
        Camera.y = Camera.Clamp(this.getY() - (Game.HEIGHT / 2), 0, mundo.Mundo.HEIGHT * 16 - Game.HEIGHT);

        checkItemCollision(); // Verificar colisão com itens
    }

    private void checkItemCollision() {
        Rectangle playerBounds = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
        Iterator<Rifinha> iterator = Game.rifinha.iterator();

        while (iterator.hasNext()) {
            Rifinha rifinha = iterator.next();
            Rectangle itemBounds = new Rectangle(rifinha.getX() + rifinha.maskx, rifinha.getY() + rifinha.masky, rifinha.maskw, rifinha.maskh);
            if (playerBounds.intersects(itemBounds)) {
                Game.coletadas++; // Atualiza o contador global
                itemCount++;
                iterator.remove(); // Remove da lista de rifinha
                break;
            }
        }
    }



    public int getItemCount() {
        return itemCount;
    }
    
    public void jump() {
        if (!inAir) {
            inAir = true;
            airSpeed = jumpSpeed;
        }
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
    public boolean check(int nextx, int nexty) {
		Rectangle player = new Rectangle(nextx + maskx, nexty+masky,maskw,maskh);
		for(int i = 0; i < Game.entidades.size(); i++) {
			Entity entidade = Game.entidades.get(i);
			if(entidade instanceof Check) {
				Rectangle solido = new Rectangle(entidade.getX() + maskx, entidade.getY()+masky,maskw,maskh);	
				if(player.intersects(solido)) {
					return true;
				}
			}
			
		}
		return false;
	}
    public boolean damage(int nextx, int nexty) {
        Rectangle player = new Rectangle(nextx + maskx, nexty + masky, maskw, maskh);
        for (int i = 0; i < Game.inimigo.size(); i++) {
            Inimigo entidade = Game.inimigo.get(i);
            Rectangle solido = new Rectangle(entidade.getX() + maskx, entidade.getY() + masky, maskw, maskh);    
            if (player.intersects(solido)) {
                pl = entidade;
                return true;
            }
        }
        return false;
    }
    
    public void tocarSom(String s) {
        som = new Sons(s);
        som.Tocar();
        som.Parar();
    }

    public void render(Graphics g) {
        if (direcaoAtual == direita && movimentacao == 1) {
            g.drawImage(playerRight[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
        }
        if (direcaoAtual == direita && movimentacao == 0) {
            g.drawImage(playerParadoRight, this.getX() - Camera.x, this.getY() - Camera.y, null);
        }
        if (direcaoAtual == esquerda && movimentacao == 1) {
            g.drawImage(playerLeft[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
        }
        if (direcaoAtual == esquerda && movimentacao == 0) {
            g.drawImage(playerParadoLeft, this.getX() - Camera.x, this.getY() - Camera.y, null);
        }
    }
}
