package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Perguntas {
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPergunta() {
		return pergunta;
	}

	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

	public String getCod_questionario() {
		return cod_questionario;
	}

	public void setCod_questionario(String cod_questionario) {
		this.cod_questionario = cod_questionario;
	}
	public int getCod_pergunta() {
		return cod_pergunta;
	}

	public void setCod_pergunta(int cod_pergunta) {
		this.cod_pergunta = cod_pergunta;
	}
	
	
	int id = 0;
	int cod_pergunta = 0;
	

	String pergunta = "";
	String resposta = "";
	String resposta_esperada = "";
	public String getResposta_esperada() {
		return resposta_esperada;
	}

	public void setResposta_esperada(String resposta_esperada) {
		this.resposta_esperada = resposta_esperada;
	}


	String cod_questionario = "";
	
	public String toString() {
		return this.getPergunta();
	}
	
	

}
