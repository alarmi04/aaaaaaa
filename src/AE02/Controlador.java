package AE02;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class Controlador {
    private Model model;
    private Vista vista;
    private ActionListener actionListenerBtnLog;
    private ActionListener actionListenerBtnConsulta;
    private ActionListener actionListenerBtnCerrarSesion;
    private ActionListener actionListenerBtnCerrarAplicacion;

    /**
     * Constructor del controlador que establece las conexiones entre el modelo y la vista.
     *a
     * @param m Modelo
     * @param v Vista
     */
    Controlador(Model m, Vista v) {
        this.model = m;
        this.vista = v;
        control();
    }

    /**
     * Configuración de los listeners y acciones del controlador.
     */
    public void control() {
        model.conexion();

        actionListenerBtnLog = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (model.conexionLog(vista.getTxtUser().getText(), vista.getTxtContra().getText())) {
                    model.consultasAdmin(vista.getCmbConsulta());
                    vista.getPnlConsulta().setVisible(true);
                    vista.getPnlUsuari().setVisible(false);
                    vista.getBtnCerrarAplicacion().setVisible(true);
                }
            }
        };
        vista.getBtnLog().addActionListener(actionListenerBtnLog);

        actionListenerBtnConsulta = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                vista.getTxtaConsulta().setText(null);
                int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estàs segur d'executar la consulta?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirmacion != JOptionPane.YES_OPTION) {
                    vista.getTxtaConsulta().setText(null);
                } else {
                    if (vista.getTxtConsulta().getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "No pot estar buida la consulta.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        vista.getTxtaConsulta().setText(model.hacerConsultas(vista.getCmbConsulta(), vista.getTxtConsulta()).toString());
                        vista.getTxtConsulta().setText(null);
                    }
                }
            }
        };
        vista.getBtnConsulta().addActionListener(actionListenerBtnConsulta);

        actionListenerBtnCerrarSesion = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                model.CerrarSesion();
                vista.getTxtUser().setText("");
                vista.getTxtContra().setText("");
                vista.getTxtConsulta().setText("");
                vista.getCmbConsulta().removeAllItems();
                vista.getTxtaConsulta().setText("");
                vista.getPnlConsulta().setVisible(false);
                vista.getBtnCerrarAplicacion().setVisible(false);
                vista.getPnlUsuari().setVisible(true);
            }
        };
        vista.getBtnCerrarSesion().addActionListener(actionListenerBtnCerrarSesion);

        actionListenerBtnCerrarAplicacion = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                model.TancarConexio();
                System.exit(0);
            }
        };
        vista.getBtnCerrarAplicacion().addActionListener(actionListenerBtnCerrarAplicacion);
    }
}
