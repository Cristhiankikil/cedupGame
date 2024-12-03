package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import entidades.Entity;

public class UserInterface {
    public void render(Graphics g) {
    	g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 9)); 
        g.drawString("Paciência", 18, 16); 
    	
    	g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 9)); 
        g.drawString("Paciência", 19, 15);    
      
        
        g.setColor(Color.BLACK);
        g.fillRect(19, 19, 52, 7);
        
           
        

        g.setColor(Color.RED);
        g.fillRect(20, 20, 50, 5);

        g.setColor(Color.GREEN);
        g.fillRect(20, 20, (int)((Game.player.life/Game.player.maxLife)*50),5);

        
        g.drawImage(Entity.rifinha, 55, 30, null);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 9)); 
        g.drawString("Rifas: " + Game.coletadas, 18, 41);
        
        g.drawImage(Entity.rifinha, 55, 30, null);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 9)); 
        g.drawString("Rifas: " + Game.coletadas, 19, 40);
        
        
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 9)); 
        g.drawString("Nivel: " + Game.level, 214, 16);
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 9)); 
        g.drawString("Nivel: " + Game.level, 215, 15);
        
        
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 8)); 
        g.drawString("FPS: " + Game.fps, 19, 130);
    }
}
