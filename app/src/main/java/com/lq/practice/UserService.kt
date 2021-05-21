package com.lq.practice

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

/**
 * @Description:
 * @author: mick
 * @CreateAt: 5/11/21 2:25 PM
 *
 * @UpdateUser: mick
 * @UpdateDate: 5/11/21 2:25 PM
 * @UpdateRemark:
 */
interface UserService {

    @GET
    fun getUser():Call<ResponseBody>
}