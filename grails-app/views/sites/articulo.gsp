<!DOCTYPE html>
<html lang="en-US">
  <head>
    <meta name="layout" content="site"/>

    <g:render template="/components/metatags" model="['titulo': articulo.titulo,
        'descripcion': articulo.descripcion, 'imagen': articulo.imagen, 'keywords': articulo.palabrasClaves,
        'url': g.createLink(absolute: true, controller: 'org', action: org.nombreURL, id: articulo.url)]"/>

    <title>${articulo.titulo} - ${org.nombre}</title>

    <asset:javascript src="lodash.min.js"/>
    <asset:javascript src="marked.js"/>

    <g:render template="components/articulo"/>
  </head>

  <body>
    <div id="wrapper">
      <articulo id="${articulo.titulo.toLowerCase()}" titulo="${articulo.titulo}"
                contenido="${articulo.contenido}" imagen="${g.fileLink(file: articulo?.imagen)}">
      </articulo>
    </div>

    <a id="to-top" href="#" class="btn btn-primary btn-lg" role="button">
      <span class="glyphicon glyphicon-chevron-up"></span>
    </a>

  </body>

</html>
