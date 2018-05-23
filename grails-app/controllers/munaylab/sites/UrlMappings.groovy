package munaylab.sites

class UrlMappings {

    static mappings = {
        "/org/$nombreURL"(controller: 'sites', action: 'index')
        "/org/$nombreOrganizacion/blog"(controller: 'sites', action: 'blog')
        "/org/$nombreOrganizacion/blog/$nombreArticulo"(controller: 'sites', action: 'articulo')

        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
