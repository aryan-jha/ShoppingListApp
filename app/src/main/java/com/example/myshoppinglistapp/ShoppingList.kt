package com.example.myshoppinglistapp

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
fun ShoppingListApp() {
    var sItems by remember { mutableStateOf(listOf<ShoppingItem>()) }
    var showDialogBox by remember { mutableStateOf(false) }
    var itemName by remember { mutableStateOf("") }
    var itemCount by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { showDialogBox = true },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Add Item")
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            items(sItems) { item ->

                if (item.isEditing) {
                    ShoppingEditor(item = item, onEditComplete = { editedName, editedQuantity ->
                        sItems = sItems.map { it.copy(isEditing = false) }
                        val editedItem = sItems.find { it.id == item.id }
                        editedItem?.let {
                            it.name = editedName
                            it.quantity = editedQuantity
                        }
                    })
                } else {
                    ShoppingListItem(item = item, onEditClick = {
                        sItems = sItems.map { it.copy(isEditing = it.id == item.id) }
                    },
                        onDeleteClick = {
                            sItems = sItems - item
                        })
                }
            }

        }

    }
    if (showDialogBox) {
        AlertDialog(onDismissRequest = { showDialogBox = false },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = {

                        if (itemName.isNotBlank()) {
                            val newItem = ShoppingItem(
                                id = sItems.size + 1,
                                name = itemName,
                                quantity = itemCount.toInt()
                            )
                            sItems = sItems + newItem
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
                Column() {
                    Text(text = "Add Shopping Item")
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = itemName, onValueChange = { itemName = it },

                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)

                    )
                    OutlinedTextField(
                        value = itemCount, onValueChange = { itemCount = it },
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
    var id: Int,
    var name: String,
    var quantity: Int,
    var isEditing: Boolean = false
)

@Composable
fun ShoppingEditor(item: ShoppingItem, onEditComplete: (String, Int) -> Unit) {
    var editedName by remember { mutableStateOf(item.name) }
    var editedQuantity by remember { mutableStateOf(item.quantity.toString()) }
    var isEditing by remember { mutableStateOf(item.isEditing) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(9.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column {
            BasicTextField(
                value = editedName,
                onValueChange = {
                    editedName = it
                },
                singleLine = true,
                modifier = Modifier
                    .wrapContentWidth() // takes only space equal to the text eg. if the text is of 5 letters then the space will also be alocated of 5 letters
                    .padding(9.dp)
            )
            BasicTextField(
                value = editedQuantity,
                onValueChange = {
                    editedQuantity = it
                },
                singleLine = true,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(9.dp)
            )
        }
        Button(onClick = {
            isEditing = false
            onEditComplete(editedName, editedQuantity.toIntOrNull() ?: 1)
        }) {
            Text(text = "Save")
        }
    }
}


@Composable
fun ShoppingListItem(
    item: ShoppingItem,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
//        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .padding(9.dp)
            .border(
                border = BorderStroke(3.dp, color = Color.Cyan),
                shape = RoundedCornerShape(20)
            ),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = item.name, modifier = Modifier.padding(9.dp))
        Text(text = "Qty:${item.quantity.toString()}", modifier = Modifier.padding(9.dp))

        IconButton(onClick = onEditClick) {
            Icon(
                Icons.Default.Edit,
                contentDescription = "Edit Button",
                tint = Color.Black // Tint color for the icon
            )
        }

        IconButton(onClick = onDeleteClick) {
            Icon(
                Icons.Default.Delete,
                contentDescription = null
            )
        }

    }
}
