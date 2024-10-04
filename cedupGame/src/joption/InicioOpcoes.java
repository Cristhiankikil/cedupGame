package joption;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import database.Questionario;
import database.QuestionarioBd;

public class InicioOpcoes {
	
	 String entrada;
    public static String inicio() {
    	
    	return JOptionPane.showInputDialog( "Nome do Jogador" );
    	  
    	   
    }
 public static String EscolheJogo() {
    	QuestionarioBd conex = new QuestionarioBd();
    	String menu = "";
    	
    	try {
			ArrayList questionarios = conex.BuscaQuest();
			int i=0;
	    	while (i < questionarios.size()) {
	    		Questionario x = (Questionario)questionarios.get(i);
	    		menu = menu + x.getId()+" - "+x.getNome()+"\n";
	    		i++;
	    	}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    		
    	
    	
    	return JOptionPane.showInputDialog( "Selecione a opção: \n" + menu );
    	  
    	   
    }

   

 
	
}
