package com.generation.blogpessoal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogpessoalApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogpessoalApplication.class, args);
	}

}

//UserDetailsImp - Determina quais informações passarão por verificação
//UserDetailsServiceImp - Verifica as informaçoes com o banco de dados
//JwtService - responsável por gerar o token
//JwtAuthFilter - filtra as informações para garantir a encriptação dos dados e trata os erros
//BasicSecurityConfig - determina as rotas permitidas e realiza a codigificação de algumas informações