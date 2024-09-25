package core.gammiechat.exception

data class CustomSocketException(
    val errorCode: ErrorCode,
) : RuntimeException(errorCode.message)