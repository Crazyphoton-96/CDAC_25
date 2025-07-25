package com.enam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class EnamSimulationApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnamSimulationApplication.class, args);
        System.out.println("\n🚀 e-NAM Simulation Application Started Successfully!");
        System.out.println("🌐 API Base URL: http://localhost:8080/api");
        System.out.println("📚 Swagger UI: http://localhost:8080/api/swagger-ui.html");
        System.out.println("🗄️ H2 Database Console: http://localhost:8080/api/h2-console");
        System.out.println("💡 Frontend URL: http://localhost:3000");
    }
}