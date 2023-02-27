package com.cursosandroidant.calculator

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by davidgonzalez on 26/02/23
 */
@RunWith(MockitoJUnitRunner::class)
class CalculatorUtilsMockTest {
    //Configuracion base para Mock, con esto simulamos las variables que a CalculatorUtils le deben de llegar
    @Mock
    lateinit var operations: Operations
    @Mock
    lateinit var listener: OnResolveListener
    lateinit var calculatorUtils: CalculatorUtils

    @Before
    fun setUp() {
        calculatorUtils = CalculatorUtils(operations, listener)
    }

    @Test
    fun cal_callCheckOrResolver_noReturn() {
        val operation = "-5x2.5"
        val isFromResolve = true
        calculatorUtils.checkOrResolve(operation, isFromResolve)
        verify(operations).tryResolve(operation, isFromResolve, listener)
    }

    @Test
    fun calc_addOperator_validSub_noReturn() {
        val operator = "-"
        //val operation = "4+-" //4+-3 fail test
        val operation = "4+"
        var isCorrect = false
        calculatorUtils.addOperator(operator, operation) {
            isCorrect = true
        }
        assertTrue(isCorrect)
    }

    @Test
    fun calc_addOperator_inValidSub_noReturn() {
        val operator = "-"
        val operation = "4+-"
        var isCorrect = false
        calculatorUtils.addOperator(operator, operation) {
            isCorrect = true
        }
        assertFalse(isCorrect)
    }

    @Test
    fun calc_callAddPoint_firstPoint_noReturn() {
        val operation = "3x2"
        var isCorrect = false
        calculatorUtils.addPoint(operation) {
            isCorrect = true
        }
        assertTrue(isCorrect)
        verifyNoInteractions(operations) //No interaccione con operaciones
    }

    @Test
    fun calc_callAddPoint_secondPoint_noReturn() {
        val operation = "3.5x2"
        val operator = "x"
        var isCorrect = false

        //Simular la respuesta
        `when`(operations.getOperator(operation)).thenReturn("x")
        `when`(operations.divideOperation(operator, operation)).thenReturn(arrayOf("3.5", "2"))

        calculatorUtils.addPoint(operation) {
            isCorrect = true
        }
        assertTrue(isCorrect)
        //Verificar que los metodos si estan siento ejecutados
        verify(operations).getOperator(operation)
        verify(operations).divideOperation(operator, operation)
    }
}