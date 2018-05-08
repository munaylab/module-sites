package org.munaylab.contenido

class CabeceraCommand implements grails.validation.Validateable {

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
