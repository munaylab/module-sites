package org.munaylab.contenido

import org.munaylab.osc.Organizacion

class Menu implements Serializable {

    String nombre
    String link
    Integer prioridad
    Articulo articulo
    static belongsTo = [organizacion: Organizacion]

    static constraints = {
        nombre nullable: false, blank: false
        link nullable: true, blank: false
        prioridad nullable: false, range: 0..10
        articulo nullable: true
        organizacion nullable: false
    }

}
