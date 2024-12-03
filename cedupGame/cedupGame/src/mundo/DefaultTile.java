package mundo;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class DefaultTile extends Tile {
    
    public DefaultTile(int x, int y) {
        super(x, y, createDefaultSprite());
    }
    
    private static BufferedImage createDefaultSprite() {
        BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();
        g.setColor(Color.RED); // Cor de preenchimento para identificar Tiles desconhecidos
        g.fillRect(0, 0, 16, 16);
        g.dispose();
        return img;
    }
}
