package org.munaylab.contenido

import org.munaylab.osc.Organizacion

class Principal {

    String titulo
    Articulo contenido
    static belongsTo = [organizacion: Organizacion]
    static hasOne = [
        accionPrincipal: Accion,
        accionSecundaria: Accion,
        accionOpcional: Accion
    ]

    static constraints = {
        titulo nullable: true, blank: false
        contenido nullable: false
        accionPrincipal nullable: true
        accionSecundaria nullable: true
        accionOpcional nullable: true
    }

}
