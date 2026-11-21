# EA3 - Incorporando UI y BD

Aplicación de escritorio en Java con Swing que recibe la URL, el usuario y la contraseña de una base de datos y prueba la conexión con JDBC, mostrando el resultado en verde si conecta y en rojo si falla.

## Estructura

```
src/conexion/
├── Main.java               Abre la ventana
├── ConexionTester.java     JFrame con el formulario y el área de resultados
├── ValidadorConexion.java  Carga el driver y prueba la conexión con DriverManager
└── ResultadoConexion.java  record con el resultado (exitosa y mensaje)
lib/
└── sqlite-jdbc-3.42.0.0.jar  Driver JDBC de SQLite
```

## Cómo ejecutar

```
javac -encoding UTF-8 -d out src/conexion/*.java
java -cp "out:lib/*" conexion.Main
```

En Windows el classpath se separa con punto y coma: `java -cp "out;lib/*" conexion.Main`

## Pruebas

- `jdbc:sqlite:inventario.db` con usuario y contraseña vacíos: conexión exitosa (crea el archivo si no existe).
- `jdbc:sqlite:/carpeta/que/no/existe/inventario.db`: SQLException, fallo de conexión.
- `jdbc:mysql://localhost:3306/jardineria` sin el driver de MySQL en lib: ClassNotFoundException, fallo del driver.
- `jdbc:oracle:thin:@localhost` : SQLException "No suitable driver".
