package com.tosin.genericproductlist.app.ui.state

import com.tosin.genericproductlist.domain.model.Product

/**
 * State
 * - Loading
 * - Return default list
 * - Return list from search
 * - Has some error, form Resource or from String
 */
data class UiProductState(
    val uiState: UiState
) {
    val products: List<Product> = listOf()
    val messageUser: String? = null
    val idMessageUser: Int? = null
}
