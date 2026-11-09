//Estuardo Antonio Molina 26878
package principal;
import modelo.CatalogoIntegraciones;
import controlador.ControladorIntegraciones;
import vista.VentanaPrincipal;
import vista.VistaConsola;
import javax.swing.SwingUtilities;
//Inicia Swing por defecto; argumento consola para menu de texto
public class Main {
    //Inicia el catalogo, el controlador y la interfaz de usuario
    public static void main(String[] args) {
        CatalogoIntegraciones catalogo = new CatalogoIntegraciones();
        catalogo.cargarIniciales();
        ControladorIntegraciones controlador = new ControladorIntegraciones(catalogo);
        if (args.length > 0 && args[0].equalsIgnoreCase("consola")) new VistaConsola(controlador).iniciar();
        else SwingUtilities.invokeLater(() -> new VentanaPrincipal(controlador).setVisible(true));
    }
}
    