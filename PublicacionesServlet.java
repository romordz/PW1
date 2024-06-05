import Entidades.Publicacion;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import Entidades.Publicacion;

@WebServlet(urlPatterns = {"/PublicacionesServlet"})
public class PublicacionesServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String titulo = request.getParameter("postTitle");
        String contenido = request.getParameter("postContent");
        String categoria = request.getParameter("postCategory");
        String imagen = "hola";
        boolean estatus = true;
        LocalDate fechaPublicacion = LocalDate.now();

        HttpSession session = request.getSession();
        int usuarioID = (int) session.getAttribute("usuarioID");
        

        /*Part filePart = request.getPart("postImage"); NECESITA enctype="multipart/form-data" en publicaciones.jsp junto al method=post
        if (filePart != null && filePart.getSize() > 0) {
            // Obtener el nombre del archivo
            String fileName = filePart.getSubmittedFileName();
            // Guardar la imagen en la carpeta del servidor
            String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String filePath = uploadPath + File.separator + fileName;
            try (InputStream inputStream = filePart.getInputStream();
                    FileOutputStream outputStream = new FileOutputStream(filePath)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                imagen = "uploads" + File.separator + fileName; // Guardar la ruta de la imagen en la base de datos
            } catch (IOException e) {
                out.println("<p>Error al subir la imagen: " + e.getMessage() + "</p>");
                return;
            }
        }*/
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/paginaweb", "root", "abc123")) {
            String sql = "INSERT INTO Publicacion (Titulo, Contenido, Imagen, Categoria, Estatus, FechaPublicacion, UsuarioID) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, titulo);
                pstmt.setString(2, contenido);
                pstmt.setString(3, imagen);
                pstmt.setString(4, categoria);
                pstmt.setBoolean(5, estatus);
                pstmt.setObject(6, fechaPublicacion);
                pstmt.setInt(7, usuarioID);

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    response.sendRedirect("publicaciones.jsp");
                } else {
                    out.println("<p>Error al crear la publicación.</p>");
                }
            }
        } catch (SQLException e) {
            out.println("<p>Error al conectar con la base de datos: " + e.getMessage() + "</p>");
        }
    }
    /*protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/paginaweb?serverTimezone=America/Monterrey", "root", "abc123");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Publicacion");

            List<Publicacion> publicaciones = new ArrayList<>();
            while (rs.next()) {
                Publicacion publicacion = new Publicacion();
                publicacion.setTitulo(rs.getString("Titulo"));
                publicacion.setContenido(rs.getString("Contenido"));
                publicacion.setFechaPublicacion(rs.getDate("FechaPublicacion"));
                publicacion.setCategoriaId(rs.getInt("CategoriaId"));
                publicaciones.add(publicacion);
            }

            con.close();
            HttpSession session = request.getSession();
            session.setAttribute("publicaciones", publicaciones);
            request.getRequestDispatcher("home.jsp").forward(request, response);
        } catch (Exception e) {
            out.println(e);
        }
    }*/
     String obtenerNombreUsuario(int usuarioID) {
                try {
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/paginaweb?serverTimezone=America/Monterrey", "root", "abc123");
                    PreparedStatement pstmt = con.prepareStatement("SELECT NombreUsuario FROM Usuario WHERE UsuarioID = ?");
                    pstmt.setInt(1, usuarioID);
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        return rs.getString("NombreUsuario");
                    } else {
                        return "Usuario Desconocido";
                    }
                } catch(Exception e) {
                    return "Error al obtener el nombre de usuario";
                }
            }
}
