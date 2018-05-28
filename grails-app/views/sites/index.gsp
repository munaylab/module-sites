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
    <g:set var="index" value="${true}" />
  </head>

  <body>
    <div id="wrapper">
      <articulo
          id="top"
          landing="true"
          background="${landing?.imagenLink ?: g.fileLink(file: landing?.imagen)}"
          titulo="${landing?.titulo ?: landing.contenido?.titulo}"
          contenido="${landing.contenido.contenido}"
          imagen="${g.fileLink(file: landing.contenido?.imagen)}"
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
              imagen="${g.fileLink(file: item.articulo?.imagen)}"
              bookmark="true"
              invertir="${style}">
          </articulo>

          <g:set var="style" value="${!style}"/>
        </g:if>
      </g:each>

    </div>

  </body>

</html>
