package com.example.bettergeeks.data.model.local

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bettergeeks.screens.recycler.Data
import com.example.bettergeeks.screens.recycler.ViewTypes


@Entity(tableName = "question_table")
data class QuestionData(
    val question: String?,
    val answer: String?,
    val topicId: Long = 0,
    val imageUrl: String?,
    val likes: Int = 0,
    val dislikes: Int = 0,
    @PrimaryKey(autoGenerate = true)
    override val id: Long = 0,
) : Data, Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readLong()
    ) {
    }

    override fun getViewType(): Int {
        return ViewTypes.QUESTION.ordinal
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(question)
        parcel.writeString(answer)
        parcel.writeLong(topicId)
        parcel.writeString(imageUrl)
        parcel.writeInt(likes)
        parcel.writeInt(dislikes)
        parcel.writeLong(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QuestionData> {
        override fun createFromParcel(parcel: Parcel): QuestionData {
            return QuestionData(parcel)
        }

        override fun newArray(size: Int): Array<QuestionData?> {
            return arrayOfNulls(size)
        }
    }
}