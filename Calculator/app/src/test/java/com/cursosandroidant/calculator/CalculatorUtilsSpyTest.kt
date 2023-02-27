package com.cursosandroidant.calculator

import org.junit.Assert
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by davidgonzalez on 26/02/23
 */
@RunWith(MockitoJUnitRunner::class)
class CalculatorUtilsSpyTest {

    //Con Spy Operations si esta funcionando internamente
    @Spy
    lateinit var operations: Operations
    @Mock
    lateinit var listener: OnResolveListener
    lateinit var calculatorUtils: CalculatorUtils

    @Before
    fun setUp() {
        calculatorUtils = CalculatorUtils(operations, listener)
    }

    //Validar que SI se puede agregar un punto despues del 2
    @Test
    fun calc_callAddPoint_validSecondPoint_noReturn() {
        val operation = "3.5x2"
        val operator = "x"
        var isCorrect = false

        calculatorUtils.addPoint(operation) {
            isCorrect = true
        }
        assertTrue(isCorrect)
        //Verificar que los metodos si estan siento ejecutados
        verify(operations).getOperator(operation)
        verify(operations).divideOperation(operator, operation)
    }

    //Validar que NO se puede agregar un punto despues del 2
    @Test
    fun calc_callAddPoint_invalidSecondPoint_noReturn() {
        val operation = "3.5x2."
        val operator = "x"
        var isCorrect = false

        calculatorUtils.addPoint(operation) {
            isCorrect = true
        }
        assertFalse(isCorrect)
        //Verificar que los metodos si estan siento ejecutados
        verify(operations).getOperator(operation)
        verify(operations).divideOperation(operator, operation)
    }
}