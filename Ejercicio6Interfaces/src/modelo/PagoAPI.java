//Estuardo Antonio Molina 26878
package modelo;
//Integracion financiera: tarjeta, banco o billetera
public class PagoAPI extends IntegracionAPI implements Transaccional, Auditable {
    private int operaciones;
    //Metodo que realiza la operacion PagoAPI
    public PagoAPI(String id, String proveedor, String endpoint, int latencia, boolean conectada) {
        super(id, proveedor, endpoint, latencia, conectada);
    }
    //Devuelve el tipo de integracion
    @Override
    public String getTipo() {
        return "Pagos";
    }
    //Metodo que realiza la operacion cobrar
    @Override
    public String cobrar(double monto) {
        if (monto <= 0 || !Double.isFinite(monto)) {
            return "Monto invalido.";
        }
        operaciones++;
        return String.format("SIMULACION: cobro autorizado por Q%.2f en %s.", monto, getProveedor());
    }
    //Metodo que realiza la operacion reintegrar
    @Override
    public String reintegrar(double monto) {
        if (monto <= 0 || !Double.isFinite(monto)) {
            return "Monto invalido.";
        }
        operaciones++;
        return String.format("SIMULACION: reintegro autorizado por Q%.2f en %s.", monto, getProveedor());
    }
    //Metodo que realiza la operacion auditar
    @Override
    public String auditar() {
        return "Auditoria " + getProveedor() + ": " + operaciones + " operaciones financieras simuladas.";
    }
    //Procesa la operacion solicitada o la ignora si no es compatible
    @Override
    public String ejecutar(String operacion, String referencia, double monto) {
        if (!isConectada()) {
            return "IGNORADA: API desconectada.";
        }
        if (operacion.equals("Cobrar")) {
            return cobrar(monto);
        }
        if (operacion.equals("Reintegrar")) {
            return reintegrar(monto);
        }
        if (operacion.equals("Auditar")) {
            return auditar();
        }
        return super.ejecutar(operacion, referencia, monto);
    }
}
