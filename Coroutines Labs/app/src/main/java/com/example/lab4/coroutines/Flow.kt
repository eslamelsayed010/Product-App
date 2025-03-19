package com.example.lab4.coroutines

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


//suspend fun main() {
//    evenNumbersFlow()
//        .take(10)
//        .collect {
//            println(it)
//        }
//
//    runBlocking {
////        val channel = Channel<String>()
////        val myList = listOf("A", "B", "C", "D")
////
////        GlobalScope.launch {
////            for (l in myList) {
////                delay(1000)
////                channel.send(l)
////                if (l == "C")
////                    channel.close()
////            }
////
////        }
////
////        for (r in channel)
////            println(r)
//
//
//        sendLitter().consumeEach {
//            println(it)
//        }
//
////        while (!channel.isClosedForSend){
////            val litter = channel.receive()
////            println(litter)
////        }
//    }
//}

fun evenNumbersFlow(): Flow<Int> = flow {
    var num = 0
    repeat(20) {
        emit(num * 2)
        delay(1000)
        num++
    }
}

@OptIn(DelicateCoroutinesApi::class, ExperimentalCoroutinesApi::class)
fun sendLitter() = GlobalScope.produce<String> {

    val myList = listOf("A", "B", "C", "D")

    for (l in myList) {
        delay(1000)
        send(l)
        if (l == "C")
            close()
    }
}



fun main() = runBlocking {
    val viewModel = ViewModel()

    val stateJob = launch {
        viewModel.counterStateFlow.collect { value ->
            println("StateFlow Collector: Counter value = $value")
        }
    }

    val sharedJob = launch {
        viewModel.eventSharedFlow.collect { event ->
            println("SharedFlow Event: Counter reached $event")
        }
    }

    viewModel.startCounter()

    delay(7000)
    stateJob.cancel()
    sharedJob.cancel()
    println("Jobs canceled")
}

class ViewModel {
    private val _counterStateFlow = MutableStateFlow(0)
    val counterStateFlow: StateFlow<Int> = _counterStateFlow.asStateFlow()

    private val _eventSharedFlow = MutableSharedFlow<Int>(replay = 2)
    val eventSharedFlow: SharedFlow<Int> = _eventSharedFlow.asSharedFlow()

    suspend fun startCounter() {
        for (i in 1..10) {
            delay(1000)
            _counterStateFlow.value = i

            if (i % 5 == 0) {
                _eventSharedFlow.emit(i)
            }
        }
    }
}

