package com.peterchege.cartify.ui.screens.login_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.peterchege.cartify.util.Screens

@ExperimentalComposeUiApi
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
){
    val keyboardController = LocalSoftwareKeyboardController.current
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
        ,

    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            if (viewModel.isLoading.value){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

            }
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {


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
                    value = viewModel.passwordState.value ,
                    onValueChange ={
                        viewModel.OnChangePassword(it)

                    },
                    label = { Text("Password") }
                )
                Button(
                    onClick = {
                        keyboardController?.hide()
                        viewModel.loginUser(
                            navController = navController,
                            scaffoldState = scaffoldState,
                            context = context
                        )
                    }
                )
                {
                    Text("Login")
                }
                Spacer(modifier = Modifier.height(30.dp))
                TextButton(onClick = {
                    navController.navigate(Screens.SIGN_UP_SCREEN)

                }) {
                    Text(text = "Dont have an account yet...Sign Up")
                }


            }

        }


    }


}