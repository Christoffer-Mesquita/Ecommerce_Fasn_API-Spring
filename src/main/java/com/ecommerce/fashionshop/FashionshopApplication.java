package com.ecommerce.fashionshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Ecommerce para Loja de Roupa", description = "API responsavel por vendas e compras de produtos", version = "1.0"))
public class FashionshopApplication {
	public static void main(String[] args) {
		SpringApplication.run(FashionshopApplication.class, args);
	}
}