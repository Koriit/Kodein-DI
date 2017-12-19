package org.kodein.test

expect enum class MethodSorters {
    DEFAULT,
    NAME_ASCENDING
}

expect annotation class FixMethodOrder(
        val value: MethodSorters = MethodSorters.DEFAULT
)
