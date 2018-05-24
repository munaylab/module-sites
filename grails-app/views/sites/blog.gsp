<!DOCTYPE html>
<html lang="en-US">
  <head>
    <meta name="layout" content="site"/>

    <g:render template="/components/metatags" model="[
        'titulo': landing?.titulo ?: landing.contenido?.titulo,
        'descripcion': landing.contenido?.descripcion,
        'keywords': landing.contenido?.palabrasClaves,
        'url': g.createLink(absolute: true, controller: 'org', action: org.nombreURL),
        'imagen': landing?.imagen
            ? landing.imagen
            : g.createLink(controller: 'archivo', action: 'show', id: landing.ariculo.imagen.id)]"/>

    <title>Blog - ${org.nombre}</title>

    <g:render template="components/articulo-card"/>
  </head>

  <body>
    <div id="wrapper" class="blog">

      <div class="col-sm-2 col-md-3 col-lg-2">

        <div class="list-group">
          <div class="list-group-item active">
            <h4 class="list-group-item-heading">
              Ultimos Posts
            </h4>
          </div>
          <g:each in="${articulos}" var="articulo">
            <a class="list-group-item" href='${g.createLink(url: "blog/${articulo.url ?: ''}")}'>
              <div class="row">
                <g:if test="${articulo.imagen}">
                  <div class="col-xs-4 imagen-item">
                    <img class="img-responsive"
                        src="${g.createLink(controller: 'archivo', action: 'show', id: articulo?.imagen?.id)}">
                  </div>
                  <div class="col-xs-8 text-item">
                    <p class="list-group-item-heading">${articulo.titulo}</p>
                    <p class="list-group-item-text">${articulo.dateCreated.format('dd MMM yy')}</p>
                  </div>
                </g:if>
                <g:else>
                  <div class="col-xs-3">
                    <span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>
                  </div>
                  <div class="col-xs-9">
                    <h5 class="list-group-item-heading">${articulo.titulo}</h5>
                    <p class="list-group-item-text">${articulo.dateCreated.format('dd MMM yy')}</p>
                  </div>
                </g:else>
              </div>
            </a>
          </g:each>
        </div>

      </div>

      <div class="col-sm-10 col-md-9 col-lg-10">
        <div class="row">

        <g:each in="${articulos}" var="articulo">
          <div class="col-sm-6 col-md-4">
            <articulo-card titulo="${articulo.titulo}" descripcion="${articulo.descripcion}"
                fecha="${articulo.dateCreated.format('dd MMMM yy')}"
                link='${g.createLink(url: "blog/${articulo.url ?: ''}")}'
                imagen="${g.createLink(controller: 'archivo', action: 'show', id: articulo.imagen?.id)}">
            </articulo-card>
          </div>
        </g:each>

        </div>
      </div>

    </div>

    <a id="to-top" href="#" class="btn btn-primary btn-lg" role="button">
      <span class="glyphicon glyphicon-chevron-up"></span>
    </a>

  </body>

</html>
