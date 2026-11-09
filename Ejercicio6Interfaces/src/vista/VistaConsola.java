//Estuardo Antonio Molina 26878
package vista;
import controlador.ControladorIntegraciones;
import modelo.IntegracionAPI;
import java.util.Scanner;
//Menu de consola requerido en el enunciado
public class VistaConsola {
    private final ControladorIntegraciones controlador;
    private final Scanner entrada = new Scanner(System.in);
    //Metodo que realiza la operacion VistaConsola
    public VistaConsola(ControladorIntegraciones controlador) {
        this.controlador = controlador;
    }
    //Inicia el menu de consola
    public void iniciar() {
        while (true) {
            System.out.println("\nCENTRO API | 1 Listar | 2 Buscar | 3 Ordenar | 4 Operacion | 5 Salir");
            String opcion = entrada.nextLine().trim();
            if (opcion.equals("5")) return;
            if (opcion.equals("1") || opcion.equals("3")) {
                for (IntegracionAPI api : opcion.equals("1") ? controlador.listar() : controlador.ordenar()) System.out.println(api + " | " + api.getLatencia() + " ms | " + (api.isConectada() ? "Conectada" : "Desconectada"));
            } else if (opcion.equals("2")) {
                System.out.print("ID: "); IntegracionAPI api = controlador.buscar(entrada.nextLine().trim());
                System.out.println(api == null ? "No encontrada." : api.configuracion());
            } else if (opcion.equals("4")) {
                System.out.print("ID: "); String id = entrada.nextLine().trim();
                System.out.print("Operacion (Consultar/Cobrar/Reintegrar/Documento/Auditar): "); String op = entrada.nextLine().trim();
                System.out.print("Referencia: "); String ref = entrada.nextLine().trim();
                System.out.print("Monto Q: "); double monto;
                try { monto = Double.parseDouble(entrada.nextLine().trim()); } catch (NumberFormatException ex) { System.out.println("Monto invalido."); continue; }
                System.out.println(controlador.ejecutar(id, op, ref, monto));
            } else System.out.println("Opcion no valida.");
        }
    }
}
