package org.munaylab.contenido

import org.munaylab.osc.Organizacion

class Accion {

    String titulo
    String link
    static belongsTo = [principal: Principal]

    static constraints = {
        titulo nullable: true, blank: false
        link nullable: false, blank: false
    }

}
