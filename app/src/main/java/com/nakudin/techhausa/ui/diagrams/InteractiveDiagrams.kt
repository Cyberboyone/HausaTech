package com.nakudin.techhausa.ui.diagrams

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Renders the interactive diagram matching [key], as referenced from a
 * lesson's Diagram.key field in the course JSON. Returns null (renders
 * nothing) for an unrecognized key so a typo in content doesn't crash the
 * app — worth checking for during content QA.
 */
@Composable
fun InteractiveDiagram(key: String, modifier: Modifier = Modifier) {
    when (key) {
        "network_layers" -> NetworkLayersDiagram(modifier)
        "neural_network" -> NeuralNetworkDiagram(modifier)
        else -> {}
    }
}

private data class LayerInfo(val title: String, val detail: String, val color: Color)

@Composable
private fun NetworkLayersDiagram(modifier: Modifier = Modifier) {
    val layers = remember {
        listOf(
            LayerInfo("Internet", "Duniyar waje - inda hare-hare ke iya fitowa", Color(0xFFEF9A9A)),
            LayerInfo("Firewall", "Yana toshe zirga-zirgar bayanai mai hatsari", Color(0xFFFFCC80)),
            LayerInfo("DMZ", "Sashin da ke rike servers da jama'a ke iya kaiwa", Color(0xFFFFF59D)),
            LayerInfo("Internal Network", "Cibiyar sadarwa ta ciki - bayanan kamfani", Color(0xFFA5D6A7)),
        )
    }
    var selected by remember { mutableIntStateOf(-1) }

    Column(modifier = modifier) {
        layers.forEachIndexed { index, layer ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 3.dp),
                colors = CardDefaults.cardColors(containerColor = layer.color),
                onClick = { selected = if (selected == index) -1 else index }
            ) {
                Column(Modifier.padding(12.dp)) {
                    Text(layer.title, style = MaterialTheme.typography.titleMedium)
                    AnimatedVisibility(visible = selected == index) {
                        Text(
                            layer.detail,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
        Text(
            "Danna kowane mataki don karin bayani",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

private data class NodeLayer(val label: String, val detail: String, val nodeCount: Int)

@Composable
private fun NeuralNetworkDiagram(modifier: Modifier = Modifier) {
    val layers = remember {
        listOf(
            NodeLayer("Input Layer", "Inda bayanai (misali pixel na hoto) ke shiga", 4),
            NodeLayer("Hidden Layer", "Inda ake sarrafa bayanai ta hanyar weights", 5),
            NodeLayer("Output Layer", "Inda amsa ko hasashe ke fitowa", 2),
        )
    }
    var selected by remember { mutableIntStateOf(-1) }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            layers.forEachIndexed { index, layer ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    repeat(layer.nodeCount) {
                        Box(
                            modifier = Modifier
                                .padding(3.dp)
                                .size(if (selected == index) 22.dp else 16.dp)
                                .clip(RoundedCornerShape(50))
                                .background(
                                    if (selected == index) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.secondary
                                )
                        )
                    }
                    Text(
                        layer.label,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            layers.forEachIndexed { index, _ ->
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .padding(2.dp),
                    onClick = { selected = if (selected == index) -1 else index }
                ) {
                    Text(
                        "Danna",
                        modifier = Modifier.padding(6.dp),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
        AnimatedVisibility(visible = selected in layers.indices) {
            if (selected in layers.indices) {
                Text(
                    layers[selected].detail,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
