<!DOCTYPE html>
<html lang="en-US">
  <head>
    <meta name="layout" content="site"/>
    <title><g:message code="label.plataforma.full"/></title>
  </head>

  <body>
    <g:render template="/components/landing" model="[landing: principal]" plugin="munaylab-sites"/>

    <g:set var="style" value="${true}"/>
    <g:each var="cabecera" in="${cabeceras}">
      <g:if test="${!cabecera.link}">
        <g:render plugin="munaylab-sites" template="/components/articulo"
            model="[titulo: cabecera.titulo, articulo: cabecera.contenido,
                    style: style, sinImagen: !cabecera.contenido.imagen]"/>
        <g:set var="style" value="${!style}"/>
      </g:if>
    </g:each>

    <a id="to-top" href="#" class="btn btn-primary btn-lg" role="button">
      <span class="glyphicon glyphicon-chevron-up"></span>
    </a>
  </body>

</html>
