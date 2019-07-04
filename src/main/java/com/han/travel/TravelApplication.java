package com.han.travel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.han.travel.dao")
public class TravelApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(TravelApplication.class, args);
	}

}
