package org.munaylab

import org.munaylab.contenido.Articulo
import org.munaylab.contenido.ArticuloCommand
import org.munaylab.contenido.Cabecera
import org.munaylab.contenido.CabeceraCommand
import org.munaylab.osc.Organizacion
import org.munaylab.user.User

import grails.gorm.transactions.Transactional

@Transactional
class ContenidoService {

    Articulo actualizarArticulo(Organizacion org, ArticuloCommand command) {
        if (!esUnArticuloValidoParaEditar(command, org)) return null

        Articulo articulo = command.id ? modificarArticulo(command) : crearArticulo(command, org)
        if (!articulo.hasErrors()) articulo.save()

        return articulo
    }

    @Transactional(readOnly = true)
    private boolean esUnArticuloValidoParaEditar(ArticuloCommand command, Organizacion org) {
        return command && command.validate() && org.id == command.orgId
    }

    @Transactional(readOnly = true)
    private Articulo modificarArticulo(ArticuloCommand command) {
        Articulo articulo = Articulo.get(command.id)
        if (!articulo) {
            articulo = new Articulo()
            articulo.errors.rejectValue('id', 'articulo.not.found')
        } else {
            articulo.imagen = command.imagen
            articulo.titulo = command.titulo
            articulo.contenido = command.contenido
            articulo.descripcion = command.descripcion
            articulo.palabrasClaves = command.palabrasClaves
            articulo.publicado = command.publicado
        }
        articulo
    }

    @Transactional(readOnly = true)
    private Articulo crearArticulo(ArticuloCommand command, Organizacion org) {
        User autor = User.get(command.autorId)
        Articulo articulo = new Articulo(command.properties)
        if (!autor) {
            articulo.errors.rejectValue('autor', 'autor.not.found')
        } else {
            articulo.autor = autor
            articulo.organizacion = org
        }
        articulo
    }

    void eliminarArticulo(Organizacion org, ArticuloCommand command) {
        if (!command) return null

        Articulo articulo = command.id ? Articulo.get(command.id) : null
        if (articulo) {
            articulo.delete()
        }
    }

    List<Cabecera> getCabecerasDeOrganizacion(Organizacion org) {
        Cabecera.findAllByOrganizacion(org, [sort: 'prioridad', order: 'desc'])
    }

    Cabecera actualizarCabecera(Organizacion org, CabeceraCommand command) {
        if (!esUnaCabeceraValidaParaEditar(command, org)) return null

        Cabecera cabecera = command.id ? modificarCabecera(command) : crearCabecera(command, org)
        if (!cabecera.hasErrors()) cabecera.save()

        return cabecera
    }

    @Transactional(readOnly = true)
    private boolean esUnaCabeceraValidaParaEditar(CabeceraCommand command, Organizacion org) {
        return command && command.validate() && org.id == command.orgId
    }

    @Transactional(readOnly = true)
    private Cabecera modificarCabecera(CabeceraCommand command) {
        Cabecera cabecera = Cabecera.get(command.id)
        if (!cabecera) {
            cabecera = new Cabecera()
            cabecera.errors.rejectValue('id', 'cabecera.not.found')
        } else {
            cabecera.titulo = command.titulo
            cabecera.nombre = command.nombre
            cabecera.link = command.link
            cabecera.prioridad = command.prioridad
            if (cabecera.contenido.id != command.contenidoId) {
                Articulo articulo = Articulo.get(command.contenidoId)
                if (articulo) {
                    cabecera.contenido = articulo
                } else {
                    cabecera.errors.rejectValue('contenido', 'articulo.not.found')
                }
            }
        }
        cabecera
    }

    @Transactional(readOnly = true)
    private Cabecera crearCabecera(CabeceraCommand command, Organizacion org) {
        Articulo articulo = Articulo.get(command.contenidoId)
        Cabecera cabecera = new Cabecera(command.properties)
        if (org && articulo) {
            cabecera.organizacion = org
            cabecera.contenido = articulo
        } else {
            if (!org) cabecera.errors.rejectValue('organizacion', 'org.not.found')
            if (!articulo) cabecera.errors.rejectValue('contenidoId', 'articulo.not.found')
        }
        cabecera
    }

}
