<section class="container ${style ? 'inverted' : ''}" id="${titulo}">

  <div class="row">

    <div class="columna-1 ${sinImagen ? 'col-md-12' : 'col-md-6'}">
      <h2 class="title-animation">
        ${articulo.titulo}
      </h2>
      <div class="content-animation">
        ${raw(articulo.contenido)}
      </div>
    </div>

    <g:if test="${!sinImagen}">
      <div class="columna-2 col-md-6">
        <img class="img-responsive image-animation" src="${articulo.imagen}" />
      </div>
    </g:if>

  </div>

</section>
