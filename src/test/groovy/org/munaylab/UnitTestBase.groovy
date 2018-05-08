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

}
