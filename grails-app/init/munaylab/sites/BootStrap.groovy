package munaylab.sites

import org.munaylab.user.User
import org.munaylab.categoria.TipoUsuario
import org.munaylab.contenido.Accion
import org.munaylab.contenido.Articulo
import org.munaylab.contenido.Menu
import org.munaylab.contenido.Landing
import org.munaylab.osc.Organizacion
import org.munaylab.osc.EstadoOrganizacion
import org.munaylab.osc.TipoOrganizacion
import org.munaylab.osc.UserOrganizacion
import org.munaylab.planificacion.Actividad
import org.munaylab.planificacion.Proyecto
import org.munaylab.planificacion.Programa
import org.munaylab.security.Role
import org.munaylab.security.UserRole
import org.munaylab.plugins.Archivo

class BootStrap {

    def init = { servletContext ->
        environments {
            development {
                def user = crearUsuario()
                def org = crearOrganizacion(user)
                agregarMicrositio(org, user)
                crearPlanificacion(org)
                def articulos = []
                15.times { articulos << "Articulo N$it" }
                crearArticulos(org, user, articulos)
            }
        }
    }

    private User crearUsuario() {
        Role oscAdmin = Role.findByAuthority('ROLE_OSC_ADMIN')
                ?: new Role(authority: 'ROLE_OSC_ADMIN').save(failOnError: true)
        def user = User.findByUsername('mcaligares@gmail.com')
        if (!user) {
            user = new User().with {
                username    = 'mcaligares@gmail.com'
                nombre      = 'Augusto'
                apellido    = 'Caligares'
                password    = 'Pass1234!'
                it
            }.save(failOnError: true)
            new UserRole(user: user, role: oscAdmin).save(failOnError: true)
        }
        return user
    }

    private Organizacion crearOrganizacion(User user) {
        def admin = TipoUsuario.findByNombre('ADMINISTRADOR')
                ?: new TipoUsuario(nombre: 'ADMINISTRADOR').save(failOnError: true)
        def org = Organizacion.findByNombre('MunayLab')
        if (!org) {
            org = new Organizacion().with {
                nombre      = 'MunayLab'
                nombreURL   = 'munaylab'
                tipo        = TipoOrganizacion.FUNDACION
                estado      = EstadoOrganizacion.REGISTRADA
                objeto      = 'Brindar herramientas innovadoras a las organizaciones de la sociedad civil.'
                it
            }
            org.addToAdmins(new UserOrganizacion(user: user, tipo: admin)).save(failOnError: true)
        }
        return org
    }

    private void agregarMicrositio(Organizacion org, User user) {
        def articuloLanding = new Articulo().with {
            autor           = user
            organizacion    = org
            publicado       = false
            titulo          = 'Landing'
            palabrasClaves  = 'principal'
            descripcion     = 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.'
            contenido       = "# Titulo Landing\n\nLorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n\nTexto acerca del articulo **Landing** que contiene informacion muy importante y necesaria para el proyecto\n"
            it
        }.save(failOnError: true)
        def principal = new Landing().with {
            titulo          = 'Titulo Landing'
            imagen          = archivo
            contenido       = articuloLanding
            organizacion    = org
            it
        }
        principal.accionPrincipal = new Accion().with {
            titulo      = 'Titulo Accion'
            link        = 'link'
            landing     = principal
            it
        }
        principal.save(failOnError: true)

        def nombreDeArticulos = ['Mision', 'Nosotros', 'Programas', 'Contribuir', 'Contacto']
        nombreDeArticulos.eachWithIndex { nombreArticulo, i ->
            def article = new Articulo().with {
                titulo          = nombreArticulo
                contenido       = "# Titulo ${nombreArticulo}\n\nLorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n\nTexto acerca del articulo **${nombreArticulo}** que contiene informacion muy importante y necesaria para el proyecto\n"
                palabrasClaves  = nombreArticulo.toLowerCase()
                descripcion     = "${nombreArticulo.toLowerCase()} Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur."
                publicado       = false
                autor           = user
                organizacion    = org
                it
            }.save(failOnError: true)
            def menu = new Menu().with {
                nombre          = nombreArticulo
                link            = nombreArticulo == 'Contacto' ? "${org.nombreURL}/blog/" : null
                prioridad       = i
                articulo        = article
                organizacion    = org
                it
            }.save(failOnError: true)
        }
    }

    private void crearArticulos(Organizacion org, User user, nombreDeArticulos) {
        nombreDeArticulos.each { nombreArticulo ->
            def article = new Articulo().with {
                titulo          = nombreArticulo
                url             = nombreArticulo.replaceAll(" ", "_").toLowerCase()
                contenido       = "# Titulo ${nombreArticulo}\n\nLorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n\nTexto acerca del articulo **${nombreArticulo}** que contiene informacion muy importante y necesaria para el proyecto\n"
                palabrasClaves  = nombreArticulo.toLowerCase()
                descripcion     = "${nombreArticulo.toLowerCase()} Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur."
                publicado       = true
                imagen          = archivo
                autor           = user
                organizacion    = org
                it
            }.save(failOnError: true)
        }
    }

    private Archivo getArchivo() {
        def resource = this.class.getResource("/mobile.jpeg")
        def file = new File(resource.toURI())
        new Archivo().with {
            nombre          = file.name
            fileBytes       = file.bytes
            fileContentType = 'image/jpeg'
            it
        }
    }

    private void crearPlanificacion(Organizacion org) {
        def programa = new Programa().with {
            nombre          = 'Innovaciones Sociales'
            descripcion     = 'Brindar innovaciones a las osc.'
            contenido       = '# Brindar innovaciones a las osc.'
            publicado       = true
            organizacion    = org
            it
        }
        def proyecto = new Proyecto().with {
            nombre          = 'Taller de Innovaciones'
            descripcion     = 'Taller donde se exponen innovaciones para la sociedad.'
            contenido       = '# Taller donde se exponen innovaciones para la sociedad.'
            publicado       = true
            organizacion    = org
            it
        }
        def actividad = new Actividad().with {
            nombre          = 'Presentacion Innovacion'
            descripcion     = 'Presentacion de innovaciones realizadas en el taller.'
            contenido       = '# Presentacion de innovaciones realizadas en el taller.'
            publicado       = true
            organizacion    = org
            proyecto        = proyecto
            it
        }
        proyecto.addToActividades(actividad)
        programa.addToProyectos(proyecto)
        programa.save(failOnError: true)
    }

    def destroy = { }

}
