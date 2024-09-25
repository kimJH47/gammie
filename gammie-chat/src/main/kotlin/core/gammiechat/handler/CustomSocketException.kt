package core.gammiechat.handler

import core.gammiechat.exception.ErrorCode

data class CustomSocketException(
    val errorCode: ErrorCode,
) : RuntimeException(errorCode.message)