package munaylab.sites

import org.munaylab.ContenidoService
import org.munaylab.OrganizacionService
import org.munaylab.osc.Organizacion
import org.munaylab.contenido.Articulo
import org.munaylab.contenido.Menu
import org.munaylab.contenido.Principal

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class SitesControllerSpec extends Specification implements ControllerUnitTest<SitesController> {

    def setup() {
        controller.contenidoService = Mock(ContenidoService)
        controller.organizacionService = Mock(OrganizacionService)
    }

    void "microsite de una organizacion"() {
        given:
        params.nombreURL = 'munaylab'
        1 * controller.organizacionService.buscarPorNombre(_) >> { new Organizacion() }
        1 * controller.contenidoService.getPrincipal(_) >> { new Principal() }
        1 * controller.contenidoService.getMenuDeOrganizacion(_) >> { [new Menu(), new Menu()] }
        when:
        controller.index()
        then:
        status == 200
        model.org != null
        model.principal != null
        model.menu.size() == 2
    }

    void "organizacion no encontrada"() {
        given:
        params.nombreURL = 'munaylab'
        1 * controller.organizacionService.buscarPorNombre(_) >> { null }
        when:
        controller.index()
        then:
        status == 404
    }

    void "articulo encontrado"() {
        given:
        params.nombreOrganizacion = 'munaylab'
        params.nombreArticulo = 'how_to'
        1 * controller.organizacionService.buscarPorNombre(_) >> { new Organizacion() }
        1 * controller.contenidoService.buscarArticulo(_,_) >> { new Articulo() }
        1 * controller.contenidoService.getMenuDeOrganizacion(_) >> { [new Menu(), new Menu()] }
        when:
        controller.articulo()
        then:
        status == 200
        model.org != null
        model.articulo != null
        model.menu.size() == 2
    }
    void "articulo no disponible"() {
        given:
        params.nombreOrganizacion = 'munaylab'
        params.nombreArticulo = 'how_to'
        1 * controller.organizacionService.buscarPorNombre(_) >> { new Organizacion() }
        1 * controller.contenidoService.buscarArticulo(_,_) >> { null }
        when:
        controller.articulo()
        then:
        status == 404
    }
    void "articulo no encontrado"() {
        given:
        1 * controller.organizacionService.buscarPorNombre(_) >> { null }
        when:
        controller.articulo()
        then:
        status == 404
    }

}
