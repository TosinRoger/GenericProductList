package com.tosin.genericproductlist.domain.wrapper

import com.tosin.genericproductlist.data.database.entity.ProductLocal
import com.tosin.genericproductlist.data.remote.entity.ProductRemote
import com.tosin.genericproductlist.domain.factory.entity.ProductFactory
import com.tosin.genericproductlist.domain.factory.entity.ProductLocalFactory
import com.tosin.genericproductlist.domain.factory.entity.ProductRemoteFactory
import com.tosin.genericproductlist.domain.model.Product
import io.mockk.MockKAnnotations
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class ProductWrapperTest {

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `wrapper Local to Ui`() {
        // GIVEN
        val productLocal = ProductLocalFactory.makeProductLocal()
        val wrapper = ProductWrapper()

        // WHEN
        val responseUI = wrapper.toUi(productLocal) as Product

        // THEN
        assertEquals(
            productLocal.id,
            responseUI.id
        )

        assertEquals(
            productLocal.title,
            responseUI.title
        )

        assertEquals(
            productLocal.description,
            responseUI.description
        )

        assertEquals(
            productLocal.price,
            responseUI.price
        )

        assertEquals(
            productLocal.discountPercentage,
            responseUI.discountPercentage
        )

        assertEquals(
            productLocal.rating,
            responseUI.rating
        )

        assertEquals(
            productLocal.stock,
            responseUI.stock
        )

        assertEquals(
            productLocal.brand,
            responseUI.brand
        )

        assertEquals(
            productLocal.category,
            responseUI.category
        )

        assertEquals(
            productLocal.thumbnail,
            responseUI.thumbnail
        )

//        assertEquals(
//            productLocal.images.first(),
//            responseUI.images.first()
//        )
    }

    @Test
    fun `wrapper Ui to Local`() {
        // GIVEN
        val product = ProductFactory.makeProduct()
        val wrapper = ProductWrapper()

        // WHEN
        val responseUI = wrapper.toLocal(product) as ProductLocal

        // THEN
        assertEquals(
            product.id,
            responseUI.id
        )

        assertEquals(
            product.title,
            responseUI.title
        )

        assertEquals(
            product.description,
            responseUI.description
        )

        assertEquals(
            product.price,
            responseUI.price
        )

        assertEquals(
            product.discountPercentage,
            responseUI.discountPercentage
        )

        assertEquals(
            product.rating,
            responseUI.rating
        )

        assertEquals(
            product.stock,
            responseUI.stock
        )

        assertEquals(
            product.brand,
            responseUI.brand
        )

        assertEquals(
            product.category,
            responseUI.category
        )

        assertEquals(
            product.thumbnail,
            responseUI.thumbnail
        )

//        assertEquals(
//            product.images.first(),
//            responseUI.images.first()
//        )
    }

    @Test
    fun `wrapper Local to Remote`() {
        // GIVEN
        val productLocal = ProductLocalFactory.makeProductLocal()
        val wrapper = ProductWrapper()

        // WHEN
        val responseUI = wrapper.toRemote(productLocal) as ProductRemote

        // THEN
        assertEquals(
            productLocal.id,
            responseUI.id
        )

        assertEquals(
            productLocal.title,
            responseUI.title
        )

        assertEquals(
            productLocal.description,
            responseUI.description
        )

        assertEquals(
            productLocal.price,
            responseUI.price
        )

        assertEquals(
            productLocal.discountPercentage,
            responseUI.discountPercentage
        )

        assertEquals(
            productLocal.rating,
            responseUI.rating
        )

        assertEquals(
            productLocal.stock,
            responseUI.stock
        )

        assertEquals(
            productLocal.brand,
            responseUI.brand
        )

        assertEquals(
            productLocal.category,
            responseUI.category
        )

        assertEquals(
            productLocal.thumbnail,
            responseUI.thumbnail
        )

//        assertEquals(
//            productLocal.images.first(),
//            responseUI.images.first()
//        )
    }

    @Test
    fun `wrapper Remote to Local`() {
        // GIVEN
        val product = ProductRemoteFactory.makeProductRemote()
        val wrapper = ProductWrapper()

        // WHEN
        val responseUI = wrapper.toLocal(product) as ProductLocal

        // THEN
        assertEquals(
            product.id,
            responseUI.id
        )

        assertEquals(
            product.title,
            responseUI.title
        )

        assertEquals(
            product.description,
            responseUI.description
        )

        assertEquals(
            product.price,
            responseUI.price
        )

        assertEquals(
            product.discountPercentage,
            responseUI.discountPercentage
        )

        assertEquals(
            product.rating,
            responseUI.rating
        )

        assertEquals(
            product.stock,
            responseUI.stock
        )

        assertEquals(
            product.brand,
            responseUI.brand
        )

        assertEquals(
            product.category,
            responseUI.category
        )

        assertEquals(
            product.thumbnail,
            responseUI.thumbnail
        )

//        assertEquals(
//            product.images.first(),
//            responseUI.images.first()
//        )
    }
}
