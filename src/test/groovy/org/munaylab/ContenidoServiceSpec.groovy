package org.munaylab

import org.munaylab.contenido.Articulo
import org.munaylab.contenido.ArticuloCommand
import org.munaylab.contenido.Menu
import org.munaylab.contenido.MenuCommand
import org.munaylab.contenido.Principal
import org.munaylab.contenido.PrincipalCommand
import org.munaylab.osc.Organizacion
import org.munaylab.user.User
import org.munaylab.plugins.ArchivoService

import grails.testing.gorm.DataTest
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class ContenidoServiceSpec extends Specification
        implements ServiceUnitTest<ContenidoService>, DataTest, UnitTestBase {

    void setupSpec() {
        mockDomains Organizacion, User, Articulo, Menu, Principal
    }

    void setup() {
        service.archivoService = Mock(ArchivoService)
    }

    void 'obtener articulo'() {
        given:
        def articulo = crearArticulo(DATOS_ARTICULO)
        expect:
        service.obtenerArticulo(1, articulo.organizacion) != null
    }
    void 'articulo no encontrado'() {
        given:
        def articulo = crearArticulo(DATOS_ARTICULO)
        expect:
        service.obtenerArticulo(2, articulo.organizacion) == null
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
        new Articulo(datos).save(flush: true, failOnError: true)
    }
    private User getCrearUsuario() {
        new User(DATOS_USER).save(flush: true, failOnError: true)
    }
    private Organizacion getCrearOrganizacion() {
        new Organizacion(DATOS_ORG_VERIFICADA).save(flush: true, failOnError: true)
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
        //assert articulo.imagen == command.imagen
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

    void 'menu valido para editar'() {
        expect:
        service.esUnMenuValidoParaEditar(new MenuCommand(DATOS_MENU_VALIDO), crearOrganizacion)
    }
    void 'menu invalido para editar'() {
        expect:
        !service.esUnMenuValidoParaEditar(new MenuCommand(DATOS_MENU_INVALIDO), crearOrganizacion)
    }
    void 'menu con organizacion invalida para editar'() {
        expect:
        !service.esUnMenuValidoParaEditar(
            new MenuCommand(DATOS_MENU_VALIDO),
            new Organizacion()
        )
    }
    void 'modificar menu correctamente'() {
        given:
        crearMenu(DATOS_MENU)
        def command = new MenuCommand(DATOS_MENU_MODIFICADA << [id: 1])
        when:
        def menu = service.modificarMenu(command)
        then:
        !menu.hasErrors() && Menu.count() == 1
        comprobarMenu(menu, command)
    }
    private crearMenu(datos) {
        def articulo = crearArticulo(DATOS_ARTICULO)
        datos << [contenido: articulo, organizacion: articulo.organizacion]
        new Menu(datos).save(flush: true)
    }
    void 'modificar menu con errores'() {
        given:
        crearMenu(DATOS_MENU)
        def command = new MenuCommand(DATOS_MENU_MODIFICADA << [id: 2])
        when:
        def menu = service.modificarMenu(command)
        then:
        menu.hasErrors() && Menu.count() == 1
    }
    void 'agregar menu correctamente'() {
        given:
        def articulo = crearArticulo(DATOS_ARTICULO)
        def command = new MenuCommand(DATOS_MENU_VALIDO)
        when:
        def menu = service.crearMenu(command, articulo.organizacion)
        then:
        !menu.hasErrors()
        comprobarMenu(menu, command)
    }
    private void comprobarMenu(menu, command) {
        assert menu.nombre == command.nombre
        assert menu.texto == command.texto
        assert menu.link == command.link
        assert menu.prioridad == command.prioridad
        assert menu.articulo.id == command.articuloId
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
