<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.*" %>
<%@page import="java.util.Date"%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="Entidades.Publicacion" %>
<%@ page import="Entidades.Categoria" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <% 
                List<Categoria> categorias = (List<Categoria>) request.getAttribute("categorias");
            %>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Página Principal</title>

  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css">
  <link rel="stylesheet" href="styleshome.css">
</head>
<body>
  <header class="bg-primary py-3">
    <div class="container">
      <h1 class="text-white"> <img src="logo.png" alt="Logo de la red social" style="width: 100px; margin-right: 10px;">Faceday</h1>
      <div class="search-bar me-3">
          <form action="BusquedaServlet?from=home" method="post" class="input-group">
    <input type="text" class="form-control" id="searchTerm" name="searchTerm" placeholder="Buscar...">
    <button class="btn btn-light" type="submit" href="BusquedaServlet">Buscar</button>
          </form>
      </div>
      <!-- Búsqueda avanzada -->
<div class="advanced-search">
  <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#advancedSearchCollapse" aria-expanded="false" aria-controls="advancedSearchCollapse">
    Búsqueda Avanzada
  </button>
  <div class="collapse" id="advancedSearchCollapse">
    <form action="BusquedaAvanzadaServlet?from=home" method="post">
      <div class="mb-3">
        <label for="advancedText" class="form-label text-white">Texto:</label>
        <input type="text" id="advancedText" name="advancedText" class="form-control">
      </div>
      <div class="mb-3 select-wrapper">
                        <label for="advancedCategory" class="form-label text-white">Categoría:</label>
                        <select id="advancedCategory" name="advancedCategory" class="form-select">
                            <option value="">Seleccionar categoría</option>
                            <% if (categorias != null) { 
                                for (Categoria categoria : categorias) { %>
                                    <option value="<%= categoria.getNombre() %>"><%= categoria.getNombre() %></option>
                            <%   } 
                            } %>
                        </select>
                    </div>
      <div class="row justify-content-center">
        <div class="col-md-6 mb-3">
          <label for="startDate" class="form-label text-white">Fecha Inicio:</label>
          <input type="date" id="startDate" name="startDate" class="form-control">
        </div>
        <div class="col-md-6 mb-3">
          <label for="endDate" class="form-label text-white">Fecha Fin:</label>
          <input type="date" id="endDate" name="endDate" class="form-control">
        </div>
      </div>
      <button type="submit" href="BusquedaAvanzadaServlet" class="btn btn-light btn-sm">Buscar</button>
    </form>
  </div>
</div>

    </div>
  </header>
  
  <main class="container my-4 custom-width">
        <section class="post-container">
            <% List<Publicacion> publicaciones = (List<Publicacion>) request.getAttribute("publicacion");
               if (publicaciones != null && !publicaciones.isEmpty()) {
                   for (Publicacion publicacion : publicaciones) { 
                       String titulo = publicacion.getTitulo();
                       String contenido = publicacion.getContenido();
                       Date fechaPublicacion = publicacion.getFechaPublicacion();
                       String categoria = publicacion.getCategoria();
                   %>
                       <article class="post bg-light p-4 mb-4">
                           <div class="post-header">
                               <a href="profile.jsp">
                                   <img src="profile.jpg" alt="Foto de perfil" class="rounded-circle me-3" style="width: 20px;">
                               </a>
                               <h2 class="mb-0"> <%= titulo %></h2>
                           </div>
                           <p class="mb-0"><%= contenido %></p>
                           <div class="post-details mt-3">
                               <p class="mb-0">Fecha: <%= fechaPublicacion %></p>
                               <p class="mb-0">Categoría: <%= categoria %></p>
                           </div>
                       </article>
            <%     } 
               } else { %>
                   <p>No se encontraron publicaciones que coincidan con los criterios de búsqueda.</p>
            <% } %>
            <div class="pagination-container d-flex justify-content-between align-items-center">
        <button class="btn btn-secondary" onclick="prevPage()">Anterior</button>
        <div class="pagination-pages" id="paginationPages"></div>
        <button class="btn btn-secondary" onclick="nextPage()">Siguiente</button>
      </div>
        </section>
    </main>

  <aside class="bg-light p-4">
    <div class="profile-container">
        <a href="publicaciones.jsp">
            <img src="profile.jpg" alt="Foto de perfil" class="rounded-circle" style="width: 100px;">
        </a>
        <h2 class="mb-0"><%= session.getAttribute("usuario") %></h2>
    </div>
</aside>

  <!-- Pie de página -->
  <footer class="bg-dark py-3 text-white">
    <div class="container">
      <div class="row">
        <div class="col-md-6">
          <div class="authors-info">
            <p class="mb-0">Autores: Ricardo Romo Rodriguez, Nombre2 Apellido2</p>
          </div>
        </div>
        <div class="col-md-6 d-flex justify-content-end">
          <div class="logout">
            <button class="btn btn-outline-light" onclick="logout()">Cerrar Sesión</button>
          </div>
        </div>
      </div>
    </div>
  </footer>

  <!-- Scripts de Bootstrap y personalizados -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
  <script src="script.js"></script>
</body>
</html>
