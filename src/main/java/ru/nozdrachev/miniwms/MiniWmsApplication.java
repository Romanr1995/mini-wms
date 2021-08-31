package ru.nozdrachev.miniwms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class MiniWmsApplication {

	public static void main(String[] args) {
//		SpringApplication.run(MiniWmsApplication.class, args);

		List<String> list = List.of("hello","ok","privet","foo","bar");
		List<Integer> collect = list.stream()
				.map(s -> s.length())
				.collect(Collectors.toList());
		
		System.out.println(collect);
	}

}
