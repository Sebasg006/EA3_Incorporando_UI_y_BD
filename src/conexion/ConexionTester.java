package conexion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ConexionTester extends JFrame {

    private static final Color COLOR_EXITO = new Color(0, 140, 0);
    private static final Color COLOR_ERROR = new Color(200, 0, 0);

    private final ValidadorConexion validadorConexion = new ValidadorConexion();

    private JTextField campoUrl;
    private JTextField campoUsuario;
    private JPasswordField campoContrasena;
    private JButton botonProbarConexion;
    private JTextArea areaResultado;

    public ConexionTester() {
        super("Probador de conexión JDBC");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(560, 380);
        setLocationRelativeTo(null);
        setContentPane(crearPanelPrincipal());
    }

    private JPanel crearPanelPrincipal() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        panelPrincipal.add(crearPanelFormulario(), BorderLayout.NORTH);
        panelPrincipal.add(crearPanelResultado(), BorderLayout.CENTER);
        return panelPrincipal;
    }

    private JPanel crearPanelFormulario() {
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 8, 8));

        campoUrl = new JTextField("jdbc:sqlite:inventario.db");
        campoUsuario = new JTextField();
        campoContrasena = new JPasswordField();
        botonProbarConexion = new JButton("Probar conexión");
        botonProbarConexion.addActionListener(this::probarConexion);

        panelFormulario.add(new JLabel("URL de conexión:"));
        panelFormulario.add(campoUrl);
        panelFormulario.add(new JLabel("Usuario:"));
        panelFormulario.add(campoUsuario);
        panelFormulario.add(new JLabel("Contraseña:"));
        panelFormulario.add(campoContrasena);
        panelFormulario.add(new JLabel());
        panelFormulario.add(botonProbarConexion);
        return panelFormulario;
    }

    private JScrollPane crearPanelResultado() {
        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setLineWrap(true);
        areaResultado.setWrapStyleWord(true);
        areaResultado.setFont(new Font(Font.MONOSPACED, Font.BOLD, 13));

        JScrollPane panelResultado = new JScrollPane(areaResultado);
        panelResultado.setBorder(BorderFactory.createTitledBorder("Resultado de la prueba"));
        return panelResultado;
    }

    private void probarConexion(ActionEvent evento) {
        String url = campoUrl.getText().trim();
        String usuario = campoUsuario.getText().trim();
        String contrasena = new String(campoContrasena.getPassword());

        ResultadoConexion resultado = validadorConexion.probarConexion(url, usuario, contrasena);
        mostrarResultado(resultado);
    }

    private void mostrarResultado(ResultadoConexion resultado) {
        areaResultado.setForeground(resultado.exitosa() ? COLOR_EXITO : COLOR_ERROR);
        areaResultado.setText(resultado.mensaje());
        areaResultado.setCaretPosition(0);
    }
}
