package org.munaylab.planificacion

import org.munaylab.osc.Organizacion

class Programa extends Planificacion {

    static hasMany = [proyectos: Proyecto]

}
