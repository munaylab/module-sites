package org.munaylab.contenido

import org.munaylab.plugins.ArchivoCommand

import grails.validation.Validateable

class ArticuloCommand implements Validateable {

    Long id
    Long orgId
    Long autorId
    String url
    String titulo
    String contenido
    ArchivoCommand imagen
    String palabrasClaves
    String descripcion
    Boolean publicado = Boolean.FALSE

    static constraints = {
        id nullable: true
        orgId nullable: true
        autorId nullable: true
        url nullable: true, size: 5..500
        titulo size: 5..100
        contenido size: 10..5000
        imagen nullable: true
        palabrasClaves size: 3..1000
        descripcion size: 3..1000
    }
}

class MenuCommand implements Validateable {

    Long id
    Long orgId
    Long articuloId
    String nombre
    String link
    Integer prioridad

    static constraints = {
        id nullable: true
        orgId nullable: true
        articuloId nullable: true, validator : { val, obj, errors ->
            if (val == null && obj.link == null)
                errors.rejectValue('articuloId', 'error.contenido.y.link.null')
        }
        nombre nullable: false, blank: false
        link nullable: true, blank: false
        prioridad nullable: false, range: 0..10
    }
}

class LandingCommand implements Validateable {

    Long id
    Long orgId
    Long contenidoId
    String titulo
    ArchivoCommand imagen
    String imagenLink
    AccionCommand accionPrincipal
    AccionCommand accionSecundaria
    AccionCommand accionOpcional

    static constraints = {
        id nullable: true
        titulo size: 5..100
        imagen nullable: true
        imagenLink nullable: true
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
