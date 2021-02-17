package com.guilherme.marvelcharacters.ui.home

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.guilherme.marvelcharacters.EventObserver
import com.guilherme.marvelcharacters.R
import com.guilherme.marvelcharacters.data.model.Character
import com.guilherme.marvelcharacters.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _homeBinding: FragmentHomeBinding? = null

    private val homeBinding get() = _homeBinding!!

    private val homeViewModel: HomeViewModel by viewModel()

    private lateinit var homeAdapter: HomeAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        homeViewModel.query?.let { homeBinding.filledTextField.editText?.setText(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _homeBinding = FragmentHomeBinding.bind(view)

        setHasOptionsMenu(true)
        setupObservers()
        setupScreenBindings()
        setupAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        homeViewModel.query = homeBinding.filledTextField.editText?.text.toString()
        _homeBinding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.actionToggleTheme) {
            homeViewModel.onActionItemClick()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun setupAdapter() {
        homeAdapter = HomeAdapter { character -> homeViewModel.onItemClick(character) }
        homeBinding.recyclerviewCharacters.adapter = homeAdapter
        homeBinding.recyclerviewCharacters.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    private fun setupScreenBindings() {
        homeBinding.composeView.setContent {
            SearchSection()
        }
    }

    @Composable
    fun SearchSection(homeViewModel: HomeViewModel = getViewModel()) {
        var searchText by remember { mutableStateOf(homeViewModel.query) }
        var isButtonEnabled by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchEditText(
                value = searchText,
                onValueChange = { text ->
                    isButtonEnabled = text.isNotEmpty()
                    searchText = text
                    homeViewModel.query = text
                },
                placeholder = stringResource(id = R.string.character),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(onSearch = { homeViewModel.onSearchCharacter(searchText) }),
                trailingIcon = if (isButtonEnabled) Icons.Filled.Close else null
            )
            SearchButton(
                onClick = { homeViewModel.onSearchCharacter(searchText) },
                enabled = isButtonEnabled,
                text = stringResource(id = R.string.search)
            )
        }
    }

    @Composable
    fun SearchEditText(
        value: String,
        onValueChange: (String) -> Unit,
        placeholder: String,
        keyboardOptions: KeyboardOptions,
        keyboardActions: KeyboardActions,
        trailingIcon: ImageVector? = null
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = { onValueChange(it) },
            placeholder = { Text(text = placeholder) },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = true,
            trailingIcon = {
                if (trailingIcon != null) {
                    Icon(
                        trailingIcon,
                        contentDescription = null
                    )
                }
            }
        )
    }

    @Composable
    fun SearchButton(
        onClick: () -> Unit,
        enabled: Boolean,
        text: String
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxWidth(),
            enabled = enabled
        ) {
            Text(text = text.toUpperCase())
        }
    }

    @Preview(
        showBackground = true,
        backgroundColor = 0xFFFFFF
    )
    @Composable
    fun DefaultPreview() {
        SearchSection()
    }

    private fun setupObservers() {
        homeViewModel.states.observe(viewLifecycleOwner) { state ->
            state?.let {
                when (state) {
                    is HomeViewModel.CharacterListState.Characters -> showCharacters(state.characters)
                    is HomeViewModel.CharacterListState.EmptyState -> showEmptyState()
                    is HomeViewModel.CharacterListState.ErrorState -> showError(state.messageId)
                    is HomeViewModel.CharacterListState.Loading -> handleLoading(mustShowLoading = true)
                }
            }
        }

        homeViewModel.navigateToDetail.observe(viewLifecycleOwner, EventObserver { character ->
            navigateToDetail(character)
        })

        homeViewModel.nightMode.observe(viewLifecycleOwner) { mode ->
            AppCompatDelegate.setDefaultNightMode(mode)
        }
    }

    private fun closeKeyboard() {
        (activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).run {
//            hideSoftInputFromWindow(homeBinding.button.windowToken, 0)
        }
    }

    private fun showError(@StringRes messageId: Int) {
        handleLoading(mustShowLoading = false)
        homeBinding.run {
            recyclerviewCharacters.visibility = View.GONE
            textviewMessage.setText(messageId)
            textviewMessage.visibility = View.VISIBLE
        }
    }

    private fun handleLoading(mustShowLoading: Boolean) {
        homeBinding.run {
            if (mustShowLoading) {
                recyclerviewCharacters.visibility = View.GONE
                textviewMessage.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun showEmptyState() {
        handleLoading(mustShowLoading = false)
        homeBinding.run {
            recyclerviewCharacters.visibility = View.GONE
            textviewMessage.setText(R.string.empty_state_message)
            textviewMessage.visibility = View.VISIBLE
        }
    }

    private fun showCharacters(list: List<Character>) {
        handleLoading(mustShowLoading = false)
        homeAdapter.characters = list
        homeAdapter.notifyDataSetChanged()

        homeBinding.textviewMessage.visibility = View.GONE
        homeBinding.recyclerviewCharacters.visibility = View.VISIBLE
    }

    private fun navigateToDetail(character: Character) {
        HomeFragmentDirections.actionHomeToDetail(character).run {
            findNavController().navigate(this)
        }
    }
}