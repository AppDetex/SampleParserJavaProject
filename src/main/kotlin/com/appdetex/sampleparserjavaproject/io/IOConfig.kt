package com.appdetex.sampleparserjavaproject.io

/**
 * The config file allows us to inject alternate implementations
 * of these functions.
 *
 * It also allows us to effectively mock the IOService in tests.
 *
 * @property newLine lambda that prints a new line to out. Defaults to kotlin.io.println()
 * @property println lambda that prints the argument passed. Defaults to kotlin.io.println(Any?)
 * @property readLine lambda that reads a line of input from System.in. Defaults to kotlin.io.readLine()
 * @constructor Permits configuration of io methods. Can be called empty to choose the default implementation.
 */
internal class IOConfig(
    internal val newLine: () -> Unit = ::println,
    internal val println: (Any?) -> Unit = ::println,
    internal val readLine: () -> String? = ::readLine
)