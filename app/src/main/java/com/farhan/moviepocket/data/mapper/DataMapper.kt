package com.farhan.moviepocket.data.mapper

/**
 * Base DataMapper interface
 */
interface DataMapper<FROM, TO> {
    fun mapFrom(value: FROM): TO
    fun mapList(value: List<FROM>): List<TO>
}