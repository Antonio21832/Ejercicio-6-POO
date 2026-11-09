//Estuardo Antonio Molina 26878
package modelo;
//Integracion de solo lectura
public class ConsultaAPI extends IntegracionAPI implements Auditable {
    private int consultas;
    //Metodo que realiza la operacion ConsultaAPI
    public ConsultaAPI(String id, String proveedor, String endpoint, int latencia, boolean conectada) {
        super(id, proveedor, endpoint, latencia, conectada);
    }
    //Devuelve el tipo de integracion
    @Override
    public String getTipo() {
        return "Consulta";
    }
    //Metodo que realiza la operacion auditar
    @Override
    public String auditar() {
        return "Auditoria " + getProveedor() + ": " + consultas + " consultas simuladas.";
    }
    //Procesa la operacion solicitada o la ignora si no es compatible
    @Override
    public String ejecutar(String operacion, String referencia, double monto) {
        if (!isConectada()) {
            return "IGNORADA: API desconectada.";
        }
        if (operacion.equals("Consultar")) { consultas++; return "SIMULACION: estado de " + referencia + " consultado en " + getProveedor() + ": ACTIVO."; }
        if (operacion.equals("Auditar")) {
            return auditar();
        }
        return super.ejecutar(operacion, referencia, monto);
    }
}
