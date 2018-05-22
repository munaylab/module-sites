<!DOCTYPE html>
<html lang="en-US">
  <head>
    <meta name="layout" content="site"/>
    <title><g:message code="label.plataforma.full"/></title>

    <asset:javascript src="lodash.min.js"/>
    <asset:javascript src="marked.js"/>

    <g:render template="components/articulo"/>

  </head>

  <body>
    <div id="wrapper">
      <articulo id="${articulo.titulo.toLowerCase()}" titulo="${articulo.titulo}"
                contenido="${articulo.contenido}" imagen="${articulo?.imagen}"></articulo>
    </div>

    <a id="to-top" href="#" class="btn btn-primary btn-lg" role="button">
      <span class="glyphicon glyphicon-chevron-up"></span>
    </a>

  </body>

</html>
