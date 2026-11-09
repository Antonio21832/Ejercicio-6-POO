//Estuardo Antonio Molina 26878
package modelo;
import java.util.Objects;
//Clase base y unica clase de elemento almacenado en el catalogo
public abstract class IntegracionAPI implements Comparable<IntegracionAPI> {
    private final String id;
    private final String proveedor;
    private final String endpoint;
    private final int latencia;
    private boolean conectada;
    //Metodo que realiza la operacion IntegracionAPI
    protected IntegracionAPI(String id, String proveedor, String endpoint, int latencia, boolean conectada) {
        if (id == null || id.trim().isEmpty() || proveedor == null || proveedor.trim().isEmpty() || endpoint == null || endpoint.trim().isEmpty() || latencia < 0) throw new IllegalArgumentException("Configuracion de API invalida.");
        this.id = id;
        this.proveedor = proveedor;
        this.endpoint = endpoint;
        this.latencia = latencia;
        this.conectada = conectada;
    }
    //Devuelve el identificador de la API
    public String getId() {
        return id;
    }
    //Devuelve el nombre del proveedor
    public String getProveedor() {
        return proveedor;
    }
    //Devuelve la direccion simulada del servicio
    public String getEndpoint() {
        return endpoint;
    }
    //Devuelve la latencia promedio en milisegundos
    public int getLatencia() {
        return latencia;
    }
    //Indica si la conexion esta activa
    public boolean isConectada() {
        return conectada;
    }
    //Actualiza el estado de la conexion
    public void setConectada(boolean conectada) {
        this.conectada = conectada;
    }
    //Devuelve el tipo de integracion
    public abstract String getTipo();
    //Las operaciones no soportadas se ignoran de forma segura, sin excepciones
    public String ejecutar(String operacion, String referencia, double monto) {
        return "IGNORADA: " + getProveedor() + " no admite la operacion " + operacion + ".";
    }
    //Metodo que realiza la operacion configuracion
    public String configuracion() {
        return "ID: " + id + "\nProveedor: " + proveedor + "\nTipo: " + getTipo() + "\nEndpoint simulado: " + endpoint + "\nLatencia: " + latencia + " ms\nEstado: " + (conectada ? "Conectada" : "Desconectada");
    }
    //Compara las integraciones por latencia y luego por ID
    @Override
    public int compareTo(IntegracionAPI otra) {
        int c = Integer.compare(latencia, otra.latencia);
        return c != 0 ? c : id.compareTo(otra.id);
    }
    //Compara dos integraciones mediante su identificador
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IntegracionAPI)) {
            return false;
        }
        return id.equals(((IntegracionAPI)obj).id);
    }
    //Genera un codigo hash a partir del ID
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    //Devuelve la informacion resumida del objeto
    @Override
    public String toString() {
        return id + " - " + proveedor + " (" + getTipo() + ")";
    }
}
