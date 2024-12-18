package com.generation.blogpessoal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.blogpessoal.model.Postagem;

public interface PostagemRepository extends JpaRepository<Postagem, Long> {

	public List<Postagem> findAllByTituloContainingIgnoreCase(@Param("titulo") String titulo);
	// findAllByTituloContainingIgnoreCase:
	// Nome do método que segue as convenções de nomenclatura do Spring Data JPA
	// para consultas de banco de dados.
	// findAllBy: Indica que o método retorna todas as instâncias que correspondem
	// ao critério.
	// Titulo: Refere-se ao campo titulo da entidade Postagem.
	// Containing: Especifica que a consulta deve buscar postagens cujo título
	// contenha a sequência de caracteres fornecida.
	// IgnoreCase: Indica que a consulta deve ignorar a diferença entre maiúsculas e
	// minúsculas ao buscar correspondências.
	// @Param("titulo") String titulo: Anotação que associa o parâmetro "titulo" ao
	// valor passado na consulta. String titulo é o valor que será procurado no campo
	// titulo das postagens.

}