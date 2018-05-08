package org.munaylab

import org.munaylab.contenido.Articulo
import org.munaylab.contenido.Cabecera
import org.munaylab.contenido.Principal
import org.munaylab.factory.SiteBuilder as Builder
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
            Builder.articulo.command.conDatos(DATOS_ARTICULO_VALIDO).crear,
            Builder.organizacion.conDatos(DATOS_ORG_VERIFICADA).crear.save(flush: true)
        )
    }
    void 'articulo invalido para editar'() {
        expect:
        !service.esUnArticuloValidoParaEditar(
            Builder.articulo.command.conDatos(DATOS_ARTICULO_INVALIDO).crear,
            Builder.organizacion.conDatos(DATOS_ORG_VERIFICADA).crear.save(flush: true)
        )
    }
    void 'organizacion de articulo invalido para editar'() {
        expect:
        !service.esUnArticuloValidoParaEditar(
            Builder.articulo.command.conDatos(DATOS_ARTICULO_VALIDO).crear,
            Builder.organizacion.crear
        )
    }
    void 'modificar articulo correctamente'() {
        given:
        crearArticulo(DATOS_ARTICULO)
        def command = Builder.articulo.command
                .conDatos(DATOS_ARTICULO_MODIFICADO)
                .conId(1)
                .crear
        when:
        def articulo = service.modificarArticulo(command)
        then:
        !articulo.hasErrors() && Articulo.count() == 1
        comprobarArticulo(articulo, command)
    }
    void 'modificar articulo con errores'() {
        given:
        crearArticulo(DATOS_ARTICULO)
        def command = Builder.articulo.command
                .conDatos(DATOS_ARTICULO_MODIFICADO)
                .conId(2)
                .crear
        when:
        def articulo = service.modificarArticulo(command)
        then:
        articulo.hasErrors() && Articulo.count() == 1
    }
    void 'agregar articulo correctamente'() {
        given:
        def user = Builder.user.conDatos(DATOS_USER).crear.save(flush: true)
        def org = Builder.organizacion.conDatos(DATOS_ORG_VERIFICADA).crear.save(flush: true)
        def command = Builder.articulo.command
                .conDatos(DATOS_ARTICULO_VALIDO)
                .crear
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
        def user = Builder.user.conDatos(DATOS_USER).crear.save(flush: true)
        def org = Builder.organizacion.conDatos(DATOS_ORG_VERIFICADA).crear.save(flush: true)
        def command = Builder.articulo.command
                .conDatos(DATOS_ARTICULO_VALIDO)
                .crear
        when:
        def articulo = service.actualizarArticulo(org, command)
        then:
        articulo != null && Articulo.count() == 1
    }
    void 'agregar articulo con errores'() {
        given:
        def user = Builder.user.conDatos(DATOS_USER).crear.save(flush: true)
        def org = Builder.organizacion.conDatos(DATOS_ORG_VERIFICADA).crear.save(flush: true)
        def command = Builder.articulo.command
                .conDatos(DATOS_ARTICULO_INVALIDO)
                .crear
        when:
        def articulo = service.actualizarArticulo(org, command)
        then:
        articulo == null && Articulo.count() == 0
    }
    void 'agregar articulo con error al guardar'() {
        given:
        def org = Builder.organizacion.conDatos(DATOS_ORG_VERIFICADA).crear.save(flush: true)
        def command = Builder.articulo.command
                .conDatos(DATOS_ARTICULO_VALIDO)
                .crear
        when:
        def articulo = service.actualizarArticulo(org, command)
        then:
        articulo.hasErrors() && Articulo.count() == 0
    }
    void 'modificar articulo correctamente'() {
        given:
        def articulo = crearArticulo(DATOS_ARTICULO)
        def command = Builder.articulo.command
                .conDatos(DATOS_ARTICULO_MODIFICADO)
                .conId(1)
                .crear
        when:
        articulo = service.actualizarArticulo(articulo.organizacion, command)
        then:
        Articulo.count() == 1
        comprobarArticulo(articulo, command)
    }
    void 'eliminar articulo nosotros'() {
        given:
        def articulo = crearArticulo(DATOS_ARTICULO)
        def command = Builder.articulo.command
                .conId(1)
                .crear
        when:
        service.eliminarArticulo(articulo.organizacion, command)
        then:
        Articulo.count() == 0
    }

    void 'cabecera valida para editar'() {
        expect:
        service.esUnaCabeceraValidaParaEditar(
            Builder.articulo.cabeceraCommand.conDatos(DATOS_CABECERA_VALIDO).crear,
            Builder.organizacion.conDatos(DATOS_ORG_VERIFICADA).crear.save(flush: true)
        )
    }
    void 'cabecera invalido para editar'() {
        expect:
        !service.esUnaCabeceraValidaParaEditar(
            Builder.articulo.cabeceraCommand.conDatos(DATOS_CABECERA_INVALIDO).crear,
            Builder.organizacion.conDatos(DATOS_ORG_VERIFICADA).crear.save(flush: true)
        )
    }
    void 'cabecera con organizacion invalida para editar'() {
        expect:
        !service.esUnaCabeceraValidaParaEditar(
            Builder.articulo.cabeceraCommand.conDatos(DATOS_CABECERA_VALIDO).crear,
            Builder.organizacion.crear
        )
    }
    void 'modificar cabecera correctamente'() {
        given:
        crearCabecera(DATOS_CABECERA)
        def command = Builder.articulo.cabeceraCommand
                .conDatos(DATOS_CABECERA_MODIFICADA)
                .conId(1)
                .crear
        when:
        def cabecera = service.modificarCabecera(command)
        then:
        !cabecera.hasErrors() && Cabecera.count() == 1
        comprobarCabecera(cabecera, command)
    }
    private crearCabecera(datos) {
        def articulo = crearArticulo(DATOS_ARTICULO)
        Builder.articulo.cabecera
                .conDatos(datos)
                .conContenido(articulo)
                .conOrganizacion(articulo.organizacion)
                .crear.save(flush: true)
    }
    private crearArticulo(datos) {
        def user = Builder.user.conDatos(DATOS_USER).crear.save(flush: true)
        def org = Builder.organizacion.conDatos(DATOS_ORG_VERIFICADA).crear.save(flush: true)
        Builder.articulo.conDatos(datos)
                .conAutor(user)
                .conOrganizacion(org)
                .crear.save(flush: true)
    }
    void 'modificar cabecera con errores'() {
        given:
        crearCabecera(DATOS_CABECERA)
        def command = Builder.articulo.cabeceraCommand
                .conDatos(DATOS_CABECERA_MODIFICADA)
                .conId(2)
                .crear
        when:
        def cabecera = service.modificarCabecera(command)
        then:
        cabecera.hasErrors() && Cabecera.count() == 1
    }
    void 'agregar cabecera correctamente'() {
        given:
        def command = Builder.articulo.cabeceraCommand.conDatos(DATOS_CABECERA_VALIDO).crear
        def articulo = crearArticulo(DATOS_ARTICULO)
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
        def command = Builder.articulo.principalCommand
                .conDatos(DATOS_PRINCIPAL_VALIDOS)
                .crear
        when:
        def principal = service.actualizarPrincipal(command, articulo.organizacion)
        then:
        principal.id != null && Principal.count() == 1
    }
}
