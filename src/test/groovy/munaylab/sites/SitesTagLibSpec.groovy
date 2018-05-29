package munaylab.sites

import grails.testing.web.taglib.TagLibUnitTest
import spock.lang.Specification

class SitesTagLibSpec extends Specification implements TagLibUnitTest<SitesTagLib> {

    void "articulo link"() {
        when:
        def link = tagLib.articuloLink(nombre: 'munaylab', articulo: 'articulo')
        then:
        link == 'http://localhost:8080/org/munaylab/blog/articulo'
    }

}
