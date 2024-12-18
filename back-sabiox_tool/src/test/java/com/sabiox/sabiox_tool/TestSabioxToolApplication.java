package com.sabiox.sabiox_tool;

import org.springframework.boot.SpringApplication;

import br.com.sabiox.sabiox_tool.SabioxToolApplication;

public class TestSabioxToolApplication {

	public static void main(String[] args) {
		SpringApplication.from(SabioxToolApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
