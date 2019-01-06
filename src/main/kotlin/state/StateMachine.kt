package state

class StateMachine {

    var currentState: State? = null
    var initState: State? = null

    fun next(action: Action) {

        var newState: State? = null

        if (action is Init) {
            newState = initState
        } else {
            for (possibleAction in currentState!!.actions) {
                if (possibleAction.javaClass == action.javaClass) {
                    newState = possibleAction.nextState
                    break
                }
            }
        }

        if (newState != currentState) {
            currentState = newState
            currentState!!.onStart(action, this)
        }

    }

    class Init : Action()
}