package com.guilherme.marvelcharacters.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.material.composethemeadapter.MdcTheme
import com.guilherme.marvelcharacters.R
import com.guilherme.marvelcharacters.model.CharacterVO
import com.guilherme.marvelcharacters.model.ImageVO

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsState()
    HomeScreen(
        state,
        onSearchButtonClick = viewModel::onSearchCharacter,
        onItemClick = { character ->
            navController.navigate("detail/$character")
        }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(
    state: HomeState,
    onSearchButtonClick: (String) -> Unit,
    onItemClick: (CharacterVO) -> Unit
) {
    var query by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(stringResource(R.string.app_name)) }
        )
        OutlinedTextField(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text(text = stringResource(id = R.string.character)) },
            value = query,
            onValueChange = { query = it },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { query = "" }) {
                        Icon(imageVector = Icons.Default.Cancel, contentDescription = null)
                    }
                }
            },
            singleLine = true,
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchButtonClick(query)
                    keyboardController?.hide()
                }
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable { onItemClick(character) }
    ) {
        Text(
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            text = character.name
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    MdcTheme {
        HomeScreen(
            state = HomeState(
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
            state = HomeState(isLoading = true),
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
            state = HomeState(errorMessageId = R.string.error_message),
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