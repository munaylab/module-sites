package munaylab.sites

import org.munaylab.contenido.Menu
import org.munaylab.osc.Organizacion

class SitesTagLib {
    static defaultEncodeAs = [taglib:'raw']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    def contenidoService

    def menu = { attrs, body ->
        if (attrs.org) {
            List<Menu> menu = contenidoService.getMenuDeOrganizacion(attrs.org)
            String homeLink = g.createLink(controller: 'org', action: attrs.org.nombreURL)
            def model = [menu: menu, homeLink: homeLink, nombre: attrs.org.nombre, isIndex: attrs.isIndex]
            out << render(template: "/components/nav", model: model)
        }
    }
}
