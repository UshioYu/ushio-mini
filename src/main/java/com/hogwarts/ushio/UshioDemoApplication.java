package com.hogwarts.ushio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.hogwarts.ushio.dao")
public class UshioDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(UshioDemoApplication.class, args);
	}

}
