package core.gammie.application


data class CustomResponse(
    val type: ResponseType,
    val body: Any? = null
)
