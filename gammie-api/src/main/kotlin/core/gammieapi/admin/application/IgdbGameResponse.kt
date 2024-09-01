package core.gammieapi.admin.application

import com.fasterxml.jackson.annotation.JsonProperty

data class IgdbGameResponse(
    val id : Long,
    val cover : CoverDto,
    val name : String,
    val rating : Double,
    @JsonProperty("rating_count")
    val ratingCount : Int
)
