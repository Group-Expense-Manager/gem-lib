package pl.edu.agh.gem.security

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class GemUser
    @JsonCreator
    constructor(
        @JsonProperty("id") val id: String,
        @JsonProperty("email") val email: String,
    )
