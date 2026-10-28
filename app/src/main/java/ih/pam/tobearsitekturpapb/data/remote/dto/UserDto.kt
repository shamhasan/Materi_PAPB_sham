package ih.pam.tobearsitekturpapb.data.remote.dto

import ih.pam.tobearsitekturpapb.data.model.Student

data class UserDto(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val university: String
)
fun UserDto.toStudent() = Student(
    id = id,
    name = "$firstName $lastName",
    email = email,
    university = university,
)