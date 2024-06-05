import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;

@WebServlet(urlPatterns = {"/Servlet"})
public class Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String usuario = request.getParameter("usuario");
        String contraseña = request.getParameter("contraseña");
        
        if (usuario == null || usuario.isEmpty() || contraseña == null || contraseña.isEmpty()) {
            response.sendRedirect("iniciosesion-regstro.jsp?error=Campos obligatorios no completados");
        }

        if (validarUsuario(usuario, contraseña)) {
            String nombreCompleto = obtenerNombreCompleto(usuario); // Función para obtener el nombre completo del usuario
        String correoElectronico = obtenerCorreoElectronico(usuario); // Función para obtener el correo electrónico del usuario
        int edad = obtenerEdad(usuario);
        
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);
            session.setAttribute("contraseña", contraseña);
            session.setAttribute("nombreCompleto", nombreCompleto);
        session.setAttribute("correoElectronico", correoElectronico);
        session.setAttribute("edad", edad);
        int idUsuario = obtenerIDUsuario(usuario);
        session.setAttribute("usuarioID", idUsuario);

            response.sendRedirect("home.jsp");
        } else {
            response.sendRedirect("iniciosesion-regstro.jsp?error=Usuario o contraseña incorrectos");
        }
        
    }
     protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/paginaweb?serverTimezone=America/Monterrey", "root", "abc123");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Usuario");

            while(rs.next()) {
                out.println("ID: " + rs.getInt("UsuarioID") + ", Nombre de Usuario: " + rs.getString("NombreUsuario") + ", Contra: " + rs.getString("Contrasena") +  "<br>");
            }

            con.close();
        } catch(Exception e) {
            out.println(e);
        }
    }
     private boolean validarUsuario(String usuario, String contraseña) {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/paginaweb?serverTimezone=America/Monterrey", "root", "abc123");
        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Usuario WHERE NombreUsuario = ?");
        pstmt.setString(1, usuario);
        ResultSet rs = pstmt.executeQuery();
        
        // Verificar si se encontró algún resultado
        if (rs.next()) {
            String contraseñaDB = rs.getString("Contrasena");
            return contraseña.equals(contraseñaDB); // Comparar las contraseñas
        } else {
            return false;
        }
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

     private String obtenerNombreCompleto(String usuario) {
    String nombreCompleto = "";
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/paginaweb?serverTimezone=America/Monterrey", "root", "abc123");
        PreparedStatement pstmt = con.prepareStatement("SELECT Nombre, SegundoNombre, Apellido, Apellidomat FROM Usuario WHERE NombreUsuario = ?");
        pstmt.setString(1, usuario);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            String nombre = rs.getString("Nombre");
            String segundoNombre = rs.getString("SegundoNombre");
            String apellido = rs.getString("Apellido");
            String apellidomat = rs.getString("Apellidomat");
            if (segundoNombre != null && !segundoNombre.isEmpty()) {
                nombreCompleto = nombre + " " + segundoNombre + " " + apellido + " " + apellidomat;
            } else {
                nombreCompleto = nombre + " " + apellido + " " + apellidomat;
            }
        }
        con.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return nombreCompleto;
}

private String obtenerCorreoElectronico(String usuario) {
    String correoElectronico = "";
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/paginaweb?serverTimezone=America/Monterrey", "root", "abc123");
        PreparedStatement pstmt = con.prepareStatement("SELECT CorreoElectronico FROM Usuario WHERE NombreUsuario = ?");
        pstmt.setString(1, usuario);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            correoElectronico = rs.getString("CorreoElectronico");
        }
        con.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return correoElectronico;
}

private int obtenerEdad(String usuario) {
    int edad = 0;
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/paginaweb?serverTimezone=America/Monterrey", "root", "abc123");
        PreparedStatement pstmt = con.prepareStatement("SELECT FechaNacimiento FROM Usuario WHERE NombreUsuario = ?");
        pstmt.setString(1, usuario);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            LocalDate fechaNacimiento = rs.getDate("FechaNacimiento").toLocalDate();
            LocalDate fechaActual = LocalDate.now();
            edad = Period.between(fechaNacimiento, fechaActual).getYears();
        }
        con.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return edad;
}
private int obtenerIDUsuario(String usuario) {
    int idUsuario = 0;
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/paginaweb?serverTimezone=America/Monterrey", "root", "abc123");
        PreparedStatement pstmt = con.prepareStatement("SELECT UsuarioID FROM Usuario WHERE NombreUsuario = ?");
        pstmt.setString(1, usuario);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            idUsuario = rs.getInt("UsuarioID");
        }
        con.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return idUsuario;
}

}
