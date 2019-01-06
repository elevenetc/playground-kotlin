package test

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import state.Action
import state.State
import state.StateMachine
import java.util.concurrent.Future

class StateMachineTests {


    var cameraGranted = true
    var tokenSuccess = true
    var onboarding: String = "s"

    lateinit var sm: StateMachine

    @Before
    fun before() {

        if (onboarding.isNotEmpty()) {

        }

        sm = StateMachine()

        val initState =
                CameraPermission()
                        .on(CameraPermissionNotGranted().goTo(CameraPermission()))
                        .on(CameraPermissionGranted().goTo(
                                GetScanToken()
                                        .on(TokenReceivingError().goTo(Finish()))
                                        .on(TokenReceived().goTo(
                                                StartScanId()
                                                        .on(ScanFinished().goTo(Finish()))
                                        ))
                        ))

        sm.initState = initState
    }

    @Test
    fun test_success() {
        cameraGranted = true
        tokenSuccess = true

        sm.next(StateMachine.Init())

        assertThat(sm.currentState).isInstanceOf(Finish::class.java)
    }

    @Test
    fun test_camera_not_granted() {
        cameraGranted = false
        tokenSuccess = true

        sm.next(StateMachine.Init())

        assertThat(sm.currentState).isInstanceOf(CameraPermission::class.java)
    }

    @Test
    fun test_token_error() {
        cameraGranted = true
        tokenSuccess = false

        sm.next(StateMachine.Init())

        assertThat(sm.currentState).isInstanceOf(Finish::class.java)
    }

    class CameraPermissionNotGranted : Action()
    class CameraPermissionGranted : Action()
    class TokenReceived(val token: String = "") : Action()
    class TokenReceivingError : Action()
    class ScanFinished : Action()

    inner class CameraPermission : State() {
        override fun onStart(action: Action, sm: StateMachine) {
            if (action is StateMachine.Init) {
                if (cameraGranted) {
                    sm.next(CameraPermissionGranted())
                } else {
                    sm.next(CameraPermissionNotGranted())
                }
            } else if (action is CameraPermissionNotGranted) {
                //show popup not granted
            }
        }
    }

    inner class GetScanToken : State() {
        override fun onStart(action: Action, sm: StateMachine) {
            //getting token...
            if (tokenSuccess) {
                sm.next(TokenReceived("hello"))
            } else {
                sm.next(TokenReceivingError())
            }
        }
    }

    inner class StartScanId : State() {
        override fun onStart(action: Action, sm: StateMachine) {
            if (action is TokenReceived) {
                val token = action.token
                //scanning with token
                sm.next(ScanFinished())
            }
        }
    }


    class Finish : State(){
        override fun onStart(action: Action, sm: StateMachine) {
            super.onStart(action, sm)
        }
    }
}