package org.munaylab.factory

import org.munaylab.contenido.*

class ArticuloBuilder {
    Articulo articulo = new Articulo()

    ArticuloCommandBuilder getCommand() {
        new ArticuloCommandBuilder()
    }
    CabeceraBuilder getCabecera() {
        new CabeceraBuilder()
    }
    CabeceraCommandBuilder getCabeceraCommand() {
        new CabeceraCommandBuilder()
    }
    PrincipalCommandBuilder getPrincipalCommand() {
        new PrincipalCommandBuilder()
    }

    ArticuloBuilder conAutor(value) {
        articulo.autor = value
        this
    }
    ArticuloBuilder conOrganizacion(value) {
        articulo.organizacion = value
        this
    }
    ArticuloBuilder conImagen(value) {
        articulo.imagen = value
        this
    }
    ArticuloBuilder conTitulo(value) {
        articulo.titulo = value
        this
    }
    ArticuloBuilder conContenido(value) {
        articulo.contenido = value
        this
    }
    ArticuloBuilder conPalabrasClaves(value) {
        articulo.palabrasClaves = value
        this
    }
    ArticuloBuilder conDescripcion(value) {
        articulo.descripcion = value
        this
    }
    ArticuloBuilder publicado() {
        articulo.publicado = true
        this
    }
    ArticuloBuilder conDatos(properties) {
        articulo.properties = properties
        this
    }
    Articulo getCrear() {
        articulo
    }
}

class ArticuloCommandBuilder {
    ArticuloCommand command = new ArticuloCommand()

    ArticuloCommandBuilder conId(value) {
        command.id = value
        this
    }
    ArticuloCommandBuilder conDatos(properties) {
        command = new ArticuloCommand(properties)
        this
    }
    ArticuloCommand getCrear() {
        command
    }
}

class CabeceraBuilder {
    Cabecera cabecera = new Cabecera()

    CabeceraBuilder conDatos(properties) {
        cabecera.properties = properties
        this
    }
    CabeceraBuilder conContenido(value) {
        cabecera.contenido = value
        this
    }
    CabeceraBuilder conOrganizacion(value) {
        cabecera.organizacion = value
        this
    }
    CabeceraBuilder conTitulo(value) {
        cabecera.titulo = value
        this
    }
    CabeceraBuilder conNombre(value) {
        cabecera.nombre = value
        this
    }
    CabeceraBuilder conLink(value) {
        cabecera.link = value
        this
    }
    CabeceraBuilder conPrioridad(value) {
        cabecera.prioridad = value
        this
    }
    Cabecera getCrear() {
        cabecera
    }
}

class CabeceraCommandBuilder {
    CabeceraCommand command = new CabeceraCommand()

    CabeceraCommandBuilder conId(value) {
        command.id = value
        this
    }
    CabeceraCommandBuilder conDatos(properties) {
        command = new CabeceraCommand(properties)
        this
    }
    CabeceraCommand getCrear() {
        command
    }
}

class PrincipalCommandBuilder {
    PrincipalCommand command = new PrincipalCommand()

    PrincipalCommandBuilder conId(value) { command.id = value; this }
    PrincipalCommandBuilder conOrgId(value) { command.orgId = value; this }
    PrincipalCommandBuilder conContenidoId(value) { command.contenidoId = value; this }
    PrincipalCommandBuilder conTitulo(value) { command.titulo = value; this }
    PrincipalCommandBuilder conAccionPrincipal(value) { command.accionPrincipal = value; this }
    PrincipalCommandBuilder conAccionSecundaria(value) { command.accionSecundaria = value; this }
    PrincipalCommandBuilder conAccionOpcional(value) { command.accionOpcional = value; this }
    PrincipalCommandBuilder conDatos(props) { command = new PrincipalCommand(props); this }
    PrincipalCommand getCrear() { command }
}
