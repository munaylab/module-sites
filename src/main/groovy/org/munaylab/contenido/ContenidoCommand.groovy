package org.munaylab.contenido

import org.munaylab.plugins.ArchivoCommand

import grails.validation.Validateable

class ArticuloCommand implements Validateable {

    Long id
    Long orgId
    Long autorId
    String titulo
    String contenido
    ArchivoCommand imagen
    String palabrasClaves
    String descripcion
    Boolean publicado

    static constraints = {
        id nullable: true
        titulo size: 5..100
        contenido size: 10..5000
        imagen nullable: true
        palabrasClaves size: 3..1000
        descripcion size: 3..1000
    }
}

class CabeceraCommand implements Validateable {

    Long id
    Long orgId
    Long contenidoId
    String titulo
    String nombre
    String link
    Integer prioridad

    static constraints = {
        id nullable: true
        orgId nullable: true
        contenidoId nullable: false
        titulo nullable: false, blank: false
        nombre nullable: false, blank: false
        link nullable: true, blank: false
        prioridad nullable: false, range: 0..10
    }
}

class PrincipalCommand implements Validateable {

    Long id
    Long orgId
    Long contenidoId
    String titulo
    AccionCommand accionPrincipal
    AccionCommand accionSecundaria
    AccionCommand accionOpcional

    static constraints = {
        id nullable: true
        titulo size: 5..100
        accionPrincipal nullable: true
        accionSecundaria nullable: true
        accionOpcional nullable: true
    }
}

class AccionCommand implements Validateable {

    Long id
    String titulo
    String link

    static constraints = {
        id nullable: true
        titulo nullable: false, blank: false
        link nullable: false, blank: false
    }
}
