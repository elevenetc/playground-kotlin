package test.autocomplete

import autocomplete.AnyNode
import autocomplete.AutoCompleter
import autocomplete.Node
import autocomplete.StringNode
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


class AutoCompleteTests {
    @Test
    fun test() {
        val completer = AutoCompleter()

        val root = StringNode("aaa")
        root.children().add(StringNode("bbb"))

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
        root.children().add(StringNode("bbb"))

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
        root.children().add(AnyNode())
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
        val fields = mutableListOf<Node>()
        val futureFields = { fields }

        val limit = StringNode("limit").add(AnyNode())

        val andOr = listOf(
                StringNode("and").addNext(futureFields),
                StringNode("or").addNext(futureFields),
                limit
        )

        val userEntry = AnyNode().add(
                andOr
        )

        fields.add(StringNode("name").add(userEntry))
        fields.add(StringNode("id").add(userEntry))



        completer.set(
                select,
                tables,
                where,
                fields
        )

        println("      |")
        completer.append('s')
        completer.complete()

        completer.append('u')
        completer.complete()

        echoNodes(completer)

        completer.append('w')
        completer.complete()

        echoNodes(completer)

        completer.append('n')
        completer.append('a')
        completer.complete()

        echoNodes(completer)

        completer.append('b')
        completer.append('o')
        completer.append('b')
        completer.complete()

        echoNodes(completer)

        completer.append('a')
        completer.complete()

        echoNodes(completer)

        completer.append('i')
        completer.complete()

        completer.append('1')
        completer.append('3')
        completer.complete()

        echoNodes(completer)

        completer.append('l')
        completer.complete()

        completer.append('3')
        completer.append('3')
        completer.complete()

        echoNodes(completer)
        echoNext(completer)

        println("      |")

        assertThat("[select][users][where][name][bob][and][id][13][limit][33]").isEqualTo(stringValue(completer))

    }

    @Test
    fun testDeleteSingle() {
        val completer = AutoCompleter()
        val node = StringNode("zed")
        completer.setRoot(node)

        completer.append('z')
        completer.complete()

        assertThat("[zed]").isEqualTo(stringValue(completer))

        completer.delete()
        assertThat("").isEqualTo(stringValue(completer))
        assertThat("ze").isEqualTo(completer.postfix())
        completer.delete()
        assertThat("z").isEqualTo(completer.postfix())

        completer.complete()

        assertThat("[zed]").isEqualTo(stringValue(completer))
    }

    @Test
    fun testDeleteTwo() {
        val completer = AutoCompleter()
        val node = StringNode("one").add(StringNode("two"))
        completer.setRoot(node)

        completer.append('o')
        completer.complete()

        assertThat("[one]").isEqualTo(stringValue(completer))

        completer.append('t')

        completer.complete()

        assertThat("[one][two]").isEqualTo(stringValue(completer))

        completer.delete()

        assertThat("[one]").isEqualTo(stringValue(completer))
        assertThat("tw").isEqualTo(completer.postfix())
        completer.delete()
        completer.delete()
        assertThat("").isEqualTo(completer.postfix())
        assertThat("[one]").isEqualTo(stringValue(completer))
        completer.delete()
        assertThat("").isEqualTo(stringValue(completer))
        assertThat("on").isEqualTo(completer.postfix())
    }

    private fun echoNext(auto: AutoCompleter) {
        println(" next | " + auto.next())
    }

    private fun echoNodes(auto: AutoCompleter) {
        println("nodes | " + auto.nodes())
    }

    fun stringValue(completer: AutoCompleter): String {
        val nodes = completer.nodes()
        val joinToString = nodes.joinToString("")
        return joinToString
    }

}

