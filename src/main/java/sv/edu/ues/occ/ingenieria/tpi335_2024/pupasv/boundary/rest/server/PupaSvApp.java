/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;

/**
 *
 * @author morales
 */
@ApplicationPath("v1")
public class PupaSvApp extends Application{
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public void getCine(){
        System.out.println("PUPASV BIENVENIDOS");
    }
}
