<!DOCTYPE html>
<html lang="en-US">
  <head>
    <meta name="layout" content="site"/>
    <g:set var="link" value="${g.articuloLink(nombre: org.nombreURL, articulo: articulo.url)}"/>
    <g:render template="/components/metatags" model="['titulo': articulo.titulo, 'url': link,
        'descripcion': articulo.descripcion, 'imagen': articulo.imagen, 'keywords': articulo.palabrasClaves]"/>

    <title>${articulo.titulo} - ${org.nombre}</title>

    <asset:javascript src="lodash.min.js"/>
    <asset:javascript src="marked.js"/>

    <g:render template="components/articulo"/>
    <g:render template="components/facebook/likes"/>
    <g:render template="components/facebook/comments"/>
  </head>

  <body>
    <div id="wrapper">
      <articulo id="${articulo.titulo.toLowerCase()}" titulo="${articulo.titulo}"
          contenido="${articulo.contenido}" imagen="${g.fileLink(file: articulo?.imagen)}">
      </articulo>

      <facebook-likes link="${link}"></facebook-likes>
      <facebook-comments link="${link}"></facebook-comments>

    </div>
  </body>

</html>
