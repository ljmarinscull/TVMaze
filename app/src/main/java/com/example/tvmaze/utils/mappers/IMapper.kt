package com.example.tvmaze.utils.mappers

/**
 * Mapper interface.
 */
interface IMapper<I, O> : IMapperOneWay<I, O>{
    fun mapReverse(input: O): I
}