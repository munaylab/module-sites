package org.munaylab

import org.munaylab.contenido.Accion
import org.munaylab.contenido.AccionCommand
import org.munaylab.contenido.Articulo
import org.munaylab.contenido.ArticuloCommand
import org.munaylab.contenido.Menu
import org.munaylab.contenido.MenuCommand
import org.munaylab.contenido.Landing
import org.munaylab.contenido.LandingCommand
import org.munaylab.osc.Organizacion
import org.munaylab.user.User
import org.munaylab.plugins.Archivo

import grails.gorm.transactions.Transactional

@Transactional
class ContenidoService {

    def archivoService

    @Transactional(readOnly = true)
    Articulo obtenerArticulo(Long articuloId, Organizacion org) {
        if (!org) return null
        Articulo.findByIdAndOrganizacion(articuloId, org)
    }

    @Transactional(readOnly = true)
    List<Articulo> obtenerTodosLosArticulos(Organizacion org) {
        if (!org) return null
        Articulo.findAllByOrganizacion(org)
    }

    Articulo actualizarArticulo(ArticuloCommand command, Organizacion org) {
        if (!esUnArticuloValidoParaEditar(command, org)) return null

        Articulo articulo = command.id ? modificarArticulo(command) : crearArticulo(command, org)
        if (!articulo.hasErrors()) {
            if (command.imagen.accion != 'none') {
                Archivo imagen = archivoService.actualizarArchivo(command.imagen)
                articulo.imagen = imagen
            }
            articulo.save()
        }

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
        Articulo articulo = new Articulo().with {
            titulo          = command.titulo
            contenido       = command.contenido
            descripcion     = command.descripcion
            palabrasClaves  = command.palabrasClaves
            publicado       = command.publicado
            it
        }
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

    @Transactional(readOnly = true)
    Menu obtenerMenu(Long menuId, Organizacion org) {
        if (!org) return null
        Menu.findByIdAndOrganizacion(menuId, org)
    }

    @Transactional(readOnly = true)
    List<Menu> getMenuDeOrganizacion(Organizacion org) {
        Menu.findAllByOrganizacion(org, [sort: 'prioridad', order: 'asc'])
    }

    Menu actualizarMenu(Organizacion org, MenuCommand command) {
        if (!esUnMenuValidoParaEditar(command, org)) return null

        Menu menu = command.id ? modificarMenu(command) : crearMenu(command, org)
        if (!menu.hasErrors()) menu.save()

        return menu
    }

    @Transactional(readOnly = true)
    private boolean esUnMenuValidoParaEditar(MenuCommand command, Organizacion org) {
        return command && command.validate() && org.id == command.orgId
    }

    @Transactional(readOnly = true)
    private Menu modificarMenu(MenuCommand command) {
        Menu menu = Menu.get(command.id)
        if (!menu) {
            menu = new Menu()
            menu.errors.rejectValue('id', 'menu.not.found')
        } else {
            menu.nombre = command.nombre
            menu.link = command.link
            menu.prioridad = command.prioridad
            if (menu?.articulo?.id != command.articuloId) {
                Articulo articulo = Articulo.get(command.articuloId)
                if (articulo) {
                    menu.articulo = articulo
                } else {
                    menu.errors.rejectValue('articulo', 'articulo.not.found')
                }
            }
        }
        menu
    }

    @Transactional(readOnly = true)
    private Menu crearMenu(MenuCommand command, Organizacion org) {
        Menu menu = new Menu(command.properties)
        menu.organizacion = org
        if (!org)
            menu.errors.rejectValue('organizacion', 'org.not.found')
        if (command.articuloId) {
            menu.articulo = Articulo.get(command.articuloId)
            if (!menu.articulo)
                menu.errors.rejectValue('articulo', 'articulo.not.found')
        }
        menu
    }

    @Transactional(readOnly = true)
    Landing getLanding(Organizacion org) {
        Landing.findByOrganizacion(org)
    }

    Landing actualizarLanding(LandingCommand command, Organizacion org) {
        if (!esUnaLandingValidaParaEditar(command, org)) return null

        Landing landing = command.id ? modificarLanding(command) : crearLanding(command, org)
        if (!landing.hasErrors()) {
            if (command?.imagen?.accion != 'none') {
                Archivo imagen = archivoService.actualizarArchivo(command.imagen)
                landing.imagen = imagen
            }
            landing.save()
        }

        landing
    }

    @Transactional(readOnly = true)
    private boolean esUnaLandingValidaParaEditar(LandingCommand command, Organizacion org) {
        return command && command.validate() && org.id == command.orgId
    }

    @Transactional(readOnly = true)
    private Landing modificarLanding(LandingCommand command) {
        Landing landing = Landing.get(command.id)
        if (!landing) {
            landing = new Landing()
            landing.errors.rejectValue('id', 'landing.not.found')
        } else {
            landing.titulo = command.titulo
            landing.imagenLink = command.imagenLink
            // TODO implementacion guardar y nuevo
            // landing.accionPrincipal = actualizarAccion(landing.accionPrincipal, command.accionPrincipal)
            // landing.accionSecundaria = actualizarAccion(landing.accionSecundaria, command.accionSecundaria)
            // landing.accionOpcional = actualizarAccion(landing.accionOpcional, command.accionOpcional)

            if (landing.contenido.id != command.contenidoId) {
                Articulo articulo = Articulo.get(command.contenidoId)
                if (articulo) {
                    landing.contenido = articulo
                } else {
                    landing.errors.rejectValue('contenido', 'articulo.not.found')
                }
            }
        }
        landing
    }

    @Transactional(readOnly = true)
    private Accion actualizarAccion(Accion accion, AccionCommand command) {
        if (accion.id == command.id) return accion

        obtenerAccion(command)
    }

    @Transactional(readOnly = true)
    private Accion obtenerAccion(AccionCommand command) {
        Accion accion = Accion.get(command.id)
        if (!accion) {
            accion = new Accion()
            accion.errors.rejectValue('id', 'accion.not.found')
        }
        accion
    }

    @Transactional(readOnly = true)
    private Landing crearLanding(LandingCommand command, Organizacion org) {
        Articulo articulo = Articulo.get(command.contenidoId)
        Landing landing = new Landing(command.properties)
        if (org && articulo) {
            landing.organizacion = org
            landing.contenido = articulo
            landing.titulo = command.titulo
            landing.imagenLink = command.imagenLink
            if (command?.accionPrincipal?.id) {
                landing.accionPrincipal = obtenerAccion(command.accionPrincipal)
            }
            if (command?.accionSecundaria?.id) {
                landing.accionSecundaria = obtenerAccion(command.accionSecundaria)
            }
            if (command?.accionOpcional?.id) {
                landing.accionOpcional = obtenerAccion(command.accionOpcional)
            }
        } else {
            if (!org) landing.errors.rejectValue('organizacion', 'org.not.found')
            if (!articulo) landing.errors.rejectValue('contenidoId', 'articulo.not.found')
        }
        landing
    }

    @Transactional(readOnly = true)
    def buscarArticulosDeOrganizacionPor(Organizacion org, criteria) {
        if (!org) return null

        Articulo.withCriteria {
            criteria.delegate = delegate
            criteria()
            eq 'organizacion', org
        }
    }

    @Transactional(readOnly = true)
    Articulo buscarArticulo(String urlDeArticulo, Organizacion org) {
        if (!org) return null

        Articulo.findPublicadoByUrlAndOrganizacion(urlDeArticulo, org)
    }

    @Transactional(readOnly = true)
    List<Articulo> obtenerUltimosArticulos(Organizacion org) {
        if (!org) return null

        Articulo.findAllPublicadoByOrganizacion(org, [sort: 'dateCreated', order: 'desc', max: 10])
    }

}
