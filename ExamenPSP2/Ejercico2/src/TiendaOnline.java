import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class TiendaOnline {

    static final  List<String> DISPONIBILIDAD = Collections.synchronizedList(new ArrayList<>());
    static Semaphore sala = new Semaphore(DISPONIBILIDAD);

}
