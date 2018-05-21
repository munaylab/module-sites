package munaylab.sites

import org.munaylab.contenido.Menu
import org.munaylab.contenido.Principal
import org.munaylab.osc.Organizacion

class SitesController {

    def contenidoService
    def organizacionService

    def index() {
        Organizacion org = organizacionService.buscarPorNombre(params.nombreURL)
        if (org) {
            Principal principal = contenidoService.getPrincipal(org)
            List<Menu> menu = contenidoService.getMenuDeOrganizacion(org)
            render view: 'index', model: [org: org, principal: principal, menu: menu]
        } else {
            render status: 404
        }
    }
}
