package munaylab.sites

import org.munaylab.contenido.Cabecera
import org.munaylab.contenido.Principal
import org.munaylab.osc.Organizacion

class SitesController {

    def contenidoService
    def organizacionService

    def index() {
        Organizacion org = organizacionService.buscarPorNombre(params.nombreURL)
        if (org) {
            Principal principal = contenidoService.getPrincipal(org)
            List<Cabecera> cabeceras = contenidoService.getCabecerasDeOrganizacion(org)
            render view: 'index', model: [org: org, principal: principal, cabeceras: cabeceras]
        } else {
            render status: 404
        }
    }
}
