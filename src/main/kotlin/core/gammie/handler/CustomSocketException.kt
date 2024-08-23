package core.gammie.handler

data class CustomSocketException(
    val errorCode: ErrorCode,
) : RuntimeException(errorCode.message)