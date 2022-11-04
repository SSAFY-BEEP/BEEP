package com.example.beep.ui.message

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.beep.data.dto.DataModel
import com.example.beep.data.api.RetrofitApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MessageScreen(viewModel: MessageViewModel = viewModel(), toNext: () -> Unit) {
    var show by remember { mutableStateOf(true) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column() {
            Button(onClick = toNext ) {
                Text(text = "Record Voice")
            }
            Text(text = "Message screen")
            postData(show, onClick = {
                show = !show
                Log.d("show", show.toString())
            })
        }

    }
}


@Composable
fun postData(
    show: Boolean,
    onClick: () -> Unit,
    viewModel: MessageViewModel = viewModel()
) {
    val ctx = LocalContext.current

    val userName = remember {
        mutableStateOf(TextFieldValue())
    }
    val job = remember {
        mutableStateOf(TextFieldValue())
    }
    val response = remember {
        mutableStateOf("")
    }


    // on below line we are creating a column.
    Column(
        // on below line we are adding a modifier to it
        // and setting max size, max height and max width
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .fillMaxWidth(),
        // on below line we are adding vertical
        // arrangement and horizontal alignment.
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = onClick, modifier = Modifier) {
            Text(text = "ToggleShow")
        }
        if (show) {
            // on below line we are creating a text
            Text(
                // on below line we are specifying text as
                // Session Management in Android.
                text = "Retrofit POST Request in Android",
                // on below line we are specifying text color.
                color = MaterialTheme.colors.primary,
                fontSize = 20.sp,
                // on below line we are specifying font family
                fontFamily = FontFamily.Default,
                // on below line we are adding font weight
                // and alignment for our text
                fontWeight = FontWeight.Bold, textAlign = TextAlign.Center
            )
            //on below line we are adding spacer
            Spacer(modifier = Modifier.height(5.dp))
            // on below line we are creating a text field for our email.
            TextField(
                // on below line we are specifying value for our email text field.
                value = userName.value,
                // on below line we are adding on value change for text field.
                onValueChange = { userName.value = it },
                // on below line we are adding place holder as text as "Enter your email"
                placeholder = { Text(text = "Enter your name") },
                // on below line we are adding modifier to it
                // and adding padding to it and filling max width
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                // on below line we are adding text style
                // specifying color and font size to it.
                textStyle = TextStyle(
                    color = androidx.compose.ui.graphics.Color.Black,
                    fontSize = 15.sp
                ),
                // on below line we ar adding single line to it.
                singleLine = true,
            )
            // on below line we are adding spacer
            Spacer(modifier = Modifier.height(5.dp))
            // on below line we are creating a text field for our email.
            TextField(
                // on below line we are specifying value for our email text field.
                value = job.value,
                // on below line we are adding on value change for text field.
                onValueChange = { job.value = it },
                // on below line we are adding place holder as text as "Enter your email"
                placeholder = { Text(text = "Enter your job") },
                // on below line we are adding modifier to it
                // and adding padding to it and filling max width
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                // on below line we are adding text style
                // specifying color and font size to it.
                textStyle = TextStyle(
                    color = androidx.compose.ui.graphics.Color.Black,
                    fontSize = 15.sp
                ),
                // on below line we ar adding single line to it.
                singleLine = true,
            )
            // on below line we are adding spacer
            Spacer(modifier = Modifier.height(10.dp))
            // on below line we are creating a button
            Button(
//                onClick = { viewModel.postTest(DataModel(userName.value.text, job.value.text)) }
                onClick = { viewModel.getTest() }
                // on below line we are calling make payment method to update data.
//                    postDataUsingRetrofit(
//                        ctx, userName, job, response
//                    )
                ,
                // on below line we are adding modifier to our button.
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // on below line we are adding text for our button
                Text(text = "Post Data", modifier = Modifier.padding(8.dp))
            }
        }


        // on below line we are adding a spacer.
        Spacer(modifier = Modifier.height(20.dp))
        // on below line we are creating a text for displaying a response.
        Text(
            text = response.value,
            color = androidx.compose.ui.graphics.Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold, modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

private fun postDataUsingRetrofit(
    ctx: Context,
    userName: MutableState<TextFieldValue>,
    job: MutableState<TextFieldValue>,
    result: MutableState<String>
) {
    var url = "https://reqres.in/api/"
    // on below line we are creating a retrofit
    // builder and passing our base url
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        // as we are sending data in json format so
        // we have to add Gson converter factory
        .addConverterFactory(GsonConverterFactory.create())
        // at last we are building our retrofit builder.
        .build()
    // below the line is to create an instance for our retrofit api class.
    val retrofitAPI = retrofit.create(RetrofitApi::class.java)
    // passing data from our text fields to our model class.
    val dataModel = DataModel(userName.value.text, job.value.text)
    // calling a method to create an update and passing our model class.
    val call: Call<DataModel?>? = retrofitAPI.postDataOriginal(dataModel)
    // on below line we are executing our method.
    call!!.enqueue(object : Callback<DataModel?> {
        override fun onResponse(call: Call<DataModel?>?, response: Response<DataModel?>) {
            // this method is called when we get response from our api.
            Toast.makeText(ctx, "Data posted to API", Toast.LENGTH_SHORT).show()
            // we are getting a response from our body and
            // passing it to our model class.
            val model: DataModel? = response.body()
            // on below line we are getting our data from model class
            // and adding it to our string.
            val resp =
                "Response Code : " + response.code() + "\n" + "User Name : " + model!!.name + "\n" + "Job : " + model!!.job
            // below line we are setting our string to our response.
            result.value = resp
        }

        override fun onFailure(call: Call<DataModel?>?, t: Throwable) {
            // we get error response from API.
            result.value = "Error found is : " + t.message
        }
    })

}