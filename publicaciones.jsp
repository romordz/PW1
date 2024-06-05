<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.*" %>
<%@page import="java.util.Date"%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="Entidades.Publicacion" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Mis Publicaciones</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/5.1.3/css/bootstrap.min.css">
  <link rel="stylesheet" href="styles-publicaciones.css">
  <link rel="stylesheet" href="styleshome.css">
</head>
<body> 
  <header class="bg-primary py-3">
    <div class="container">
      <h1 class="text-white" style= "margin-right: 1050px;"> <img src="logo.png" alt="Logo de la red social" style="width: 100px;">Faceday</h1>
      <div class="search-bar me-3">
          <form action="BusquedaServlet?from=publicaciones" method="post" class="input-group">
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
    <form action="BusquedaAvanzadaServlet?from=publicaciones" method="post">
      <div class="mb-3">
        <label for="advancedText" class="form-label text-white">Texto:</label>
        <input type="text" id="advancedText" name="advancedText" class="form-control">
      </div>
      <div class="mb-3">
        <label for="advancedCategory" class="form-label text-white">Categoría:</label>
        <input type="text" id="advancedCategory" name="advancedCategory" class="form-control">
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
      <button onclick="window.history.back()" class="back-button" style="background-color: #4267b2; color: #fff; padding: 12px; border: none; border-radius: 6px; cursor: pointer; transition: background-color 0.2s ease-in-out; position: absolute; left: 20px; top: 20px;">Volver</button>
  </header>
  <div class="container">
    <main>
      <section class="post-container">
        <form id="createForm" class="post-form" action="PublicacionesServlet" method="post">
          <h2>Crear Publicación</h2>
          <div class="mb-3">
            <label for="postTitle" class="form-label">*Título:</label>
            <input type="text" id="postTitle" name="postTitle" class="form-control" required>
          </div>
        
          <div class="mb-3">
            <label for="postContent" class="form-label">*Contenido:</label>
            <textarea id="postContent" name="postContent" class="form-control" required></textarea>
          </div>
        
          <div class="mb-3">
            <label for="postImage" class="form-label">Imagen:</label>
            <input type="file" id="postImage" name="postImage" accept="image/*" class="form-control" required>
          </div>
          <div id="imagePreviewContainer" class="mb-3">
            <img id="imagePreview" class="img-thumbnail" style="max-width: 400px; max-height: 400px;">
        </div>
          <div class="mb-3">
            <button id="toggleImageButton" onclick="toggleImagePreview()" class="btn btn-primary">👁️Mostrar Vista Previa</button>
          </div>
          
          <div class="mb-3 select-wrapper">
            <label for="postCategory" class="form-label">*Categoría:</label>
            <select id="postCategory" name="postCategory" class="form-select" required>
              <option value="">Selecciona una categoría</option>
              <option value="Tecnología">Tecnología</option>
              <option value="Deportes">Deportes</option>
              <option value="Viajes">Viajes</option>
              <option value="Música">Música</option>
            </select>
          </div>
          <button type="submit" href="PublicacionesServlet" class="btn btn-primary">Publicar</button>
        </form>
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
    </div>

      <aside class="bg-light p-4">
    <div class="profile-container">
        <a href="profile.jsp">
            <img src="profile.jpg" alt="Foto de perfil" class="rounded-circle" style="width: 100px;">
        </a>
        <h2 class="mb-0"><%= session.getAttribute("usuario") %></h2>
    </div>
</aside>
    
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
  <script src="script.js"></script>
</body>
</html>
