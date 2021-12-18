package com.hjiee.domain.entity

import java.text.SimpleDateFormat
import java.util.*

sealed class DomainEntity {
    interface BeerItem {

    }

    data class TokenInfo(
        val accessToken: String,
        val refreshToken: String
    )

    data class Response<ELEMENT>(
        val isSuccess: Boolean,
        val message: String,
        val data: ELEMENT
    )

    data class Beers(
        val beers: List<Beer>,
        val cursor: Cursor?
    )

    data class Beer(
        val id: Int,
        val abv: Float,
        val krName: String,
        val starAvg: Float,
        val engName: String,
        val thumbnailImage: String
//        val aromas: List<String>,
//        val beerStyle: String,
//        val brewery: String,
//        val country: String,
//        val imageUrl: List<String>,
//        val rateAvg: Float,
//        val reviewList: List<Review>,
//        val isFavorite: Boolean
    )

    data class Cursor(
        val cursor: Int?
    )

    class Review(
        val beerId: Int,
        val content: String,
        val reviewId: Int,
        val star: Float,
        val userId: String,
        createdDate: String,
        updatedDate: String
    ) {
        val createdAt: String = SimpleDateFormat("yyyy. MM. dd").format(createdDate)
        val updatedAt: String = SimpleDateFormat("yyyy. MM. dd").format(updatedDate)

    }

    class User(
        val id: Int,
        val email: String,
        val nickName: String,
        val reviewCount: Int,
        val levelId: Int,
        val profileImage: String
    )

    class StyleLargeCategory(
        val bigName: String,
        val middleCategories: List<StyleMediumCategory>
    )

    class StyleMediumCategory(
        val middleName: String,
        val description: String,
        val smallCategories: List<String>
    )

    class Token(
        val tokenType: String,
        val accessToken: String,
        val expiresIn: Int,
        val refreshToken: String,
        val refreshTokenExpiresIn: Int,
        val scope: String
    )

    class Page(
        val nextCursor: Int?
    )

}