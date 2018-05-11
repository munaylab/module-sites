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

    void 'articulo valido para editar'() {
        expect:
        service.esUnArticuloValidoParaEditar(
            new ArticuloCommand(DATOS_ARTICULO_VALIDO),
            new Organizacion(DATOS_ORG_VERIFICADA).save(flush: true)
        )
    }
    void 'articulo invalido para editar'() {
        expect:
        !service.esUnArticuloValidoParaEditar(
            new ArticuloCommand(DATOS_ARTICULO_INVALIDO),
            new Organizacion(DATOS_ORG_VERIFICADA).save(flush: true)
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
        def user = new User(DATOS_USER).save(flush: true)
        def org = new Organizacion(DATOS_ORG_VERIFICADA).save(flush: true)
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
        def user = new User(DATOS_USER).save(flush: true)
        def org = new Organizacion(DATOS_ORG_VERIFICADA).save(flush: true)
        def command = new ArticuloCommand(DATOS_ARTICULO_VALIDO)
        when:
        def articulo = service.actualizarArticulo(command, org)
        then:
        articulo != null && Articulo.count() == 1
    }
    void 'agregar articulo con errores'() {
        given:
        def user = new User(DATOS_USER).save(flush: true)
        def org = new Organizacion(DATOS_ORG_VERIFICADA).save(flush: true)
        def command = new ArticuloCommand(DATOS_ARTICULO_INVALIDO)
        when:
        def articulo = service.actualizarArticulo(command, org)
        then:
        articulo == null && Articulo.count() == 0
    }
    void 'agregar articulo con error al guardar'() {
        given:
        def org = new Organizacion(DATOS_ORG_VERIFICADA).save(flush: true)
        def command = new ArticuloCommand(DATOS_ARTICULO_VALIDO)
        when:
        def articulo = service.actualizarArticulo(command, org)
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
            new Organizacion(DATOS_ORG_VERIFICADA).save(flush: true)
        )
    }
    void 'cabecera invalido para editar'() {
        expect:
        !service.esUnaCabeceraValidaParaEditar(
            new CabeceraCommand(DATOS_CABECERA_INVALIDO),
            new Organizacion(DATOS_ORG_VERIFICADA).save(flush: true)
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
    private crearArticulo(datos) {
        def user = new User(DATOS_USER).save(flush: true)
        def org = new Organizacion(DATOS_ORG_VERIFICADA).save(flush: true)
        datos << [autor: user, organizacion: org]
        new Articulo(datos).save(flush: true)
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
