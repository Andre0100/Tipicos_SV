package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.unitTests;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.PagoDetalleBean;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.PagoDetalle;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PagoDetalleBeanTest extends AbstractDataPersistenceTest<PagoDetalle> {

    @InjectMocks
    PagoDetalleBean cut;

    @Override
    public AbstractDataPersistence<PagoDetalle> getBean() {
        return cut;
    }

    @Override
    public PagoDetalle crearInstancia() {
        return new PagoDetalle(1L);
    }

    @Override
    public List<PagoDetalle> listaInstancias() {
        return List.of(new PagoDetalle(1L), new PagoDetalle(2L));
    }

    @Override
    public Class<PagoDetalle> getClaseEntidad() {
        return PagoDetalle.class;
    }
}
