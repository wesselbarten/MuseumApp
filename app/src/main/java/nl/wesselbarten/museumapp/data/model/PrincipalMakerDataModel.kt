package nl.wesselbarten.museumapp.data.model

import com.google.gson.annotations.SerializedName
import nl.wesselbarten.museumapp.domain.model.PrincipalMaker

data class PrincipalMakerDataModel(
    @SerializedName("name")
    val name: String,
    @SerializedName("biography")
    val biography: Any?,
    @SerializedName("dateOfBirth")
    val dateOfBirth: String?,
    @SerializedName("dateOfBirthPrecision")
    val dateOfBirthPrecision: Any?,
    @SerializedName("dateOfDeath")
    val dateOfDeath: String?,
    @SerializedName("dateOfDeathPrecision")
    val dateOfDeathPrecision: Any?,
    @SerializedName("nationality")
    val nationality: Any?,
    @SerializedName("occupation")
    val occupation: List<String>,
    @SerializedName("placeOfBirth")
    val placeOfBirth: String?,
    @SerializedName("placeOfDeath")
    val placeOfDeath: String?,
    @SerializedName("productionPlaces")
    val productionPlaces: List<Any>,
    @SerializedName("qualification")
    val qualification: Any?,
    @SerializedName("roles")
    val roles: List<String>,
    @SerializedName("unFixedName")
    val unFixedName: String
) {

    fun toDomainModel(): PrincipalMaker {
        return PrincipalMaker(
            name = name,
            biography = biography,
            dateOfBirth = dateOfBirth,
            dateOfBirthPrecision = dateOfBirthPrecision,
            dateOfDeath = dateOfDeath,
            dateOfDeathPrecision = dateOfDeathPrecision,
            nationality = nationality,
            occupation = occupation,
            placeOfBirth = placeOfBirth,
            placeOfDeath = placeOfDeath,
            productionPlaces = productionPlaces,
            qualification = qualification,
            roles = roles,
            unFixedName = unFixedName
        )
    }
}