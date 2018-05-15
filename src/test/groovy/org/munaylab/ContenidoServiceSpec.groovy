package org.munaylab

import org.munaylab.contenido.Articulo
import org.munaylab.contenido.ArticuloCommand
import org.munaylab.contenido.Cabecera
import org.munaylab.contenido.CabeceraCommand
import org.munaylab.contenido.Principal
import org.munaylab.contenido.PrincipalCommand
import org.munaylab.osc.Organizacion
import org.munaylab.user.User

import grails.testing.gorm.DataTest
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class ContenidoServiceSpec extends Specification
        implements ServiceUnitTest<ContenidoService>, DataTest, UnitTestBase {

    void setupSpec() {
        mockDomains Organizacion, User, Articulo, Cabecera, Principal
    }

    void 'obtener lista de articulos'() {
        given:
        def articulo = crearArticulo(DATOS_ARTICULO)
        expect:
        service.obtenerTodosLosArticulos(articulo.organizacion).size() == 1
    }
    void 'obtener listado de articulos'() {
        given:
        def org = crearOrganizacion
        def datos = DATOS_ARTICULO << [autor: crearUsuario, organizacion: org]
        10.times { new Articulo(datos).save(flush: true) }
        expect:
        service.obtenerTodosLosArticulos(org).size() == 10
    }
    void 'buscar articulo sin especificar la organizacion'() {
        expect:
        null == service.buscarArticulosDeOrganizacionPor(null, null)
    }
    void 'buscar articulo encontrado'() {
        given:
        def articulo = crearArticulo(DATOS_ARTICULO)
        def criteria = { eq 'titulo', 'Nosotros' }
        expect:
        service.buscarArticulosDeOrganizacionPor(articulo.organizacion, criteria).size() == 1
    }
    void 'buscar articulo no encontrado'() {
        given:
        def articulo = crearArticulo(DATOS_ARTICULO)
        def criteria = { eq 'titulo', 'Otros' }
        expect:
        service.buscarArticulosDeOrganizacionPor(articulo.organizacion, criteria).isEmpty()
    }
    void 'buscar articulos'() {
        given:
        def org = crearOrganizacion
        def criteria = { like 'titulo', 'Titulo%' }
        and:
        def datos = DATOS_ARTICULO << [autor: crearUsuario, organizacion: org]
        20.times {
            datos.titulo = "Titulo $it"
            new Articulo(datos).save(flush: true)
        }
        expect:
        service.buscarArticulosDeOrganizacionPor(org, criteria).size() == 20
    }
    private crearArticulo(datos) {
        datos << [autor: crearUsuario, organizacion: crearOrganizacion]
        new Articulo(datos).save(flush: true)
    }
    private User getCrearUsuario() {
        new User(DATOS_USER).save(flush: true)
    }
    private Organizacion getCrearOrganizacion() {
        new Organizacion(DATOS_ORG_VERIFICADA).save(flush: true)
    }
    private getCrearOrganizacionYUsuario() {
        def user = crearUsuario
        def org = crearOrganizacion
        [org, user]
    }

    void 'articulo valido para editar'() {
        expect:
        service.esUnArticuloValidoParaEditar(
            new ArticuloCommand(DATOS_ARTICULO_VALIDO),
            crearOrganizacion
        )
    }
    void 'articulo invalido para editar'() {
        expect:
        !service.esUnArticuloValidoParaEditar(
            new ArticuloCommand(DATOS_ARTICULO_INVALIDO),
            crearOrganizacion
        )
    }
    void 'organizacion de articulo invalido para editar'() {
        expect:
        !service.esUnArticuloValidoParaEditar(
            new ArticuloCommand(DATOS_ARTICULO_VALIDO),
            new Organizacion()
        )
    }
    void 'modificar articulo correctamente'() {
        given:
        crearArticulo(DATOS_ARTICULO)
        def command = new ArticuloCommand(DATOS_ARTICULO_MODIFICADO << [id: 1])
        when:
        def articulo = service.modificarArticulo(command)
        then:
        !articulo.hasErrors() && Articulo.count() == 1
        comprobarArticulo(articulo, command)
    }
    void 'modificar articulo con errores'() {
        given:
        crearArticulo(DATOS_ARTICULO)
        def command = new ArticuloCommand(DATOS_ARTICULO_MODIFICADO << [id: 2])
        when:
        def articulo = service.modificarArticulo(command)
        then:
        articulo.hasErrors() && Articulo.count() == 1
    }
    void 'agregar articulo correctamente'() {
        given:
        def (org, user) = crearOrganizacionYUsuario
        def command = new ArticuloCommand(DATOS_ARTICULO_VALIDO)
        when:
        def articulo = service.crearArticulo(command, org)
        then:
        !articulo.hasErrors()
        comprobarArticulo(articulo, command)
    }
    private void comprobarArticulo(articulo, command) {
        assert articulo.titulo == command.titulo
        assert articulo.contenido == command.contenido
        assert articulo.imagen == command.imagen
        assert articulo.palabrasClaves == command.palabrasClaves
        assert articulo.descripcion == command.descripcion
        assert articulo.publicado == command.publicado
    }

    void 'agregar articulo correctamente'() {
        given:
        def (org, user) = crearOrganizacionYUsuario
        def command = new ArticuloCommand(DATOS_ARTICULO_VALIDO)
        when:
        def articulo = service.actualizarArticulo(command, org)
        then:
        articulo != null && Articulo.count() == 1
    }
    void 'agregar articulo con errores'() {
        given:
        def (org, user) = crearOrganizacionYUsuario
        def command = new ArticuloCommand(DATOS_ARTICULO_INVALIDO)
        when:
        def articulo = service.actualizarArticulo(command, org)
        then:
        articulo == null && Articulo.count() == 0
    }
    void 'agregar articulo con error al guardar'() {
        given:
        def command = new ArticuloCommand(DATOS_ARTICULO_VALIDO)
        when:
        def articulo = service.actualizarArticulo(command, crearOrganizacion)
        then:
        articulo.hasErrors() && Articulo.count() == 0
    }
    void 'modificar articulo correctamente'() {
        given:
        def articulo = crearArticulo(DATOS_ARTICULO)
        def command = new ArticuloCommand(DATOS_ARTICULO_MODIFICADO << [id: 1])
        when:
        articulo = service.actualizarArticulo(command, articulo.organizacion)
        then:
        Articulo.count() == 1
        comprobarArticulo(articulo, command)
    }
    void 'eliminar articulo nosotros'() {
        given:
        def articulo = crearArticulo(DATOS_ARTICULO)
        def command = new ArticuloCommand([id: 1])
        when:
        service.eliminarArticulo(articulo.organizacion, command)
        then:
        Articulo.count() == 0
    }

    void 'cabecera valida para editar'() {
        expect:
        service.esUnaCabeceraValidaParaEditar(
            new CabeceraCommand(DATOS_CABECERA_VALIDO),
            crearOrganizacion
        )
    }
    void 'cabecera invalido para editar'() {
        expect:
        !service.esUnaCabeceraValidaParaEditar(
            new CabeceraCommand(DATOS_CABECERA_INVALIDO),
            crearOrganizacion
        )
    }
    void 'cabecera con organizacion invalida para editar'() {
        expect:
        !service.esUnaCabeceraValidaParaEditar(
            new CabeceraCommand(DATOS_CABECERA_VALIDO),
            new Organizacion()
        )
    }
    void 'modificar cabecera correctamente'() {
        given:
        crearCabecera(DATOS_CABECERA)
        def command = new CabeceraCommand(DATOS_CABECERA_MODIFICADA << [id: 1])
        when:
        def cabecera = service.modificarCabecera(command)
        then:
        !cabecera.hasErrors() && Cabecera.count() == 1
        comprobarCabecera(cabecera, command)
    }
    private crearCabecera(datos) {
        def articulo = crearArticulo(DATOS_ARTICULO)
        datos << [contenido: articulo, organizacion: articulo.organizacion]
        new Cabecera(datos).save(flush: true)
    }
    void 'modificar cabecera con errores'() {
        given:
        crearCabecera(DATOS_CABECERA)
        def command = new CabeceraCommand(DATOS_CABECERA_MODIFICADA << [id: 2])
        when:
        def cabecera = service.modificarCabecera(command)
        then:
        cabecera.hasErrors() && Cabecera.count() == 1
    }
    void 'agregar cabecera correctamente'() {
        given:
        def articulo = crearArticulo(DATOS_ARTICULO)
        def command = new CabeceraCommand(DATOS_CABECERA_VALIDO)
        when:
        def cabecera = service.crearCabecera(command, articulo.organizacion)
        then:
        !cabecera.hasErrors()
        comprobarCabecera(cabecera, command)
    }
    private void comprobarCabecera(cabecera, command) {
        assert cabecera.titulo == command.titulo
        assert cabecera.nombre == command.nombre
        assert cabecera.link == command.link
        assert cabecera.prioridad == command.prioridad
        assert cabecera.contenido.id == command.contenidoId
    }

    void 'agregar contenido principal'() {
        given:
        def articulo = crearArticulo(DATOS_ARTICULO)
        def command = new PrincipalCommand(DATOS_PRINCIPAL_VALIDOS)
        when:
        def principal = service.actualizarPrincipal(command, articulo.organizacion)
        then:
        principal.id != null && Principal.count() == 1
    }
}
