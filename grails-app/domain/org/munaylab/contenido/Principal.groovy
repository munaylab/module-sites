package org.munaylab.contenido

import org.munaylab.osc.Organizacion

class Principal {

    String titulo
    String imagen
    Articulo contenido
    static belongsTo = [organizacion: Organizacion]
    Accion accionPrincipal
    Accion accionSecundaria
    Accion accionOpcional

    static constraints = {
        titulo nullable: true, blank: false
        imagen nullable: true, blank: false
        contenido nullable: false
        accionPrincipal nullable: true
        accionSecundaria nullable: true
        accionOpcional nullable: true
    }

}
