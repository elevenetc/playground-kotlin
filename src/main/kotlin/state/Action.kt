package state

open class Action {

    lateinit var nextState: State

    fun goTo(state: State): Action {
        nextState = state
        return this
    }
}