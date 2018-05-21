package org.munaylab

import org.munaylab.contenido.Accion
import org.munaylab.contenido.AccionCommand
import org.munaylab.contenido.Articulo
import org.munaylab.contenido.ArticuloCommand
import org.munaylab.contenido.Menu
import org.munaylab.contenido.MenuCommand
import org.munaylab.contenido.Principal
import org.munaylab.contenido.PrincipalCommand
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
            menu.texto = command.texto
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
    Principal getPrincipal(Organizacion org) {
        Principal.findByOrganizacion(org)
    }

    Principal actualizarPrincipal(PrincipalCommand command, Organizacion org) {
        if (!esUnContenidoPrincipalValidoParaEditar(command, org)) return null

        Principal principal = command.id ? modificarPrincipal(command) : crearPrincipal(command, org)
        if (!principal.hasErrors()) principal.save()

        principal
    }

    @Transactional(readOnly = true)
    private boolean esUnContenidoPrincipalValidoParaEditar(PrincipalCommand command, Organizacion org) {
        return command && command.validate() && org.id == command.orgId
    }

    @Transactional(readOnly = true)
    private Principal modificarPrincipal(PrincipalCommand command) {
        Principal principal = Principal.get(command.id)
        if (!principal) {
            principal = new Principal()
            principal.errors.rejectValue('id', 'principal.not.found')
        } else {
            principal.titulo = command.titulo
            principal.accionPrincipal = actualizarAccion(principal.accionPrincipal, command.accionPrincipal)
            principal.accionSecundaria = actualizarAccion(principal.accionSecundaria, command.accionSecundaria)
            principal.accionOpcional = actualizarAccion(principal.accionOpcional, command.accionOpcional)

            if (principal.contenido.id != command.contenidoId) {
                Articulo articulo = Articulo.get(command.contenidoId)
                if (articulo) {
                    principal.contenido = articulo
                } else {
                    principal.errors.rejectValue('contenido', 'articulo.not.found')
                }
            }
        }
        principal
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
    private Principal crearPrincipal(PrincipalCommand command, Organizacion org) {
        Articulo articulo = Articulo.get(command.contenidoId)
        Principal principal = new Principal(command.properties)
        if (org && articulo) {
            principal.organizacion = org
            principal.contenido = articulo
            principal.titulo = command.titulo
            if (command?.accionPrincipal?.id) {
                principal.accionPrincipal = obtenerAccion(command.accionPrincipal)
            }
            if (command?.accionSecundaria?.id) {
                principal.accionSecundaria = obtenerAccion(command.accionSecundaria)
            }
            if (command?.accionOpcional?.id) {
                principal.accionOpcional = obtenerAccion(command.accionOpcional)
            }
        } else {
            if (!org) principal.errors.rejectValue('organizacion', 'org.not.found')
            if (!articulo) principal.errors.rejectValue('contenidoId', 'articulo.not.found')
        }
        principal
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
}
