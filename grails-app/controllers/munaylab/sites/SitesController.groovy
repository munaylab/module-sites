package munaylab.sites

import org.munaylab.contenido.Cabecera
import org.munaylab.osc.Organizacion

class SitesController {

    def organizacionService

    def index() {
        Organizacion org = organizacionService.buscarPorNombre(params.nombreURL)
        if (org) {
            render view: 'index', model: [org: org, cabeceras: Cabecera.findAllByOrganizacion(org)]
        } else {
            render status: 404
        }
    }
}
