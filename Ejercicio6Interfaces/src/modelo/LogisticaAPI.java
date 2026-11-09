//Estuardo Antonio Molina 26878
package modelo;
//Proveedor de entregas y guias de envio
public class LogisticaAPI extends IntegracionAPI implements Documentable, Auditable {
    private int documentos;
    //Metodo que realiza la operacion LogisticaAPI
    public LogisticaAPI(String id, String proveedor, String endpoint, int latencia, boolean conectada) {
        super(id, proveedor, endpoint, latencia, conectada);
    }
    //Devuelve el tipo de integracion
    @Override
    public String getTipo() {
        return "Logistica";
    }
    //Metodo que realiza la operacion generarDocumento
    @Override
    public String generarDocumento(String referencia) {
        documentos++;
        return "SIMULACION: guia de envio GUIA-" + getId() + "-" + documentos + " para " + referencia + ".";
    }
    //Metodo que realiza la operacion auditar
    @Override
    public String auditar() {
        return "Auditoria " + getProveedor() + ": " + documentos + " guias simuladas.";
    }
    //Procesa la operacion solicitada o la ignora si no es compatible
    @Override
    public String ejecutar(String operacion, String referencia, double monto) {
        if (!isConectada()) {
            return "IGNORADA: API desconectada.";
        }
        if (operacion.equals("Documento")) {
            return generarDocumento(referencia);
        }
        if (operacion.equals("Auditar")) {
            return auditar();
        }
        if (operacion.equals("Consultar")) {
            return "SIMULACION: paquete " + referencia + " en transito por " + getProveedor() + ".";
        }
        return super.ejecutar(operacion, referencia, monto);
    }
}
