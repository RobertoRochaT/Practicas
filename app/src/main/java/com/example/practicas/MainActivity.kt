package com.example.practicas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Leaderboard
import androidx.compose.material.icons.rounded.SportsFootball
import androidx.compose.material.icons.rounded.Upcoming
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.practicas.R
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

    fun getAllTeams(): List<Team> = teamsInternal
}

private fun playerSlug(name: String): String = name
    .lowercase()
    .replace("á", "a")
    .replace("é", "e")
    .replace("í", "i")
    .replace("ó", "o")
    .replace("ú", "u")
    .replace("ñ", "n")
    .replace("&", "and")
    .replace("'", "")
    .replace(".", "")
    .replace(" ", "-")

data class PlayerProfile(
    val id: String,
    val name: String,
    val teamId: String,
    val position: String,
    val awards: String,
    val biography: String,
    val headshotRes: Int = R.drawable.nfl
)

data class GoatPlayer(
    val rank: Int,
    val name: String,
    val position: String,
    val debutYear: Int,
    val teamId: String
)

data class GoatTeam(
    val rank: Int,
    val name: String,
    val season: String,
    val teamId: String
)

enum class GameStatus { Final, Upcoming }

data class DivisionStanding(
    val teamId: String,
    val record: String,
    val trend: String
)

data class Game(
    val id: String,
    val homeTeamId: String,
    val awayTeamId: String,
    val status: GameStatus,
    val homeScore: Int?,
    val awayScore: Int?,
    val date: String,
    val location: String,
    val homeRecord: String,
    val awayRecord: String,
    val odds: String,
    val keyStats: List<Pair<String, String>>,
    val divisionStandings: List<DivisionStanding>
)

data class StatRow(
    val label: String,
    val values: List<String>
)

data class StatCategory(
    val title: String,
    val headers: List<String>,
    val rows: List<StatRow>
)

object LogoAssets {
    private val teamLogos = mapOf(
        "bills" to R.drawable.buffalo_bills,
        "dolphins" to R.drawable.miami_dolphins,
        "patriots" to R.drawable.new_england_patriots,
        "jets" to R.drawable.new_york_jets,
        "ravens" to R.drawable.baltimore_ravens,
        "bengals" to R.drawable.cincinnati_bengals,
        "browns" to R.drawable.cleveland_browns,
        "steelers" to R.drawable.pittsburgh_steelers,
        "texans" to R.drawable.houston_texans,
        "colts" to R.drawable.indianapolis_colts,
        "jaguars" to R.drawable.jacksonville_jaguars,
        "titans" to R.drawable.tennessee_titans,
        "chiefs" to R.drawable.kansas_city_chiefs,
        "chargers" to R.drawable.san_diego_chargers,
        "cowboys" to R.drawable.dallas_cowboys,
        "giants" to R.drawable.new_york_giants,
        "eagles" to R.drawable.philadelphia_eagles,
        "commanders" to R.drawable.washington_commanders,
        "bears" to R.drawable.chicago_bears,
        "lions" to R.drawable.detroit_lions,
        "packers" to R.drawable.green_bay_packers,
        "vikings" to R.drawable.minnesota_vikings,
        "saints" to R.drawable.new_orleans_saints,
        "buccaneers" to R.drawable.tampa_bay_buccaneers,
        "cardinals" to R.drawable.arizona_cardinals,
        "rams" to R.drawable.st_louis_rams,
        "49ers" to R.drawable.san_francisco_49ers,
        "seahawks" to R.drawable.seattle_seahawks
    )

    fun teamLogo(teamId: String): Int = teamLogos[teamId] ?: R.drawable.nfl
}

object PlayersRepository {
    private val featuredProfiles = listOf(
        PlayerProfile(
            id = playerSlug("Tom Brady"),
            name = "Tom Brady",
            teamId = "patriots",
            position = "QB",
            awards = "7× Super Bowl, 3× MVP, 5× Super Bowl MVP",
            biography = "Tom Brady redefinió la dinastía de los New England Patriots y estableció la vara de la longevidad en la NFL.",
            headshotRes = R.drawable.new_england_patriots
        ),
        PlayerProfile(
            id = playerSlug("Patrick Mahomes"),
            name = "Patrick Mahomes",
            teamId = "chiefs",
            position = "QB",
            awards = "2× MVP, 3× Campeón del Super Bowl",
            biography = "Mahomes combina creatividad y brazo élite para mantener a los Chiefs como la ofensiva más explosiva de la liga.",
            headshotRes = R.drawable.kansas_city_chiefs
        ),
        PlayerProfile(
            id = playerSlug("Jerry Rice"),
            name = "Jerry Rice",
            teamId = "49ers",
            position = "WR",
            awards = "3× Super Bowl, Líder histórico en recepciones y yardas",
            biography = "Jerry Rice dominó la posición de receptor abierto con disciplina y química perfecta con Joe Montana y Steve Young.",
            headshotRes = R.drawable.san_francisco_49ers
        ),
        PlayerProfile(
            id = playerSlug("Lawrence Taylor"),
            name = "Lawrence Taylor",
            teamId = "giants",
            position = "LB",
            awards = "2× Super Bowl, MVP 1986, 10× Pro Bowl",
            biography = "LT revolucionó la defensiva moderna con agresividad inigualable y redefinió el rol del pass rusher.",
            headshotRes = R.drawable.new_york_giants
        ),
        PlayerProfile(
            id = playerSlug("Walter Payton"),
            name = "Walter Payton",
            teamId = "bears",
            position = "RB",
            awards = "MVP 1977, Super Bowl XX, 9× Pro Bowl",
            biography = "Sweetness combinó resistencia, elegancia y corazón para liderar a los Bears dentro y fuera del campo.",
            headshotRes = R.drawable.chicago_bears
        ),
        PlayerProfile(
            id = playerSlug("Ray Lewis"),
            name = "Ray Lewis",
            teamId = "ravens",
            position = "LB",
            awards = "2× Super Bowl, 2× Defensive POY",
            biography = "Ray Lewis dio identidad a los Ravens con liderazgo vocal y ferocidad en cada snap.",
            headshotRes = R.drawable.baltimore_ravens
        ),
        PlayerProfile(
            id = playerSlug("Peyton Manning"),
            name = "Peyton Manning",
            teamId = "colts",
            position = "QB",
            awards = "5× MVP, 2× Campeón del Super Bowl",
            biography = "Manning llevó la ofensiva cerebral de los Colts a niveles históricos con precisión quirúrgica.",
            headshotRes = R.drawable.indianapolis_colts
        ),
        PlayerProfile(
            id = playerSlug("Barry Sanders"),
            name = "Barry Sanders",
            teamId = "lions",
            position = "RB",
            awards = "MVP 1997, 10× Pro Bowl",
            biography = "Los cortes imposibles de Barry Sanders encendieron a Detroit y lo convirtieron en mito viviente.",
            headshotRes = R.drawable.detroit_lions
        ),
        PlayerProfile(
            id = playerSlug("Aaron Donald"),
            name = "Aaron Donald",
            teamId = "rams",
            position = "DL",
            awards = "Super Bowl LVI, 3× Defensive POY",
            biography = "Aaron Donald domina las trincheras con fuerza bruta y técnica impecable desde el interior defensivo.",
            headshotRes = R.drawable.st_louis_rams
        ),
        PlayerProfile(
            id = playerSlug("Josh Allen"),
            name = "Josh Allen",
            teamId = "bills",
            position = "QB",
            awards = "2× Pro Bowl, Jugador Ofensivo AFC",
            biography = "Allen mezcla físico imponente y brazo láser para encender a Bills Mafia en cada jugada.",
            headshotRes = R.drawable.buffalo_bills
        ),
        PlayerProfile(
            id = playerSlug("Justin Jefferson"),
            name = "Justin Jefferson",
            teamId = "vikings",
            position = "WR",
            awards = "Ofensivo del Año 2022, 3× Pro Bowl",
            biography = "JJettas convirtió el griddy en símbolo de una nueva generación de receptores dominantes en Minnesota.",
            headshotRes = R.drawable.minnesota_vikings
        )
    ).associateBy { it.id }

    fun highlightsFor(team: Team): List<PlayerProfile> = team.notablePlayers.map { name ->
        val slug = playerSlug(name)
        featuredProfiles[slug] ?: createGenericProfile(team, name, slug)
    }

    fun getProfileById(teamId: String, playerId: String): PlayerProfile? {
        val team = TeamsRepository.getTeamById(teamId) ?: return null
        val featured = featuredProfiles[playerId]
        if (featured != null) {
            return if (featured.teamId == teamId) featured else featured.copy(teamId = teamId)
        }
        val playerName = team.notablePlayers.firstOrNull { playerSlug(it) == playerId }
            ?: return null
        return createGenericProfile(team, playerName, playerId)
    }

    private fun createGenericProfile(team: Team, name: String, slug: String): PlayerProfile =
        PlayerProfile(
            id = slug,
            name = name,
            teamId = team.id,
            position = "Figura histórica",
            awards = "Premios múltiples y legado en ${team.city}",
            biography = "$name dejó huella en los ${team.city} ${team.name} con actuaciones decisivas y liderazgo ejemplar."
        )
}

object GamesRepository {
    private val games = listOf(
        Game(
            id = "chiefs-bills-divisional",
            homeTeamId = "chiefs",
            awayTeamId = "bills",
            status = GameStatus.Final,
            homeScore = 27,
            awayScore = 24,
            date = "Dom, 21 Ene · Arrowhead Stadium",
            location = "Kansas City, MO",
            homeRecord = "12-5",
            awayRecord = "13-4",
            odds = "KC -2.5 · O/U 48.5",
            keyStats = listOf(
                "Patrick Mahomes" to "312 YDS · 3 TD",
                "Josh Allen" to "289 YDS · 2 TD",
                "Total Yards" to "KC 421 - BUF 407"
            ),
            divisionStandings = listOf(
                DivisionStanding("chiefs", "12-5", "▲1"),
                DivisionStanding("broncos", "8-9", "="),
                DivisionStanding("raiders", "6-11", "▼1"),
                DivisionStanding("chargers", "5-12", "▼1")
            )
        ),
        Game(
            id = "49ers-ravens-superbowl",
            homeTeamId = "49ers",
            awayTeamId = "ravens",
            status = GameStatus.Final,
            homeScore = 31,
            awayScore = 28,
            date = "Dom, 11 Feb · Allegiant Stadium",
            location = "Las Vegas, NV",
            homeRecord = "14-4",
            awayRecord = "15-3",
            odds = "SF -1.5 · O/U 47.0",
            keyStats = listOf(
                "Christian McCaffrey" to "142 YDS Totales · 2 TD",
                "Lamar Jackson" to "3 TD Totales",
                "Turnovers" to "SF 1 - BAL 2"
            ),
            divisionStandings = listOf(
                DivisionStanding("49ers", "13-4", "▲1"),
                DivisionStanding("seahawks", "9-8", "="),
                DivisionStanding("rams", "9-8", "▲1"),
                DivisionStanding("cardinals", "4-13", "▼1")
            )
        ),
        Game(
            id = "jets-dolphins-week1",
            homeTeamId = "jets",
            awayTeamId = "dolphins",
            status = GameStatus.Upcoming,
            homeScore = null,
            awayScore = null,
            date = "Dom, 8 Sep · 13:00 ET",
            location = "MetLife Stadium",
            homeRecord = "0-0",
            awayRecord = "0-0",
            odds = "MIA -1.5 · O/U 46.0",
            keyStats = listOf(
                "Claves" to "Reaparición de Aaron Rodgers",
                "Duelo" to "Hill vs Sauce Gardner",
                "Clima" to "Prob. lluvia ligera"
            ),
            divisionStandings = listOf(
                DivisionStanding("bills", "0-0", "-"),
                DivisionStanding("dolphins", "0-0", "-"),
                DivisionStanding("jets", "0-0", "-"),
                DivisionStanding("patriots", "0-0", "-")
            )
        ),
        Game(
            id = "cowboys-eagles-week4",
            homeTeamId = "cowboys",
            awayTeamId = "eagles",
            status = GameStatus.Upcoming,
            homeScore = null,
            awayScore = null,
            date = "Dom, 29 Sep · 20:20 ET",
            location = "AT&T Stadium",
            homeRecord = "0-0",
            awayRecord = "0-0",
            odds = "EVEN · O/U 49.5",
            keyStats = listOf(
                "Duelo" to "Prescott vs Hurts",
                "Ritmo" to "Dallas 31.2 PPG en casa",
                "Clave" to "Pass rush Eagles"
            ),
            divisionStandings = listOf(
                DivisionStanding("eagles", "0-0", "-"),
                DivisionStanding("cowboys", "0-0", "-"),
                DivisionStanding("giants", "0-0", "-"),
                DivisionStanding("commanders", "0-0", "-")
            )
        )
    )

    fun gamesByStatus(status: GameStatus): List<Game> = games.filter { it.status == status }

    fun getGameById(id: String): Game? = games.firstOrNull { it.id == id }
}

object GoatRankingsRepository {
    val goatPlayers = listOf(
        GoatPlayer(1, "Tom Brady", "QB", 2000, "patriots"),
        GoatPlayer(2, "Jerry Rice", "WR", 1985, "49ers"),
        GoatPlayer(3, "Lawrence Taylor", "LB", 1981, "giants"),
        GoatPlayer(4, "Walter Payton", "RB", 1975, "bears"),
        GoatPlayer(5, "Peyton Manning", "QB", 1998, "colts"),
        GoatPlayer(6, "Ray Lewis", "LB", 1996, "ravens"),
        GoatPlayer(7, "Joe Montana", "QB", 1979, "49ers"),
        GoatPlayer(8, "Deion Sanders", "CB", 1989, "cowboys"),
        GoatPlayer(9, "Reggie White", "DE", 1984, "packers"),
        GoatPlayer(10, "Barry Sanders", "RB", 1989, "lions")
    )

    val goatTeams = listOf(
        GoatTeam(1, "Chicago Bears", "1985", "bears"),
        GoatTeam(2, "New England Patriots", "2007", "patriots"),
        GoatTeam(3, "San Francisco 49ers", "1989", "49ers"),
        GoatTeam(4, "Pittsburgh Steelers", "1978", "steelers"),
        GoatTeam(5, "Kansas City Chiefs", "2022", "chiefs"),
        GoatTeam(6, "Dallas Cowboys", "1993", "cowboys"),
        GoatTeam(7, "Baltimore Ravens", "2000", "ravens"),
        GoatTeam(8, "Denver Broncos", "1998", "broncos"),
        GoatTeam(9, "New York Giants", "1990", "giants"),
        GoatTeam(10, "Green Bay Packers", "1966", "packers")
    )
}

object StatsRepository {
    val categories = listOf(
        StatCategory(
            title = "Passing",
            headers = listOf("Jugador", "TD", "INT", "YDS", "% Comp"),
            rows = listOf(
                StatRow("Patrick Mahomes (KC)", listOf("38", "9", "4,950", "67.4")),
                StatRow("Joe Burrow (CIN)", listOf("34", "10", "4,385", "69.2")),
                StatRow("Josh Allen (BUF)", listOf("36", "14", "4,720", "65.1")),
                StatRow("Tua Tagovailoa (MIA)", listOf("33", "9", "4,534", "68.8"))
            )
        ),
        StatCategory(
            title = "Rushing",
            headers = listOf("Jugador", "TD", "YDS", "AVG", "Carries"),
            rows = listOf(
                StatRow("Derrick Henry (TEN)", listOf("16", "1,445", "4.7", "307")),
                StatRow("Christian McCaffrey (SF)", listOf("14", "1,380", "4.9", "281")),
                StatRow("Nick Chubb (CLE)", listOf("12", "1,328", "5.2", "256")),
                StatRow("Saquon Barkley (NYG)", listOf("10", "1,242", "4.6", "270"))
            )
        ),
        StatCategory(
            title = "Receiving",
            headers = listOf("Jugador", "REC", "YDS", "TD", "YAC"),
            rows = listOf(
                StatRow("Justin Jefferson (MIN)", listOf("122", "1,809", "10", "598")),
                StatRow("Tyreek Hill (MIA)", listOf("119", "1,732", "9", "611")),
                StatRow("Ja'Marr Chase (CIN)", listOf("108", "1,421", "12", "512")),
                StatRow("CeeDee Lamb (DAL)", listOf("105", "1,357", "9", "482"))
            )
        ),
        StatCategory(
            title = "Defense",
            headers = listOf("Jugador", "Tackles", "Sacks", "INT", "FF"),
            rows = listOf(
                StatRow("Micah Parsons (DAL)", listOf("87", "15.5", "2", "3")),
                StatRow("T.J. Watt (PIT)", listOf("74", "18.0", "1", "5")),
                StatRow("Fred Warner (SF)", listOf("118", "6.5", "3", "2")),
                StatRow("Jalen Ramsey (MIA)", listOf("76", "2.0", "5", "3"))
            )
        )
    )
}

sealed class RootDestination(val baseRoute: String, val icon: ImageVector, val label: String) {
    data object Home : RootDestination("home", Icons.Rounded.Home, "Home")
    data object Teams : RootDestination("teams", Icons.Rounded.SportsFootball, "Equipos")
    data object Games : RootDestination("games", Icons.Rounded.Upcoming, "Partidos")
    data object Rankings : RootDestination("rankings", Icons.Rounded.Leaderboard, "GOAT")
    data object Stats : RootDestination("stats", Icons.Rounded.BarChart, "Stats")
}

val RootDestinations = listOf(
    RootDestination.Home,
    RootDestination.Teams,
    RootDestination.Games,
    RootDestination.Rankings,
    RootDestination.Stats
)

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
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route?.substringBefore("?")
    val showBottomBar = backStackEntry?.destination?.hierarchy?.any { destination ->
        val base = destination.route?.substringBefore("?")
        RootDestinations.any { it.baseRoute == base }
    } == true

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f)) {
                    RootDestinations.forEach { destination ->
                        val selected = currentRoute == destination.baseRoute
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(destination.baseRoute) {
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
                            label = {
                                Text(text = destination.label)
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = RootDestination.Home.baseRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(RootDestination.Home.baseRoute) {
                HomeScreen(
                    onConferenceSelected = { conference ->
                        navController.navigate("${RootDestination.Teams.baseRoute}?conference=${conference.name}")
                    },
                    onExploreGames = {
                        navController.navigate(RootDestination.Games.baseRoute)
                    },
                    onExploreRankings = {
                        navController.navigate(RootDestination.Rankings.baseRoute)
                    }
                )
            }
            composable(
                route = "${RootDestination.Teams.baseRoute}?conference={conference}",
                arguments = listOf(navArgument("conference") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                })
            ) { backStack ->
                val conference = backStack.arguments?.getString("conference")
                    ?.let { runCatching { Conference.valueOf(it) }.getOrNull() }
                    ?: Conference.AFC
                TeamsScreen(
                    initialConference = conference,
                    onTeamSelected = { team ->
                        navController.navigate("team/${team.id}")
                    },
                    onPlayerSelected = { team, player ->
                        navController.navigate("player/${team.id}/${player.id}")
                    }
                )
            }
            composable(RootDestination.Games.baseRoute) {
                GamesScreen(
                    onGameSelected = { game ->
                        navController.navigate("game/${game.id}")
                    }
                )
            }
            composable(RootDestination.Rankings.baseRoute) {
                RankingsScreen(onTeamSelected = { teamId ->
                    navController.navigate("team/$teamId")
                })
            }
            composable(RootDestination.Stats.baseRoute) {
                StatsScreen()
            }
            composable(
                route = "team/{teamId}",
                arguments = listOf(navArgument("teamId") { type = NavType.StringType })
            ) { backStack ->
                val teamId = backStack.arguments?.getString("teamId")
                val team = teamId?.let(TeamsRepository::getTeamById)
                if (team != null) {
                    TeamDetailScreen(
                        team = team,
                        onPlayerSelected = { player ->
                            navController.navigate("player/${team.id}/${player.id}")
                        },
                        onBack = { navController.popBackStack() }
                    )
                }
            }
            composable(
                route = "player/{teamId}/{playerId}",
                arguments = listOf(
                    navArgument("teamId") { type = NavType.StringType },
                    navArgument("playerId") { type = NavType.StringType }
                )
            ) { backStack ->
                val teamId = backStack.arguments?.getString("teamId")
                val playerId = backStack.arguments?.getString("playerId")
                val profile = if (teamId != null && playerId != null) {
                    PlayersRepository.getProfileById(teamId, playerId)
                } else null
                val team = teamId?.let(TeamsRepository::getTeamById)
                if (profile != null && team != null) {
                    PlayerDetailScreen(
                        team = team,
                        profile = profile,
                        onBack = { navController.popBackStack() }
                    )
                }
            }
            composable(
                route = "game/{gameId}",
                arguments = listOf(navArgument("gameId") { type = NavType.StringType })
            ) { backStack ->
                val gameId = backStack.arguments?.getString("gameId")
                val game = gameId?.let(GamesRepository::getGameById)
                if (game != null) {
                    GameDetailScreen(
                        game = game,
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    onConferenceSelected: (Conference) -> Unit,
    onExploreGames: () -> Unit,
    onExploreRankings: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = 48.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "NFL Universe",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Explora conferencias, leyendas y datos con estilo Apple Sports + NBA 2K.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f)
                    )
                }
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(360.dp)
                    .clip(RoundedCornerShape(36.dp))
                ) {
                    Column(Modifier.fillMaxSize()) {
                        ConferenceHeroCard(
                            title = "AFC",
                            subtitle = "American Football Conference",
                            gradient = Brush.verticalGradient(listOf(Color(0xFF8B0000), Color(0xFF360000))),
                            logoRes = R.drawable.afc,
                            alignment = Alignment.Center,
                            onClick = { onConferenceSelected(Conference.AFC) },
                            modifier = Modifier.weight(1f)
                        )
                        ConferenceHeroCard(
                            title = "NFC",
                            subtitle = "National Football Conference",
                            gradient = Brush.verticalGradient(listOf(Color(0xFF0A2A6B), Color(0xFF001233))),
                            logoRes = R.drawable.nfc_logo,
                            alignment = Alignment.Center,
                            onClick = { onConferenceSelected(Conference.NFC) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.nfl),
                        contentDescription = "NFL shield",
                        modifier = Modifier
                            .size(120.dp)
                            .align(Alignment.Center)
                    )
                }
            }
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = "Accesos rápidos",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    QuickActionButton(label = "Partidos", description = "Resultados y próximos duelos", onClick = onExploreGames)
                    QuickActionButton(label = "GOAT Charts", description = "Ranking histórico", onClick = onExploreRankings)
                }
            }
        }
    }
}

@Composable
private fun RowScope.QuickActionButton(label: String, description: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .weight(1f)
            .height(120.dp)
            .clip(RoundedCornerShape(24.dp))
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ConferenceHeroCard(
    title: String,
    subtitle: String,
    gradient: Brush,
    logoRes: Int,
    alignment: Alignment,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(36.dp))
            .background(gradient)
            .clickable(onClick = onClick),
        contentAlignment = alignment
    ) {
        Image(
            painter = painterResource(id = logoRes),
            contentDescription = "$title logo",
            modifier = Modifier.size(140.dp)
        )
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(24.dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Black)
            Text(text = subtitle, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun TeamsScreen(
    initialConference: Conference,
    onTeamSelected: (Team) -> Unit,
    onPlayerSelected: (Team, PlayerProfile) -> Unit
) {
    var selectedConference by remember { mutableStateOf(initialConference) }
    LaunchedEffect(initialConference) {
        selectedConference = initialConference
    }
    val teams = remember(selectedConference) { TeamsRepository.getTeamsForConference(selectedConference) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Franquicias ${selectedConference.name}",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
        )
        TabRow(
            selectedTabIndex = Conference.entries.indexOf(selectedConference),
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            Conference.entries.forEach { conference ->
                val selected = conference == selectedConference
                Tab(
                    selected = selected,
                    onClick = { selectedConference = conference },
                    text = {
                        Text(
                            text = conference.name,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(teams) { team ->
                TeamCard(
                    team = team,
                    onClick = { onTeamSelected(team) },
                    onPlayerSelected = { profile -> onPlayerSelected(team, profile) }
                )
            }
        }
    }
}

@Composable
fun TeamCard(
    team: Team,
    onClick: () -> Unit,
    onPlayerSelected: (PlayerProfile) -> Unit
) {
    val gradient = remember(team.primaryColor, team.secondaryColor) {
        Brush.linearGradient(listOf(team.primaryColor, team.secondaryColor))
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TeamLogo(team = team, size = 72.dp)
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "${team.city} ${team.name}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = team.championships,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(gradient)
                    .padding(16.dp)
            ) {
                Text(
                    text = team.history,
                    style = MaterialTheme.typography.bodySmall,
                    color = team.textColor,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Jugadores insignia",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                PlayersRepository.highlightsFor(team).forEach { profile ->
                    PlayerPill(profile = profile, onClick = { onPlayerSelected(profile) })
                }
            }
        }
    }
}

@Composable
fun TeamLogo(team: Team, size: Dp = 96.dp) {
    val logoRes = remember(team.id) { LogoAssets.teamLogo(team.id) }
    Card(
        shape = CircleShape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.size(size)
    ) {
        Image(
            painter = painterResource(id = logoRes),
            contentDescription = "${team.name} logo",
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Composable
private fun PlayerPill(profile: PlayerProfile, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(50))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = profile.name, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
            Text(
                text = profile.position,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = "Ver historia",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
            textDecoration = TextDecoration.Underline
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamDetailScreen(
    team: Team,
    onPlayerSelected: (PlayerProfile) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "${team.city} ${team.name}", fontWeight = FontWeight.Bold)
                        Text(
                            text = "Desde ${team.established}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
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
                        .clip(RoundedCornerShape(32.dp))
                        .background(Brush.linearGradient(listOf(team.primaryColor, team.secondaryColor)))
                        .padding(24.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        TeamLogo(team = team)
                        Text(
                            text = team.championships,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = team.textColor,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(text = "Historia de la franquicia", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                    Text(
                        text = team.history,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.9f)
                    )
                }
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(text = "Jugadores destacados", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                    PlayersRepository.highlightsFor(team).forEach { profile ->
                        PlayerPill(profile = profile, onClick = { onPlayerSelected(profile) })
                    }
                }
            }
            item {
                Divider()
            }
            item {
                Text(
                    text = "Colores icónicos",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    ColorSwatch(team.primaryColor, "Primario")
                    ColorSwatch(team.secondaryColor, "Secundario")
                }
            }
            item {
                Spacer(modifier = Modifier.height(paddingValues.calculateBottomPadding()))
            }
        }
    }
}

@Composable
private fun ColorSwatch(color: Color, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(color)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
fun PlayerDetailScreen(team: Team, profile: PlayerProfile, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(28.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TeamLogo(team = team, size = 100.dp)
                Text(text = profile.name, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Text(text = profile.position, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(
                    text = profile.awards,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = profile.biography,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun GamesScreen(onGameSelected: (Game) -> Unit) {
    val finals = remember { GamesRepository.gamesByStatus(GameStatus.Final) }
    val upcoming = remember { GamesRepository.gamesByStatus(GameStatus.Upcoming) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Text(text = "Partidos finalizados", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
        }
        items(finals) { game ->
            GameCard(game = game, onClick = { onGameSelected(game) })
        }
        item {
            Text(text = "Próximos partidos", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
        }
        items(upcoming) { game ->
            GameCard(game = game, onClick = { onGameSelected(game) })
        }
    }
}

@Composable
private fun GameCard(game: Game, onClick: () -> Unit) {
    val homeTeam = TeamsRepository.getTeamById(game.homeTeamId)
    val awayTeam = TeamsRepository.getTeamById(game.awayTeamId)
    val gradient = if (homeTeam != null && awayTeam != null) {
        Brush.horizontalGradient(listOf(homeTeam.primaryColor, awayTeam.primaryColor))
    } else {
        Brush.horizontalGradient(listOf(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.colorScheme.surface))
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(gradient)
                    .padding(16.dp)
            ) {
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = game.date, style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.85f))
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        GameTeamLogo(team = homeTeam, size = 56.dp)
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = if (game.homeScore != null) "${game.homeScore} - ${game.awayScore}" else "VS", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black, color = Color.White)
                            Text(text = game.location, style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.7f))
                        }
                        GameTeamLogo(team = awayTeam, size = 56.dp)
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text(text = homeTeam?.abbreviation ?: "HOME", fontWeight = FontWeight.SemiBold)
                    Text(text = game.homeRecord, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(text = awayTeam?.abbreviation ?: "AWAY", fontWeight = FontWeight.SemiBold)
                    Text(text = game.awayRecord, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Text(text = "Odds: ${game.odds}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "Detalles")
            }
        }
    }
}

@Composable
private fun GameTeamLogo(team: Team?, size: Dp) {
    if (team != null) {
        TeamLogo(team = team, size = size)
    } else {
        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "NFL",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetailScreen(game: Game, onBack: () -> Unit) {
    val homeTeam = TeamsRepository.getTeamById(game.homeTeamId)
    val awayTeam = TeamsRepository.getTeamById(game.awayTeamId)
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Game Center", fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                homeTeam?.primaryColor ?: MaterialTheme.colorScheme.primary,
                                awayTeam?.primaryColor ?: MaterialTheme.colorScheme.tertiary
                            )
                        )
                    )
                    .padding(24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (homeTeam != null) TeamLogo(team = homeTeam, size = 80.dp)
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "VS", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black, color = Color.White)
                        Text(
                            text = if (game.homeScore != null) "${game.homeScore} - ${game.awayScore}" else game.date,
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White
                        )
                    }
                    if (awayTeam != null) TeamLogo(team = awayTeam, size = 80.dp)
                }
            }
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(text = "Estadísticas clave", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                game.keyStats.forEach { (label, value) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = label, fontWeight = FontWeight.SemiBold)
                        Text(text = value, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "Standings divisionales", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                game.divisionStandings.forEach { standing ->
                    val team = TeamsRepository.getTeamById(standing.teamId)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = team?.abbreviation ?: standing.teamId.uppercase(), fontWeight = FontWeight.SemiBold)
                        Text(text = standing.record, style = MaterialTheme.typography.bodySmall)
                        Text(text = standing.trend, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
            Text(text = "Odds de apuestas: ${game.odds}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
fun RankingsScreen(onTeamSelected: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Text(text = "GOAT Players", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }
        items(GoatRankingsRepository.goatPlayers) { player ->
            RankingCard(
                title = "${player.rank}. ${player.name}",
                subtitle = "${player.position} · Debut ${player.debutYear}",
                teamId = player.teamId,
                onClick = { onTeamSelected(player.teamId) }
            )
        }
        item {
            Text(text = "GOAT Teams", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }
        items(GoatRankingsRepository.goatTeams) { team ->
            RankingCard(
                title = "${team.rank}. ${team.name}",
                subtitle = "Temporada ${team.season}",
                teamId = team.teamId,
                onClick = { onTeamSelected(team.teamId) }
            )
        }
    }
}

@Composable
private fun RankingCard(title: String, subtitle: String, teamId: String, onClick: () -> Unit) {
    val team = TeamsRepository.getTeamById(teamId)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (team != null) {
                TeamLogo(team = team, size = 64.dp)
            } else {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
            }
            Column {
                Text(text = title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(text = subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
fun StatsScreen() {
    val categories = remember { StatsRepository.categories }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(categories) { category ->
            StatsTable(category)
        }
    }
}

@Composable
private fun StatsTable(category: StatCategory) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(text = category.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                category.headers.forEach { header ->
                    Text(
                        text = header,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),
                        maxLines = 1
                    )
                }
            }
            category.rows.forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f))
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = row.label, modifier = Modifier.weight(1.6f), style = MaterialTheme.typography.bodySmall)
                    row.values.forEach { value ->
                        Text(
                            text = value,
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.End
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
    PracticasTheme {
        NFLApp()
    }
}
