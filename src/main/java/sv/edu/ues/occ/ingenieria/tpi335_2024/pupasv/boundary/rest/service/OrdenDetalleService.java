///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.service;
//
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.inject.Inject;
//import jakarta.inject.Named;
//import jakarta.ws.rs.NotFoundException;
//import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.dto.OrdenDetalleDTO;
//import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.OrdenDetalleBean;
//import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.OrdenDetallePKBean;
//import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.OrdenDetalle;
//import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.OrdenDetallePK;
//
///**
// *
// * @author morales
// */
//@Named
//@ApplicationScoped
//public class OrdenDetalleService {
//    
//    @Inject
//    private OrdenDetalleBean OrdenDetalleBean;
//    
//    //Bean de prueba
//    @Inject
//    private OrdenDetallePKBean OrdenDetallePKBean;
//    
//    public OrdenDetalleDTO obtenerPorID(Long id){
//        OrdenDetalle ordenDeta = OrdenDetalleBean.findById(id);
//        
//        //Prueba del bean pk
//        OrdenDetallePK ordepk = OrdenDetallePKBean.findById(id);
//        
//        
//        if(ordenDeta == null){
//            throw new NotFoundException("Orden no encontrada");
//        }
//        return toDTO(ordepk);
//    }
//    
//    
//    private OrdenDTO toDTO(OrdenDetallePK ordenDeta){
//        OrdenDetalleDTO dto = new OrdenDetalleDTO();
//        dto.setId(ordenDeta.get)
//    }
//}
