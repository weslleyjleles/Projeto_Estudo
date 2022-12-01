package br.com.ProjetoEstudo.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class ConexaoDao {

	private Connection connection = null;
	private java.sql.Statement statement = null;
	private ResultSet resultset = null;
	
	public ConexaoDao() {
		conectar();
	}
private void conectar() {
		
		String servidor = "jdbc:postgresql://localhost/5432/postgres/Projeto_Estudo";
		String usuario = "postgres";
		String senha = "root";
		String driver = "com.postgresql.cj.jdbc.Driver";

		try {
			Class.forName(driver);
			this.connection = DriverManager.getConnection(servidor, usuario, senha);
			this.statement = this.connection.createStatement();
		} catch (Exception e) {
			System.out.println("ERRO" + e.getMessage());
		}

	}

	
}
