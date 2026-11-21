package conexion;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ValidadorConexion {

    private static final int TIEMPO_ESPERA_SEGUNDOS = 5;

    public ResultadoConexion probarConexion(String url, String usuario, String contrasena) {
        if (url == null || url.isBlank()) {
            return new ResultadoConexion(false, "Fallo de conexión: debe escribir la URL de conexión.");
        }

        try {
            cargarDriver(url);
            DriverManager.setLoginTimeout(TIEMPO_ESPERA_SEGUNDOS);

            try (Connection conexion = DriverManager.getConnection(url, usuario, contrasena)) {
                DatabaseMetaData datos = conexion.getMetaData();
                return new ResultadoConexion(true,
                        "Conexión exitosa\n"
                        + "URL: " + url + "\n"
                        + "Motor: " + datos.getDatabaseProductName() + " " + datos.getDatabaseProductVersion() + "\n"
                        + "Driver: " + datos.getDriverName() + " " + datos.getDriverVersion());
            }
        } catch (ClassNotFoundException e) {
            return new ResultadoConexion(false,
                    "Fallo del driver: no se encontró la clase " + e.getMessage()
                    + "\nRevise que el .jar del driver esté en la carpeta lib y en el classpath.");
        } catch (SQLException e) {
            return new ResultadoConexion(false,
                    "Fallo de conexión\nMensaje del error: " + e.getMessage()
                    + "\nCódigo SQLState: " + e.getSQLState());
        }
    }

    private void cargarDriver(String url) throws ClassNotFoundException {
        String claseDriver = obtenerClaseDriver(url);
        if (claseDriver != null) {
            Class.forName(claseDriver);
        }
    }

    private String obtenerClaseDriver(String url) {
        if (url.startsWith("jdbc:sqlite:")) {
            return "org.sqlite.JDBC";
        }
        if (url.startsWith("jdbc:mysql:")) {
            return "com.mysql.cj.jdbc.Driver";
        }
        if (url.startsWith("jdbc:postgresql:")) {
            return "org.postgresql.Driver";
        }
        return null;
    }
}
