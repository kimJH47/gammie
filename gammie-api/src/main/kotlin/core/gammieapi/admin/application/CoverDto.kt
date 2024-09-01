package core.gammieapi.admin.application

import com.fasterxml.jackson.annotation.JsonProperty

data class CoverDto(
    val id: Int,
    @JsonProperty("image_id")
    val imageId: String
)
