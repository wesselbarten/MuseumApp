package nl.wesselbarten.museumapp.domain.model

data class PrincipalMaker(
    val name: String,
    val biography: Any?,
    val dateOfBirth: String?,
    val dateOfBirthPrecision: Any?,
    val dateOfDeath: String?,
    val dateOfDeathPrecision: Any?,
    val nationality: Any?,
    val occupation: List<String>,
    val placeOfBirth: String?,
    val placeOfDeath: String?,
    val productionPlaces: List<Any>,
    val qualification: Any?,
    val roles: List<String>,
    val unFixedName: String
)