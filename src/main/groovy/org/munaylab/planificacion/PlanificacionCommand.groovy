package org.munaylab.planificacion

class PlanificacionCommand implements grails.validation.Validateable {

    Long id
    Long orgId
    String nombre
    String descripcion
    String contenido
    Date fechaDeInicio
    Date fechaDeFin
    Boolean publicado = Boolean.FALSE


    static constraints = {
        id nullable: true
        nombre size: 5..500
        descripcion size: 5..1000
        contenido size: 10..5000
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
