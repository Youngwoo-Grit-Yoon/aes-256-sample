package com.example.aes256;

import com.example.aes256.utility.Aes256;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.MessageFormat;

@SpringBootApplication
public class Aes256Application {
	public static void main(String[] args) {
		SpringApplication.run(Aes256Application.class, args);
	}

	@Bean
	public CommandLineRunner run() {
		return (args) -> {
			String encryptedText = Aes256.encrypt(Aes256.padString("01080036455"));
			System.out.println(MessageFormat.format("""
					암호문 : {0}""", encryptedText));

			String decryptedText = Aes256.decrypt(encryptedText);
			System.out.println(MessageFormat.format("""
					복호문 : {0}""", decryptedText));
		};
	}
}
