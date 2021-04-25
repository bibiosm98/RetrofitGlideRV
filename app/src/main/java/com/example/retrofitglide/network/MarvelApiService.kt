package com.example.retrofitglide.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val BASE_URL = "https://gateway.marvel.com/"

enum class ComicApiFilter(val value: String?) {
    SHOW_ALL(null),
    SHOW_ANT("ant"),
    SHOW_IRON("iron"),
    SHOW_SPIDER("spider")
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .add(NullToEmptyStringConverter)
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface MarvelApiService{
    @GET("v1/public/comics")
    fun getComics(@Query("ts") timestamp: String,
                  @Query("apikey") apiKey: String,
                  @Query("hash") hash: String,
                  @Query("titleStartsWith") type: String?,
                  @Query("limit") limit: Int?
            ): Deferred<ComicProperty>
//    suspend fun ERASED???!!!
}

object MarvelApi {
    val retrofitService: MarvelApiService by lazy {
        retrofit.create(MarvelApiService::class.java)
    }
}

object NullToEmptyStringConverter {
    @FromJson
    fun fromJson(reader: JsonReader): String {
        if (reader.peek() != JsonReader.Token.NULL) {
            return reader.nextString()
        }
        reader.nextNull<Unit>()
        return ""
    }
}