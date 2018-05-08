package munaylab.sites

import org.munaylab.contenido.Cabecera
import org.munaylab.osc.Organizacion

class SitesController {

    def organizacionService
    def contenidoService

    def index() {
        Organizacion org = organizacionService.buscarPorNombre(params.nombreURL)
        if (org) {
            List<Cabecera> cabeceras = contenidoService.getCabecerasDeOrganizacion(org)
            render view: 'index', model: [org: org, cabeceras: cabeceras]
        } else {
            render status: 404
        }
    }
}
