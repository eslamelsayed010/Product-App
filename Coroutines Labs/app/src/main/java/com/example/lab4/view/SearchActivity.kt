package com.example.lab4.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lab4.vm.SearchViewModel

class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchScreen()
        }
    }
}

@Composable
fun SearchScreen(viewModel: SearchViewModel = viewModel()) {
    var query by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text(text = "Search Names")

        Spacer(modifier = Modifier.height(8.dp))

        SearchBar(query) { newText ->
            query = newText
            viewModel.search(query)
        }

        Spacer(modifier = Modifier.height(16.dp))

        val names by viewModel.filteredNames.collectAsState()

        if (names.isEmpty() && query.isNotEmpty()) {
            Text("No results found", color = Color.Gray)
        } else {
            names.forEach { name ->
                Text(text = name, modifier = Modifier.padding(4.dp))
            }
        }
    }
}

@Composable
fun SearchBar(text: String, onTextChange: (String) -> Unit) {
    OutlinedTextField(
        value = text,
        onValueChange = { onTextChange(it) },
        label = { Text("Enter a letter") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { }),
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchScreen() {
    SearchScreen()
}
