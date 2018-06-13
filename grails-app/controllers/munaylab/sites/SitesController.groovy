package munaylab.sites

import org.munaylab.contenido.Articulo
import org.munaylab.contenido.Menu
import org.munaylab.contenido.Landing
import org.munaylab.osc.Organizacion
import org.munaylab.osc.EstadoOrganizacion
import org.munaylab.planificacion.Programa
import org.munaylab.planificacion.Proyecto
import org.munaylab.planificacion.Actividad

class SitesController {

    def contenidoService
    def organizacionService
    def planificacionService

    private boolean esUnaOrganizacionValida(Organizacion org) {
        return org && org.estado == EstadoOrganizacion.VERIFICADA
    }

    def index() {
        Organizacion org = organizacionService.buscarPorNombre(params.nombreURL)
        if (esUnaOrganizacionValida(org)) {
            Landing landing = contenidoService.getLanding(org)
            List<Menu> menu = contenidoService.getMenuDeOrganizacion(org)
            render view: 'index', model: [org: org, landing: landing, menu: menu]
        } else {
            render status: 404
        }
    }

    def blog() {
        Organizacion org = organizacionService.buscarPorNombre(params.nombreOrganizacion)
        if (esUnaOrganizacionValida(org)) {
            Landing landing = contenidoService.getLanding(org)
            List<Articulo> articulos = contenidoService.obtenerUltimosArticulos(org)
            render view: 'blog', model: [org: org, landing: landing, articulos: articulos]
        } else {
            render status: 404
        }
    }

    def articulo() {
        Organizacion org = organizacionService.buscarPorNombre(params.nombreOrganizacion)
        if (esUnaOrganizacionValida(org)) {
            Articulo articulo = contenidoService.buscarArticulo(params.nombreArticulo, org)
            if (articulo) {
                render view: 'articulo', model: [org: org, articulo: articulo]
                return
            }
        }
        render status: 404
    }

    def planning() {
        Organizacion org = organizacionService.buscarPorNombre(params.nombreOrganizacion)
        if (esUnaOrganizacionValida(org)) {
            List<Programa> programas = planificacionService.getProgramas(org)
            if (!programas.isEmpty()) {
                render view: 'planning', model: [org: org, programas: programas]
                return
            }
        }
        render status: 404
    }

    def programa(Long id) {
        Organizacion org = organizacionService.buscarPorNombre(params.nombreOrganizacion)
        if (esUnaOrganizacionValida(org)) {
            Programa programa = planificacionService.getPrograma(id, org)
            if (programa) {
                List<Programa> programas = planificacionService.getProgramas(org)
                render view: 'planning', model: [planning: programa, org: org, programas: programas]
                return
            }
        }
        render status: 404
    }

    def proyecto(Long id) {
        Organizacion org = organizacionService.buscarPorNombre(params.nombreOrganizacion)
        if (esUnaOrganizacionValida(org)) {
            Proyecto proyecto = planificacionService.getProyecto(id, org)
            if (proyecto) {
                List<Programa> programas = planificacionService.getProgramas(org)
                render view: 'planning', model: [planning: proyecto, org: org, programas: programas]
                return
            }
        }
        render status: 404
    }

    def actividad(Long id) {
        Organizacion org = organizacionService.buscarPorNombre(params.nombreOrganizacion)
        if (esUnaOrganizacionValida(org)) {
            Actividad actividad = planificacionService.getActividad(id, org)
            if (actividad) {
                List<Programa> programas = planificacionService.getProgramas(org)
                render view: 'planning', model: [planning: actividad, org: org, programas: programas]
                return
            }
        }
        render status: 404
    }

}
