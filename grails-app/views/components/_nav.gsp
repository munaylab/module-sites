<nav id="mainNav" class="navbar navbar-default navbar-fixed-top">
  <div class="container-fluid">

    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed"
          data-toggle="collapse" data-target="#navbar-collapse">
        <span class="sr-only">Navegación</span> <i class="fa fa-1x fa-bars"></i>
      </button>
      <a class="navbar-brand page-scroll" href="#section-top">
        <g:if test="${org}">
          <b>${org.nombre}</b>
        </g:if>
        <g:else>
          <g:message code="label.plataforma.nombre"/>
        </g:else>
      </a>
    </div>

    <div class="collapse navbar-collapse" id="navbar-collapse">
      <ul class="nav navbar-nav navbar-right">
        <g:each var="cabecera" in="${cabeceras}">
          <li>
            <g:if test="${cabecera.link}">
              <a class="external-link btn btn-default navbar-btn" href="${cabecera.link}">${cabecera.nombre}</a>
            </g:if>
            <g:else>
              <a class="page-scroll" href="#${cabecera.titulo}">${cabecera.nombre}</a>
            </g:else>
          </li>
        </g:each>
      </ul>
    </div>

  </div>
</nav>
