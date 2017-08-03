package reactive

import io.reactivex.Single
import java.util.*

var abcSingle = Single.fromCallable { Arrays.asList("a", "b", "c") }