package models

import android.os.Parcel
import android.os.Parcelable
import utils.DateUtils
import java.util.*

class Task(
    var name: String?,
    var description: String? = null,
    var start: Date? = null,
    var end: Date? = null,
    var priority: Priority? = null,
    var hasNotifications: Boolean = false
) : Parcelable {
    var id: UUID? = null
    var isComplete: Boolean = false

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        DateUtils.parse(parcel.readString()),
        DateUtils.parse(parcel.readString())
    ) {
        this.priority = this.readPriority(parcel.readInt())
        this.hasNotifications = parcel.readByte() != 0.toByte()
        this.isComplete = parcel.readByte() != 0.toByte()

        this.id = UUID.fromString(parcel.readString())
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        val priority = this.priority

        parcel.writeString(this.name)
        parcel.writeString(this.description)
        parcel.writeString(DateUtils.stringifyDateTime(this.start))
        parcel.writeString(DateUtils.stringifyDateTime(this.end))
        parcel.writeInt(priority?.value ?: 0)
        parcel.writeByte(if (this.hasNotifications) 1 else 0)
        parcel.writeByte(if (this.isComplete) 1 else 0)

        parcel.writeString(this.id.toString())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Task> {
        override fun createFromParcel(parcel: Parcel): Task {
            return Task(parcel)
        }

        override fun newArray(size: Int): Array<Task?> {
            return arrayOfNulls(size)
        }
    }

    fun readPriority(value: Int): Priority? {
        return Priority.values().firstOrNull() { it.value == value }
    }
}
