import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class RegistroVacunasGUI extends JFrame implements ActionListener {
    private JTextField campoCui;
    private JTextArea areaVacunas;
    private JLabel etiquetaMensaje;
    private JButton botonBuscar, botonRegistrar;
    private HashMap<String, Vacuna> registroVacunas;

    public RegistroVacunasGUI() {
        super("Registro de Vacunas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        // Crear componentes de la interfaz gráfica
        campoCui = new JTextField(10);
        areaVacunas = new JTextArea(10, 30);
        areaVacunas.setEditable(false);
        etiquetaMensaje = new JLabel();
        botonBuscar = new JButton("Buscar");
        botonRegistrar = new JButton("Registrar");
        botonBuscar.addActionListener(this);
        botonRegistrar.addActionListener(this);

        // Organizar componentes en paneles y agregarlos al frame
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.add(new JLabel("Ingrese CUI: "));
        panelBusqueda.add(campoCui);
        panelBusqueda.add(botonBuscar);
        add(panelBusqueda, BorderLayout.NORTH);

        JScrollPane panelVacunas = new JScrollPane(areaVacunas);
        add(panelVacunas, BorderLayout.CENTER);

        JPanel panelRegistro = new JPanel();
        panelRegistro.add(botonRegistrar);
        panelRegistro.add(etiquetaMensaje);
        add(panelRegistro, BorderLayout.SOUTH);

        // Leer registro de vacunas del archivo, si existe
        registroVacunas = new HashMap<String, Vacuna>();
        File archivoVacunas = new File("registro_vacunas.txt");
        if (archivoVacunas.exists()) {
            try (BufferedReader lector = new BufferedReader(new FileReader(archivoVacunas))) {
                String linea;
                while ((linea = lector.readLine()) != null) {
                    String[] partes = linea.split(",");
                    String cui = partes[0].trim();
                    String fecha = partes[1].trim();
                    String tipo = partes[2].trim();
                    String dosis = partes[3].trim();
                    Vacuna vacuna = new Vacuna(fecha, tipo, dosis);
                    registroVacunas.put(cui, vacuna);
                }
            } catch (IOException ex) {
                System.err.println("Error leyendo archivo de registro: " + ex.getMessage());
            }
        }

        // Mostrar la ventana
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonBuscar) {
            String cuiBuscado = campoCui.getText().trim();
            if (registroVacunas.containsKey(cuiBuscado)) {
                Vacuna vacuna = registroVacunas.get(cuiBuscado);
                areaVacunas.setText("Fecha: " + vacuna.getFecha() + "\n"
                        + "Tipo: " + vacuna.getTipo() + "\n"
                        + "Dosis: " + vacuna.getCentro());
                etiquetaMensaje.setText("");
            } else {
                areaVacunas.setText("");
                etiquetaMensaje.setText("No existe vacuna registrada para ese CUI");
            }
        } else if (e.getSource() == botonRegistrar) {
            String cuiNuevo = campoCui.getText().trim();
            if (registroVacunas.containsKey(cuiNuevo)) {
                etiquetaMensaje.setText("Ya existe un registro de vacuna para ese CUI");
            } else {
                RegistroVacunaDialog dialogo = new RegistroVacunaDialog(this, cuiNuevo);
                Vacuna nuevaVacuna = dialogo.mostrar();
                if (nuevaVacuna != null) {
                    registroVacunas.put(cuiNuevo, nuevaVacuna);
                    guardarRegistro();
                    etiquetaMensaje.setText("Registro de vacuna guardado");
                } else {
                    etiquetaMensaje.setText("Registro de vacuna cancelado");
                }
            }
        }
    }

    private void guardarRegistro() {
        File archivoVacunas = new File("registro_vacunas.txt");
        try (FileWriter escritor = new FileWriter(archivoVacunas)) {
            for (String cui : registroVacunas.keySet()) {
                Vacuna vacuna = registroVacunas.get(cui);
                String linea = cui + ", " + vacuna.getFecha() + ", " + vacuna.getTipo()
                        + ", " + vacuna.getCentro() + "\n";
                escritor.write(linea);
            }
        } catch (IOException ex) {
            System.err.println("Error escribiendo archivo de registro: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new RegistroVacunasGUI();
    }
}