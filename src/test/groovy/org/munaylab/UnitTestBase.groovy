package org.munaylab

import org.munaylab.osc.EstadoOrganizacion
import org.munaylab.osc.TipoOrganizacion

interface UnitTestBase {

    static final DATOS_USER = [nombre: 'Augusto', apellido: 'Caligares',
        username: 'mcaligares@gmail.com', password: 'password']

    static final DATOS_ORG_VERIFICADA = [nombre: 'Fundación MunayLab', tipo: TipoOrganizacion.FUNDACION,
        nombreURL: 'fundacion_munaylab', estado: EstadoOrganizacion.VERIFICADA,
        objeto: 'brindar soluciones a las organizaciones sociales']

    static final DATOS_ARTICULO = [titulo: 'Nosotros', contenido: 'nosotros contenido',
        imagen: 'imagen.jpg', palabrasClaves: 'keywords', descripcion: 'descripcion', publicado: false]

    static final DATOS_ARTICULO_VALIDO = [orgId: 1, autorId: 1, titulo: 'Nosotros',
        contenido: 'nosotros contenido', imagen: 'imagen.jpg', palabrasClaves: 'keywords',
        descripcion: 'descripcion', publicado: false]

    static final DATOS_ARTICULO_MODIFICADO = [orgId: 1, autorId: 1, titulo: 'Nosotros modificado',
        contenido: 'nosotros modificado', imagen: 'imagenmodificada.jpg',
        palabrasClaves: 'keywords, modificado', descripcion: 'descripcion modificada', publicado: false]

    static final DATOS_ARTICULO_INVALIDO = [orgId: 1, autorId: 1, contenido: 'nosotros contenido',
        imagen: 'imagen.jpg', palabrasClaves: 'keywords', publicado: false]

    static final DATOS_CABECERA_VALIDO = [orgId: 1, contenidoId: 1, titulo: 'titulo',
        nombre: 'nombre', link: 'link', prioridad: 0]

    static final DATOS_CABECERA_INVALIDO = [orgId: 1, contenidoId: 1, link: 'link', prioridad: 0]

    static final DATOS_CABECERA = [titulo: 'Titulo', nombre: 'Nombre', link: 'link', prioridad: 0]

    static final DATOS_CABECERA_MODIFICADA = [orgId: 1, contenidoId: 1, titulo: 'titulo modificado',
        nombre: 'nombre modificado', link: 'link_modificado', prioridad: 1]

    static final DATOS_PRINCIPAL_VALIDOS = [orgId: 1, contenidoId: 1, titulo: 'Pagina Principal']

    static final DATOS_PRINCIPAL_INVALIDOS = [orgId: 1, contenidoId: 1]

    static final DATOS_PROGRAMA_VALIDOS = [orgId: 1, nombre: 'mi programa',
        imagen: 'imagen/de/mi/programa.jpg', descripcion: 'descripcion del programa']

    static final DATOS_PROGRAMA_INVALIDOS = [orgId: 2, descripcion: 'descripcion del programa',
        imagen: 'imagen/de/mi/programa.jpg']

    static final DATOS_PROGRAMA = [nombre: 'Innovaciones Sociales', imagen: 'programa/innovacion',
        descripcion: 'Brindar innovaciones a las osc.', publicado: true]

    static final DATOS_PROYECTO_VALIDOS = [orgId: 1, programaId: 1, nombre: 'mi proyecto',
        imagen: 'imagen/de/mi/proyecto.jpg', descripcion: 'descripcion del proyecto']

    static final DATOS_PROYECTO_INVALIDOS = [orgId: 2, programaId: 1,
        imagen: 'imagen/de/mi/proyecto.jpg', descripcion: 'descripcion del proyecto']

    static final DATOS_PROYECTO = [nombre: 'Taller de Innovaciones', imagen: 'proyecto/programa/innovacion',
        descripcion: 'Taller donde se exponen innovaciones para la sociedad', publicado: true]

    static final DATOS_ACTIVIDAD_VALIDOS = [orgId: 1, nombre: 'mi actividad',
        imagen: 'imagen/de/mi/actividad.jpg', descripcion: 'descripcion del actividad']

    static final DATOS_ACTIVIDAD_INVALIDOS = [orgId: 1,
        imagen: 'imagen/de/mi/actividad.jpg', descripcion: 'descripcion del actividad']

    static final DATOS_ACTIVIDAD = [nombre: 'Presentacion Innovacion', imagen: 'proyecto/actividad/innovacion',
        descripcion: 'Presentacion de innovaciones realizadas en el taller', publicado: true]

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
