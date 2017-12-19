package kodein.demo

import org.kodein.*
import org.kodein.erased.*
import kodein.demo.coffee.*

fun main(args: Array<String>) {
    Application()
}

class Application : KodeinAware {

    override val kodein = Kodein {
//        import(thermosiphonModule)
//        import(electricHeaterModule)

//        bind<Coffee>() with provider { Coffee() }

//        // this is bound in the scope of an activity so any retrieval using the same activity will return the same Kettle instance
//        bind<Kettle<*>>() with singleton { Kettle<Coffee>(instance(), instance(), provider()) }

//        constant("author") with "Salomon BRYS"

        bind<String>()
    }

//    private val _kettle: Kettle<Coffee> by instance()

    init {
//        val author: String by instance("author")
        val author = "Salomon BRYS"
        println("Kodein 5 Demo by $author")

//        _kettle.brew()
    }

}
