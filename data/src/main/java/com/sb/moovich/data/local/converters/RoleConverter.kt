package com.sb.moovich.data.local.converters

import androidx.room.TypeConverter
import com.sb.moovich.domain.entity.Message

class RoleConverter {
    @TypeConverter
    fun fromRole(role: Message.Role): String {
        return role.name
    }

    @TypeConverter
    fun toRole(name: String): Message.Role {
        return Message.Role.valueOf(name)
    }
}
