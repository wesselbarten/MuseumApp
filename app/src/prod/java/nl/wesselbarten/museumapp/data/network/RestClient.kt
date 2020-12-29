package nl.wesselbarten.museumapp.data.network

import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.*

private const val PARAMETER_API_KEY = "key"

class RestClient(
    baseUrl: String,
    apiKey: String,
    enableLogging: Boolean,
    gson: Gson
) {

    private val retrofit: Retrofit

    init {
        val okHttpClient = OkHttpClient.Builder()
            .also {
                if (enableLogging) {
                    val loggingInterceptor = HttpLoggingInterceptor()
                    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                    it.addInterceptor(loggingInterceptor)
                }
            }
            .addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val originalRequest = chain.request()
                    val url = originalRequest.url.newBuilder()
                        .addQueryParameter(PARAMETER_API_KEY, apiKey)
                        .build()

                    val request = originalRequest.newBuilder()
                        .url(url)
                        .header("Content-Type", "application/json")
                        .build()

                    return chain.proceed(request)
                }
            })
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(buildLocalisedEndpointUrl(baseUrl))
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun getArtCollectionApiService(): ArtCollectionApiService = retrofit.create()

    /**
     * Create a localised version of the endpoint url to the api.
     * @param baseUrl String the base url of the endpoint.
     * @return the endpoint url of the collection api.
     */
    private fun buildLocalisedEndpointUrl(baseUrl: String): String {
        val urlStringBuilder = StringBuilder(baseUrl)
        if (Locale.getDefault().language == "nl") {
            urlStringBuilder.append("nl/")
        } else {
            urlStringBuilder.append("en/")
        }

        return urlStringBuilder.toString()
    }
}