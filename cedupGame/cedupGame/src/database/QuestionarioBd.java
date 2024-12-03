package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class QuestionarioBd {
	
	String erro = "";
	
	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}
	
	
	public ArrayList<Perguntas> buscaDados(String cod_questionario) throws ClassNotFoundException, SQLException {
        ArrayList<Perguntas> list = new ArrayList<>();
        Conexao conex = new Conexao();
        String sql = "SELECT * FROM jogo where cod_questionario = " +cod_questionario; 
        Class.forName(conex.getDriver());
        Connection MyConn = DriverManager.getConnection(conex.getUrl(), conex.getUser(), conex.getSenha());
        Statement MyStm = MyConn.createStatement();
        ResultSet m = MyStm.executeQuery(sql);
        
        try {
            while (m.next()) {
            	Perguntas cp = new Perguntas();
                cp.setId(m.getInt("id"));
                cp.setCod_questionario(m.getString("cod_questionario"));
                cp.setPergunta(m.getString("pergunta"));
                cp.setResposta(m.getBoolean("resposta")+"");
                cp.setResposta_esperada(m.getString("resposta"));
                System.out.println("Chegou aqui banco "+cp.getPergunta()+cp.getResposta());
      
                list.add(cp);
            }
            m.close();
            MyStm.close();
            MyConn.close();
         
        } catch (Exception e) {
        	this.setErro(""+e);
            e.printStackTrace(); 
        }
        return list;
    }
	
	public ArrayList<Questionario> BuscaQuest() throws ClassNotFoundException, SQLException {
        ArrayList<Questionario> list = new ArrayList<>();
        Conexao conex = new Conexao();
        String sql = "SELECT * FROM questionario";
        Class.forName(conex.getDriver());
        Connection MyConn = DriverManager.getConnection(conex.getUrl(), conex.getUser(), conex.getSenha());
        Statement MyStm = MyConn.createStatement();
        ResultSet m = MyStm.executeQuery(sql);
        
        try {
            while (m.next()) {
            	Questionario cp = new Questionario();
                cp.setId(m.getInt("id"));
                cp.setNome(m.getString("nome"));
                
      
                list.add(cp);
            }
            m.close();
            MyStm.close();
            MyConn.close();
         
        } catch (Exception e) {
        	this.setErro(""+e);
            e.printStackTrace(); 
        }
        return list;
    }
	
	
	public int CriaJogada (String jogador,String questionario ) {
		
		int jogada = 0;
		try {
			this.erro = "";
            String url = "jdbc:postgresql://localhost:5433/postgres";
            String driver = "org.postgresql.Driver";
            String user = "postgres";
            String senha = "postgres";
            //sql = "";
            
            String sql = "insert into jogadas (nome_jogador, cod_questionario) values ('"+jogador+"', "+questionario+")";
            System.out.println(sql);
            Class.forName(driver);
            Connection MyConn = DriverManager.getConnection(url, user, senha);
               
            
            PreparedStatement inserirCadsp = MyConn.prepareStatement(sql);
                inserirCadsp.executeUpdate();
                inserirCadsp.close();
                
                
                Statement MyStm = MyConn.createStatement();
                String sqlLeitura = "select max(id) as id from jogadas";
                ResultSet m = MyStm.executeQuery(sqlLeitura);
                
                
                    while (m.next()) {
                    	
                        jogada=(m.getInt("id"));
                    }
                    m.close();
                    MyStm.close();
                    MyConn.close();
                    this.InserePerguntas(this.buscaDados(questionario), jogada);
        } catch (Exception e) {
            this.setErro(""+e);
            System.out.println("" + e);
        }
                return jogada;
		
	}
		
	
	public void InserePerguntas (ArrayList lista, int cod_jogadas  ) {		
				
		try {
			
			this.erro = "";
            String url = "jdbc:postgresql://localhost:5433/postgres";
            String driver = "org.postgresql.Driver";
            String user = "postgres";
            String senha = "postgres";
            //sql = "";
            
           
           // System.out.println(sql);
            Class.forName(driver);
            Connection MyConn = DriverManager.getConnection(url, user, senha);
               
            int ponteiro = 0;
			 while(ponteiro < lista.size()) {
				 Perguntas perguntas = (Perguntas)lista.get(ponteiro);
				 
				 String sql = "insert into jogadas_pergunta (cod_jogadas, cod_pergunta, texto_pergunta, resposta_esperada)"
				 		+ " values ("+cod_jogadas+","+perguntas.getId()+", '"+perguntas.getPergunta()+"','"+perguntas.getResposta_esperada()+"')";
				 
				 PreparedStatement inserirCadsp = MyConn.prepareStatement(sql);
	                inserirCadsp.executeUpdate();
	                inserirCadsp.close();
	                ponteiro++;
			 }
            
            
            
            MyConn.close();
        } catch (Exception e) {
            this.setErro(""+e);
            System.out.println("" + e);
        }
	}

	
	public ArrayList<Perguntas> buscaDaJogada(int cod_jogada) throws ClassNotFoundException, SQLException {
        ArrayList<Perguntas> list = new ArrayList<>();
        Conexao conex = new Conexao();
        String sql = "SELECT * FROM jogadas_pergunta where st_respondido is null and cod_jogadas = " +cod_jogada; 
        Class.forName(conex.getDriver());
        Connection MyConn = DriverManager.getConnection(conex.getUrl(), conex.getUser(), conex.getSenha());
        Statement MyStm = MyConn.createStatement();
        ResultSet m = MyStm.executeQuery(sql);
        
        try {
            while (m.next()) {
            	Perguntas cp = new Perguntas();
                cp.setId(m.getInt("id"));
                cp.setCod_pergunta(m.getInt("cod_pergunta"));
                cp.setPergunta(m.getString("texto_pergunta"));
                cp.setResposta(m.getBoolean("resposta_jogador")+"");
                cp.setResposta_esperada(m.getString("resposta_esperada"));
                cp.setCod_jogada(cod_jogada);
                
               System.out.println("Lendo da jogada: "+m.getString("cod_pergunta")+ m.getString("texto_pergunta")+new java.util.Date());
               
                list.add(cp);
            }
            
            System.out.println(sql);
            m.close();
            MyStm.close();
            MyConn.close();
         
        } catch (Exception e) {
        	this.setErro(""+e);
            e.printStackTrace(); 
        }
        return list;
    }
	
	
	public int buscaQtdPerguntas(int cod_jogada) throws ClassNotFoundException, SQLException {
 
        int retorno = 0;
        
        Conexao conex = new Conexao();
        String sql = "SELECT COUNT(id) as qtd FROM jogadas_pergunta where cod_jogadas = " +cod_jogada; 
        Class.forName(conex.getDriver());
        Connection MyConn = DriverManager.getConnection(conex.getUrl(), conex.getUser(), conex.getSenha());
        Statement MyStm = MyConn.createStatement();
        ResultSet m = MyStm.executeQuery(sql);
        
        try {
            while (m.next()) {
            	
            	retorno = m.getInt("qtd");
            }
            
            System.out.println(sql);
            m.close();
            MyStm.close();
            MyConn.close();
         
        } catch (Exception e) {
        	this.setErro(""+e);
            e.printStackTrace(); 
        }
        return retorno;
    }
	
	
	public String buscaJogador(int cod_jogada) throws ClassNotFoundException, SQLException {
		 
        String retorno = "";
        
        Conexao conex = new Conexao();
        String sql = " Select nome_jogador from jogadas where id = " +cod_jogada; 
        Class.forName(conex.getDriver());
        Connection MyConn = DriverManager.getConnection(conex.getUrl(), conex.getUser(), conex.getSenha());
        Statement MyStm = MyConn.createStatement();
        ResultSet m = MyStm.executeQuery(sql);
        
        try {
            while (m.next()) {
            	
            	retorno = m.getString("nome_jogador");
            }
            
            System.out.println(sql);
            m.close();
            MyStm.close();
            MyConn.close();
         
        } catch (Exception e) {
        	this.setErro(""+e);
            e.printStackTrace(); 
        }
        return retorno;
    }
	
	
	
	public String buscaPontos(int cod_jogada) throws ClassNotFoundException, SQLException {
		 
        String retorno = "";
        
        Conexao conex = new Conexao();
        String sql = "select count(cod_pergunta)as qtd from jogadas_pergunta where acertou is true and cod_jogadas = " +cod_jogada; 
        Class.forName(conex.getDriver());
        Connection MyConn = DriverManager.getConnection(conex.getUrl(), conex.getUser(), conex.getSenha());
        Statement MyStm = MyConn.createStatement();
        ResultSet m = MyStm.executeQuery(sql);
        
        try {
            while (m.next()) {
            	
            	retorno = m.getString("qtd");
            }
            
            System.out.println(sql);
            m.close();
            MyStm.close();
            MyConn.close();
         
        } catch (Exception e) {
        	this.setErro(""+e);
            e.printStackTrace(); 
        }
        return retorno;
    }
	
	
	
	public void RespondePerguntas (int cod_jogadas, int cod_pergunta, String resposta_jogador, String acertou) {		
		
		try {
			
			this.erro = "";
            String url = "jdbc:postgresql://localhost:5433/postgres";
            String driver = "org.postgresql.Driver";
            String user = "postgres";
            String senha = "postgres";
            //sql = "";
            
            /*String sql = "insert into jogadas_pergunta (st_respondido, cod_jogadas, cod_pergunta, resposta_jogador, acertou) values"
            		+ " ('true', "+cod_jogadas+","+cod_pergunta+",'"+resposta_jogador+"','"+acertou+"')";*/
            String sql = "update jogadas_pergunta set st_respondido = 'true', resposta_jogador ='"+resposta_jogador+"', acertou = '"+acertou+"'"
            		+ " where cod_jogadas = "+cod_jogadas+" and cod_pergunta = "+cod_pergunta;
            System.out.println(sql+new java.util.Date());
            Class.forName(driver);
            Connection MyConn = DriverManager.getConnection(url, user, senha);
               
            
            PreparedStatement inserirCadsp = MyConn.prepareStatement(sql);
                inserirCadsp.executeUpdate();
                inserirCadsp.close();
                
                
                	Statement MyStm = MyConn.createStatement();
                
                    MyStm.close();
                    MyConn.close();
                   
        } catch (Exception e) {
            this.setErro(""+e);
            System.out.println("" + e);
        }
                
		
	}
}
