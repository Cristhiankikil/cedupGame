package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class Conexao {
    public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	String url = "jdbc:postgresql://localhost:5433/postgres";
    String driver = "org.postgresql.Driver";
    String user = "postgres";
    String senha = "postgres";

	
	
	String erro = "";
	
	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

	
	public String Executa(String sql,String banco) {
        String retorno = "0";
        try {
        
            this.erro = "";
            String url = "jdbc:postgresql://localhost:5433/postgres";
            String driver = "org.postgresql.Driver";
            String user = "postgres";
            String senha = "postgres";
            //sql = "";
            Class.forName(driver);
            Connection MyConn = DriverManager.getConnection(url, user, senha);
               
            
            PreparedStatement inserirCadsp = MyConn.prepareStatement(sql);
                inserirCadsp.executeUpdate();
                inserirCadsp.close();
            
            MyConn.close();
        } catch (Exception e) {
            this.setErro(""+e);
            System.out.println("" + e);
        }
        return retorno;
    }

}
