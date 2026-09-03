package com.nakudin.techhausa.ui.components

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.nakudin.techhausa.model.Diagram
import com.nakudin.techhausa.ui.diagrams.InteractiveDiagram

/**
 * Renders a lesson's optional diagram: a static PNG from assets/images/
 * ("type": "image") or an interactive Compose diagram keyed by name
 * ("type": "interactive"), each with an optional Hausa caption underneath.
 */
@Composable
fun DiagramView(diagram: Diagram, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.padding(16.dp)) {
            when (diagram.type) {
                "image" -> diagram.asset?.let { assetName ->
                    val context = LocalContext.current
                    val bitmap = remember(assetName) {
                        runCatching {
                            context.assets.open("images/$assetName").use { BitmapFactory.decodeStream(it) }
                        }.getOrNull()
                    }
                    bitmap?.let {
                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = diagram.caption,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                "interactive" -> diagram.key?.let { key ->
                    InteractiveDiagram(key = key, modifier = Modifier.fillMaxWidth())
                }
            }
            diagram.caption?.let { caption ->
                Text(
                    caption,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
