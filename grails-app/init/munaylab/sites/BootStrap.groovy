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
        new Programa().with {
            nombre          = 'Investigar y desarrollar herramientas innovadoras.'
            descripcion     = 'Investigar y desarrollar herramientas innovadoras para facilitar y agilizar los procesos de comunicación, cooperación, participación y formación destinados a las OSFL (organizaciones sin fines de lucro) y a la ciudadanía.'
            contenido       = '# Investigar y desarrollar herramientas innovadoras\n\n Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n Investigar y desarrollar herramientas innovadoras para facilitar y agilizar los procesos de comunicación, cooperación, participación y formación destinados a las OSFL (organizaciones sin fines de lucro) y a la ciudadanía.'
            publicado       = true
            organizacion    = org
            it
        }.addToProyectos(
            new Proyecto().with {
                nombre          = 'Comunicación'
                descripcion     = 'Agilizar los diferentes canales de comunicación para las OSFL y/o la ciudadanía.'
                contenido       = '# Comunicación\n\nAgilizar los diferentes canales de comunicación para las OSFL y/o la ciudadanía.\n Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n\nAgilizar los diferentes canales de comunicación para las OSFL y/o la ciudadanía.'
                publicado       = true
                organizacion    = org
                it
            }
            .addToActividades(
                new Actividad().with {
                    nombre          = 'Integración de los canales de comunicación'
                    descripcion     = 'Mejorar la productividad realizando la integración de los diferentes canales de comunicación entre las OSFL - ciudadanía y viceversa.'
                    contenido       = '# Integración de los canales de comunicación.\n\nMejorar la productividad realizando la integración de los diferentes canales de comunicación entre las OSFL - ciudadanía y viceversa.\n Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'
                    publicado       = true
                    organizacion    = org
                    it
                }
            )
            .addToActividades(
                new Actividad().with {
                    nombre          = 'Identificar el canal de comunicación'
                    descripcion     = 'Aumentar la eficacia en la comunicación identificando el canal apropiado.'
                    contenido       = '# Identificar el canal de comunicación.\n\nAumentar la eficacia en la comunicación identificando el canal apropiado.\n Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'
                    publicado       = true
                    organizacion    = org
                    it
                }
            )
            .addToActividades(
                new Actividad().with {
                    nombre          = 'Herramientas para la transparencia organizacional'
                    descripcion     = 'Implementar herramientas que permitan la transparencia organizacional en las OSFL.'
                    contenido       = '# Herramientas para la transparencia organizacional.\n\nImplementar herramientas que permitan la transparencia organizacional en las OSFL.\n Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'
                    publicado       = true
                    organizacion    = org
                    it
                }
            )
        )
        .addToProyectos(
            new Proyecto().with {
                nombre          = 'Colaboración'
                descripcion     = 'Fomentar la colaboración y participación ciudadana en OSFL.'
                contenido       = '# Colaboración.\n\nFomentar la colaboración y participación ciudadana en OSFL.\n Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'
                publicado       = true
                organizacion    = org
                it
            }
            .addToActividades(
                new Actividad().with {
                    nombre          = 'Herramientas para facilitar la colaboración y participación'
                    descripcion     = 'Implementar herramientas que faciliten la colaboración y participación ciudadana en las OSFL.'
                    contenido       = '# Herramientas para facilitar la colaboración y participación.\n\nImplementar herramientas que faciliten la colaboración y participación ciudadana en las OSFL.\n Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'
                    publicado       = true
                    organizacion    = org
                    it
                }
            )
            .addToActividades(
                new Actividad().with {
                    nombre          = 'Información disponible para colaborar y participar'
                    descripcion     = 'Brindar la información necesaria para que la comunidad pueda colaborar y/o participar en la elaboración de las herramientas brindadas.'
                    contenido       = '# Información disponible para colaborar y participar.\n\nBrindar la información necesaria para que la comunidad pueda colaborar y/o participar en la elaboración de las herramientas brindadas.\n Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'
                    publicado       = true
                    organizacion    = org
                    it
                }
            )
        )
        .addToProyectos(
            new Proyecto().with {
                nombre          = 'Información, capacitación y formación'
                descripcion     = 'Brindar información, capacitación y formación de conceptos innovadores o necesarios para alcanzar los demás objetivos.'
                contenido       = '# Información, capacitación y formación.\n\n Brindar información, capacitación y formación de conceptos innovadores o necesarios para alcanzar los demás objetivos.\n Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'
                publicado       = true
                organizacion    = org
                it
            }
            .addToActividades(
                new Actividad().with {
                    nombre          = 'Brindar capacitación y formación'
                    descripcion     = 'Brindar capacitación y/o formación para que la comunidad pueda colaborar y/o participar en la elaboración de las herramientas brindadas.'
                    contenido       = '# Brindar capacitación y formación\n\nBrindar capacitación y/o formación para que la comunidad pueda colaborar y/o participar en la elaboración de las herramientas brindadas.\n Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'
                    publicado       = true
                    organizacion    = org
                    it
                }
            )
            .addToActividades(
                new Actividad().with {
                    nombre          = 'Brindar canales de cooperación'
                    descripcion     = 'Brindar y/o fortalecer los mecanismos necesarios para que las OSFL puedan entablar canales de cooperación.'
                    contenido       = '# Brindar canales de cooperación\n\nBrindar y/o fortalecer los mecanismos necesarios para que las OSFL puedan entablar canales de cooperación.\n Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'
                    publicado       = true
                    organizacion    = org
                    it
                }
            )
            .addToActividades(
                new Actividad().with {
                    nombre          = 'Fortalecer el entorno habilitante para las OSFL'
                    descripcion     = 'Brindar y/o fortalecer los mecanismos necesarios para que las OSFL puedan entablar canales de cooperación.'
                    contenido       = '# Fortalecer el entorno habilitante para las OSFL\n\nFortalecer el entorno habilitante para las OSFL.\n Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'
                    publicado       = true
                    organizacion    = org
                    it
                }
            )
        ).save(failOnError: true)
    }

    def destroy = { }

}
