import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RegistroVacunaDialog extends JPanel {
    private JTextField campoFecha, campoTipo, campoDosis;

    public RegistroVacunaDialog(RegistroVacunasGUI parent, String cui) {
        campoFecha = new JTextField(10);
        campoTipo = new JTextField(10);
        campoDosis = new JTextField(10);
        add(new JTextField("Ingrese datos de vacuna para CUI " + cui));
        add(new JTextField("Fecha (DD/MM/AAAA): "));
        add(campoFecha);
        add(new JTextField("Tipo: "));
        add(campoTipo);
        add(new JTextField("Dosis: "));
        add(campoDosis);
    }

    public Vacuna mostrar() {
        int opcion = JOptionPane.showConfirmDialog(this, this, "Registro de Vacuna", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            String fecha = campoFecha.getText().trim();
            String tipo = campoTipo.getText().trim();
            String dosis = campoDosis.getText().trim();
            return new Vacuna(fecha, tipo, dosis);
        } else {
            return null;
        }
    }
}