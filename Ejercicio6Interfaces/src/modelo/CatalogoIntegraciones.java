//Estuardo Antonio Molina 26878
package modelo;
import java.util.ArrayList;
import java.util.Collections;
//Unica lista polimorfica persistente del sistema
public class CatalogoIntegraciones {
    private final ArrayList<IntegracionAPI> integraciones = new ArrayList<IntegracionAPI>();
    //Metodo que realiza la operacion agregar
    public boolean agregar(IntegracionAPI api) {
        if (api == null || buscar(api.getId()) != null) {
            return false;
        }
        return integraciones.add(api);
    }
    //Metodo que realiza la operacion buscar
    public IntegracionAPI buscar(String id) {
        for (IntegracionAPI api : integraciones) if (api.getId().equalsIgnoreCase(id)) return api;
        return null;
    }
    //Metodo que realiza la operacion listar
    public ArrayList<IntegracionAPI> listar() {
        return new ArrayList<IntegracionAPI>(integraciones);
    }
    //Metodo que realiza la operacion ordenarPorLatencia
    public ArrayList<IntegracionAPI> ordenarPorLatencia() {
        ArrayList<IntegracionAPI> copia = listar();
        Collections.sort(copia);
        return copia;
    }
    //Metodo que realiza la operacion total
    public int total() {
        return integraciones.size();
    }
    //Metodo que realiza la operacion conectadas
    public int conectadas() {
        int n = 0;
        for (IntegracionAPI api : integraciones) if (api.isConectada()) n++;
        return n;
    }
    //Registra las diez integraciones iniciales
    public void cargarIniciales() {
        agregar(new PagoAPI("P01", "Tarjeta Uno", "https://simulado.local/pagos/tarjeta", 120, true));
        agregar(new PagoAPI("P02", "Banco Transfer", "https://simulado.local/pagos/banco", 180, true));
        agregar(new PagoAPI("P03", "Billetera GT", "https://simulado.local/pagos/wallet", 95, true));
        agregar(new LogisticaAPI("L01", "Envio Express", "https://simulado.local/envios/express", 85, true));
        agregar(new LogisticaAPI("L02", "Ultima Milla", "https://simulado.local/envios/milla", 210, true));
        agregar(new LogisticaAPI("L03", "Ruta Nacional", "https://simulado.local/envios/ruta", 165, false));
        agregar(new FelAPI("F01", "Certificador FEL Uno", "https://simulado.local/fel/uno", 230, true));
        agregar(new FelAPI("F02", "Factura Electronica GT", "https://simulado.local/fel/dos", 195, true));
        agregar(new ConsultaAPI("C01", "Rastreo Global", "https://simulado.local/consulta/rastreo", 70, true));
        agregar(new ConsultaAPI("C02", "Estado Pedidos", "https://simulado.local/consulta/pedidos", 110, false));
    }
}
