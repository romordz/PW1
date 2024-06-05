<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Navbar</title>
  
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="styleshome.css">
</head>
<body>
    <header class="bg-primary py-3">
        <div class="container">
            <div class="search-bar me-3">
                <input type="text" class="form-control" placeholder="Buscar...">
                <button class="btn btn-light" type="submit" href="BusquedaServlet">Buscar</button>
            </div>
            <!-- Búsqueda avanzada -->
            <div class="advanced-search">
                <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#advancedSearchCollapse" aria-expanded="false" aria-controls="advancedSearchCollapse">
                    Búsqueda Avanzada
                </button>
                <div class="collapse" id="advancedSearchCollapse">
                    <form>
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
    </header>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
