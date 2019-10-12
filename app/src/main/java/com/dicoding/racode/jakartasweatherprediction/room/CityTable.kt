package com.dicoding.racode.jakartasweatherprediction.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dicoding.racode.jakartasweatherprediction.utils.Constant

@Entity(
    tableName = Constant.Table.CITY
)
class CityTable(@PrimaryKey val id: Int,
                val name: String)