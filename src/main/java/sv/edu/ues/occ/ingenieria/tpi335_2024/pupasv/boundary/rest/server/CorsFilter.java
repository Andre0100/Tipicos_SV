/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

/**
 *
 * @author morales
 */
@Provider
public class CorsFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        // Permitir todas las solicitudes de cualquier origen
        HttpServletRequest responseContext;
        containerResponseContext.getHeaders().add("Access-Control-Allow-Origin", "*");

        // Métodos permitidos
        containerResponseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");

        // Encabezados permitidos
        containerResponseContext.getHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");

        // Permitir encabezados expuestos
        containerResponseContext.getHeaders().add("Access-Control-Expose-Headers", "Authorization");
        containerResponseContext.getHeaders().add("Access-Control-Expose-Headers", "Authorization, Total-records");
    }
}