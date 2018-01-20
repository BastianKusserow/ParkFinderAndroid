package com.example.basti.parkfinder.Model

import android.content.Context
import android.util.Log
import java.io.FileNotFoundException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable


/**
 * Created by Basti on 13.01.18.
 */

class ParkModel(val latitude: Double, val longitude: Double) : Serializable {

    // Constant with a file name


    // Serializes an object and saves it to a file
    fun saveToFile(context: Context) {
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            val objectOutputStream = ObjectOutputStream(it)
            objectOutputStream.writeObject(this)
            objectOutputStream.close()
            it.close()
        }


    }

    companion object {
        var fileName = "parkModel.ser"
        // Creates an object by reading it from a file
        fun readFromFile(context: Context): ParkModel? {
            var parkModel: ParkModel? = null
            try {
                context.openFileInput(fileName).use {
                    val objectInputStream = ObjectInputStream(it)
                    parkModel = objectInputStream.readObject() as ParkModel
                    objectInputStream.close()
                    it.close()
                }
            } catch (e: FileNotFoundException) {
                Log.i("PERSISTANCE", "No such file")
            }

            return parkModel
        }

        fun deleteFile(context: Context): Boolean {
            val success = context.deleteFile(fileName)
            Log.i("DELETION", success.toString())
            return success
        }
    }


}