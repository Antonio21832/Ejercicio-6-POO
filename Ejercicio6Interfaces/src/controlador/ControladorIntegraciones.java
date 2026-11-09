//Estuardo Antonio Molina 26878
package controlador;
import modelo.*;
import java.util.ArrayList;
//Controlador compartido por consola y Swing
public class ControladorIntegraciones {
    private final CatalogoIntegraciones catalogo;
    //Metodo que realiza la operacion ControladorIntegraciones
    public ControladorIntegraciones(CatalogoIntegraciones catalogo) {
        this.catalogo = catalogo;
    }
    //Metodo que realiza la operacion listar
    public ArrayList<IntegracionAPI> listar() {
        return catalogo.listar();
    }
    //Metodo que realiza la operacion ordenar
    public ArrayList<IntegracionAPI> ordenar() {
        return catalogo.ordenarPorLatencia();
    }
    //Metodo que realiza la operacion buscar
    public IntegracionAPI buscar(String id) {
        return catalogo.buscar(id);
    }
    //Procesa la operacion solicitada o la ignora si no es compatible
    public String ejecutar(String id, String operacion, String referencia, double monto) {
        IntegracionAPI api = buscar(id);
        return api == null ? "No existe la integracion " + id + "." : api.ejecutar(operacion, referencia, monto);
    }
    //Metodo que realiza la operacion resumen
    public String resumen() {
        return catalogo.total() + " integraciones  |  " + catalogo.conectadas() + " conectadas  |  " + (catalogo.total() - catalogo.conectadas()) + " desconectadas";
    }
    //Sobrecarga: parametros definidos por contratos de interfaces
    public String procesar(Transaccional api, double monto) {
        return api.cobrar(monto);
    }
    //Sobrecarga que recibe un contrato de interfaz para procesar una operacion
    public String procesar(Transaccional api, double monto, boolean reintegro) {
        return reintegro ? api.reintegrar(monto) : api.cobrar(monto);
    }
    //Sobrecarga que recibe un contrato de interfaz para procesar una operacion
    public String procesar(Documentable api, String referencia) {
        return api.generarDocumento(referencia);
    }
    //Sobrecarga que recibe un contrato de interfaz para procesar una operacion
    public String procesar(Auditable api) {
        return api.auditar();
    }
}
