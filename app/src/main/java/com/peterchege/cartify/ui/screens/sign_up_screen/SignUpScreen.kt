package com.peterchege.cartify.ui.screens.sign_up_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.peterchege.cartify.util.Screens


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel:SignUpViewModel = hiltViewModel()
){
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            if (viewModel.isLoading.value){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Cartify",
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp),
                    textAlign = TextAlign.Center,
                )
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.fullNameState.value ,
                    onValueChange ={
                        viewModel.OnChangeFullname(it)

                    },
                    label = { Text("Full Name") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.emailState.value ,
                    onValueChange ={
                        viewModel.OnChangeEmail(it)

                    },
                    label = { Text("Email Address") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.phoneNumberState.value ,
                    onValueChange ={
                        viewModel.OnChangePhoneNumber(it)

                    },
                    label = { Text("Phone Number") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.addressState.value ,
                    onValueChange ={
                        viewModel.OnChangeAddress(it)

                    },
                    label = { Text("Address") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.passwordState.value ,
                    onValueChange ={
                        viewModel.OnChangePassword(it)

                    },
                    label = { Text("Password") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.confirmPasswordState.value ,
                    onValueChange ={
                        viewModel.OnChangeConfirmPassword(it)

                    },
                    label = { Text("Confirm Password") }
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        viewModel.signUpUser(
                            navController = navController,
                            scaffoldState = scaffoldState,
                            context = context
                        )
                    }
                )
                {
                    Text("Sign Up")
                }
                Spacer(modifier = Modifier.height(30.dp))
                TextButton(onClick = {
                    navController.navigate(Screens.LOGIN_SCREEN)

                }) {
                    Text(text = "Login")
                }

            }

        }


    }

}