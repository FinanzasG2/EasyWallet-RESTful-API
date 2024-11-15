package com.example.EasyWalletAPI;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@OpenAPIDefinition(
		info = @Info(title = "EasyWallet API", version = "v1")
		/*servers = {
				@Server(url = "https://easywallet-restful-api.up.railway.app", description = "Railway API Server")
		}*/
)
@SpringBootApplication
public class EasyWalletApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasyWalletApiApplication.class, args);
	}

}
