package com.appdetex.sampleparserjavaproject

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class MainTest: StringSpec({
    "Ensure that a simple test runs" {
        "hello".length shouldBe 5
    }
})

