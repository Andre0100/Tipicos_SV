package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.ProductoPrecio;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.TipoProducto;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProductoPrecioBeanTest extends AbstractDataPersistenceTest<ProductoPrecio> {

    @InjectMocks
    ProductoPrecioBean cut;

    @Override
    public AbstractDataPersistence<ProductoPrecio> getBean() {
        return cut;
    }

    @Override
    public ProductoPrecio crearInstancia() {
        return new ProductoPrecio(1L);
    }

    @Override
    public List<ProductoPrecio> listaInstancias() {
        return List.of(new ProductoPrecio(1L), new ProductoPrecio(2L));
    }

    @Override
    public Class<ProductoPrecio> getClaseEntidad() {
        return ProductoPrecio.class;
    }
}
