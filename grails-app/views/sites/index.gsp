<!DOCTYPE html>
<html lang="en-US">
  <head>
    <meta name="layout" content="site"/>
    <title><g:message code="label.plataforma.full"/></title>
  </head>

  <body>
    <g:render template="/components/landing" model="[landing: principal]" plugin="munaylab-sites"/>

    <g:set var="style" value="${true}"/>
    <g:each var="item" in="${menu}">
      <g:if test="${!item.link}">
        <g:render plugin="munaylab-sites" template="/components/articulo"
            model="[titulo: item.titulo, articulo: item.contenido,
                    style: style, sinImagen: !item.contenido.imagen]"/>
        <g:set var="style" value="${!style}"/>
      </g:if>
    </g:each>

    <a id="to-top" href="#" class="btn btn-primary btn-lg" role="button">
      <span class="glyphicon glyphicon-chevron-up"></span>
    </a>
  </body>

</html>
