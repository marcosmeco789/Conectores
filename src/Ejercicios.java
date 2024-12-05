import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Ejercicios {

    private Connection conexion;

    public void abrirConexion(String bd, String servidor, String usuario,
            String password) {
        try {
            String url = String.format("jdbc:mysql://localhost:3306/add?useServerPrepStmts=true", "usuario",
                    "contraseña");
            // Establecemos la conexión con la BD
            this.conexion = DriverManager.getConnection(url, usuario, password);
            if (this.conexion != null) {
                System.out.println("Conectado a " + bd + " en " + servidor);
            } else {
                System.out.println("No conectado a " + bd + " en " + servidor);
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getLocalizedMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("Código error: " + e.getErrorCode());
        }
    }

    public void cerrarConexion() {
        try {
            this.conexion.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getLocalizedMessage());
        }
    }

    private PreparedStatement ps = null; // atributo de instancia

    public void ej1(String patron, int numResultados) throws SQLException {
        String query = "select * from aulas where nombreAula like ? limit ?";
        if (this.ps == null)
            this.ps = this.conexion.prepareStatement(query);
        ps.setString(1, patron);
        ps.setInt(2, numResultados);
        ResultSet resu = ps.executeQuery();
        while (resu.next()) {
            System.out.println(resu.getInt(1) + "\t" + resu.getString("nombreAula"));
        }
    }

    public void ej2(String nombre, String apellidos, int altura, int aula, String nombreAsignatura)
            throws SQLException {
        String query1 = "INSERT INTO alumnos (nombre, apellidos, altura, aula) VALUES (?, ?, ?, ?)";
        String query2 = "INSERT INTO asignaturas (NOMBRE) VALUES (?)";

        try (PreparedStatement psAlumnos = this.conexion.prepareStatement(query1);
                PreparedStatement psAsignaturas = this.conexion.prepareStatement(query2)) {

            psAlumnos.setString(1, nombre);
            psAlumnos.setString(2, apellidos);
            psAlumnos.setInt(3, altura);
            psAlumnos.setInt(4, aula);
            int filasAfectadas1 = psAlumnos.executeUpdate();
            System.out.println("Filas insertadas en alumnos: " + filasAfectadas1);

            psAsignaturas.setString(1, nombreAsignatura);
            int filasAfectadas2 = psAsignaturas.executeUpdate();
            System.out.println("Filas insertadas en asignaturas: " + filasAfectadas2);

        } catch (SQLException e) {
            System.out.println("Se ha producido un error: " + e.getLocalizedMessage());
        }
    }


    public void ej3(int codigoAsignatura, int codigoAlumno) throws SQLException {
        String query1 = "DELETE FROM asignaturas WHERE COD = ?";
        String query2 = "DELETE FROM alumnos WHERE codigo = ?";

        try (PreparedStatement psAsignaturas = this.conexion.prepareStatement(query1);
                PreparedStatement psAlumnos = this.conexion.prepareStatement(query2);) {

            psAsignaturas.setInt(1, codigoAsignatura);
            int filasAfectadas1 = psAsignaturas.executeUpdate();
            System.out.println("Filas insertadas en asignaturas: " + filasAfectadas1);

            psAlumnos.setInt(1, codigoAlumno);
            int filasAfectadas2 = psAlumnos.executeUpdate();
            System.out.println("Filas insertadas en alumnos: " + filasAfectadas2);
        } catch (Exception e) {
            System.out.println("Se ha producido un error: " + e.getLocalizedMessage());
        }

    }


    public void modificarAlumnos(String nombre, String apellidos, int altura, int aula, int codigo)
            throws SQLException {
        String query = "UPDATE alumnos SET nombre = ?, apellidos = ?, altura = ?, aula = ? WHERE codigo = ?";
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setString(1, nombre);
            ps.setString(2, apellidos);
            ps.setInt(3, altura);
            ps.setInt(4, aula);
            ps.setInt(5, codigo);

            int filasAfectadas = ps.executeUpdate();
            System.out.println("Filas insertadas en alumnos: " + filasAfectadas);
        } catch (Exception e) {
            System.out.println("Se ha producido un error: " + e.getLocalizedMessage());
        }
    }


    public void modificarAsignaturas(String nombre, int cod)
            throws SQLException {
        String query = "UPDATE asignaturas SET NOMBRE = ? WHERE COD = ?";
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setString(1, nombre);
            ps.setInt(2, cod);

            int filasAfectadas = ps.executeUpdate();
            System.out.println("Filas insertadas en alumnos: " + filasAfectadas);
        } catch (Exception e) {
            System.out.println("Se ha producido un error: " + e.getLocalizedMessage());
        }
    }

    public void ej5a() throws SQLException{
        String query = "select nombreAula from aulas where numero IN (select aula from alumnos)";

        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ResultSet resu = ps.executeQuery();
            while (resu.next()) {
                System.out.println(resu.getString("nombreAula"));
                
            }
        } catch (Exception e) {
                    System.out.println("Se ha producido un error: " + e.getLocalizedMessage());
                }
            }
        
            public void ej5b() throws SQLException {
                String query = "SELECT alumnos.nombre, asignaturas.NOMBRE, notas.NOTA from notas JOIN ";
            }

            public static void main(String[] args) {
        Ejercicios ej = new Ejercicios();

        ej.abrirConexion("add", "localhost", "root", "");
        try {
            // ej.ej1("A%", 1);
            // ej.ej2("Marcos", "Gonzalez", 162, 31, "Acceso a Datos");
            // ej.ej3(1, 1);
            // ej.modificarAlumnos("Marcos", "Meco", 163, 11, 16);
            // ej.modificarAsignaturas("Programaciooon", 1);
            //ej.ej5a();
            ej.ej5b();
        } catch (SQLException e) {

            e.printStackTrace();
        }
        ej.cerrarConexion();
    }

}
