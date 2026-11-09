
 //Estuardo Antonio Molina 26878
package vista;
import controlador.ControladorIntegraciones;
import modelo.IntegracionAPI;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
//Ventana principal del sistema de integraciones
public class VentanaPrincipal extends JFrame {
    private final ControladorIntegraciones controlador;
    private final DefaultTableModel tablaModelo;
    private final JTable tabla;
    private final JTextField id = new JTextField(8);
    private final JTextField referencia = new JTextField("PED-1001", 10);
    private final JTextField monto = new JTextField("150.00", 7);
    private final JComboBox<String> operacion = new JComboBox<String>(new String[]{"Consultar", "Cobrar", "Reintegrar", "Documento", "Auditar"});
    private final JTextArea salida = new JTextArea(7, 45);
    private final JLabel resumen = new JLabel();
    //Constructor que prepara los componentes de la ventana
    public VentanaPrincipal(ControladorIntegraciones controlador) {
        super("Sistema de APIs");
        this.controlador = controlador;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 630);
        setMinimumSize(new Dimension(780, 580));
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 240, 240));
        setLayout(new BorderLayout(8, 8));
        JPanel cabecera = new JPanel(new BorderLayout(8, 8));
        cabecera.setBorder(BorderFactory.createEmptyBorder(12, 15, 10, 15));
        cabecera.setBackground(new Color(225, 230, 235));
        JLabel titulo = new JLabel("Integraciones de APIs");
        titulo.setForeground(Color.BLACK);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 19));
        resumen.setForeground(Color.DARK_GRAY);
        resumen.setFont(new Font("SansSerif", Font.PLAIN, 13));
        cabecera.add(titulo, BorderLayout.NORTH);
        cabecera.add(resumen, BorderLayout.SOUTH);
        add(cabecera, BorderLayout.NORTH);
        tablaModelo = new DefaultTableModel(new String[]{"ID", "Proveedor", "Tipo", "Conexion", "Latencia (ms)"}, 0) {
            //Evita modificar directamente los datos de la tabla
            @Override
            public boolean isCellEditable(int fila, int col) {
                return false;
            }
        };
        tabla = new JTable(tablaModelo);
        tabla.setRowHeight(24);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() >= 0) {
                id.setText(String.valueOf(tabla.getValueAt(tabla.getSelectedRow(), 0)));
            }
        });
        JPanel centro = new JPanel(new BorderLayout(10, 10));
        centro.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT, 9, 5));
        JButton listar = new JButton("Mostrar todas");
        JButton ordenar = new JButton("Ordenar latencia");
        JButton buscar = new JButton("Buscar ID");
        barra.add(listar);
        barra.add(ordenar);
        barra.add(new JLabel("ID:"));
        barra.add(id);
        barra.add(buscar);
        centro.add(barra, BorderLayout.NORTH);
        centro.add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(centro, BorderLayout.CENTER);
        JPanel inferior = new JPanel(new BorderLayout(8, 8));
        inferior.setBorder(BorderFactory.createEmptyBorder(0, 20, 18, 20));
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 9, 5));
        JButton ejecutar = new JButton("Ejecutar");
        acciones.add(new JLabel("Operacion:"));
        acciones.add(operacion);
        acciones.add(new JLabel("Referencia:"));
        acciones.add(referencia);
        acciones.add(new JLabel("Monto Q:"));
        acciones.add(monto);
        acciones.add(ejecutar);
        inferior.add(acciones, BorderLayout.NORTH);
        salida.setEditable(false);
        salida.setLineWrap(true);
        salida.setWrapStyleWord(true);
        salida.setFont(new Font("Monospaced", Font.PLAIN, 13));
        salida.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        inferior.add(new JScrollPane(salida), BorderLayout.CENTER);
        add(inferior, BorderLayout.SOUTH);
        //Boton que muestra todas las integraciones
        listar.addActionListener(e -> cargar(controlador.listar()));
        //Boton que ordena las integraciones por latencia
        ordenar.addActionListener(e -> {
            cargar(controlador.ordenar());
            mostrar("Catalogo ordenado por latencia ascendente.");
        });
        //Boton que busca una integracion por su ID
        buscar.addActionListener(e -> {
            IntegracionAPI api = controlador.buscar(id.getText().trim());
            mostrar(api == null ? "No se encontro la API." : api.configuracion());
            if (api != null) {
                seleccionar(api.getId());
            }
        });
        //Boton que ejecuta la operacion seleccionada
        ejecutar.addActionListener(e -> {
            double valor;
            try {
                valor = Double.parseDouble(monto.getText().trim());
            }
            catch (NumberFormatException ex) {
                mostrar("Ingrese un monto numerico valido.");
                return;
            }
            String clave = id.getText().trim();
            if (clave.isEmpty()) {
                mostrar("Seleccione una fila o escriba un ID.");
                return;
            }
            mostrar(controlador.ejecutar(clave, (String) operacion.getSelectedItem(), referencia.getText().trim(), valor));
        });
        cargar(controlador.listar());
        mostrar("Seleccione una integracion para consultar o realizar una operacion.");
    }
    //Metodo que actualiza la tabla con las integraciones
    private void cargar(ArrayList<IntegracionAPI> datos) {
        tablaModelo.setRowCount(0);
        for (IntegracionAPI api : datos) {
            tablaModelo.addRow(new Object[]{api.getId(), api.getProveedor(), api.getTipo(), api.isConectada() ? "Conectada" : "Desconectada", api.getLatencia()});
        }
        resumen.setText("  " + controlador.resumen());
    }
    //Metodo que selecciona una integracion de la tabla
    private void seleccionar(String clave) {
        for (int i = 0; i < tabla.getRowCount(); i++) {
            if (tabla.getValueAt(i, 0).equals(clave)) {
                tabla.setRowSelectionInterval(i, i);
                tabla.scrollRectToVisible(tabla.getCellRect(i, 0, true));
                break;
            }
        }
    }
    //Metodo que muestra un mensaje en la ventana
    private void mostrar(String mensaje) {
        salida.setText(mensaje);
        salida.setCaretPosition(0);
    }
}
