package core.gammiechat.exception

class ChatException(
    errorCode: ErrorCode,
) : RuntimeException(errorCode.message)