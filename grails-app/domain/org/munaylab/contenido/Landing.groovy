package org.munaylab.contenido

import org.munaylab.osc.Organizacion
import org.munaylab.plugins.Archivo

class Landing {

    String titulo
    Archivo imagen
    String imagenLink
    Articulo contenido
    static belongsTo = [organizacion: Organizacion]
    Accion accionPrincipal
    Accion accionSecundaria
    Accion accionOpcional

    static constraints = {
        titulo nullable: true, blank: false
        imagen nullable: true
        imagenLink nullable: true
        contenido nullable: false
        accionPrincipal nullable: true
        accionSecundaria nullable: true
        accionOpcional nullable: true
    }

}
