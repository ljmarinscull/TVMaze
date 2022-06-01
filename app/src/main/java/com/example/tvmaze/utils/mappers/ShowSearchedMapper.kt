package com.example.tvmaze.utils.mappers

import com.example.tvmaze.data.api.model.ShowSearchedJO
import com.example.tvmaze.data.api.model.getPoster
import com.example.tvmaze.data.model.Show
import com.example.tvmaze.utils.joinToStringValidValue
import com.example.tvmaze.utils.validValue
import javax.inject.Inject

class ShowSearchedMapper @Inject constructor(): IMapperOneWay<ShowSearchedJO, Show> {
    override fun map(input: ShowSearchedJO): Show {
        return Show(
            id =  input.show.id,
            favorite = false,
            name = input.show.name.validValue(),
            poster = input.show.image.getPoster(),
            time = input.show.schedule?.time.validValue(),
            days = input.show.schedule?.days.joinToStringValidValue(),
            genres =  input.show.genres.joinToStringValidValue(),
            summary =  input.show.summary.validValue()
        )
    }
}