package com.example.practicas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.practicas.ui.theme.PracticasTheme

enum class Conference(val displayName: String) {
    AFC("American Football Conference"),
    NFC("National Football Conference")
}

data class Team(
    val id: String,
    val name: String,
    val city: String,
    val conference: Conference,
    val abbreviation: String,
    val primaryColor: Color,
    val secondaryColor: Color,
    val textColor: Color,
    val established: Int,
    val championships: String,
    val history: String,
    val notablePlayers: List<String>
)

object TeamsRepository {
    private val teamsInternal = listOf(
        Team(
            id = "bills",
            name = "Bills",
            city = "Buffalo",
            conference = Conference.AFC,
            abbreviation = "BUF",
            primaryColor = Color(0xFF00338D),
            secondaryColor = Color(0xFFC60C30),
            textColor = Color.White,
            established = 1960,
            championships = "AFL titles in the early 1960s; four consecutive AFC Championships in the 1990s",
            history = "The Bills became synonymous with resilience during their run of four straight Super Bowl appearances in the early 1990s, led by Jim Kelly and Thurman Thomas. Recent seasons have featured Josh Allen's dynamic offense and a rabid fanbase braving Orchard Park winters.",
            notablePlayers = listOf("Josh Allen", "Stefon Diggs", "Jim Kelly")
        ),
        Team(
            id = "dolphins",
            name = "Dolphins",
            city = "Miami",
            conference = Conference.AFC,
            abbreviation = "MIA",
            primaryColor = Color(0xFF008E97),
            secondaryColor = Color(0xFFF58220),
            textColor = Color.White,
            established = 1966,
            championships = "Back-to-back Super Bowls VII & VIII including the NFL's only perfect season",
            history = "Miami authored the NFL's lone perfect season in 1972 under Don Shula. From the Killer B's defense to the Dan Marino air show, the Dolphins have been defined by offensive innovation and South Florida flair.",
            notablePlayers = listOf("Dan Marino", "Larry Csonka", "Tua Tagovailoa")
        ),
        Team(
            id = "patriots",
            name = "Patriots",
            city = "New England",
            conference = Conference.AFC,
            abbreviation = "NE",
            primaryColor = Color(0xFF002244),
            secondaryColor = Color(0xFFC60C30),
            textColor = Color.White,
            established = 1960,
            championships = "Six Super Bowl titles in the Brady-Belichick era",
            history = "The Patriots evolved from AFL upstarts to an NFL dynasty. Between 2001 and 2018, New England captured six Super Bowls with Tom Brady and Bill Belichick redefining sustained excellence in the salary-cap era.",
            notablePlayers = listOf("Tom Brady", "Rob Gronkowski", "Tedy Bruschi")
        ),
        Team(
            id = "jets",
            name = "Jets",
            city = "New York",
            conference = Conference.AFC,
            abbreviation = "NYJ",
            primaryColor = Color(0xFF125740),
            secondaryColor = Color(0xFF003F2D),
            textColor = Color.White,
            established = 1960,
            championships = "Super Bowl III upset under Broadway Joe Namath",
            history = "The Jets shocked the football world in Super Bowl III when Joe Namath's guarantee toppled the Colts. Gang Green continues to chase a return to glory, backed by one of the league's most passionate fanbases.",
            notablePlayers = listOf("Joe Namath", "Darrelle Revis", "Sauce Gardner")
        ),
        Team(
            id = "ravens",
            name = "Ravens",
            city = "Baltimore",
            conference = Conference.AFC,
            abbreviation = "BAL",
            primaryColor = Color(0xFF241773),
            secondaryColor = Color(0xFF9E7C0C),
            textColor = Color.White,
            established = 1996,
            championships = "Super Bowls XXXV and XLVII built on dominant defense",
            history = "Born from relocation, the Ravens quickly forged a defensive identity featuring Ray Lewis and Ed Reed. The Lamar Jackson era has married a creative offense with Baltimore's trademark toughness.",
            notablePlayers = listOf("Ray Lewis", "Ed Reed", "Lamar Jackson")
        ),
        Team(
            id = "bengals",
            name = "Bengals",
            city = "Cincinnati",
            conference = Conference.AFC,
            abbreviation = "CIN",
            primaryColor = Color(0xFFFB4F14),
            secondaryColor = Color(0xFF000000),
            textColor = Color.White,
            established = 1968,
            championships = "AFC Champions in 1981, 1988, and 2021",
            history = "Paul Brown's Bengals introduced iconic tiger stripes and high-powered offenses. After 1980s success, the franchise roared back with Joe Burrow and Ja'Marr Chase leading a new-age aerial attack.",
            notablePlayers = listOf("Joe Burrow", "Chad Johnson", "Anthony Muñoz")
        ),
        Team(
            id = "browns",
            name = "Browns",
            city = "Cleveland",
            conference = Conference.AFC,
            abbreviation = "CLE",
            primaryColor = Color(0xFF311D00),
            secondaryColor = Color(0xFFFB4F14),
            textColor = Color(0xFFF2F2F2),
            established = 1946,
            championships = "Four NFL Championships prior to the Super Bowl era",
            history = "Cleveland's storied franchise boasts Otto Graham, Jim Brown, and the Dawg Pound. After a relocation hiatus in the 1990s, the expansion Browns returned with fans as loyal as ever along Lake Erie.",
            notablePlayers = listOf("Jim Brown", "Myles Garrett", "Joe Thomas")
        ),
        Team(
            id = "steelers",
            name = "Steelers",
            city = "Pittsburgh",
            conference = Conference.AFC,
            abbreviation = "PIT",
            primaryColor = Color(0xFF000000),
            secondaryColor = Color(0xFFFFB612),
            textColor = Color(0xFFF8F9FA),
            established = 1933,
            championships = "Six Super Bowls; the Steel Curtain dynasty of the 1970s",
            history = "Known for the Terrible Towel and blue-collar ethos, Pittsburgh has collected six Lombardi Trophies. From the Steel Curtain to Big Ben Roethlisberger, toughness defines the black and gold.",
            notablePlayers = listOf("Terry Bradshaw", "Mean Joe Greene", "Ben Roethlisberger")
        ),
        Team(
            id = "texans",
            name = "Texans",
            city = "Houston",
            conference = Conference.AFC,
            abbreviation = "HOU",
            primaryColor = Color(0xFF03202F),
            secondaryColor = Color(0xFFA71930),
            textColor = Color.White,
            established = 2002,
            championships = "Multiple AFC South titles in the 2010s",
            history = "Houston re-entered the NFL in 2002 with the expansion Texans. Recent years have seen dynamic talents like J.J. Watt and C.J. Stroud ignite a young franchise searching for playoff breakthroughs.",
            notablePlayers = listOf("J.J. Watt", "Andre Johnson", "C.J. Stroud")
        ),
        Team(
            id = "colts",
            name = "Colts",
            city = "Indianapolis",
            conference = Conference.AFC,
            abbreviation = "IND",
            primaryColor = Color(0xFF003A70),
            secondaryColor = Color(0xFFFFFFFF),
            textColor = Color(0xFF002244),
            established = 1953,
            championships = "World champions in 1958, Super Bowl V and XLI",
            history = "The Colts' legacy stretches from Johnny Unitas to Peyton Manning. After relocating from Baltimore to Indianapolis, the horseshoe became synonymous with prolific passing attacks and steady success.",
            notablePlayers = listOf("Peyton Manning", "Marvin Harrison", "Jonathan Taylor")
        ),
        Team(
            id = "jaguars",
            name = "Jaguars",
            city = "Jacksonville",
            conference = Conference.AFC,
            abbreviation = "JAX",
            primaryColor = Color(0xFF006778),
            secondaryColor = Color(0xFFD7A22A),
            textColor = Color.White,
            established = 1995,
            championships = "AFC Championship Game appearances in 1996 and 2017",
            history = "An expansion success story, the Jaguars quickly reached the playoffs in the late 1990s. Duval County now rallies behind Trevor Lawrence and a roster built on speed and opportunistic defense.",
            notablePlayers = listOf("Tony Boselli", "Fred Taylor", "Trevor Lawrence")
        ),
        Team(
            id = "titans",
            name = "Titans",
            city = "Tennessee",
            conference = Conference.AFC,
            abbreviation = "TEN",
            primaryColor = Color(0xFF0C2340),
            secondaryColor = Color(0xFFA2AAAD),
            textColor = Color.White,
            established = 1960,
            championships = "AFL titles as the Oilers; Super Bowl XXXIV appearance",
            history = "Born as the Houston Oilers, the Titans electrified the league with the Music City Miracle and the Steve McNair era. Today Derrick Henry powers a bruising identity in Nashville.",
            notablePlayers = listOf("Steve McNair", "Eddie George", "Derrick Henry")
        ),
        Team(
            id = "broncos",
            name = "Broncos",
            city = "Denver",
            conference = Conference.AFC,
            abbreviation = "DEN",
            primaryColor = Color(0xFFFB4F14),
            secondaryColor = Color(0xFF002244),
            textColor = Color.White,
            established = 1960,
            championships = "Three Super Bowl titles including the 1997-98 back-to-back run",
            history = "Mile High magic has produced legendary quarterbacks from John Elway to Peyton Manning. Denver's orange crush defenses and thin-air home field have delivered multiple Lombardi trophies.",
            notablePlayers = listOf("John Elway", "Von Miller", "Terrell Davis")
        ),
        Team(
            id = "chiefs",
            name = "Chiefs",
            city = "Kansas City",
            conference = Conference.AFC,
            abbreviation = "KC",
            primaryColor = Color(0xFFE31837),
            secondaryColor = Color(0xFFFFFFFF),
            textColor = Color.White,
            established = 1960,
            championships = "Super Bowls IV, LIV, LVII, and LVIII",
            history = "The Chiefs reemerged as an NFL powerhouse under Andy Reid and Patrick Mahomes, blending creative offense with Arrowhead Stadium's deafening atmosphere. Their four Lombardis span the AFL era to the present day dynasty.",
            notablePlayers = listOf("Patrick Mahomes", "Travis Kelce", "Len Dawson")
        ),
        Team(
            id = "raiders",
            name = "Raiders",
            city = "Las Vegas",
            conference = Conference.AFC,
            abbreviation = "LV",
            primaryColor = Color(0xFF000000),
            secondaryColor = Color(0xFFA5ACAF),
            textColor = Color.White,
            established = 1960,
            championships = "Three Super Bowls under the Al Davis ethos",
            history = "From Oakland to Los Angeles and now Las Vegas, the Raiders built a renegade image backed by three Super Bowl victories. The silver and black remain iconic with a commitment to vertical passing and swagger.",
            notablePlayers = listOf("Marcus Allen", "Howie Long", "Davante Adams")
        ),
        Team(
            id = "chargers",
            name = "Chargers",
            city = "Los Angeles",
            conference = Conference.AFC,
            abbreviation = "LAC",
            primaryColor = Color(0xFF0080C6),
            secondaryColor = Color(0xFFFFD200),
            textColor = Color.White,
            established = 1960,
            championships = "1963 AFL champions; multiple AFC West titles",
            history = "With powder blue uniforms and high-flying offenses, the Chargers have showcased stars like LaDainian Tomlinson and Justin Herbert. The franchise continues to chase its first Super Bowl title from Hollywood.",
            notablePlayers = listOf("LaDainian Tomlinson", "Junior Seau", "Justin Herbert")
        ),
        Team(
            id = "cowboys",
            name = "Cowboys",
            city = "Dallas",
            conference = Conference.NFC,
            abbreviation = "DAL",
            primaryColor = Color(0xFF041E42),
            secondaryColor = Color(0xFF869397),
            textColor = Color.White,
            established = 1960,
            championships = "Five Super Bowls; America's Team brand",
            history = "The Cowboys became America's Team with a blend of glamour and success. Tom Landry's Doomsday Defense, the Triplets dynasty of the 1990s, and a global following define Dallas football.",
            notablePlayers = listOf("Troy Aikman", "Emmitt Smith", "Micah Parsons")
        ),
        Team(
            id = "giants",
            name = "Giants",
            city = "New York",
            conference = Conference.NFC,
            abbreviation = "NYG",
            primaryColor = Color(0xFF0B2265),
            secondaryColor = Color(0xFFA71930),
            textColor = Color.White,
            established = 1925,
            championships = "Eight NFL titles including four Super Bowls",
            history = "One of the NFL's charter franchises, the Giants boast iconic moments from the 1958 Greatest Game to helmet catches that toppled dynasties. Defense and grit are hallmarks of Big Blue.",
            notablePlayers = listOf("Lawrence Taylor", "Eli Manning", "Saquon Barkley")
        ),
        Team(
            id = "eagles",
            name = "Eagles",
            city = "Philadelphia",
            conference = Conference.NFC,
            abbreviation = "PHI",
            primaryColor = Color(0xFF004C54),
            secondaryColor = Color(0xFFACC0C6),
            textColor = Color.White,
            established = 1933,
            championships = "NFL champions in 1948-49, Super Bowl LII",
            history = "Philadelphia's passionate fanbase witnessed the Philly Special and the franchise's first Super Bowl in 2017. The Eagles' midnight green tradition pairs a fierce defense with creative offense.",
            notablePlayers = listOf("Reggie White", "Donovan McNabb", "Jalen Hurts")
        ),
        Team(
            id = "commanders",
            name = "Commanders",
            city = "Washington",
            conference = Conference.NFC,
            abbreviation = "WAS",
            primaryColor = Color(0xFF5A1414),
            secondaryColor = Color(0xFFFFB612),
            textColor = Color(0xFFF4F4F4),
            established = 1932,
            championships = "Three Super Bowls during the Gibbs era",
            history = "Washington's franchise has undergone reinvention while celebrating titles from the 1980s and early 90s. The Burgundy and Gold continue to spotlight defensive legends and a proud regional fanbase.",
            notablePlayers = listOf("John Riggins", "Darrell Green", "Terry McLaurin")
        ),
        Team(
            id = "bears",
            name = "Bears",
            city = "Chicago",
            conference = Conference.NFC,
            abbreviation = "CHI",
            primaryColor = Color(0xFF0B162A),
            secondaryColor = Color(0xFFC83803),
            textColor = Color.White,
            established = 1920,
            championships = "Nine NFL Championships including Super Bowl XX",
            history = "The Monsters of the Midway set defensive standards with legends like George Halas and Walter Payton. Soldier Field remains home to a storied franchise steeped in Midwestern toughness.",
            notablePlayers = listOf("Walter Payton", "Dick Butkus", "Brian Urlacher")
        ),
        Team(
            id = "lions",
            name = "Lions",
            city = "Detroit",
            conference = Conference.NFC,
            abbreviation = "DET",
            primaryColor = Color(0xFF0076B6),
            secondaryColor = Color(0xFFB0B7BC),
            textColor = Color.White,
            established = 1930,
            championships = "Four NFL Championships in the 1950s",
            history = "Detroit's Honolulu Blue tradition features Thanksgiving Day showcases and generational talents like Barry Sanders. Recent rebuilds have reinvigorated pride across the Motor City.",
            notablePlayers = listOf("Barry Sanders", "Calvin Johnson", "Amon-Ra St. Brown")
        ),
        Team(
            id = "packers",
            name = "Packers",
            city = "Green Bay",
            conference = Conference.NFC,
            abbreviation = "GB",
            primaryColor = Color(0xFF203731),
            secondaryColor = Color(0xFFFFB612),
            textColor = Color(0xFFF5F5F5),
            established = 1919,
            championships = "13 league titles including four Super Bowls",
            history = "Owned by their fans, the Packers transformed small-town Green Bay into Titletown. From Vince Lombardi to Brett Favre and Aaron Rodgers, excellence has been a Lambeau staple.",
            notablePlayers = listOf("Bart Starr", "Reggie White", "Aaron Rodgers")
        ),
        Team(
            id = "vikings",
            name = "Vikings",
            city = "Minnesota",
            conference = Conference.NFC,
            abbreviation = "MIN",
            primaryColor = Color(0xFF4F2683),
            secondaryColor = Color(0xFFFFC62F),
            textColor = Color.White,
            established = 1961,
            championships = "NFC champions in the 1970s; four Super Bowl appearances",
            history = "The Vikings' horn-blowing entrances accompany a history of dominant defenses and prolific offenses. Purple Pride fuels expectations for the franchise's first Lombardi Trophy.",
            notablePlayers = listOf("Fran Tarkenton", "Randy Moss", "Justin Jefferson")
        ),
        Team(
            id = "falcons",
            name = "Falcons",
            city = "Atlanta",
            conference = Conference.NFC,
            abbreviation = "ATL",
            primaryColor = Color(0xFFA71930),
            secondaryColor = Color(0xFF000000),
            textColor = Color.White,
            established = 1965,
            championships = "NFC champions in 1998 and 2016",
            history = "Atlanta football has featured the Dirty Bird era, Michael Vick's electric playmaking, and a 2016 Super Bowl run. The Falcons now blend speed and swagger in the heart of the South.",
            notablePlayers = listOf("Matt Ryan", "Julio Jones", "Jessie Bates III")
        ),
        Team(
            id = "panthers",
            name = "Panthers",
            city = "Carolina",
            conference = Conference.NFC,
            abbreviation = "CAR",
            primaryColor = Color(0xFF0085CA),
            secondaryColor = Color(0xFF101820),
            textColor = Color.White,
            established = 1995,
            championships = "NFC champions in 2003 and 2015",
            history = "Charlotte's Panthers reached the Super Bowl twice within two decades of existence. From Sam Mills' Keep Pounding legacy to Cam Newton's MVP season, the franchise is defined by resilience.",
            notablePlayers = listOf("Cam Newton", "Luke Kuechly", "Julius Peppers")
        ),
        Team(
            id = "saints",
            name = "Saints",
            city = "New Orleans",
            conference = Conference.NFC,
            abbreviation = "NO",
            primaryColor = Color(0xFFD3BC8D),
            secondaryColor = Color(0xFF101820),
            textColor = Color(0xFF101820),
            established = 1967,
            championships = "Super Bowl XLIV champions",
            history = "The Saints captured New Orleans' spirit with the 2009 Super Bowl triumph. Drew Brees and Sean Payton engineered record-setting offenses in the Superdome's electric atmosphere.",
            notablePlayers = listOf("Drew Brees", "Rickey Jackson", "Alvin Kamara")
        ),
        Team(
            id = "buccaneers",
            name = "Buccaneers",
            city = "Tampa Bay",
            conference = Conference.NFC,
            abbreviation = "TB",
            primaryColor = Color(0xFFD50A0A),
            secondaryColor = Color(0xFF0A1F44),
            textColor = Color.White,
            established = 1976,
            championships = "Super Bowls XXXVII and LV",
            history = "From the orange creamsicle era to a pirate ship stadium, Tampa Bay's franchise has evolved into a modern contender. Tom Brady's arrival delivered a second Lombardi on home turf.",
            notablePlayers = listOf("Derrick Brooks", "Warren Sapp", "Mike Evans")
        ),
        Team(
            id = "cardinals",
            name = "Cardinals",
            city = "Arizona",
            conference = Conference.NFC,
            abbreviation = "ARI",
            primaryColor = Color(0xFF97233F),
            secondaryColor = Color(0xFF000000),
            textColor = Color.White,
            established = 1898,
            championships = "NFL champions in 1925 and 1947; NFC champions in 2008",
            history = "The Cardinals are the NFL's oldest continuously run franchise, relocating from Chicago to St. Louis and now Arizona. Modern eras highlight Larry Fitzgerald's playoff heroics and desert sunsets in Glendale.",
            notablePlayers = listOf("Larry Fitzgerald", "Kurt Warner", "Budda Baker")
        ),
        Team(
            id = "rams",
            name = "Rams",
            city = "Los Angeles",
            conference = Conference.NFC,
            abbreviation = "LAR",
            primaryColor = Color(0xFF003594),
            secondaryColor = Color(0xFFFFD100),
            textColor = Color.White,
            established = 1936,
            championships = "Super Bowls XXXIV and LVI",
            history = "The Rams have starred in Cleveland, Los Angeles, and St. Louis, pioneering modern offenses under coaches like Kurt Warner's Greatest Show on Turf and Sean McVay's innovative schemes.",
            notablePlayers = listOf("Aaron Donald", "Kurt Warner", "Cooper Kupp")
        ),
        Team(
            id = "49ers",
            name = "49ers",
            city = "San Francisco",
            conference = Conference.NFC,
            abbreviation = "SF",
            primaryColor = Color(0xFFAA0000),
            secondaryColor = Color(0xFFB3995D),
            textColor = Color(0xFFF5F2E8),
            established = 1946,
            championships = "Five Super Bowls in the 1980s and 1990s",
            history = "Bill Walsh's West Coast offense and stars like Joe Montana, Jerry Rice, and Steve Young delivered five Lombardis. The modern 49ers blend physical defense with creative rushing attacks.",
            notablePlayers = listOf("Joe Montana", "Jerry Rice", "Christian McCaffrey")
        ),
        Team(
            id = "seahawks",
            name = "Seahawks",
            city = "Seattle",
            conference = Conference.NFC,
            abbreviation = "SEA",
            primaryColor = Color(0xFF002244),
            secondaryColor = Color(0xFF69BE28),
            textColor = Color.White,
            established = 1976,
            championships = "Super Bowl XLVIII champions",
            history = "The Seahawks' 12th Man crowd fuels one of the league's loudest stadiums. The Legion of Boom defense and Russell Wilson helped capture the franchise's first title in 2013.",
            notablePlayers = listOf("Russell Wilson", "Marshawn Lynch", "Richard Sherman")
        )
    )

    private val teamsById = teamsInternal.associateBy { it.id }

    fun getTeamsForConference(conference: Conference): List<Team> =
        teamsInternal.filter { it.conference == conference }

    fun getTeamById(id: String): Team? = teamsById[id]
}

sealed class Screen(val route: String) {
    data object ConferenceSelection : Screen("conference-selection")
    data object ConferenceTeams : Screen("conference-teams/{conference}") {
        fun createRoute(conference: Conference) = "conference-teams/${conference.name}"
    }
    data object TeamDetail : Screen("team-detail/{teamId}") {
        fun createRoute(teamId: String) = "team-detail/$teamId"
    }
}

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
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.ConferenceSelection.route
    ) {
        composable(Screen.ConferenceSelection.route) {
            ConferenceSelectionScreen(
                onConferenceSelected = { conference ->
                    navController.navigate(Screen.ConferenceTeams.createRoute(conference))
                }
            )
        }
        composable(
            Screen.ConferenceTeams.route,
            arguments = listOf(navArgument("conference") { type = NavType.StringType })
        ) { backStackEntry ->
            val conferenceName = backStackEntry.arguments?.getString("conference")
            val conference = conferenceName?.let { runCatching { Conference.valueOf(it) }.getOrNull() }
            if (conference != null) {
                val teams = remember(conference) { TeamsRepository.getTeamsForConference(conference) }
                ConferenceTeamsScreen(
                    conference = conference,
                    teams = teams,
                    onBack = { navController.popBackStack() },
                    onTeamSelected = { team ->
                        navController.navigate(Screen.TeamDetail.createRoute(team.id))
                    }
                )
            }
        }
        composable(
            Screen.TeamDetail.route,
            arguments = listOf(navArgument("teamId") { type = NavType.StringType })
        ) { backStackEntry ->
            val teamId = backStackEntry.arguments?.getString("teamId")
            val team = teamId?.let(TeamsRepository::getTeamById)
            if (team != null) {
                TeamDetailScreen(
                    team = team,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}

@Composable
fun ConferenceSelectionScreen(onConferenceSelected: (Conference) -> Unit) {
    val colorScheme = MaterialTheme.colorScheme
    val backgroundBrush = remember(colorScheme) {
        Brush.verticalGradient(
            listOf(
                colorScheme.surfaceVariant,
                colorScheme.background
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundBrush),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "NFL Conference Hub",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Tap a conference letter to explore every franchise",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ConferenceLetterButton(
                    letter = "A",
                    label = "AFC",
                    colors = listOf(Color(0xFFD50A0A), Color(0xFF7F0000)),
                    onClick = { onConferenceSelected(Conference.AFC) }
                )
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                listOf(MaterialTheme.colorScheme.surface, MaterialTheme.colorScheme.primary.copy(alpha = 0.35f))
                            )
                        )
                        .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "NFL",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                ConferenceLetterButton(
                    letter = "N",
                    label = "NFC",
                    colors = listOf(Color(0xFF0B51A1), Color(0xFF001F54)),
                    onClick = { onConferenceSelected(Conference.NFC) }
                )
            }
        }
    }
}

@Composable
fun ConferenceLetterButton(
    letter: String,
    label: String,
    colors: List<Color>,
    onClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .background(Brush.linearGradient(colors))
                .border(4.dp, Color.White.copy(alpha = 0.6f), CircleShape)
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = letter,
                fontSize = 64.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConferenceTeamsScreen(
    conference: Conference,
    teams: List<Team>,
    onBack: () -> Unit,
    onTeamSelected: (Team) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = conference.displayName, fontWeight = FontWeight.Bold)
                        Text(
                            text = "${teams.size} franchises",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(teams) { team ->
                TeamCard(team = team, onClick = { onTeamSelected(team) })
            }
            item {
                Spacer(modifier = Modifier.height(paddingValues.calculateBottomPadding()))
            }
        }
    }
}

@Composable
fun TeamCard(team: Team, onClick: () -> Unit) {
    val gradient = remember(team.primaryColor, team.secondaryColor) {
        Brush.linearGradient(listOf(team.primaryColor, team.secondaryColor))
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(gradient)
            .clickable(onClick = onClick)
            .padding(18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TeamLogo(team)
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "${team.city} ${team.name}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = team.textColor
            )
            Text(
                text = team.championships,
                style = MaterialTheme.typography.bodySmall,
                color = team.textColor.copy(alpha = 0.85f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun TeamLogo(team: Team) {
    val logoBrush = remember(team.primaryColor, team.secondaryColor) {
        Brush.radialGradient(listOf(team.secondaryColor, team.primaryColor))
    }
    Box(
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(logoBrush)
            .border(3.dp, Color.White.copy(alpha = 0.7f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = team.abbreviation,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = team.textColor
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamDetailScreen(team: Team, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "${team.city} ${team.name}", fontWeight = FontWeight.Bold)
                        Text(
                            text = "Established ${team.established}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(
                            Brush.linearGradient(listOf(team.primaryColor, team.secondaryColor))
                        )
                        .padding(24.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TeamLogo(team)
                        Text(
                            text = team.championships,
                            color = team.textColor,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "Franchise History",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = team.history,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.85f)
                    )
                }
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "Notable Players",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        team.notablePlayers.forEach { player ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(team.primaryColor)
                                )
                                Text(
                                    text = player,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(paddingValues.calculateBottomPadding()))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NFLAppPreview() {
    PracticasTheme {
        NFLApp()
    }
}
