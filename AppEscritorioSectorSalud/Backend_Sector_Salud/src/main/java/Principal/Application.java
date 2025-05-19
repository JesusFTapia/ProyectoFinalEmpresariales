/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Principal;

/**
 *
 * @author jl4ma
 */
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import service.RabbitMQSenderService;

@SpringBootApplication
@EntityScan("model")
@EnableJpaRepositories(basePackages = "repository") 
@ComponentScan(basePackages = {"controlador", "service", "repository"})
 // Asegúrate de que el paquete 'service' esté siendo escaneado
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // Este método será ejecutado al iniciar la aplicación
//    @Bean
//    public CommandLineRunner demo(RabbitMQSenderService senderService) {
//        return (args) -> {
////             Enviar un mensaje directamente desde el main para probar
////            senderService.enviarSolicitudExpediente("247313", "SoIr4DRRA4dmMk8FcDJBvMppOPT2", "Gomez");
//            System.out.println("Mensaje enviado a la cola Cliente/Servidor");
//            
//            senderService.enviarRespuestaSolicitud("123456", "abcd-1234", "Ficticio", true, "2025-05-14 00:00");
//            senderService.enviarAgendarCita("235633", "V2fDWB1zSeMunfkwI4MeSS3PESk1", "Jesus Tapía", "2025-10-17 09:00");
//            //formato fecha 2000-00-00 00:00
////            senderService.enviarRespuestaSolicitud("235633", "abcd-1234", "Ficticio", true, "2025-05-14 00:00");
//        };
//    }
    
}
