<section class="container ${landing.imagen ? 'imagen' : ''}" id="section-top">

  <g:if test="${landing.imagen}">
    <div class="fondo"></div>
    <style>.fondo {background-image: url('${landing.imagen}');}</style>
  </g:if>

  <div class="row">

    <g:set var="sinImagen" value="${!landing.contenido.imagen}" />

    <div class="columna-1 ${sinImagen ? 'col-md-12' : 'col-md-6'}">
      <h1 class="title-animation">
        ${landing.titulo ?: landing.contenido.titulo}
      </h1>
      <div class="content-animation">
        ${raw(landing.contenido.contenido)}
      </div>
    </div>

    <g:if test="${!sinImagen}">
      <div class="columna-2 col-md-6 text-center">
        <img class="img-responsive image-animation" src="${landing.contenido.imagen}" />

        <div class="botones">
          <g:if test="${landing.accionOpcional}">
            <a href="${landing.accionOpcional.link}" class="btn btn-default btn-primary">
              ${landing.accionOpcional.titulo}
            </a>
          </g:if>
          <g:if test="${landing.accionPrincipal}">
            <a href="${landing.accionPrincipal.link}" class="btn btn-default btn-primary">
              ${landing.accionPrincipal.titulo}
            </a>
          </g:if>
          <g:if test="${landing.accionSecundaria}">
            <a href="${landing.accionSecundaria.link}" class="btn btn-default">
              ${landing.accionSecundaria.titulo}
            </a>
          </g:if>
        </div>

      </div>
    </g:if>

  </div>
</section>
