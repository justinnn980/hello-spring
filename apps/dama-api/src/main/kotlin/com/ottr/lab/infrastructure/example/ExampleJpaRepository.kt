package com.ottr.lab.infrastructure.example

import com.ottr.lab.domain.example.ExampleModel
import org.springframework.data.jpa.repository.JpaRepository

interface ExampleJpaRepository : JpaRepository<ExampleModel, Long>
