package org.munaylab

import org.munaylab.osc.EstadoOrganizacion
import org.munaylab.osc.TipoOrganizacion

interface UnitTestBase {

    static final DATOS_USER = [nombre: 'Augusto', apellido: 'Caligares',
        username: 'mcaligares@gmail.com', password: 'password']

    static final DATOS_ORG_VERIFICADA = [nombre: 'Fundación MunayLab', tipo: TipoOrganizacion.FUNDACION,
        nombreURL: 'fundacion_munaylab', estado: EstadoOrganizacion.VERIFICADA,
        objeto: 'brindar soluciones a las organizaciones sociales',
        descripcion: 'brindar soluciones a las organizaciones sociales']

    static final DATOS_ARTICULO = [titulo: 'Nosotros', contenido: 'nosotros contenido', url: 'nosotros',
        palabrasClaves: 'keywords', descripcion: 'descripcion', publicado: false]

    static final DATOS_ARTICULO_VALIDO = [orgId: 1, autorId: 1, titulo: 'Nosotros',
        contenido: 'nosotros contenido', palabrasClaves: 'keywords',
        imagen: [accion: 'none'], descripcion: 'descripcion', publicado: false]

    static final DATOS_ARTICULO_MODIFICADO = [orgId: 1, autorId: 1, titulo: 'Nosotros modificado',
        contenido: 'nosotros modificado', palabrasClaves: 'keywords, modificado',
        imagen: [accion: 'none'], descripcion: 'descripcion modificada', publicado: false]

    static final DATOS_ARTICULO_INVALIDO = [orgId: 1, autorId: 1, contenido: 'nosotros contenido',
        palabrasClaves: 'keywords', publicado: false]

    static final DATOS_MENU_VALIDO = [orgId: 1, articuloId: 1, nombre: 'nombre', link: 'link', prioridad: 0]

    static final DATOS_MENU_INVALIDO = [orgId: 1, articuloId: 1, link: 'link', prioridad: 0]

    static final DATOS_MENU = [nombre: 'Nombre', link: 'link', prioridad: 0]

    static final DATOS_MENU_MODIFICADA = [orgId: 1, articuloId: 1,
        nombre: 'nombre modificado', link: 'link_modificado', prioridad: 1]

    static final DATOS_LANDING_VALIDOS = [orgId: 1, contenidoId: 1, titulo: 'Pagina Principal']

    static final DATOS_LANDING_INVALIDOS = [orgId: 1, contenidoId: 1]

    static final DATOS_PROGRAMA_VALIDOS = [orgId: 1, nombre: 'mi programa',
        descripcion: 'descripcion del programa', contenido: '# Contenido de mi programa']

    static final DATOS_PROGRAMA_INVALIDOS = [orgId: 2, descripcion: 'descripcion del programa']

    static final DATOS_PROGRAMA = [nombre: 'Innovaciones Sociales', publicado: true,
        descripcion: 'Brindar innovaciones a las osc.', contenido: '# Contenido de mi programa']

    static final DATOS_PROYECTO_VALIDOS = [orgId: 1, programaId: 1, nombre: 'mi proyecto',
        descripcion: 'descripcion del proyecto', contenido: '# Contenido de mi proyecto']

    static final DATOS_PROYECTO_INVALIDOS = [orgId: 2, programaId: 1, descripcion: 'descripcion del proyecto']

    static final DATOS_PROYECTO = [nombre: 'Taller de Innovaciones', publicado: true,
        descripcion: 'Taller donde se exponen innovaciones para la sociedad',
        contenido: '# Contenido de mi proyecto']

    static final DATOS_ACTIVIDAD_VALIDOS = [orgId: 1, nombre: 'mi actividad',
        descripcion: 'descripcion del actividad',
        contenido: '# Contenido de mi actividad']

    static final DATOS_ACTIVIDAD_INVALIDOS = [orgId: 1, descripcion: 'descripcion del actividad']

    static final DATOS_ACTIVIDAD = [nombre: 'Presentacion Innovacion', publicado: true,
        descripcion: 'Presentacion de innovaciones realizadas en el taller',
        contenido: '# Contenido de mi actividad']

    static final DATOS_DOMICILIO_VALIDOS = [id: 1, calle: 'Reconquista', numero: '1125', barrio: 'Centro',
        localidad: 'Cuidad Autonoma de Buenos Aires', provincia: 'Buenos Aires']

    static final DATOS_EVENTO_VALIDOS = [orgId: 1, nombre: 'mi evento', imagen: 'imagen/de/mi/evento.jpg',
        descripcion: 'descripcion del evento', fechaIni: new Date() + 9, fechaFin: new Date() + 9,
        horaIni: '08:00', horaFin: '10:00']

    static final DATOS_EVENTO_INVALIDOS = [orgId: 1, imagen: 'imagen/de/mi/evento.jpg',
        descripcion: 'descripcion del evento', fechaIni: new Date(), fechaFin: new Date()]

    static final DATOS_EVENTO_CON_DIRECCION_VALIDOS = [orgId: 1, nombre: 'mi evento', imagen: 'imagen/de/mi/evento.jpg',
        descripcion: 'descripcion del evento', fechaIni: new Date() + 9, fechaFin: new Date() + 9,
        fechaDifusion: new Date() + 2, horaIni: '08:00', horaFin: '10:00']

    static final DATOS_EVENTO = [nombre: 'Presentacion Plataforma', imagen: 'evento/presentacion.jpg',
        descripcion: 'Presentacion de la plataforma a las OSC locales', publicado: true,
        fechaIni: new Date() +20, fechaFin: new Date() +21]

}
