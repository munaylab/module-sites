package org.munaylab.contenido

class ArticuloCommand implements grails.validation.Validateable {

    Long id
    Long orgId
    Long autorId
    String titulo
    String contenido
    String imagen
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
