package org.kodein

import org.kodein.bindings.KodeinBinding

open class KodeinDefinedBinding(val binding: KodeinBinding<*, *, *>, val fromModule: String?)

/**
 * A Map containing all bindings associated to their keys
 */
typealias BindingsMap = Map<Kodein.Key<*, *, *>, List<KodeinDefinedBinding>>

private fun BindingsMap._description(withOverrides: Boolean, ident: Int, keyBindDisp: Kodein.Key<*, *, *>.() -> String, bindingDisp: KodeinBinding<*, *, *>.() -> String): String {

    fun StringBuilder.appendBindings(ident: Int, entries: List<Map.Entry<Kodein.Key<*, *, *>, List<KodeinDefinedBinding>>>) =
            entries.forEach {
                val keyDescription = it.key.keyBindDisp()
                append("${" ".repeat(ident)}$keyDescription with ${it.value.first().binding.bindingDisp()}")
                if (withOverrides) {
                    val subIdent = keyDescription.length - 4
                    it.value.subList(1, it.value.size).forEach {
                        append("${" ".repeat(subIdent)}overrides ${it.binding.bindingDisp()}")
                    }
                }
                append("\n")
            }

    val byModule = entries.groupBy { it.value.first().fromModule }
    val modules = byModule.keys.filterNotNull().sorted()

    return buildString {
        byModule[null]?.let { appendBindings(ident, it) }

        modules.forEach {
            append("${" ".repeat(ident)}module $it {\n")
            appendBindings(ident + 4, byModule[it]!!)
            append("${" ".repeat(ident)}}\n")
        }
    }
}


/**
 * The description of all bindings in this map, using type simple display names.
 *
 * @receiver The bindings map, obtained with [KodeinContainer.bindings].
 */
fun BindingsMap.description(withOverrides: Boolean = false, ident: Int = 8): String = _description(withOverrides, ident, Kodein.Key<*, *, *>::bindDescription, KodeinBinding<*, *, *>::description)

/**
 * The description of all bindings in this map, using type full display names.
 *
 * @receiver The bindings map, obtained with [KodeinContainer.bindings].
 */
fun BindingsMap.fullDescription(withOverrides: Boolean = false, ident: Int = 8): String = _description(withOverrides, ident, Kodein.Key<*, *, *>::bindFullDescription, KodeinBinding<*, *, *>::fullDescription)
