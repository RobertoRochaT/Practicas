package com.example.practicas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.BoxScope.align
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.practicas.ui.theme.PracticasTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticasTheme {
                Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    NFLApp()
                }
            }
        }
    }
}

@Composable
fun NFLApp() {
    var showSplash by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(2200)
        showSplash = false
    }

    AnimatedVisibility(
        visible = showSplash,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        SplashScreen()
    }

    AnimatedVisibility(
        visible = !showSplash,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        NFLMainScreen()
    }
}

@Composable
fun SplashScreen() {
    val scale = remember { Animatable(0.6f) }
    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
                        MaterialTheme.colorScheme.surface
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .scale(scale.value)
                    .size(160.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .border(
                        width = 4.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(24.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "NFL",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Stats & News",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Official-style NFL experience",
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

enum class BottomDestination(val route: String, val label: String, val icon: ImageVector) {
    Home("home", "Home", Icons.Default.Home),
    Matches("matches", "Matches", Icons.Default.Schedule),
    News("news", "News", Icons.Default.Article),
    Profile("profile", "Profile", Icons.Default.BarChart)
}

@Composable
fun NFLMainScreen() {
    val navController = rememberNavController()
    val destinations = remember { BottomDestination.entries.toList() }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = backStackEntry?.destination?.route
                destinations.forEach { destination ->
                    NavigationBarItem(
                        selected = currentRoute == destination.route,
                        onClick = {
                            navController.navigate(destination.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = destination.icon,
                                contentDescription = destination.label
                            )
                        },
                        label = { Text(destination.label) }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = BottomDestination.Home.route,
            modifier = Modifier
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            composable(BottomDestination.Home.route) {
                HomeScreen()
            }
            composable(BottomDestination.Matches.route) {
                MatchesScreen()
            }
            composable(BottomDestination.News.route) {
                NewsScreen()
            }
            composable(BottomDestination.Profile.route) {
                PlayerProfileScreen()
            }
        }
    }
}

data class LiveGame(
    val matchup: String,
    val score: String,
    val quarter: String,
    val detail: String
)

data class MatchStatistic(
    val statName: String,
    val home: String,
    val away: String
)

data class TeamHistory(val team: String, val highlight: String)

data class StandingRow(
    val team: String,
    val record: String,
    val points: Int
)

data class ScheduleMatch(
    val date: String,
    val game: String,
    val venue: String
)

data class NewsArticle(
    val headline: String,
    val summary: String,
    val timestamp: String
)

@Composable
fun HomeScreen() {
    val liveGames = listOf(
        LiveGame("Chiefs vs. Bills", "24 - 21", "Q4 02:31", "Mahomes finds Kelce for the go-ahead TD."),
        LiveGame("49ers vs. Eagles", "17 - 17", "Q3 06:12", "Defenses trading stops at the Linc.")
    )

    val matchStats = listOf(
        MatchStatistic("Total Yards", "375", "362"),
        MatchStatistic("Rushing Yards", "142", "121"),
        MatchStatistic("Third Down", "7/12", "6/13"),
        MatchStatistic("Penalties", "5 (42)", "8 (68)")
    )

    val teamHistory = listOf(
        TeamHistory("Green Bay Packers", "13 NFL Championships including 4 Super Bowls."),
        TeamHistory("New England Patriots", "Dynasty years with Brady & Belichick (6 Lombardis).")
    )

    val standings = listOf(
        StandingRow("Ravens", "11-3", 356),
        StandingRow("Dolphins", "10-4", 421),
        StandingRow("Jaguars", "9-5", 308)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            SectionHeader(title = "Live Scores", subtitle = "Real-time updates from every stadium")
            Spacer(modifier = Modifier.height(12.dp))
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                liveGames.forEach { game ->
                    LiveScoreCard(game)
                }
            }
        }

        item {
            SectionHeader(title = "Match Statistics", subtitle = "Breakdown of tonight's primetime clash")
            Spacer(modifier = Modifier.height(12.dp))
            StatsComparisonCard(matchStats)
        }

        item {
            SectionHeader(title = "Team History", subtitle = "Legacy moments across the league")
            Spacer(modifier = Modifier.height(12.dp))
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                teamHistory.forEach { history ->
                    HistoryCard(history)
                }
            }
        }

        item {
            SectionHeader(title = "Standings", subtitle = "AFC Conference leaders")
            Spacer(modifier = Modifier.height(12.dp))
            StandingsCard(standings)
        }
    }
}

@Composable
fun MatchesScreen() {
    val schedule = listOf(
        ScheduleMatch("Thu, Nov 21", "Steelers at Browns", "FirstEnergy Stadium"),
        ScheduleMatch("Sun, Nov 24", "Cowboys at Commanders", "FedExField"),
        ScheduleMatch("Mon, Nov 25", "Chiefs at Chargers", "SoFi Stadium"),
        ScheduleMatch("Thu, Nov 28", "Packers at Lions", "Ford Field")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            SectionHeader(title = "Match Schedule", subtitle = "Upcoming fixtures and venues")
        }
        items(schedule) { match ->
            ScheduleCard(match)
        }
    }
}

@Composable
fun NewsScreen() {
    val newsArticles = listOf(
        NewsArticle(
            headline = "Playoff picture intensifies with dramatic finishes",
            summary = "Overtime thrillers in the AFC reshaped the postseason race as contenders fought to the final whistle.",
            timestamp = "12m ago"
        ),
        NewsArticle(
            headline = "Rookie QB continues meteoric rise",
            summary = "The first-year sensation posted another 300-yard night, drawing comparisons to the league's elite passers.",
            timestamp = "32m ago"
        ),
        NewsArticle(
            headline = "Defensive juggernauts dominate Week 12",
            summary = "Three teams held opponents under 10 points behind relentless pass rushes and secondary play.",
            timestamp = "1h ago"
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            SectionHeader(title = "Top News", subtitle = "Breaking stories and analysis")
        }
        items(newsArticles) { article ->
            NewsCard(article)
        }
    }
}

@Composable
fun PlayerProfileScreen() {
    val seasonStats = listOf(78, 92, 105, 110, 124)
    val radarMetrics = mapOf(
        "Speed" to 8f,
        "Strength" to 7f,
        "Agility" to 9f,
        "Awareness" to 8.5f,
        "Endurance" to 7.5f
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            SectionHeader(title = "Player Profile", subtitle = "Justin Jefferson · WR · Minnesota Vikings")
        }
        item {
            PlayerInfoCard()
        }
        item {
            SectionHeader(title = "Season Performance", subtitle = "Receptions per season")
            Spacer(modifier = Modifier.height(12.dp))
            PerformanceLineChart(data = seasonStats, label = "Receptions")
        }
        item {
            SectionHeader(title = "Skill Radar", subtitle = "Scaled 1-10 across core attributes")
            Spacer(modifier = Modifier.height(12.dp))
            RadarChart(metrics = radarMetrics)
        }
    }
}

@Composable
fun SectionHeader(title: String, subtitle: String) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun LiveScoreCard(game: LiveGame) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f)),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = game.matchup,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = game.score,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = game.quarter,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = game.detail,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun StatsComparisonCard(stats: List<MatchStatistic>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f)),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Home", color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
                Text("Stat", color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
                Text("Away", color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(12.dp))
            stats.forEach { stat ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(stat.home, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium)
                    Text(stat.statName, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(stat.away, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

@Composable
fun HistoryCard(history: TeamHistory) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f)),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = history.team,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = history.highlight,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun StandingsCard(standings: List<StandingRow>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f)),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Team", color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
                Text("Record", color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
                Text("PF", color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
            }
            standings.forEach { row ->
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(row.team, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium)
                    Text(row.record, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(row.points.toString(), color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun ScheduleCard(match: ScheduleMatch) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f)),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(match.date, color = MaterialTheme.colorScheme.secondary)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = match.game,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = match.venue,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun NewsCard(article: NewsArticle) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f)),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = article.headline,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = article.summary,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = article.timestamp,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
fun PlayerInfoCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f)),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "JJ",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Justin Jefferson",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Appearances: 11 · TDs: 7 · Yards: 1,062",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatChip(label = "Targets", value = "108")
                StatChip(label = "Catch %", value = "71%")
                StatChip(label = "YAC", value = "427")
            }
        }
    }
}

@Composable
fun StatChip(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun PerformanceLineChart(data: List<Int>, label: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f)),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(16.dp))
            val maxValue = (data.maxOrNull() ?: 0).coerceAtLeast(1)
            val lineColor = MaterialTheme.colorScheme.primary
            Canvas(modifier = Modifier.fillMaxSize()) {
                val chartHeight = size.height
                val chartWidth = size.width
                val stepX = chartWidth / (data.size - 1).coerceAtLeast(1)

                val path = Path()
                data.forEachIndexed { index, value ->
                    val x = stepX * index
                    val y = chartHeight - (value / maxValue.toFloat()) * chartHeight
                    if (index == 0) {
                        path.moveTo(x, y)
                    } else {
                        path.lineTo(x, y)
                    }
                }

                drawPath(
                    path = path,
                    color = lineColor,
                    style = Stroke(width = 6f)
                )

                data.forEachIndexed { index, value ->
                    val x = stepX * index
                    val y = chartHeight - (value / maxValue.toFloat()) * chartHeight
                    drawCircle(
                        color = lineColor,
                        radius = 10f,
                        center = Offset(x, y)
                    )
                }
            }
        }
    }
}

@Composable
fun RadarChart(metrics: Map<String, Float>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f)),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            val entries = metrics.entries.toList()
            val gridColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)
            val primaryColor = MaterialTheme.colorScheme.primary
            val fillColor = primaryColor.copy(alpha = 0.25f)
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val radius = size.minDimension / 2.5f
                    val center = Offset(size.width / 2, size.height / 2)
                    val angleStep = (Math.PI * 2 / entries.size).toFloat()

                    // Draw grid
                    for (level in 1..5) {
                        val levelRadius = radius * (level / 5f)
                        val gridPath = Path()
                        entries.forEachIndexed { index, _ ->
                            val angle = angleStep * index - Math.PI.toFloat() / 2
                            val point = Offset(
                                x = center.x + levelRadius * kotlin.math.cos(angle),
                                y = center.y + levelRadius * kotlin.math.sin(angle)
                            )
                            if (index == 0) {
                                gridPath.moveTo(point.x, point.y)
                            } else {
                                gridPath.lineTo(point.x, point.y)
                            }
                        }
                        gridPath.close()
                        drawPath(
                            path = gridPath,
                            color = gridColor,
                            style = Stroke(width = 2f)
                        )
                    }

                    val dataPath = Path()
                    entries.forEachIndexed { index, entry ->
                        val normalized = (entry.value / 10f).coerceIn(0f, 1f)
                        val angle = angleStep * index - Math.PI.toFloat() / 2
                        val point = Offset(
                            x = center.x + radius * normalized * kotlin.math.cos(angle),
                            y = center.y + radius * normalized * kotlin.math.sin(angle)
                        )
                        if (index == 0) {
                            dataPath.moveTo(point.x, point.y)
                        } else {
                            dataPath.lineTo(point.x, point.y)
                        }
                        drawCircle(
                            color = primaryColor,
                            radius = 8f,
                            center = point
                        )
                    }
                    dataPath.close()

                    drawPath(
                        path = dataPath,
                        color = fillColor
                    )
                    drawPath(
                        path = dataPath,
                        color = primaryColor,
                        style = Stroke(width = 4f)
                    )
                }
                Column(
                    modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    entries.forEach { entry ->
                        Text(
                            text = "${entry.key}: ${entry.value}",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NFLAppPreview() {
    PracticasTheme(darkTheme = true, dynamicColor = false) {
        NFLApp()
    }
}
