package com.guilherme.marvelcharacters.ui.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.material.composethemeadapter.MdcTheme
import com.guilherme.marvelcharacters.model.CharacterVO
import com.guilherme.marvelcharacters.model.ImageVO
import com.guilherme.marvelcharacters.ui.home.CharacterItem

@Composable
fun FavoritesScreen(
    state: FavoritesState,
    onItemClick: (CharacterVO) -> Unit,
    onErrorMessageShown: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.list) { character ->
                CharacterItem(character, onItemClick = onItemClick)
                Divider()
            }
        }
        state.messageId?.let { id ->
            val message = stringResource(id)
            LaunchedEffect(id) {
                snackbarHostState.showSnackbar(message, duration = SnackbarDuration.Long)
                onErrorMessageShown()
            }
        }
        SnackbarHost(
            modifier = Modifier.align(Alignment.BottomCenter),
            hostState = snackbarHostState
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    MdcTheme {
        FavoritesScreen(
            state = FavoritesState(
                list = listOf(
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
            onItemClick = { },
            onErrorMessageShown = { }
        )
    }
}