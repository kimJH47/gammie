package core.gammiechat.handler

data class CustomSocketException(
    val errorCode: ErrorCode,
) : RuntimeException(errorCode.message)