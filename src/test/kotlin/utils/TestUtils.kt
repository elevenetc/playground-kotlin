package utils

import org.mockito.Mockito

inline fun anyException() = Mockito.any(Exception::class.java) ?: Exception()