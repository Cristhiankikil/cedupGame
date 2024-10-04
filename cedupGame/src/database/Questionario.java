package database;

import java.util.ArrayList;

public class Questionario {
	int id = 0;
	String nome = "";
	ArrayList perguntas = new ArrayList();
	
	public ArrayList getPerguntas() {
		return perguntas;
	}
	public void setPerguntas(ArrayList perguntas) {
		this.perguntas = perguntas;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

}
