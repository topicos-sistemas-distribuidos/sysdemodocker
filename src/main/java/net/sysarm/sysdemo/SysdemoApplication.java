package net.sysarm.sysdemo;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import net.sysarm.sysdemo.util.Constantes;

/**
 * Classe principal da aplicacao
 */
@SpringBootApplication
public class SysdemoApplication implements ApplicationRunner, CommandLineRunner{
	//@Value("${spring.application.name}")
	//String name;
	@Value("${spring.datasource.url}")
	String datasource;
	/**
	 * metodo principal que carrega a aplicacao
	 */
	public static void main(String[] args) {
		new Constantes();
		String mainPath = "TBD";
		String uploadsPath = Constantes.uploadDirectory;
		String picturesPath = Constantes.picturesDirectory;
		String banco = "TBD";
		String portaBanco = "TBD";
		String ipServidor = "TBD";
		String ipServidorBD = "TBD";
		String portaSA = "TBD";

		System.out.println("Diretório principal: " + mainPath);
		System.out.println("Diretório de controle de uploads: " + uploadsPath);
		System.out.println("Diretório de fotos: " + picturesPath);
		System.out.println("Banco da aplicação: " + banco);
		System.out.println("Porta do banco: " + portaBanco);
		System.out.println("Porta do Servidor de Aplicação: " + portaSA);
		System.out.println("Ip do Servidor de Aplicação: " + ipServidor);
		System.out.println("Ip do Servidor de Banco de Dados: " + ipServidorBD);

		new File(uploadsPath).mkdir();

		SpringApplication.run(SysdemoApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		System.out.println("Comandos executados após a inicialização da aplicação");
		//mostra os valores do application.properties
		/*
		if (!name.isEmpty()){
			System.out.println("O nome da aplicação é: " + name);
		}
		*/
		if (!datasource.isEmpty()){
			System.out.println("datasouce: " + datasource);
		}

	}

	@Override
	public void run(String... arg0) throws Exception {
		System.out.println("Executa os comandos a partir da linha de comando de iniialização da aplicação via prompt de commando");
		if (arg0.length > 0){
			System.out.println("Argumento passado: " + arg0[0]);
		}
		
	}

}