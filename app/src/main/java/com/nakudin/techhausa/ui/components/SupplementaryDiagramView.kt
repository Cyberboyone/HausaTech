package com.nakudin.techhausa.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nakudin.techhausa.model.SupplementaryDiagram
import com.nakudin.techhausa.ui.theme.HausaTechColors
import com.nakudin.techhausa.ui.theme.HausaTechSpacing

@Composable
fun SupplementaryDiagramView(diagram: SupplementaryDiagram, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = HausaTechColors.SurfaceVariant),
        border = BorderStroke(1.dp, HausaTechColors.Outline)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                diagram.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = HausaTechColors.OnBackground
            )
            Spacer(Modifier.height(HausaTechSpacing.Sm))
            when (diagram.type) {
                "flow" -> FlowDiagram(diagram)
                "compare" -> CompareDiagram(diagram)
                "stack" -> StackDiagram(diagram)
                "hub" -> HubDiagram(diagram)
            }
            diagram.caption?.let { cap ->
                Spacer(Modifier.height(HausaTechSpacing.Sm))
                Text(
                    cap,
                    style = MaterialTheme.typography.labelSmall,
                    color = HausaTechColors.Muted
                )
            }
        }
    }
}

@Composable
private fun FlowDiagram(d: SupplementaryDiagram) {
    val steps = d.items ?: return
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        steps.forEachIndexed { i, step ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .width(28.dp)
                        .height(28.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(HausaTechColors.Accent),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "${i + 1}",
                        color = HausaTechColors.Background,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(Modifier.width(HausaTechSpacing.Sm))
                Text(
                    step,
                    style = MaterialTheme.typography.bodyMedium,
                    color = HausaTechColors.OnBackground,
                    modifier = Modifier.weight(1f)
                )
            }
            if (i < steps.lastIndex) {
                Box(
                    modifier = Modifier
                        .padding(start = 13.dp)
                        .width(2.dp)
                        .height(10.dp)
                        .background(HausaTechColors.Muted.copy(alpha = 0.3f))
                )
            }
        }
    }
}

@Composable
private fun CompareDiagram(d: SupplementaryDiagram) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        SideColumn(d.leftTitle, d.leftItems, Modifier.weight(1f))
        SideColumn(d.rightTitle, d.rightItems, Modifier.weight(1f))
    }
}

@Composable
private fun SideColumn(title: String?, items: List<String>?, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(HausaTechColors.Elevated)
            .padding(12.dp)
    ) {
        title?.let {
            Text(
                it,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = HausaTechColors.Accent
            )
            Spacer(Modifier.height(HausaTechSpacing.Xs))
        }
        items?.forEach { item ->
            Text(
                "• $item",
                style = MaterialTheme.typography.bodySmall,
                color = HausaTechColors.OnBackground,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

@Composable
private fun StackDiagram(d: SupplementaryDiagram) {
    val items = d.items ?: return
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        items.reversed().forEachIndexed { i, item ->
            val alpha = 0.4f + (items.size - i) * 0.12f
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(HausaTechColors.Accent.copy(alpha = alpha.coerceAtMost(1f)))
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    item,
                    style = MaterialTheme.typography.bodyMedium,
                    color = HausaTechColors.Background,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun HubDiagram(d: SupplementaryDiagram) {
    val center = d.center ?: return
    val satellites = d.satellites ?: return
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(14.dp))
                .background(HausaTechColors.Accent)
                .padding(horizontal = 24.dp, vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                center,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = HausaTechColors.Background
            )
        }
        Spacer(Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .width(2.dp)
                .height(12.dp)
                .background(HausaTechColors.Muted.copy(alpha = 0.3f))
        )
        Spacer(Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            satellites.forEach { sat ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(HausaTechColors.SurfaceVariant)
                        .border(1.dp, HausaTechColors.Outline, RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        sat,
                        style = MaterialTheme.typography.labelSmall,
                        color = HausaTechColors.Muted,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
