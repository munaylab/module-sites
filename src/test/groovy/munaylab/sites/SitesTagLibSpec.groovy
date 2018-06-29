package munaylab.sites

import org.munaylab.ContenidoService
import org.munaylab.contenido.Menu
import org.munaylab.osc.Organizacion

import grails.testing.web.taglib.TagLibUnitTest
import spock.lang.Specification

class SitesTagLibSpec extends Specification implements TagLibUnitTest<SitesTagLib> {

    def setup() {
        tagLib.contenidoService = Mock(ContenidoService)
    }

    void "menu de organizacion"() {
        given:
        def menues = [new Menu(nombre: 'menu1'), new Menu(nombre: 'menu2')]
        1 * tagLib.contenidoService.getMenuDeOrganizacion(_) >> {menues}
        when:
        def output = tagLib.menu(org: new Organizacion())
        then:
        output.contains('menu1')
        output.contains('menu2')
    }

    void "articulo link"() {
        when:
        def link = tagLib.articuloLink(nombre: 'munaylab', articulo: 'articulo')
        then:
        link == 'http://localhost:8080/munaylab/blog/articulo'
    }

}
