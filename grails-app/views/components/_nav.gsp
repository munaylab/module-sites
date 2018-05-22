<g:set var="home" value="${g.createLink(controller: 'org', action: org.nombreURL)}" />

<nav id="mainNav" class="navbar navbar-default navbar-fixed-top">
  <div class="container-fluid">

    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed"
          data-toggle="collapse" data-target="#navbar-collapse">
        <span class="sr-only">Navegación</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <g:if test="${index}">
        <a class="navbar-brand page-scroll" href="#section-top">
          <b>${org.nombre}</b>
        </a>
      </g:if>
      <g:else>
        <a class="navbar-brand page-scroll" href="${home}#section-top">
          <b>${org.nombre}</b>
        </a>
      </g:else>

    </div>

    <div class="collapse navbar-collapse" id="navbar-collapse">
      <ul class="nav navbar-nav navbar-right">
        <g:each var="item" in="${menu}">
          <li>
            <g:if test="${item.link}">
              <a class="external-link btn btn-default navbar-btn" href="${item.link}">${item.nombre}</a>
            </g:if>
            <g:else>
              <g:if test="${index}">
                <a class="page-scroll"
                    href="#${item.nombre.toLowerCase()}">${item.nombre}</a>
              </g:if>
              <g:else>
                <a class="page-scroll"
                    href="${home}#${item.nombre.toLowerCase()}">${item.nombre}</a>
              </g:else>
            </g:else>
          </li>
        </g:each>
      </ul>
    </div>

  </div>
</nav>
