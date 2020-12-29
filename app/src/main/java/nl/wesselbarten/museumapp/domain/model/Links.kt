package nl.wesselbarten.museumapp.domain.model

data class Links(
    val self: String?,
    val web: String?
) {

    override fun equals(other: Any?): Boolean {
        if (other is Links) {
            return self == other.self &&
                    web == other.web
        }
        return false
    }
}