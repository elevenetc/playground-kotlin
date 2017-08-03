package test

import io.reactivex.functions.Consumer
import org.junit.Test
import reactive.abcSingle
import kotlin.test.assertEquals

/**
 * Created by eugene.levenetc on 24/05/2017.
 */

class RxTests {
    @Test fun testSingle() {
        abcSingle.subscribe(Consumer {
            assertEquals("a", it[0])
            assertEquals("b", it[1])
            assertEquals("c", it[2])
        })
    }
}