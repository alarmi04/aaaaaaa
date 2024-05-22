package AE02;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Model {
    private Connection con;
    private boolean esAdmin;

    /**
     * Realitza la connexió a la base de dades utilitzant les dades del fitxer client.xml.
     */
    public void conexion() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(new File("client.xml"));
            NodeList nodeList = document.getElementsByTagName("connection");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                System.out.println("");
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    String url = eElement.getElementsByTagName("url").item(0).getTextContent();
                    String user = eElement.getElementsByTagName("user").item(0).getTextContent();
                    String password = eElement.getElementsByTagName("password").item(0).getTextContent();
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection(url, user, password);
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Realitza la connexió a la base de dades comprovant les credencials d'usuari i tipus d'accés.
     * Si l'usuari és un administrador, es connecta utilitzant les dades del fitxer admin.xml.
     *
     * @param user   Nom d'usuari
     * @param contra Contrasenya de l'usuari
     * @return Cert si les credencials són correctes, fals altrament
     */
    public boolean conexionLog(String user, String contra) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT user, pass, type FROM users");

            while (rs.next()) {
                if (rs.getString(1).equals(user) && rs.getString(2).equals(hashMD5(contra))) {
                    if (rs.getString(3).equals("admin")) {
                        con.close();
                        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                        Document document = dBuilder.parse(new File("admin.xml"));
                        NodeList nodeList = document.getElementsByTagName("connection");

                        for (int i = 0; i < nodeList.getLength(); i++) {
                            Node node = nodeList.item(i);

                            if (node.getNodeType() == Node.ELEMENT_NODE) {
                                Element eElement = (Element) node;
                                String url = eElement.getElementsByTagName("url").item(0).getTextContent();
                                String user2 = eElement.getElementsByTagName("user").item(0).getTextContent();
                                String password = eElement.getElementsByTagName("password").item(0).getTextContent();

                                Class.forName("com.mysql.cj.jdbc.Driver");
                                con = DriverManager.getConnection(url, user2, password);
                            }
                        }

                        System.out.println("Admin");
                        esAdmin = true;
                        return true;
                    } else {
                        System.out.println("Client");
                        esAdmin = false;
                        return true;
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Usuari o contrasenya incorrectes.", "Error d'inici de sessió", JOptionPane.ERROR_MESSAGE);
            rs.close();
            stmt.close();

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Genera el resum MD5 de la contrasenya proporcionada.
     *
     * @param input Contrasenya a xifrar
     * @return Resum MD5 de la contrasenya
     * @throws NoSuchAlgorithmException Excepció si l'algorisme de xifrat no està disponible
     */
    private String hashMD5(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(input.getBytes());
        byte[] digest = md.digest();

        StringBuilder hashedPassword = new StringBuilder();
        for (byte b : digest) {
            hashedPassword.append(String.format("%02x", b & 0xff));
        }

        return hashedPassword.toString();
    }

    /**
     * Afegeix les consultes possibles al desplegable, depenent si l'usuari és administrador o no.
     *
     * @param cmbConsulta Desplegable de consultes
     */
    public void consultasAdmin(JComboBox<String> cmbConsulta) {
        if (esAdmin) {
            cmbConsulta.addItem("SELECT");
            cmbConsulta.addItem("INSERT");
            cmbConsulta.addItem("UPDATE");
            cmbConsulta.addItem("DELETE");
        } else {
            cmbConsulta.addItem("SELECT");
        }
    }

    /**
     * Tanca la connexió a la base de dades amb una confirmació prèvia.
     */
    public void TancarConexio() {
        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estàs segur de tancar l'aplicació?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmacion != JOptionPane.YES_OPTION) {

        } else {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Tanca la sessió actual, reconnectant-se a la base de dades després de tancar-la.
     */
    public void CerrarSesion() {
        try {
            if (con == null || con.isClosed()) {
                JOptionPane.showMessageDialog(null, "La sessió ja està tancada.", "Informació", JOptionPane.INFORMATION_MESSAGE);
            } else {
                con.close();
                conexion();
                JOptionPane.showMessageDialog(null, "La sessió es va tancar correctament.", "Informació", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al tancar la sessió: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Executa les consultes SQL en funció de la selecció de l'usuari.
     *
     * @param cmbConsulta Desplegable de consultes
     * @param txtConsulta Camp de text amb la consulta SQL
     * @return StringBuilder amb el resultat de la consulta
     */
    public StringBuilder hacerConsultas(JComboBox cmbConsulta, JTextField txtConsulta) {
        try {
            if (con == null || con.isClosed()) {
                System.out.println("La connexió no està oberta.");
                return null;
            }

            if (cmbConsulta.getSelectedItem().toString().equals("SELECT")) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(cmbConsulta.getSelectedItem().toString() + " " + txtConsulta.getText().toString());
                StringBuilder consulta = new StringBuilder();

                while (rs.next()) {
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        consulta.append(rs.getString(i)).append(" ");
                    }
                    consulta.append("\n");
                }

                rs.close();
                stmt.close();
                JOptionPane.showMessageDialog(null, "Consulta SELECT feta amb èxit.", "Informació", JOptionPane.INFORMATION_MESSAGE);
                return consulta;
            } else if (cmbConsulta.getSelectedItem().toString().equals("INSERT")) {
                PreparedStatement psInsertar = con.prepareStatement(cmbConsulta.getSelectedItem().toString() + " " + txtConsulta.getText().toString());
                StringBuilder consulta = new StringBuilder();
                int resultadoInsertar = psInsertar.executeUpdate();
                consulta.append("Número de registres afectats: " + resultadoInsertar);
                JOptionPane.showMessageDialog(null, "Consulta INSERT feta amb èxit.", "Informació", JOptionPane.INFORMATION_MESSAGE);
                return consulta;
            } else if (cmbConsulta.getSelectedItem().toString().equals("UPDATE")) {
                PreparedStatement psUpdate = con.prepareStatement(cmbConsulta.getSelectedItem().toString() + " " + txtConsulta.getText().toString());
                StringBuilder consulta = new StringBuilder();
                int resultadoUpdate = psUpdate.executeUpdate();
                consulta.append("Número de registres afectats: " + resultadoUpdate);
                JOptionPane.showMessageDialog(null, "Consulta UPDATE feta amb èxit.", "Informació", JOptionPane.INFORMATION_MESSAGE);
                return consulta;
            } else if (cmbConsulta.getSelectedItem().toString().equals("DELETE")) {
                PreparedStatement psDelete = con.prepareStatement(cmbConsulta.getSelectedItem().toString() + " " + txtConsulta.getText().toString());
                StringBuilder consulta = new StringBuilder();
                int resultadoDelete = psDelete.executeUpdate();
                consulta.append("Número de registres afectats: " + resultadoDelete);
                JOptionPane.showMessageDialog(null, "Consulta DELETE feta amb èxit.", "Informació", JOptionPane.INFORMATION_MESSAGE);
                return consulta;
            } else {
                return null;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Consulta no vàlida.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
