package munaylab.sites

import org.munaylab.contenido.Menu
import org.munaylab.osc.Organizacion

class SitesTagLib {

    def contenidoService

    static encodeAsForTags = [menu: [taglib:'raw'], articuloLink: [taglib:'html']]

    def menu = { attrs, body ->
        if (attrs.org) {
            List<Menu> menu = contenidoService.getMenuDeOrganizacion(attrs.org)
            String homeLink = g.createLink(controller: attrs.org.nombreURL)
            def model = [menu: menu, homeLink: homeLink, nombre: attrs.org.nombre]
            out << render(template: "/components/menubar", model: model)
        }
    }

    def articuloLink = { attrs, body ->
        String link = g.createLink(absolute: true, controller: 'org', action: attrs.nombre)
        out << "$link/blog/$attrs.articulo"
    }

}
