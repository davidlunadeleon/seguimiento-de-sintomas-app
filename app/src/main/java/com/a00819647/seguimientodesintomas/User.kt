package com.a00819647.seguimientodesintomas

data class UserInfo(var userList: ArrayList<UserItem> = arrayListOf())

data class UserItem (var name: String?=null, var phone: String?=null, var lastName: String?=null, var id: String?=null, var email: String?=null, var dateOfBirth: String?=null,)