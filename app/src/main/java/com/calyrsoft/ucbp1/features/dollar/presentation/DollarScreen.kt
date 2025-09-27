// DollarScreen.kt - ACTUALIZADO
package com.calyrsoft.ucbp1.features.dollar.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.calyrsoft.ucbp1.core.presentation.components.TopAppBarWithBack
import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DollarScreen(
    navController: NavController,
    viewModelDollar: DollarViewModel = koinViewModel()
) {
    val state by viewModelDollar.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBarWithBack(
                title = "Cotización del Dólar",
                navController = navController,
                actions = {
                    IconButton(
                        onClick = { viewModelDollar.loadHistory() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Actualizar",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            when (val stateValue = state) {
                is DollarViewModel.DollarUIState.Error -> {
                    Text(
                        text = stateValue.message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }

                DollarViewModel.DollarUIState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(32.dp)
                    )
                }

                is DollarViewModel.DollarUIState.Success -> {
                    ImprovedDollarCards(dollar = stateValue.data)

                    Spacer(modifier = Modifier.height(24.dp))

                    // Historial
                    Text(
                        text = "Historial de Cambios",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    DollarHistoryList(history = stateValue.history)
                }
            }
        }
    }
}

@Composable
fun ImprovedDollarCards(dollar: DollarModel) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE3F2FD), // Azul claro
                contentColor = Color(0xFF0D47A1) // Azul oscuro
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "💵 DÓLAR OFICIAL",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D47A1)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    DollarValueItemImproved(
                        title = "COMPRA",
                        value = String.format("%.2f", dollar.oficialCompra),
                        color = Color(0xFF1B5E20) // Verde
                    )

                    DollarValueItemImproved(
                        title = "VENTA",
                        value = String.format("%.2f", dollar.oficialVenta),
                        color = Color(0xFFB71C1C) // Rojo
                    )
                }
            }
        }

        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF3E5F5), // Morado claro
                contentColor = Color(0xFF4A148C) // Morado oscuro
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "DÓLAR PARALELO",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4A148C)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    DollarValueItemImproved(
                        title = "COMPRA",
                        value = String.format("%.2f", dollar.paraleloCompra),
                        color = Color(0xFF1B5E20) // Verde
                    )

                    DollarValueItemImproved(
                        title = "VENTA",
                        value = String.format("%.2f", dollar.paraleloVenta),
                        color = Color(0xFFB71C1C) // Rojo
                    )
                }
            }
        }

        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🕒 ",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Actualizado: ${dollar.fechaActualizacion}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun DollarValueItemImproved(title: String, value: String, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
            color = color.copy(alpha = 0.8f)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "$$value",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}

@Composable
fun DollarHistoryList(history: List<DollarModel>) {
    if (history.isEmpty()) {
        Text(
            text = "No hay historial disponible",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.padding(16.dp)
        )
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(history) { dollar ->
                ImprovedDollarHistoryItem(dollar = dollar)
            }
        }
    }
}

@Composable
fun ImprovedDollarHistoryItem(dollar: DollarModel) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = formatDate(dollar.timestamp),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Oficial
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Oficial Compra: $${String.format("%.2f", dollar.oficialCompra)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF1B5E20)
                )
                Text(
                    text = "Oficial Venta: $${String.format("%.2f", dollar.oficialVenta)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFB71C1C)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Paralelo
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Paralelo Compra: $${String.format("%.2f", dollar.paraleloCompra)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF1B5E20)
                )
                Text(
                    text = "Paralelo Venta: $${String.format("%.2f", dollar.paraleloVenta)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFB71C1C)
                )
            }
        }
    }
}

private fun getCurrentTime(): String {
    return SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
}

private fun formatDate(timestamp: Long): String {
    return try {
        SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(Date(timestamp))
    } catch (e: Exception) {
        "Fecha inválida"
    }
}