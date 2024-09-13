package core.gammiechat.handler

class ChatException(
    errorCode: ErrorCode,
) : RuntimeException(errorCode.message)