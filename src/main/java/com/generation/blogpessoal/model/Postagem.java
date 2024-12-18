package com.generation.blogpessoal.model;
import java.time.LocalDateTime;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
//sinalizando que é uma entidade no banco de dados
@Entity
//anotacao para dar nome a essa entidade que vai ser criada na tabela
@Table(name = "tb_postagens")
public class Postagem {
	//criacao das variaveis que vao existir na aplicacao
	//cricao da tabela no MySQL
	
		// 4 colunas que vao existir no banco de dados
	@Id //comentario para dizer ao banco de dados que essa variavel vai ser o ID da tabela
	@GeneratedValue(strategy = GenerationType.IDENTITY) //comentario para criar aquele auto_increment no id de forma indefinida (pra sempre)
	private Long id;
	
	@NotBlank //anotacao para tornar essa variavel uma NOT NULL que não aceita espaços vazios tbm, uma not null receberia um valor vazio
	@Size(min = 5 , max = 100) // determinando o tamanho da VARCHAR
	private String titulo;
	
	@NotBlank
	@Size(max = 1000) // posso criar apenas o max, sem precisar ter um minimo, aceitando qualquer valor > 0
	private String texto;
	
	@UpdateTimestamp // marca um campo em uma entidade que será automaticamente atualizado com o horário atual sempre que a entidade for atualizada.
	private LocalDateTime data;
	
			//criando uma chave estrangeira
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Tema tema;
	
	@ManyToOne
	@JsonIgnoreProperties("usuario")
	private Usuario usuario;
	
	//metodos para acessar essas variaveis 
		//criar para ter acesso aos valores
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public LocalDateTime getData() {
		return data;
	}
	public void setData(LocalDateTime data) {
		this.data = data;
	}
	public Tema getTema() {
		return tema;
	}
	public void setTema(Tema tema) {
		this.tema = tema;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}