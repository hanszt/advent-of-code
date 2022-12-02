package utils

import kotlin.test.assertEquals

fun <T> T.assertEqualTo(expected: T, message: String? = null) = assertEquals(expected, this, message)
