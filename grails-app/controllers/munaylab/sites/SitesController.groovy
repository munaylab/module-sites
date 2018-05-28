package munaylab.sites

import org.munaylab.contenido.Articulo
import org.munaylab.contenido.Menu
import org.munaylab.contenido.Landing
import org.munaylab.osc.Organizacion
import org.munaylab.planificacion.Programa
import org.munaylab.planificacion.Proyecto
import org.munaylab.planificacion.Actividad

class SitesController {

    def contenidoService
    def organizacionService
    def planificacionService

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

    def planning() {
        Organizacion org = organizacionService.buscarPorNombre(params.nombreOrganizacion)
        if (org) {
            List<Menu> menu = contenidoService.getMenuDeOrganizacion(org)
            List<Programa> programas = planificacionService.getProgramas(org)
            if (programas.isEmpty()) {
                render status: 404
            } else {
                render view: 'planning', model: [org: org, menu: menu, programas: programas]
            }
        } else {
            render status: 404
        }
    }

    def programa(Long id) {
        Organizacion org = organizacionService.buscarPorNombre(params.nombreOrganizacion)
        if (org) {
            List<Menu> menu = contenidoService.getMenuDeOrganizacion(org)
            Programa programa = planificacionService.getPrograma(id, org)
            if (programa) {
                List<Programa> programas = planificacionService.getProgramas(org)
                render view: 'planning', model: [planning: programa, org: org, menu: menu, programas: programas]
            } else {
                render status: 404
            }
        } else {
            render status: 404
        }
    }

    def proyecto(Long id) {
        Organizacion org = organizacionService.buscarPorNombre(params.nombreOrganizacion)
        if (org) {
            List<Menu> menu = contenidoService.getMenuDeOrganizacion(org)
            Proyecto proyecto = planificacionService.getProyecto(id, org)
            if (proyecto) {
                List<Programa> programas = planificacionService.getProgramas(org)
                render view: 'planning', model: [planning: proyecto, org: org, menu: menu, programas: programas]
            } else {
                render status: 404
            }
        } else {
            render status: 404
        }
    }

    def actividad(Long id) {
        Organizacion org = organizacionService.buscarPorNombre(params.nombreOrganizacion)
        if (org) {
            List<Menu> menu = contenidoService.getMenuDeOrganizacion(org)
            Actividad actividad = planificacionService.getActividad(id, org)
            if (actividad) {
                List<Programa> programas = planificacionService.getProgramas(org)
                render view: 'planning', model: [planning: actividad, org: org, menu: menu, programas: programas]
            } else {
                render status: 404
            }
        } else {
            render status: 404
        }
    }

}
