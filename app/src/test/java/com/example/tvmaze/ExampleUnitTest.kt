package com.example.tvmaze

import com.example.tvmaze.data.api.model.ShowImageJO
import com.example.tvmaze.data.api.model.ShowJO
import com.example.tvmaze.data.api.model.ShowScheduleJO
import com.example.tvmaze.data.api.model.getPoster
import com.example.tvmaze.utils.joinToStringValidValue
import com.example.tvmaze.utils.mappers.ShowMapper
import com.example.tvmaze.utils.validValue
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun showMapper_isCorrect() {
        val showMapper = ShowMapper()
        val showJO = ShowJO(id = 1,
            name = "Avatar",
            image = ShowImageJO(medium = "www.google.com", null),
            schedule = ShowScheduleJO("8:00", listOf("Monday")),
            genres = listOf("Action","Adventures"),
            summary = "Avatar"
        )

        val result = showMapper.map(showJO)

        assertEquals(showJO.image?.medium, result.poster)
        assertEquals(showJO.schedule?.time, result.time)
        assertEquals(showJO.schedule?.days?.get(0), result.days)
        assertEquals(showJO.genres?.joinToStringValidValue(), result.genres)
    }


}