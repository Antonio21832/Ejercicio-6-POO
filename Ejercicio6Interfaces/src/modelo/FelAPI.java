//Estuardo Antonio Molina 26878
package modelo;
//Certificador FEL simulado
public class FelAPI extends IntegracionAPI implements Documentable, Auditable {
    private int facturas;
    //Metodo que realiza la operacion FelAPI
    public FelAPI(String id, String proveedor, String endpoint, int latencia, boolean conectada) {
        super(id, proveedor, endpoint, latencia, conectada);
    }
    //Devuelve el tipo de integracion
    @Override
    public String getTipo() {
        return "FEL";
    }
    //Metodo que realiza la operacion generarDocumento
    @Override
    public String generarDocumento(String referencia) {
        facturas++;
        return "SIMULACION: XML FEL generado <factura referencia=\"" + referencia + "\" numero=\"" + facturas + "\"/>";
    }
    //Metodo que realiza la operacion auditar
    @Override
    public String auditar() {
        return "Auditoria " + getProveedor() + ": " + facturas + " facturas simuladas.";
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
        return super.ejecutar(operacion, referencia, monto);
    }
}
