package com.example.supremedollop.salesmanSearch

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.supremedollop.R
import com.example.supremedollop.model.Salesman

@Composable
fun SalesmanSearchScreen(salesmanViewData: List<Salesman>, onQueryChanged: (String) -> Unit) {

    Column {
        SearchField(onQueryChanged)
        LazyColumn {
            items(salesmanViewData) { salesman ->
                SalesmanListItem(salesman.name,salesman.areas.joinToString(", "))
            }
        }
    }
}

@Composable
fun SalesmanListItem(name: String, details: String) {
    var isExpanded by remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded }) {
        Surface(
            modifier = Modifier.size(41.dp),
            shape = CircleShape,
            color = LightGray
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = name.first().toString(),
                    fontWeight = Bold
                )
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = name, fontWeight = Bold)
            Spacer(modifier = Modifier.weight(3f))
            if (isExpanded) {
                Text(text = details, color = Color.Gray)
            }
        }
        IconButton(onClick = { isExpanded = !isExpanded }) {
            Icon(
                imageVector = if (isExpanded)
                    ImageVector.vectorResource(R.drawable.baseline_keyboard_arrow_down_24)
                else
                    ImageVector.vectorResource(R.drawable.baseline_chevron_right_24),
                contentDescription = if (isExpanded) "Collapse" else "Expand"
            )
        }
    }
    HorizontalDivider()
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(onQueryChanged: (String) -> Unit) {
    var searchText by remember { mutableStateOf("") }

    TextField(
        value = searchText,
        onValueChange = {newValue ->
            if (newValue.length <= 5 && newValue.all { it.isDigit() }) {
                searchText = newValue
                onQueryChanged(newValue)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search"
            )
        },
        trailingIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.baseline_mic_24),
                contentDescription = "Mic"
            )
        },
        label = { Text("Suche") },
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.Gray
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchField() {
    SearchField {}
}
@Preview
@Composable
fun PreviewSalesmanListItem() {
    SalesmanListItem(name = "Anna Müller", details = "73133, 76131")
}

@Preview(showBackground = true)
@Composable
fun PreviewSalesmanSearchScreen() {
    SalesmanSearchScreen(
        salesmanViewData = listOf(
                Salesman("John Doe", listOf("76200", "76201", "76202")),
                Salesman("Anna Müller", listOf("73133, 76131"))
            ),
        onQueryChanged = {}
    )
}