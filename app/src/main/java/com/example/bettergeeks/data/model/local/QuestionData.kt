package com.example.bettergeeks.data.model.local

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bettergeeks.screens.recycler.Data
import com.example.bettergeeks.screens.recycler.ViewTypes
import java.util.UUID


@Entity(tableName = "question_table")
data class QuestionData(
    val question: String? = "",
    val answer: String? = "",
    val topicId: String = "",
    val imageUrl: String? = "",
    val likes: Int = 0,
    val dislikes: Int = 0,
    val isLiked: Boolean = false,
    val isDisliked: Boolean = false,
    @PrimaryKey
    override val id: String = UUID.randomUUID().toString(),
) : Data, Parcelable {

    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()!!,
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readBoolean(),
        parcel.readBoolean(),
    )

    override fun getViewType(): Int {
        return ViewTypes.QUESTION.ordinal
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(question)
        parcel.writeString(answer)
        parcel.writeString(topicId)
        parcel.writeString(imageUrl)
        parcel.writeInt(likes)
        parcel.writeInt(dislikes)
        parcel.writeString(id)
        parcel.writeBoolean(isLiked)
        parcel.writeBoolean(isDisliked)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QuestionData> {
        @RequiresApi(Build.VERSION_CODES.Q)
        override fun createFromParcel(parcel: Parcel): QuestionData {
            return QuestionData(parcel)
        }

        override fun newArray(size: Int): Array<QuestionData?> {
            return arrayOfNulls(size)
        }
    }
}