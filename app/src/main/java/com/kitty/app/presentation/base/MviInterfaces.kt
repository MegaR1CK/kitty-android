package com.kitty.app.presentation.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * Состояние экрана, на основе которого отрисовывается интерфейс
 */
interface UiState

/**
 * Эффект экрана, событие, происходящее единожды и не требующее хранения
 */
interface UiEffect

/**
 * Намерение изменить состояние экрана
 */
interface UiIntent

/**
 * Reducer отвечает за преобразование старого стейта и события в новый стейт
 */
interface Reducer<S : UiState, I : UiIntent> {
    suspend fun reduce(oldState: S, intent: I): S
}

/** Store отвечает за хранение потоков стейтов и эффектов */
interface Store<S : UiState, I : UiIntent, E : UiEffect> {
    val stateFlow: StateFlow<S>
    val effectsFlow: Flow<E>
    suspend fun dispatch(intent: I)
    suspend fun effect(effect: E)
}
