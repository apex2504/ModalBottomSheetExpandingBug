package moe.apex.bottomsheet

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import moe.apex.bottomsheet.ui.theme.BottomSheetDemoTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BottomSheetDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                            var showQuicklyExpandSheet by remember { mutableStateOf(false) }

                            Button(onClick = { showQuicklyExpandSheet = true }) {
                                Text(text = "Quickly expand after clicking me")
                            }

                            if (showQuicklyExpandSheet) {
                                QuicklyExpandBottomSheet {
                                    showQuicklyExpandSheet = false
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ModifierParameter")
fun BottomSheetBase(
    contentModifier: Modifier = Modifier,
    sheetState: SheetState,
    onDismissRequest: () -> Unit
) {
    ModalBottomSheet(
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        contentWindowInsets = { WindowInsets() }
    ) {
        /* Set the `userScrollEnabled` param to `!sheetState.isAnimationRunning` to work around the
           issue whereby the sheet disappears if you try scrolling/expanding before the opening
           animation is finished. */
        BottomSheetContent(modifier = contentModifier, userScrollEnabled = true)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContent(modifier: Modifier = Modifier, userScrollEnabled: Boolean) {
    LazyColumn(
        modifier = modifier,
        userScrollEnabled = userScrollEnabled,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = WindowInsets.navigationBars.asPaddingValues()
    ) {
        repeat(100) {
            item(key = it) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Text(
                        text = "Item $it",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun QuicklyExpandBottomSheet(onDismissRequest: () -> Unit) {
    BottomSheetBase(
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false),
        onDismissRequest = onDismissRequest
    )
}
