package nl.wesselbarten.museumapp.presentation.error

import android.content.Context
import nl.wesselbarten.museumapp.R
import timber.log.Timber
import javax.inject.Inject

class ErrorHandler @Inject constructor(
    private val context: Context
) {

    private val errorMap = mapOf(
        ErrorCodes.UNABLE_TO_GET_ART_OBJECTS to R.string.error_message_unable_to_get_art_objects,
        ErrorCodes.UNABLE_TO_GET_NEXT_ART_OBJECTS_PAGE to R.string.error_message_unable_to_get_art_objects,
        ErrorCodes.UNABLE_TO_REFRESH_ART_OBJECTS to R.string.error_message_unable_to_refresh_art_objects,
        ErrorCodes.UNABLE_TO_FIND_ART_OBJECT to R.string.error_message_unable_to_find_art_object
    )

    fun handleError(e: Exception, errorCode: Int): String {
        val errorResourceId = errorMap[errorCode] ?: throw IllegalArgumentException("No matching error message for error code: $errorCode")

        Timber.e(e)

        return context.getString(errorResourceId)
    }
}