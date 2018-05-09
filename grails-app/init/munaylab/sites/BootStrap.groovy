package munaylab.sites

import org.munaylab.user.User
import org.munaylab.categoria.TipoUsuario
import org.munaylab.contenido.Accion
import org.munaylab.contenido.Articulo
import org.munaylab.contenido.Cabecera
import org.munaylab.contenido.Principal
import org.munaylab.osc.Organizacion
import org.munaylab.osc.EstadoOrganizacion
import org.munaylab.osc.TipoOrganizacion
import org.munaylab.osc.UserOrganizacion
import org.munaylab.security.Role
import org.munaylab.security.UserRole

class BootStrap {

    def init = { servletContext ->
        log.info "initializing..."

        environments {
            development {
                crearOrganizacion()
            }
        }
    }

    void crearOrganizacion() {
        Role oscAdmin = Role.findByAuthority('ROLE_OSC_ADMIN')
                ?: new Role(authority: 'ROLE_OSC_ADMIN').save()
        def admin = TipoUsuario.findByNombre('ADMINISTRADOR')
                ?: new TipoUsuario(nombre: 'ADMINISTRADOR').save()
        def user = new User().with {
            username    = 'mcaligares@gmail.com'
            nombre      = 'Augusto'
            apellido    = 'Caligares'
            password    = 'Pass1234!'
            it
        }
        def org = new Organizacion().with {
            nombre      = 'MunayLab'
            nombreURL   = 'munaylab'
            tipo        = TipoOrganizacion.FUNDACION
            estado      = EstadoOrganizacion.REGISTRADA
            objeto      = 'Brindar herramientas innovadoras a las organizaciones de la sociedad civil.'
            it
        }
        org.addToAdmins(new UserOrganizacion(user: user, tipo: admin)).save()

        new UserRole(user: user, role: oscAdmin).save()

        agregarArticulos(org, user)
    }

    void agregarArticulos(Organizacion org, User user) {
        def articulo = new Articulo().with {
            autor           = user
            organizacion    = org
            imagen          = 'http://placehold.it/900x800'
            publicado       = true
            titulo          = 'Principal'
            palabrasClaves  = 'principal'
            descripcion     = 'descripcion principal'
            contenido       = '<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p> <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>'
            it
        }.save()
        def principal = new Principal().with {
            titulo          = 'Titulo Principal'
            imagen          = 'http://placehold.it/900x800'
            contenido       = articulo
            organizacion    = org
            it
        }
        principal.accionPrincipal = new Accion().with {
            titulo      = 'Titulo Accion'
            link        = 'link'
            principal   = principal
            it
        }
        principal.save(failOnError: true)

        def mision = new Articulo().with {
            autor           = user
            organizacion    = org
            imagen          = 'http://placehold.it/900x800'
            publicado       = true
            titulo          = 'Mision'
            palabrasClaves  = 'mision'
            descripcion     = 'descripcion mision'
            contenido       = '<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p> <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>'
            it
        }.save()
        def nosotros = new Articulo().with {
            autor           = user
            organizacion    = org
            imagen          = 'http://placehold.it/900x800'
            publicado       = true
            titulo          = 'Nosotros'
            palabrasClaves  = 'nosotros'
            descripcion     = 'descripcion nosotros'
            contenido       = '<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p> <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>'
            it
        }.save()
        def programas = new Articulo().with {
            autor           = user
            organizacion    = org
            imagen          = 'http://placehold.it/900x800'
            publicado       = true
            titulo          = 'Programas'
            palabrasClaves  = 'programas'
            descripcion     = 'descripcion programas'
            contenido       = '<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p> <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>'
            it
        }.save()
        def contacto = new Articulo().with {
            autor           = user
            organizacion    = org
            imagen          = 'http://placehold.it/900x800'
            publicado       = true
            titulo          = 'Contacto'
            palabrasClaves  = 'contacto'
            descripcion     = 'descripcion contacto'
            contenido       = '<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p> <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>'
            it
        }.save()
        def contribuir = new Articulo().with {
            autor           = user
            organizacion    = org
            imagen          = 'http://placehold.it/900x800'
            publicado       = true
            titulo          = 'Contribuir'
            palabrasClaves  = 'contribuir'
            descripcion     = 'descripcion contribuir'
            contenido       = '<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p> <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>'
            it
        }.save()

        new Cabecera().with {
            organizacion    = org
            contenido       = mision
            titulo          = mision.titulo
            nombre          = mision.titulo
            prioridad       = 0
            it
        }.save()
        new Cabecera().with {
            organizacion    = org
            contenido       = nosotros
            titulo          = nosotros.titulo
            nombre          = nosotros.titulo
            prioridad       = 1
            it
        }.save()
        new Cabecera().with {
            organizacion    = org
            contenido       = programas
            titulo          = programas.titulo
            nombre          = programas.titulo
            prioridad       = 2
            it
        }.save()
        new Cabecera().with {
            organizacion    = org
            contenido       = contacto
            titulo          = contacto.titulo
            nombre          = contacto.titulo
            link            = 'mailto:contacto@munaylab.org'
            prioridad       = 3
            it
        }.save()
        new Cabecera().with {
            organizacion    = org
            contenido       = contribuir
            titulo          = contribuir.titulo
            nombre          = contribuir.titulo
            prioridad       = 4
            it
        }.save()
    }

    def destroy = {
    }
}
