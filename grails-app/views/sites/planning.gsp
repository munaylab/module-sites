<!DOCTYPE html>
<html lang="en-US">
  <head>
    <meta name="layout" content="site"/>

    <g:if test="${!planning}">
      <title>Planificación - ${org.nombre}</title>
      <g:set var="planning" value="${programas.first()}" />
    </g:if>
    <g:else>
      <title>${planning.nombre} - ${org.nombre}</title>
      <script>location.href = '#${planning.id}';</script>
    </g:else>

    <g:render template="/components/metatags" model="[
        'titulo': planning?.nombre, 'descripcion': planning?.descripcion,
        'url': g.createLink(absolute: true, controller: 'org', action: org.nombreURL, id: 'planning')]"/>

    <asset:javascript src="lodash.min.js"/>
    <asset:javascript src="marked.js"/>

    <g:render template="components/planning"/>
  </head>

  <body>
    <div id="wrapper">

      <div class="container planning">

        <div class="col-md-4 indice">
          <div class="well pull-right affix">
            <g:each in="${programas}" var="programa">
              <ul class="programas">
                <li>
                  <a href="#${programa.id}">${programa.nombre}</a>
                  <ul class="proyectos">
                    <g:each in="${programa.proyectos}" var="proyecto">
                      <li>
                        <a href="#${proyecto.id}">${proyecto.nombre}</a>
                        <ul class="actividades">
                          <g:each in="${proyecto.actividades}" var="actividad">
                            <li>
                              <a href="#${actividad.id}">${actividad.nombre}</a>
                            </li>
                          </g:each>
                        </ul>
                      </li>
                    </g:each>
                  </ul>
                </li>
              </ul>
            </g:each>
          </div>
        </div>

        <div class="col-md-offset-4 col-md-8">
          <g:each in="${programas}" var="programa">
            <planning id="${programa.id}" contenido="${programa.contenido}"></planning>

            <g:each in="${programa.proyectos}" var="proyecto">
              <planning id="${proyecto.id}" contenido="${proyecto.contenido}"></planning>

              <g:each in="${proyecto.actividades}" var="actividad">
                <planning id="${actividad.id}" contenido="${actividad.contenido}"></planning>
              </g:each>

            </g:each>

          </g:each>
        </div>

      </div>
    </div>
  </body>

</html>
