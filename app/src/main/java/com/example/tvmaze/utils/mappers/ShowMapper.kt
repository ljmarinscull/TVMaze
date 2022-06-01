package com.example.tvmaze.utils.mappers

import com.example.tvmaze.data.api.model.ShowImageJO
import com.example.tvmaze.data.api.model.ShowJO
import com.example.tvmaze.data.api.model.ShowScheduleJO
import com.example.tvmaze.data.api.model.getPoster
import com.example.tvmaze.data.model.Show
import com.example.tvmaze.utils.joinToStringValidValue
import com.example.tvmaze.utils.separateToList
import com.example.tvmaze.utils.validValue
import javax.inject.Inject

/**
 * A mapper to convert ShowJO to Show and reverse.
 */
class ShowMapper @Inject constructor() : IMapper<ShowJO, Show> {

    override fun map(input: ShowJO): Show {
        return Show(
                id = input.id,
                name = input.name.validValue(),
                poster = input.image.getPoster(),
                time = input.schedule?.time.validValue(),
                days = input.schedule?.days.joinToStringValidValue(),
                genres = input.genres.joinToStringValidValue(),
                summary = input.summary.validValue()
        )
    }

    override fun mapReverse(input: Show): ShowJO {
        return ShowJO(
            id = input.id,
            name = input.name,
            image = ShowImageJO(medium = input.poster, original = null),
            schedule = ShowScheduleJO(input.time, input.days.separateToList()),
            genres = input.genres.separateToList(),
            summary = input.summary
        )
    }
}