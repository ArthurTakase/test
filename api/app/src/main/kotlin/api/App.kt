/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package api

import io.javalin.Javalin

fun main() {
    val app = Javalin.create().start(4000)
    app.get("/") { ctx -> ctx.result("Hello World") }
}
