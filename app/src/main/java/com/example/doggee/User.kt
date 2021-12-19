package com.example.doggee

data class User(
    val id:Int,
    val email:String,
    val password:String,
    val token:String,
    val memberSince:Long
)
data class LoginRequest(
    var email:String,
    var password:String
)
data class EmailChangeRequest(var email:String)

data class Pets_Data(
    val id: Int=0,
    val url:String="",
    val name: String="",
    val type:String="",
    val age:Int=0,
    val vaccinated:Int=0

)
data class Petss_Data(
    val id: Int=0,
    val url:String="",
    val name: String="",
    val type:String="",
    val age:Int=0,
    val interestId:Int=0

)

data class AllPets(
    val pets:List<Pets_Data>?=null
)

data class LoginHistoryDataClass(
    val loginTimestamp:Long=0,
)

data class LogList(
    val loginEntries:List<LoginHistoryDataClass>?= null
)
data class otherUserDataClass(
    val email:String,
    val reservationsAt:String
)
data class otherUserList(
    val users:List<otherUserDataClass>?= null
)

data class DogId(
    val petId:Int)

data class IntDetail(
    val userId:Int,
    val petId:Int,
    val interestId:Int,
    val loginTimestamp:Long
)
data class IntList(
    val petInterests:List<IntDetail>?=null
)
