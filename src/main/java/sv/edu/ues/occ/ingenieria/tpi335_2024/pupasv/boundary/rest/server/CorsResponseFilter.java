/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author andrea
 */

/** Flujo del Filtro: 
    El navegador hace una petición al backend
    El filtro añade las cabeceras CORS
    Si es preflight (OPTIONS), termina inmediatamente
    Si no, continúa con la cadena de filtros (chain.doFilter())
 */



@WebFilter("/*") //Anotacion indica que este filtro se aplicara a todas las URLs (/*) de la app y se ejecuta automaticamente para cada peticion HTTP
public class CorsResponseFilter implements Filter {
    
    @Override //Esta anotación indica que el método está sobrescribiendo un método de su clase padre (en este caso, de la interfaz Filter)
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response; 
        
        //Permite solicitudes desde el frontend en http://localhost:3000 ( cambiarla por la URL real del frontend en producción )
        res.setHeader("Access-Control-Allow-Origin", "http://localhost:3000"); //Cambia a la URL de tu frontend
        
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD"); //Habilita los métodos HTTP más comunes para APIs REST
        
        res.setHeader("Access-Control-Allow-Headers", "Origin, Accept, Content-Type, Access-Control-Request-Method, " +
                "Access-Control-Request-Headers, Authorization,not-found-id-sala");  //Autoriza cabeceras personalizadas como Authorization y not-found-id-sala. Incluye cabeceras estándar y personalizadas
        
        res.setHeader("Access-Control-Allow-Credentials", "true"); //Permite el envío de cookies/credenciales (true)
        
        res.setHeader("Access-Control-Expose-Headers", "Location"); //Expone la cabecera Location al frontend

        // Verificar si es una preflight request
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            res.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        chain.doFilter(request, response);
    }

}
