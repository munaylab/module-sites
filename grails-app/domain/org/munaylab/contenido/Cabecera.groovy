package org.munaylab.contenido

import org.munaylab.osc.Organizacion

class Cabecera implements Serializable {

    String titulo
    String nombre
    String link
    Integer prioridad
    Articulo contenido
    static belongsTo = [organizacion: Organizacion]

    static constraints = {
        id composite: ['titulo', 'organizacion']
        titulo nullable: false, blank: false
        nombre nullable: false, blank: false
        link nullable: true, blank: false
        prioridad nullable: false, range: 0..10
        contenido nullable: true
    }

}
