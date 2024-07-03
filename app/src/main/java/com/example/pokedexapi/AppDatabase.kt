package com.example.pokedexapi

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.pokedexapi.model.PokemonsEvo
import com.example.pokedexapi.model.PokemonsModel
import com.example.pokedexapi.model.PokemonsTypes

@Database(entities = [PokemonsModel::class, PokemonsTypes::class, PokemonsEvo::class], version = 6)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokeDao(): PokemonsDao
    abstract fun typesDao(): TypesDao
    abstract fun evoDao(): EvoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }

}
