package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.unitTests;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.ComboDetalleBean;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.ComboDetalle;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.ComboDetallePK;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ComboDetalleBeanTest extends AbstractDataPersistenceTest<ComboDetalle> {

    @InjectMocks
    ComboDetalleBean cut;

    @Override
    public AbstractDataPersistence<ComboDetalle> getBean() {
        return cut;
    }

    @Override
    public ComboDetalle crearInstancia() {
        return new ComboDetalle(new ComboDetallePK(1,1L));
    }

    @Override
    public List<ComboDetalle> listaInstancias() {
        return List.of(
                new ComboDetalle(1,1L),
                new ComboDetalle(2,2L)
        );
    }

    @Override
    public Class<ComboDetalle> getClaseEntidad() {
        return ComboDetalle.class;
    }
}
