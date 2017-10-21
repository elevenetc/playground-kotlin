package autoquery

import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SingleNodeTests {
    @Test
    fun testNotComplete() {
        val node = SingleNode("hello")
        node.complete()
        assertFalse(node.isCompleted())

        node.append('h')
        node.append('e')
        node.append('l')
        node.append('l')
        node.append('o')
        assertFalse(node.isCompleted())
        node.append('z')
        assertFalse(node.isCompleted())
    }
}