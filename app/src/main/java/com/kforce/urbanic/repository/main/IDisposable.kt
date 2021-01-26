package com.kforce.urbanic.repository.main

/**
 * Represents a disposable resource.
 */
interface IDisposable {
    /**
     * Dispose the resource, the operation should be idempotent.
     */
    fun dispose()

    /**
     * Returns true if this resource has been disposed.
     * @return true if this resource has been disposed
     */
    val isDisposed: Boolean
}