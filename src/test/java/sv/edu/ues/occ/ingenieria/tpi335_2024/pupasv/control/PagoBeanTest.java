package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Pago;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PagoBeanTest extends AbstractDataPersistenceTest<Pago> {

    @InjectMocks
    PagoBean cut;

    @Override
    public AbstractDataPersistence<Pago> getBean() {
        return cut;
    }

    @Override
    public Pago crearInstancia() {
        return new Pago(1L);
    }

    @Override
    public List<Pago> listaInstancias() {
        return List.of(new Pago(1L), new Pago(2L));
    }

    @Override
    public Class<Pago> getClaseEntidad() {
        return Pago.class;
    }
}
