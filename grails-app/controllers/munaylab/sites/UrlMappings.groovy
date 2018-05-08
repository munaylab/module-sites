package munaylab.sites

class UrlMappings {

    static mappings = {
        "/sites/$nombreURL"(controller: 'sites', action: 'index')

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
