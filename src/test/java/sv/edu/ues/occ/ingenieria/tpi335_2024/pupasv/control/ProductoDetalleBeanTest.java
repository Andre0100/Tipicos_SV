package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control;

import org.junit.Ignore;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.ProductoDetalle;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.ProductoDetallePK;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProductoDetalleBeanTest extends AbstractDataPersistenceTest<ProductoDetalle> {

    @InjectMocks
    ProductoDetalleBean cut;

    @Override
    public AbstractDataPersistence<ProductoDetalle> getBean() {
        return cut;
    }

    @Override
    public ProductoDetalle crearInstancia() {
        return new ProductoDetalle(new ProductoDetallePK(1, 1L));
    }

    @Override
    public List<ProductoDetalle> listaInstancias() {
        return List.of(
                new ProductoDetalle(1, 1L),
                new ProductoDetalle(2, 2L)
        );
    }

    @Override
    public Class<ProductoDetalle> getClaseEntidad() {
        return ProductoDetalle.class;
    }
}
