package state

open class State {

    val actions = mutableListOf<Action>()

    fun on(action: Action): State {
        actions.add(action)
        return this
    }

    open fun onStart(action: Action, sm: StateMachine) {

    }
}