package munaylab.sites

import org.munaylab.contenido.Articulo
import org.munaylab.contenido.Menu
import org.munaylab.contenido.Landing
import org.munaylab.osc.Organizacion

class SitesController {

    def contenidoService
    def organizacionService

    def index() {
        Organizacion org = organizacionService.buscarPorNombre(params.nombreURL)
        if (org) {
            Landing landing = contenidoService.getLanding(org)
            List<Menu> menu = contenidoService.getMenuDeOrganizacion(org)
            render view: 'index', model: [org: org, landing: landing, menu: menu]
        } else {
            render status: 404
        }
    }

    def blog() {
        Organizacion org = organizacionService.buscarPorNombre(params.nombreOrganizacion)
        if (org) {
            Landing landing = contenidoService.getLanding(org)
            List<Menu> menu = contenidoService.getMenuDeOrganizacion(org)
            List<Articulo> articulos = contenidoService.obtenerUltimosArticulos(org)
            render view: 'blog', model: [org: org, menu: menu, landing: landing, articulos: articulos]
        } else {
            render status: 404
        }
    }

    def articulo() {
        Organizacion org = organizacionService.buscarPorNombre(params.nombreOrganizacion)
        if (org) {
            Articulo articulo = contenidoService.buscarArticulo(params.nombreArticulo, org)
            if (articulo) {
                List<Menu> menu = contenidoService.getMenuDeOrganizacion(org)
                render view: 'articulo', model: [org: org, menu: menu, articulo: articulo]
                return
            }
        }
        render status: 404
    }

}
