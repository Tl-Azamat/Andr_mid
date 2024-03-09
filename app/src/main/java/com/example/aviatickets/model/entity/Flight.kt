package com.example.aviatickets.model.entity

import com.google.gson.annotations.SerializedName

/**
 * think about json deserialization considering its snake_case format
 */
data class Flight(
    @SerializedName("departure_location")
    val departureLocation: Location,

    @SerializedName("arrival_location")
    val arrivalLocation: Location,

    @SerializedName("departure_time_info")
    val departureTimeInfo: String,

    @SerializedName("arrival_time_info")
    val arrivalTimeInfo: String,

    @SerializedName("flight_number")
    val flightNumber: String,

    @SerializedName("airline")
    val airline: Airline,

    @SerializedName("duration")
    val duration: Int
)