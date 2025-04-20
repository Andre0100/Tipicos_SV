package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.OrdenDetalle;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.OrdenDetallePK;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class OrdenDetalleBeanTest extends AbstractDataPersistenceTest<OrdenDetalle>{

    @InjectMocks
    OrdenDetalleBean cut;

    @Override
    public AbstractDataPersistence<OrdenDetalle> getBean() {
        return cut;
    }

    @Override
    public OrdenDetalle crearInstancia() {
        return new OrdenDetalle(new OrdenDetallePK(1,1L));
    }

    @Override
    public List<OrdenDetalle> listaInstancias() {
        return List.of(
                new OrdenDetalle(1,1L),
                new OrdenDetalle(2,2L)
        );
    }

    @Override
    public Class<OrdenDetalle> getClaseEntidad() {
        return OrdenDetalle.class;
    }
}
