package com.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;
import com.generation.blogpessoal.repository.TemaRepository;

import jakarta.validation.Valid;

// @RestController -define que recebera requisicoes (url, verbo, corpo)
// url - endereco da requisicao (endpoint)
// verbo- metodo http sera adicionado na classe controladora
// corpo da requisicao - objeto que contem os dados que serao persistidos no
// banco, nem toda requisicao envia dados no corpo da requisicao
@RestController
@RequestMapping("/postagens") // url do recurso postagens
@CrossOrigin(origins = "*", allowedHeaders = "*") // indica que a Classe controladora permitirá o recebimento de
													// requisições realizadas de fora do domínio
													// consumir a API
// url principal é /postagens
// a partir daqui, todos os metodos adicionados vao ter sua propria /url
public class PostagemController {
	@Autowired // injecao de dependencia
	private PostagemRepository postagemRepository;
	// private tipo nome
	// estou trazendo(injetando) PostagemRepository(jpa) para ser usada e chamando
	// por postagemRepository

	// funcao para retornar todos os valores registrados na tabela, com os valores
	// herdados de Postagem e em formato de lista
	// ResponseEntity vai criar um JSON os os atritutos de Postagem (herdados)
	// sempre usar ResponseEntity para requisições
	// ReponseEntity.ok (200),nunca da erro, retorna a lista sempre, seja vazia ou
	// com itens
	// funcionando como um Optional
	// postagemRepository.finlAll() - funcao JPA para retornar todos os itens dentro
	// de postagemRepository
	// getAll() é apenas o nome para chamar a função, boa pratica
	// ResponseEntity é o objeto de requisições e respostas responsavel por
	// trabalhar o JSON
	
	@Autowired
	private TemaRepository temaRepository;
	
	@GetMapping
	public ResponseEntity<List<Postagem>> getAll() { // objeto como list pois quero retornar todos os valores
		return ResponseEntity.ok(postagemRepository.findAll());
	}

	@GetMapping("/{id}") // {} serve para assinalar que esta esperando receber um valor
	public ResponseEntity<Postagem> getById(@PathVariable Long id) {
		return postagemRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				// vai gerar ReponseEntity com um objeto, nome getById
				// @PathVariable é usada para extrair valores da URL da solicitação e passá-los
				// como argumentos para o método do controlador
				// postagemRepository.findById(id), acessa o bando de dados com o id que o
				// usuario enviou
				// .map(classe Optional) faz o mapeamento para saber se o id existe no banco
				// .map vai encapsular o objeto para nao criar erro
				// (resposta -> ResponseEntity.ok(resposta)), caso exista o id no banco
				// retorna ok + as informações
				// ResponseEntity no lambda, faz o papel de retornar as informações no formato
				// JSON
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
		// se nao tiver, vai criar um objeto com uma mensagem de erro
		// ResponseEntity.status(HttpStatus.NOT_FOUND, Cria uma resposta HTTP com o
		// status 404 (Not Found).
		// .build(): Constrói a resposta HTTP com o status definido.
	}

	// Essa anotação indica que o método abaixo responderá a solicitações HTTP GET
	// para o endpoint /titulo/{titulo}, onde {titulo} é um valor variável que será
	// passado na URL.
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
	} 

	@PostMapping // metodo de post
	public ResponseEntity<Postagem> post(@Valid @RequestBody Postagem postagem) {
		// @Valid - Valida o objeto postagem conforme as anotações de validação
		// @RequestBody - Converte o corpo da requisição em um objeto Postagem
			if (temaRepository.existsById(postagem.getTema().getId()))
				return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
		// Retorna uma resposta HTTP 201 Created com a postagem salva
		// @Valid: Anota a postagem para garantir que as validações especificadas na
		// classe Postagem (como @NotNull, @Size, etc.) sejam aplicadas automaticamente.
		// Se os dados da postagem não atenderem às validações, uma resposta de erro
		// será gerada.
		// @RequestBody: Indica que o corpo da solicitação HTTP deve ser convertido em
		// um objeto , ou seja, um JSON
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema não existe!", null);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT) // Define que o método retorna um status 204 No Content, se a operação for
											// positiva
	// O status 204 indica que a solicitação foi bem-sucedida e que o servidor não
	// está retornando nenhum conteúdo.
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		// @PathVariable Long id - Extrai o valor do ID da URL e o passa como parâmetro
		// para o método
		Optional<Postagem> postagem = postagemRepository.findById(id);
		// Procura a postagem com o ID fornecido no repositório (ou seja, no banco de
		// dados). O método findById(id) retorna um objeto Optional<Postagem>, que pode
		// ou não conter uma postagem.
		if (postagem.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		// throw cria uma nova exception
		// Se a postagem não for encontrada, lança uma exceção ResponseStatusException
		// com o status HTTP 404 Not Found, indicando que o recurso solicitado não
		// existe.
		postagemRepository.deleteById(id);
		// Se a postagem for encontrada, deleta a postagem pelo ID fornecido utilizando
		// o método deleteById(id) do repositório.
	}

	// criacao metodo put
	@PutMapping
	public ResponseEntity<Postagem> put(@Valid @RequestBody Postagem postagem) {
		// @Valid - Valida o objeto postagem conforme as anotações de validação
		// @RequestBody - Converte o corpo da requisição em um objeto Postagem
		if (postagemRepository.existsById(postagem.getId())) {
			
			if(temaRepository.existsById(postagem.getTema().getId()))
				return ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem));
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			//metodo anterior
//		//return postagemRepository.findById(postagem.getId())// Procura a postagem pelo ID
//				
//				.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem)))
//				// Atualiza e retorna a postagem se encontrada
//				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());// Retorna 404 se não encontrada

	}

}