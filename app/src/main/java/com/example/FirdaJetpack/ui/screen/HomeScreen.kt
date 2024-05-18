package com.example.FirdaJetpack.ui.screen

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.FirdaJetpack.R
import com.example.FirdaJetpack.di.Injection
import com.example.FirdaJetpack.model.Animal
import com.example.FirdaJetpack.ui.common.UiState
import com.example.FirdaJetpack.ui.item.EmptyList
import com.example.FirdaJetpack.ui.item.AnimalsItem
import com.example.FirdaJetpack.ui.item.Search
import com.example.FirdaJetpack.ui.viewmodel.HomeViewModel
import com.example.FirdaJetpack.ui.viewmodel.ViewModelFactory

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Int) -> Unit,
) {
    val query by viewModel.query
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.search(query)
            }
            is UiState.Success -> {
                HomeContent(
                    query = query,
                    onQueryChange = viewModel::search,
                    listAnimal = uiState.data,
                    onFavoriteIconClicked = { id, newState ->
                        viewModel.updateAnimal(id, newState)
                    },
                    navigateToDetail = navigateToDetail
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    query: String,
    onQueryChange: (String) -> Unit,
    listAnimal: List<Animal>,
    onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
) {
    Column {
        Search(
            query = query,
            onQueryChange = onQueryChange
        )
        if (listAnimal.isNotEmpty()) {
            ListAnimal(
                listAnimal = listAnimal,
                onFavoriteIconClicked = onFavoriteIconClicked,
                navigateToDetail = navigateToDetail
            )
        } else {
            EmptyList(
                Warning = stringResource(R.string.empty_data),
                modifier = Modifier
                    .testTag("emptyList")
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListAnimal(
    listAnimal: List<Animal>,
    onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
    contentPaddingTop: Dp = 0.dp,
) {
    LazyColumn(
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp,
            top = contentPaddingTop
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .testTag("lazy_list")
    ) {
        items(listAnimal, key = { it.id }) { item ->
            AnimalsItem(
                id = item.id,
                name = item.name,
                type = item.type,
                image = item.image,
                life = item.life,
                isFavorite = item.isFavorite,
                onFavoriteIconClicked = onFavoriteIconClicked,
                modifier = Modifier
                    .animateItemPlacement(tween(durationMillis = 200))
                    .clickable { navigateToDetail(item.id) }
            )
        }
    }
}
