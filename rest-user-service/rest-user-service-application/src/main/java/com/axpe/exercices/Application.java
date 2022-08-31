/*******************************************************************************
 * 
 * Autor: coe.architecture@axpe.com
 * 
 * © Axpe Consulting S.L. 2022. Todos los derechos reservados.
 * 
 ******************************************************************************/

package com.axpe.exercices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Clase para arrancar el Webnode como una aplicación Spring-boot
 * 
 * @author coe.architecture@axpe.com
 *
 */
@SpringBootApplication
@EnableConfigurationProperties
public class Application {
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
}
