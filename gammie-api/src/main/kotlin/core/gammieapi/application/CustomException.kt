package core.gammieapi.application


class CustomException(
    errorCode: ErrorCode
) : RuntimeException(errorCode.message)
