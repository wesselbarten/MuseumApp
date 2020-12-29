package nl.wesselbarten.museumapp.domain.model

data class WebImage(
    val guid: String,
    val height: Int,
    val offsetPercentageX: Int,
    val offsetPercentageY: Int,
    val url: String,
    val width: Int
) {

    override fun equals(other: Any?): Boolean {
        if (other is WebImage) {
            return guid == other.guid &&
                    height == other.height &&
                    offsetPercentageX == other.offsetPercentageX &&
                    offsetPercentageY == other.offsetPercentageY &&
                    url == other.url &&
                    width == other.width
        }

        return false
    }
}