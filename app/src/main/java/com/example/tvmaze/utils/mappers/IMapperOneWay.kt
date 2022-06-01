package com.example.tvmaze.utils.mappers

interface IMapperOneWay<I, O>  {
    fun map(input: I): O
}