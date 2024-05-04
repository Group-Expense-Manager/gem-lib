package pl.edu.agh.gem.helper.user

import pl.edu.agh.gem.helper.user.DummyUser.EMAIL
import pl.edu.agh.gem.helper.user.DummyUser.USER_ID
import pl.edu.agh.gem.security.GemUser

object DummyUser {
    const val USER_ID = "userId"
    const val OTHER_USER_ID = "otherUserId"
    
    const val EMAIL = "email@gmail.com"
    const val OTHER_EMAIL = "other.email@gmail.com"
}

fun createGemUser(
    id: String = USER_ID, 
    email: String = EMAIL
) = GemUser(
        id = id, 
        email = email
)
