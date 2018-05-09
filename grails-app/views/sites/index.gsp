<!DOCTYPE html>
<html lang="en-US">
  <head>
    <meta name="layout" content="main"/>
    <title><g:message code="label.plataforma.full"/></title>
    <g:if test="${principal.imagen}">
      <style media="screen">
        #page-top {
          background-image: url('${principal.imagen}');
        }
      </style>
    </g:if>
  </head>

  <body>
    <section class="container ${principal.imagen ? 'imagen' : ''}" id="page-top">
      <div class="container-img"></div>
      <div class="row">
        <h1 class="col-md-12">${principal.titulo ?: principal.contenido.titulo}</h1>
        <hr class="divisor">
        <div class="columna-1 col-md-6">
          <g:if test="${principal.titulo}">
            <h2>${principal.contenido.titulo}</h2>
          </g:if>
          <div class="html">
            ${raw(principal.contenido.contenido)}
          </div>
        </div>
        <div class="columna-2 col-md-6">
          <img class="img-responsive" src="${principal.contenido.imagen}" />

          <div class="botones">
            <g:if test="${principal.accionOpcional}">
              <a href="${principal.accionOpcional.link}" class="btn btn-default btn-primary">
                ${principal.accionOpcional.titulo}
              </a>
            </g:if>
            <g:if test="${principal.accionPrincipal}">
              <a href="${principal.accionPrincipal.link}" class="btn btn-default btn-primary">
                ${principal.accionPrincipal.titulo}
              </a>
            </g:if>
            <g:if test="${principal.accionSecundaria}">
              <a href="${principal.accionSecundaria.link}" class="btn btn-default">
                ${principal.accionSecundaria.titulo}
              </a>
            </g:if>
          </div>

        </div>

      </div>
    </section>

    <g:set var="style" value="${true}" />
    <g:each var="cabecera" in="${cabeceras}">
      <g:if test="${!cabecera.link}">
        <section class="container ${style ? 'inverted' : ''}" id="${cabecera.titulo}">

          <div class="row">
            <div class="columna-1 col-md-6">
              <h2>${cabecera.contenido.titulo}</h2>
              <div class="html">
                ${raw(cabecera.contenido.contenido)}
              </div>
            </div>
            <div class="columna-2 col-md-6">
              <img class="img-responsive" src="${cabecera.contenido.imagen}" />
            </div>
          </div>

        </section>
        <g:set var="style" value="${!style}" />
      </g:if>
    </g:each>

    <a id="back-to-top" href="#" class="btn btn-primary btn-lg back-to-top" role="button">
      <span class="glyphicon glyphicon-chevron-up"></span>
    </a>
  </body>

</html>
