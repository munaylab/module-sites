<!DOCTYPE html>
<html lang="en-US">
  <head>
    <meta name="layout" content="site"/>

    <g:render template="/components/metatags" model="[
        'titulo': landing?.titulo ?: landing.contenido?.titulo,
        'descripcion': landing.contenido?.descripcion,
        'imagen': landing?.imagen ?: landing.contenido?.imagen,
        'keywords': landing.contenido?.palabrasClaves,
        'url': g.createLink(absolute: true, controller: 'org', action: org.nombreURL)]"/>

    <title>${org.nombre}</title>

    <asset:javascript src="lodash.min.js"/>
    <asset:javascript src="marked.js"/>

    <g:render template="components/articulo"/>

    <style > .fondo { background-image: url(${landing.imagen}); } </style>

    <g:set var="index" value="${true}" />
  </head>

  <body>
    <div id="wrapper">
      <articulo
          id="section-top"
          landing="true"
          background="${landing?.imagen}"
          titulo="${landing?.titulo ?: landing.contenido?.titulo}"
          contenido="${landing.contenido.contenido}"
          imagen="${landing.contenido?.imagen}"
          linkOpcional="${landing?.accionOpcional?.link}"
          tituloOpcional="${landing?.accionOpcional?.titulo}"
          linkPrincipal="${landing?.accionPrincipal?.link}"
          tituloPrincipal="${landing?.accionPrincipal?.titulo}"
          linkSecundario="${landing?.accionSecundaria?.link}"
          tituloSecundario="${landing?.accionSecundaria?.titulo}">
      </articulo>


      <g:set var="style" value="${true}"/>
      <g:each var="item" in="${menu}">
        <g:if test="${!item.link}">

          <articulo
              id="${item.nombre.toLowerCase()}"
              titulo="${item.nombre}"
              contenido="${item.articulo.contenido}"
              imagen="${item.articulo?.imagen}"
              bookmark="true"
              invertir="${style}">
          </articulo>

          <g:set var="style" value="${!style}"/>
        </g:if>
      </g:each>

    </div>

    <a id="to-top" href="#" class="btn btn-primary btn-lg" role="button">
      <span class="glyphicon glyphicon-chevron-up"></span>
    </a>

  </body>

</html>
