package com.guilherme.marvelcharacters.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.material.composethemeadapter.MdcTheme
import com.guilherme.marvelcharacters.R
import com.guilherme.marvelcharacters.model.CharacterVO
import com.guilherme.marvelcharacters.model.ImageVO

@Composable
fun HomeScreen(
    state: HomeState,
    onSearchButtonClick: (String) -> Unit,
    onClearButtonClick: () -> Unit,
    onItemClick: (CharacterVO) -> Unit
) {
    var query by rememberSaveable { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(0.7f),
                label = { Text(text = stringResource(id = R.string.character)) },
                value = query,
                onValueChange = { query = it },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = onClearButtonClick) {
                            Icon(imageVector = Icons.Default.Cancel, contentDescription = null)
                        }
                    }
                },
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onSearch = { onSearchButtonClick(query) }
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
            )
            Button(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(0.3f),
                onClick = { onSearchButtonClick(query) }
            ) {
                Text(text = stringResource(R.string.search).uppercase())
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when {
                state.isLoading -> CircularProgressIndicator()
                state.errorMessageId != null -> {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center,
                        text = stringResource(state.errorMessageId)
                    )
                }
                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(state.characters) { character ->
                            CharacterItem(character, onItemClick)
                            Divider()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterItem(
    character: CharacterVO,
    onItemClick: (CharacterVO) -> Unit
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clickable { onItemClick(character) },
        text = character.name
    )
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    MdcTheme {
        HomeScreen(
            state = HomeState.initialState().copy(
                characters = listOf(
                    CharacterVO(
                        id = 0,
                        name = "Spider-Man",
                        description = "Teste",
                        thumbnail = ImageVO(path = "", extension = "")
                    ),
                    CharacterVO(
                        id = 0,
                        name = "Spider-Man",
                        description = "Teste",
                        thumbnail = ImageVO(path = "", extension = "")
                    )
                )
            ),
            onClearButtonClick = { },
            onSearchButtonClick = { },
            onItemClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeLoadingPreview() {
    MdcTheme {
        HomeScreen(
            state = HomeState.initialState().showLoading(),
            onClearButtonClick = { },
            onSearchButtonClick = { },
            onItemClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeErrorPreview() {
    MdcTheme {
        HomeScreen(
            state = HomeState.initialState().copy(errorMessageId = R.string.error_message),
            onClearButtonClick = { },
            onSearchButtonClick = { },
            onItemClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterItemPreview() {
    MdcTheme {
        CharacterItem(
            character = CharacterVO(
                id = 0,
                name = "Spider-Man",
                description = "Teste",
                thumbnail = ImageVO(path = "", extension = "")
            ),
            onItemClick = { }
        )
    }
}