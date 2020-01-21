package test.autocomplete

import autocomplete.AnyNode
import autocomplete.AutoCompleter
import autocomplete.StringNode
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


class AutoCompleteTests {
    @Test
    fun test() {
        val completer = AutoCompleter()

        val root = StringNode("aaa")
        root.children.add(StringNode("bbb"))

        completer.setRoot(root)

        completer.append('a')
        assertThat(completer.complete()).isTrue()
        assertThat(completer.nodes()).isEqualTo(listOf(StringNode("aaa")))

        completer.append('b')

        assertThat(completer.complete()).isTrue()
        assertThat(completer.nodes()).isEqualTo(listOf(StringNode("aaa"), StringNode("bbb")))
    }

    @Test
    fun testAuto() {
        val completer = AutoCompleter()

        val root = StringNode("aaa")
        root.children.add(StringNode("bbb"))

        completer.setRoot(root)

        completer.append('a')
        completer.append('a')
        completer.append('a')
        completer.complete()

        assertThat(completer.nodes()).isEqualTo(listOf(StringNode("aaa")))
    }

    @Test
    fun testAny() {
        val completer = AutoCompleter()
        val root = StringNode("aaa")
        root.children.add(AnyNode())
        completer.setRoot(root)

        completer.append('a')
        completer.complete()

        completer.append('z')
        completer.append('x')
        completer.append('y')
        completer.complete()

        assertThat("[aaa][zxy]").isEqualTo(stringValue(completer))
    }

    @Test
    fun basicSQL() {
        val completer = AutoCompleter()
        val select = listOf(StringNode("select"))
        val tables = listOf(StringNode("users"), StringNode("apts"))
        val where = listOf(StringNode("where"))
        val fields = listOf(
                StringNode("name").add(AnyNode()),
                StringNode("id").add(AnyNode())
        )
        val andOr = listOf(StringNode("and"), StringNode("or"))

        completer.set(
                select,
                tables,
                where,
                fields,
                andOr
        )
    }

    fun stringValue(completer: AutoCompleter): String {
        val nodes = completer.nodes()
        val joinToString = nodes.joinToString("")
        return joinToString
    }

}

