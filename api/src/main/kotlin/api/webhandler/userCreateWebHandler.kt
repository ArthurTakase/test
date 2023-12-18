import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.javalin.http.Context
import javax.xml.crypto.Data
import java.util.UUID;

data class UserForm(val email: String? = null, val name: String? = null, val last_key: String? = null, val password: String? = null)

class UserCreateWebHandler(val userRepository: UserRepository) : WebHandler() {
    override fun call(ctx: Context) {
        val objectMapper = jacksonObjectMapper()
        val userObj = objectMapper.readTree(ctx.body()).get("user")
        val userData = objectMapper.readValue(userObj.toString(), UserForm::class.java)

        val name = userData.name
        val email = userData.email
        val password = userData.password
        val last_key = UUID.randomUUID().toString()


        if (name.isNullOrEmpty() || email.isNullOrEmpty() || last_key.isNullOrEmpty() || password.isNullOrEmpty()) {
            ctx.status(400).json("Missing data")
        } else {
            val user = userRepository.create(name, email, last_key, password)

            if (user != null) {
                ctx.status(201).json(user)
            } else {
                ctx.status(500).json("Error creating user")
            }
        }
    }
}
