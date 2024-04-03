package com.example.myshoppinglistapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun ShoppingListApp(){
    var sItems by remember { mutableStateOf(listOf<ShoppingItem>())}
    var showDialogBox by remember {
        mutableStateOf(false)
    }
    var itemName by remember {
        mutableStateOf("")
    }
    var itemCount by remember {
        mutableStateOf("")
    }

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ){
        Button(
            onClick = {showDialogBox = true},
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Add Item")
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ){
            items(sItems){

            }

        }

    }
    if(showDialogBox){
        AlertDialog(onDismissRequest = { showDialogBox = false},
            confirmButton = {
                            Row (
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ){
                                Button(onClick = { 
                                    
                                    if (itemName.isNotBlank()){
                                        val newItem = ShoppingItem(
                                            id = sItems.size+1,
                                            name = itemName,
                                            quantity = itemCount.toInt()
                                        )
                                        sItems = sItems+newItem
                                        itemName = ""
                                        showDialogBox = false

                                    }
                                    
                                    
                                }) {
                                    Text(text = "Add")
                                }
                                Button(onClick = { /*TODO*/ }) {
                                    Text(text = "Clear")
                                }
                            }
                                                                                 },
            text = {
                Column (){
                    Text(text = "Add Shopping Item")
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(value = itemName, onValueChange = { itemName = it},

                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)

                        )
                    OutlinedTextField(value = itemCount, onValueChange = { itemCount = it },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)
                    )

//                    Spacer(modifier = Modifier.height(27.dp))

                }
            }
        )
    }


}

data class ShoppingItem(
    var id : Int,
    var name : String,
    var quantity : Int,
    var isEditing : Boolean = false
)