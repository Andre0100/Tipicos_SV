package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto.ProductoConPrecioDTO;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Producto;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.ProductoPrecio;

@Stateless
@LocalBean
public class ProductoBean extends AbstractDataPersistence<Producto> implements Serializable{

    @Inject
    protected TipoProductoBean tPBean;
    
    @PersistenceContext(unitName = "PupaPU")
    EntityManager em;
    
    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public ProductoBean(){
        super(Producto.class);
    }
    
    public List<Producto> findByActivo(){
        
        try{
            return em.createNamedQuery("Producto.findByActivo", Producto.class).
            setParameter("activo", true).getResultList();
 
        }catch(Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return List.of();
    }
    
    
    public Map<String, List<ProductoConPrecioDTO>> getProductosAgrupadosPorTipo(){
        
        Map<String, List<ProductoConPrecioDTO>> resultados = new HashMap<>();

        //obtener tipos de productos activos
        List<String> tiposProducto  = tPBean.findByNombreTipoProductoName();
        
        
        //POr tipo obtener los productos junto con su precio
        for(String tipo: tiposProducto){
            List<Object[]> productosConPrecios = em.createNamedQuery("Producto.findByTipoConPrecio", Object[].class)
                    .setParameter("nombreTipo", tipo)
                    .getResultList();
            
            List<ProductoConPrecioDTO> productosDTO = productosConPrecios.stream()
                .map(result -> new ProductoConPrecioDTO(
                        (Producto) result[0], 
                        (ProductoPrecio) result[1]))
                .collect(Collectors.toList());
        
        resultados.put(tipo, productosDTO);
        }
        return resultados;
    }

}
