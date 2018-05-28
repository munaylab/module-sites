package org.munaylab.planificacion

import org.munaylab.osc.Organizacion

class Planificacion {

    String nombre
    String descripcion
    String contenido
    Date fechaDeInicio
    Date fechaDeFin
    Date dateCreated
    Date lastUpdated
    Boolean publicado = Boolean.FALSE

    static belongsTo = [organizacion: Organizacion]

    static constraints = {
        nombre size: 5..500
        descripcion size: 5..1000
        contenido blank: false
        fechaDeInicio nullable: true, validator: { val, obj ->
            if (val && val < new Date()) return 'menorFechaActual'
        }
        fechaDeFin nullable: true, validator: { val, obj ->
            if (val) {
                if (!obj.fechaIni) return 'faltaFechaInicio'
                if (obj.fechaIni && obj.fechaIni > val) return 'fechaIniDespuesDeFechaFin'
            }
        }
    }

}
