package org.munaylab.contenido

import org.munaylab.user.User
import org.munaylab.osc.Organizacion

class Articulo {

    User autor
    String titulo
    String contenido
    String imagen
    String palabrasClaves
    String descripcion
    Boolean publicado = false
    static belongsTo = [organizacion: Organizacion]

    Date dateCreated
    Date lastUpdated

    static constraints = {
        titulo size: 5..100
        contenido size: 10..5000
        imagen nullable: true
        palabrasClaves size: 3..1000
        descripcion size: 3..1000
    }

}
