package com.ottr.lab.domain.example

interface ExampleRepository {
    fun find(id: Long): ExampleModel?
}
