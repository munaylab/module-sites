package org.munaylab.contenido

import org.munaylab.user.User
import org.munaylab.osc.Organizacion
import org.munaylab.plugins.Archivo

class Articulo {

    User autor
    String titulo
    String contenido
    Archivo imagen
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
