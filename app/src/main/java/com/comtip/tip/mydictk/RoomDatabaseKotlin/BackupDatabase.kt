package com.comtip.tip.mydictk.RoomDatabaseKotlin

import android.app.Activity
import android.os.Environment
import android.widget.Toast
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.channels.FileChannel

/**
 * Created by TipRayong on 22/3/2561 16:07
 * MyDictK
 */
abstract  class BackupDatabase {
    companion object {
        fun backup(activity: Activity,dbName:String){

            val sd:File = Environment.getExternalStorageDirectory()

            if(sd.canWrite()){
                val currentDBPath:String = activity.getDatabasePath(dbName).absolutePath
                val backupDBPath = "$dbName.db";

                val currentDB = File(currentDBPath)
                val backupDB = File(sd,backupDBPath)

                if(currentDB.exists()){
                    val src:FileChannel = FileInputStream(currentDB).channel
                    val dst:FileChannel = FileOutputStream(backupDB).channel
                    dst.transferFrom(src,0,src.size())
                    src.close()
                    dst.close()

                    Toast.makeText(activity,"Back Up Database !!!",Toast.LENGTH_SHORT).show()
                }
            }

        }

        fun restore(activity: Activity,dbName: String):Boolean{
            var completed = false

            val sd:File = Environment.getExternalStorageDirectory()
            if(sd.canWrite()){
                val currentDBPath:String = activity.getDatabasePath(dbName).absolutePath
                val backupDBPath = "$dbName.db";

                val currentDB = File(currentDBPath)
                val backupDB = File(sd,backupDBPath)

                if(currentDB.exists()){
                    val src:FileChannel = FileInputStream(backupDB).channel
                    val dst:FileChannel = FileOutputStream(currentDB).channel
                    dst.transferFrom(src,0,src.size())
                    src.close()
                    dst.close()
                    Toast.makeText(activity,"Restore Database !!!",Toast.LENGTH_SHORT).show()
                    completed = true
                } else{
                    Toast.makeText(activity,"Not Exist !!!",Toast.LENGTH_SHORT).show()
                    completed = false
                }
            }

           return completed
        }

    }
}

