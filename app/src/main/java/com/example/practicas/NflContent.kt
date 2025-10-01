package com.example.practicas

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.SportsFootball
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

enum class Conference(
    val displayName: String,
    val shortName: String,
    val accent: Color,
    val secondary: Color
) {
    AFC(
        displayName = "American Football Conference",
        shortName = "AFC",
        accent = Color(0xFFD7192D),
        secondary = Color(0xFF790014)
    ),
    NFC(
        displayName = "National Football Conference",
        shortName = "NFC",
        accent = Color(0xFF005CB9),
        secondary = Color(0xFF001F54)
    )
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
    val notablePlayers: List<String>,
    val logoRes: Int?
)

data class PlayerProfile(
    val id: String,
    val name: String,
    val position: String,
    val achievements: String,
    val biography: String,
    val teamId: String,
    val accentColor: Color,
    val era: String
)

enum class GameStatus { Final, Upcoming }

data class Game(
    val id: String,
    val homeTeam: String,
    val awayTeam: String,
    val homeScore: Int?,
    val awayScore: Int?,
    val date: String,
    val record: String,
    val venue: String,
    val status: GameStatus,
    val odds: String
)

data class GoatEntry(
    val rank: Int,
    val title: String,
    val subtitle: String,
    val descriptor: String,
    val badgeColor: Color,
    val logoRes: Int? = null
)

data class StatRow(
    val label: String,
    val columns: List<String>
)

data class StatCategory(
    val title: String,
    val headers: List<String>,
    val rows: List<StatRow>
)

object TeamsRepository {
    val teams: List<Team> = listOf(
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
            notablePlayers = listOf("josh_allen", "stefon_diggs"),
            logoRes = R.drawable.buffalo_bills
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
            notablePlayers = listOf("dan_marino", "larry_csonka"),
            logoRes = R.drawable.miami_dolphins
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
            notablePlayers = listOf("tom_brady", "rob_gronkowski"),
            logoRes = R.drawable.new_england_patriots
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
            notablePlayers = listOf("joe_namath", "darrelle_revis"),
            logoRes = R.drawable.new_york_jets
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
            notablePlayers = listOf("ray_lewis", "lamar_jackson"),
            logoRes = R.drawable.baltimore_ravens
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
            notablePlayers = listOf("joe_burrow", "chad_johnson"),
            logoRes = R.drawable.cincinnati_bengals
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
            notablePlayers = listOf("jim_brown", "myles_garrett"),
            logoRes = R.drawable.cleveland_browns
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
            notablePlayers = listOf("terry_bradshaw", "troy_polamalu"),
            logoRes = R.drawable.pittsburgh_steelers
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
            notablePlayers = listOf("jj_watt", "andre_johnson"),
            logoRes = R.drawable.houston_texans
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
            notablePlayers = listOf("peyton_manning", "marvin_harrison"),
            logoRes = R.drawable.indianapolis_colts
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
            notablePlayers = listOf("tony_boselli", "trevor_lawrence"),
            logoRes = R.drawable.jacksonville_jaguars
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
            notablePlayers = listOf("steve_mcnair", "derrick_henry"),
            logoRes = R.drawable.tennessee_titans
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
            notablePlayers = listOf("john_elway", "von_miller"),
            logoRes = null
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
            notablePlayers = listOf("patrick_mahomes", "travis_kelce"),
            logoRes = R.drawable.kansas_city_chiefs
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
            notablePlayers = listOf("marcus_allen", "davante_adams"),
            logoRes = null
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
            notablePlayers = listOf("ladainian_tomlinson", "junior_seau"),
            logoRes = R.drawable.san_diego_chargers
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
            notablePlayers = listOf("troy_aikman", "micah_parsons"),
            logoRes = R.drawable.dallas_cowboys
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
            notablePlayers = listOf("lawrence_taylor", "eli_manning"),
            logoRes = R.drawable.new_york_giants
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
            notablePlayers = listOf("reggie_white", "jalen_hurts"),
            logoRes = R.drawable.philadelphia_eagles
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
            notablePlayers = listOf("darrell_green", "terry_mclaurin"),
            logoRes = R.drawable.washington_commanders
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
            notablePlayers = listOf("walter_payton", "brian_urlacher"),
            logoRes = R.drawable.chicago_bears
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
            notablePlayers = listOf("barry_sanders", "calvin_johnson"),
            logoRes = R.drawable.detroit_lions
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
            notablePlayers = listOf("bart_starr", "aaron_rodgers"),
            logoRes = R.drawable.green_bay_packers
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
            notablePlayers = listOf("fran_tarkenton", "justin_jefferson"),
            logoRes = R.drawable.minnesota_vikings
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
            notablePlayers = listOf("matt_ryan", "julio_jones"),
            logoRes = null
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
            championships = "Two NFC championships in 2003 and 2015",
            history = "Carolina's expansion franchise quickly found success with a 2003 Super Bowl appearance. The Cam Newton era brought MVP fireworks and a return to the big game in 2015.",
            notablePlayers = listOf("steve_smith", "luke_kuechly"),
            logoRes = null
        ),
        Team(
            id = "saints",
            name = "Saints",
            city = "New Orleans",
            conference = Conference.NFC,
            abbreviation = "NO",
            primaryColor = Color(0xFF101820),
            secondaryColor = Color(0xFFD3BC8D),
            textColor = Color.White,
            established = 1967,
            championships = "Super Bowl XLIV champions",
            history = "The Saints became a symbol of resilience for New Orleans following Hurricane Katrina. Drew Brees and Sean Payton crafted a record-breaking offense that delivered the city's first Lombardi Trophy.",
            notablePlayers = listOf("drew_brees", "demario_davis"),
            logoRes = R.drawable.new_orleans_saints
        ),
        Team(
            id = "buccaneers",
            name = "Buccaneers",
            city = "Tampa Bay",
            conference = Conference.NFC,
            abbreviation = "TB",
            primaryColor = Color(0xFFD50A0A),
            secondaryColor = Color(0xFF34302B),
            textColor = Color.White,
            established = 1976,
            championships = "Super Bowls XXXVII and LV",
            history = "From the creamsicle era to Super Bowl champions, the Buccaneers have transformed behind ferocious defenses and star quarterbacks, culminating in a 2020 title run led by Tom Brady.",
            notablePlayers = listOf("derrick_brooks", "mike_evans"),
            logoRes = R.drawable.tampa_bay_buccaneers
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
            championships = "NFL champions in 1925 and 1947",
            history = "The NFL's oldest franchise has traveled from Chicago to St. Louis to the desert. The Cardinals electrified fans with Kurt Warner's 2008 Super Bowl run and Larry Fitzgerald's heroic moments.",
            notablePlayers = listOf("larry_fitzgerald", "pat_tillman"),
            logoRes = R.drawable.arizona_cardinals
        ),
        Team(
            id = "rams",
            name = "Rams",
            city = "Los Angeles",
            conference = Conference.NFC,
            abbreviation = "LAR",
            primaryColor = Color(0xFF003594),
            secondaryColor = Color(0xFFFF8200),
            textColor = Color.White,
            established = 1936,
            championships = "Super Bowls XXXIV and LVI",
            history = "The Rams have reinvented themselves across Cleveland, Los Angeles, and St. Louis. Sean McVay's modern offense delivered a title in 2021 powered by Aaron Donald and Cooper Kupp.",
            notablePlayers = listOf("aaron_donald", "cooper_kupp"),
            logoRes = R.drawable.st_louis_rams
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
            notablePlayers = listOf("russell_wilson", "richard_sherman"),
            logoRes = R.drawable.seattle_seahawks
        ),
        Team(
            id = "forty_niners",
            name = "49ers",
            city = "San Francisco",
            conference = Conference.NFC,
            abbreviation = "SF",
            primaryColor = Color(0xFFAA0000),
            secondaryColor = Color(0xFFB3995D),
            textColor = Color.White,
            established = 1946,
            championships = "Five Super Bowls spanning the Walsh and Shanahan eras",
            history = "The 49ers epitomize West Coast offense brilliance from Joe Montana and Jerry Rice to modern innovators. Physical defense and creative schemes define a five-time Super Bowl champion.",
            notablePlayers = listOf("joe_montana", "christian_mccaffrey"),
            logoRes = R.drawable.san_francisco_49ers
        )
    )

    private val teamsById = teams.associateBy { it.id }

    fun getTeamsForConference(conference: Conference): List<Team> =
        teams.filter { it.conference == conference }

    fun getTeamById(id: String): Team? = teamsById[id]
}
}

object PlayerRepository {
    private val playersInternal = listOf(
        PlayerProfile(
            id = "josh_allen",
            name = "Josh Allen",
            position = "QB",
            achievements = "2× Pro Bowl, 2020 All-Pro",
            biography = "Allen brought rocket-arm velocity and improvisation to Buffalo, leading consecutive deep playoff runs while embodying the Bills' fearless energy.",
            teamId = "bills",
            accentColor = TeamsRepository.getTeamById("bills")?.primaryColor ?: Color(0xFF0A1128),
            era = "2018-present"
        ),
        PlayerProfile(
            id = "stefon_diggs",
            name = "Stefon Diggs",
            position = "WR",
            achievements = "4× Pro Bowl, 2020 Receiving yards leader",
            biography = "Diggs' precise route running and highlight catches transformed Buffalo's passing attack, pairing artistry with swagger.",
            teamId = "bills",
            accentColor = TeamsRepository.getTeamById("bills")?.secondaryColor ?: Color(0xFFD7192D),
            era = "2020-present"
        ),
        PlayerProfile(
            id = "dan_marino",
            name = "Dan Marino",
            position = "QB",
            achievements = "NFL MVP (1984), Hall of Fame",
            biography = "Marino rewrote passing records with lightning quick release and vertical aggression, inspiring future generations of quarterbacks.",
            teamId = "dolphins",
            accentColor = TeamsRepository.getTeamById("dolphins")?.primaryColor ?: Color(0xFF008E97),
            era = "1983-1999"
        ),
        PlayerProfile(
            id = "larry_csonka",
            name = "Larry Csonka",
            position = "FB",
            achievements = "2× Super Bowl champion, Super Bowl VIII MVP",
            biography = "Csonka's bruising runs powered Miami's perfect season, setting the tone for the Dolphins' ground-and-pound dynasty.",
            teamId = "dolphins",
            accentColor = TeamsRepository.getTeamById("dolphins")?.secondaryColor ?: Color(0xFFF58220),
            era = "1968-1979"
        ),
        PlayerProfile(
            id = "tom_brady",
            name = "Tom Brady",
            position = "QB",
            achievements = "7× Super Bowl champion, 3× MVP",
            biography = "Brady defined modern excellence with clutch comebacks and meticulous preparation, guiding the Patriots' two-decade dynasty.",
            teamId = "patriots",
            accentColor = TeamsRepository.getTeamById("patriots")?.primaryColor ?: Color(0xFF002244),
            era = "2000-2019"
        ),
        PlayerProfile(
            id = "rob_gronkowski",
            name = "Rob Gronkowski",
            position = "TE",
            achievements = "4× Super Bowl champion, 4× All-Pro",
            biography = "Gronk combined size, speed, and personality to redefine tight end dominance, serving as Brady's ultimate mismatch weapon.",
            teamId = "patriots",
            accentColor = TeamsRepository.getTeamById("patriots")?.secondaryColor ?: Color(0xFFC60C30),
            era = "2010-2018"
        ),
        PlayerProfile(
            id = "joe_namath",
            name = "Joe Namath",
            position = "QB",
            achievements = "Super Bowl III champion, Hall of Fame",
            biography = "Broadway Joe's swagger and guarantee delivered a defining AFL victory, cementing the Jets' place in league lore.",
            teamId = "jets",
            accentColor = TeamsRepository.getTeamById("jets")?.primaryColor ?: Color(0xFF125740),
            era = "1965-1976"
        ),
        PlayerProfile(
            id = "darrelle_revis",
            name = "Darrelle Revis",
            position = "CB",
            achievements = "Super Bowl champion, 7× Pro Bowl",
            biography = "Revis Island shut down elite receivers weekly, showcasing masterful technique and football IQ at cornerback.",
            teamId = "jets",
            accentColor = TeamsRepository.getTeamById("jets")?.secondaryColor ?: Color(0xFF003F2D),
            era = "2007-2017"
        ),
        PlayerProfile(
            id = "ray_lewis",
            name = "Ray Lewis",
            position = "LB",
            achievements = "2× Defensive Player of the Year, 2× Super Bowl champion",
            biography = "Lewis' fiery leadership and sideline-to-sideline range anchored Baltimore's relentless defenses.",
            teamId = "ravens",
            accentColor = TeamsRepository.getTeamById("ravens")?.primaryColor ?: Color(0xFF241773),
            era = "1996-2012"
        ),
        PlayerProfile(
            id = "lamar_jackson",
            name = "Lamar Jackson",
            position = "QB",
            achievements = "NFL MVP (2019), 2× Pro Bowl",
            biography = "Jackson's dual-threat wizardry reimagined Baltimore's offense with unstoppable option looks and explosive scrambles.",
            teamId = "ravens",
            accentColor = TeamsRepository.getTeamById("ravens")?.secondaryColor ?: Color(0xFF9E7C0C),
            era = "2018-present"
        ),
        PlayerProfile(
            id = "joe_burrow",
            name = "Joe Burrow",
            position = "QB",
            achievements = "Pro Bowl, 2021 Comeback Player",
            biography = "Burrow's cool command and pinpoint timing elevated Cincinnati to its first Super Bowl appearance since 1988.",
            teamId = "bengals",
            accentColor = TeamsRepository.getTeamById("bengals")?.primaryColor ?: Color(0xFFFB4F14),
            era = "2020-present"
        ),
        PlayerProfile(
            id = "chad_johnson",
            name = "Chad Johnson",
            position = "WR",
            achievements = "6× Pro Bowl, 2× All-Pro",
            biography = "Johnson blended elite footwork with showmanship, putting the Bengals back on primetime with unforgettable celebrations.",
            teamId = "bengals",
            accentColor = TeamsRepository.getTeamById("bengals")?.secondaryColor ?: Color.Black,
            era = "2001-2011"
        ),
        PlayerProfile(
            id = "jim_brown",
            name = "Jim Brown",
            position = "RB",
            achievements = "3× MVP, Hall of Fame",
            biography = "Brown's unmatched power and grace made him the gold standard for running backs and a symbol of athletic dominance.",
            teamId = "browns",
            accentColor = TeamsRepository.getTeamById("browns")?.primaryColor ?: Color(0xFF311D00),
            era = "1957-1965"
        ),
        PlayerProfile(
            id = "myles_garrett",
            name = "Myles Garrett",
            position = "EDGE",
            achievements = "Defensive Player of the Year (2023), 4× All-Pro",
            biography = "Garrett pairs rare explosiveness with refined technique, headlining Cleveland's resurgence in the trenches.",
            teamId = "browns",
            accentColor = TeamsRepository.getTeamById("browns")?.secondaryColor ?: Color(0xFFFB4F14),
            era = "2017-present"
        ),
        PlayerProfile(
            id = "terry_bradshaw",
            name = "Terry Bradshaw",
            position = "QB",
            achievements = "4× Super Bowl champion, Hall of Fame",
            biography = "Bradshaw's deep-ball fearlessness fueled Pittsburgh's 1970s dynasty alongside the Steel Curtain defense.",
            teamId = "steelers",
            accentColor = TeamsRepository.getTeamById("steelers")?.secondaryColor ?: Color(0xFFFFB612),
            era = "1970-1983"
        ),
        PlayerProfile(
            id = "troy_polamalu",
            name = "Troy Polamalu",
            position = "S",
            achievements = "Defensive Player of the Year (2010), 8× Pro Bowl",
            biography = "Polamalu's instincts, hair-flying blitzes, and acrobatic picks embodied the Steelers' chaos-inducing defense.",
            teamId = "steelers",
            accentColor = TeamsRepository.getTeamById("steelers")?.primaryColor ?: Color.Black,
            era = "2003-2014"
        ),
        PlayerProfile(
            id = "jj_watt",
            name = "J.J. Watt",
            position = "DE",
            achievements = "3× Defensive Player of the Year, Walter Payton Man of the Year",
            biography = "Watt dominated games with relentless pursuit and community impact, becoming the face of Houston football.",
            teamId = "texans",
            accentColor = TeamsRepository.getTeamById("texans")?.primaryColor ?: Color(0xFF03202F),
            era = "2011-2021"
        ),
        PlayerProfile(
            id = "andre_johnson",
            name = "Andre Johnson",
            position = "WR",
            achievements = "7× Pro Bowl, Texans Ring of Honor",
            biography = "Johnson's physicality and contested-catch artistry delivered Houston its first superstar receiver.",
            teamId = "texans",
            accentColor = TeamsRepository.getTeamById("texans")?.secondaryColor ?: Color(0xFFA71930),
            era = "2003-2016"
        ),
        PlayerProfile(
            id = "peyton_manning",
            name = "Peyton Manning",
            position = "QB",
            achievements = "5× MVP, Super Bowl XLI champion",
            biography = "Manning orchestrated offensive symphonies with audibles and precision, defining professionalism in Indianapolis.",
            teamId = "colts",
            accentColor = TeamsRepository.getTeamById("colts")?.primaryColor ?: Color(0xFF003A70),
            era = "1998-2011"
        ),
        PlayerProfile(
            id = "marvin_harrison",
            name = "Marvin Harrison",
            position = "WR",
            achievements = "8× Pro Bowl, Hall of Fame",
            biography = "Harrison's timing with Peyton Manning produced record-setting chemistry and unstoppable option routes.",
            teamId = "colts",
            accentColor = TeamsRepository.getTeamById("colts")?.secondaryColor ?: Color.White,
            era = "1996-2008"
        ),
        PlayerProfile(
            id = "tony_boselli",
            name = "Tony Boselli",
            position = "LT",
            achievements = "Hall of Fame, 5× Pro Bowl",
            biography = "Boselli anchored Jacksonville's line with quiet dominance, protecting Mark Brunell during the franchise's early success.",
            teamId = "jaguars",
            accentColor = TeamsRepository.getTeamById("jaguars")?.primaryColor ?: Color(0xFF006778),
            era = "1995-2001"
        ),
        PlayerProfile(
            id = "trevor_lawrence",
            name = "Trevor Lawrence",
            position = "QB",
            achievements = "Pro Bowl, AFC South champion",
            biography = "Lawrence's poise and touch reinvigorated Duval, pairing with innovative play calling for a new era.",
            teamId = "jaguars",
            accentColor = TeamsRepository.getTeamById("jaguars")?.secondaryColor ?: Color(0xFFD7A22A),
            era = "2021-present"
        ),
        PlayerProfile(
            id = "steve_mcnair",
            name = "Steve McNair",
            position = "QB",
            achievements = "NFL MVP (2003), 3× Pro Bowl",
            biography = "Air McNair embodied grit, playing through pain and delivering the Titans to the brink of a championship.",
            teamId = "titans",
            accentColor = TeamsRepository.getTeamById("titans")?.primaryColor ?: Color(0xFF0C2340),
            era = "1995-2007"
        ),
        PlayerProfile(
            id = "derrick_henry",
            name = "Derrick Henry",
            position = "RB",
            achievements = "Offensive Player of the Year (2020), 2× rushing champion",
            biography = "Henry's king-sized stiff-arm and long-speed made Tennessee's offense a throwback force.",
            teamId = "titans",
            accentColor = TeamsRepository.getTeamById("titans")?.secondaryColor ?: Color(0xFFA2AAAD),
            era = "2016-present"
        ),
        PlayerProfile(
            id = "john_elway",
            name = "John Elway",
            position = "QB",
            achievements = "2× Super Bowl champion, Hall of Fame",
            biography = "Elway's rocket arm and late-game heroics defined Denver's identity and delivered back-to-back titles.",
            teamId = "broncos",
            accentColor = TeamsRepository.getTeamById("broncos")?.secondaryColor ?: Color(0xFF002244),
            era = "1983-1998"
        ),
        PlayerProfile(
            id = "von_miller",
            name = "Von Miller",
            position = "EDGE",
            achievements = "Super Bowl 50 MVP, 8× Pro Bowl",
            biography = "Miller's first-step burst and signature strip-sacks sparked a suffocating Denver defense.",
            teamId = "broncos",
            accentColor = TeamsRepository.getTeamById("broncos")?.primaryColor ?: Color(0xFFFB4F14),
            era = "2011-present"
        ),
        PlayerProfile(
            id = "patrick_mahomes",
            name = "Patrick Mahomes",
            position = "QB",
            achievements = "3× Super Bowl champion, 2× MVP",
            biography = "Mahomes blends creativity, arm angles, and clutch brilliance, redefining quarterback possibilities for Kansas City.",
            teamId = "chiefs",
            accentColor = TeamsRepository.getTeamById("chiefs")?.primaryColor ?: Color(0xFFE31837),
            era = "2017-present"
        ),
        PlayerProfile(
            id = "travis_kelce",
            name = "Travis Kelce",
            position = "TE",
            achievements = "4× All-Pro, NFL record consecutive 1000-yard seasons for TE",
            biography = "Kelce's feel for space and yards-after-catch flair keeps Kansas City's offense unpredictable.",
            teamId = "chiefs",
            accentColor = TeamsRepository.getTeamById("chiefs")?.secondaryColor ?: Color.White,
            era = "2013-present"
        ),
        PlayerProfile(
            id = "marcus_allen",
            name = "Marcus Allen",
            position = "RB",
            achievements = "Super Bowl XVIII MVP, Hall of Fame",
            biography = "Allen's slashing style and vision delivered iconic Raiders moments, including a legendary Super Bowl run.",
            teamId = "raiders",
            accentColor = TeamsRepository.getTeamById("raiders")?.primaryColor ?: Color.Black,
            era = "1982-1997"
        ),
        PlayerProfile(
            id = "davante_adams",
            name = "Davante Adams",
            position = "WR",
            achievements = "6× Pro Bowl, 3× All-Pro",
            biography = "Adams' release package and red-zone mastery brought superstar receiving to the Raiders' new era in Vegas.",
            teamId = "raiders",
            accentColor = TeamsRepository.getTeamById("raiders")?.secondaryColor ?: Color(0xFFA5ACAF),
            era = "2022-present"
        ),
        PlayerProfile(
            id = "ladainian_tomlinson",
            name = "LaDainian Tomlinson",
            position = "RB",
            achievements = "NFL MVP (2006), Hall of Fame",
            biography = "LT's record-breaking touchdown season and dual-threat ability made the Chargers must-see excitement.",
            teamId = "chargers",
            accentColor = TeamsRepository.getTeamById("chargers")?.primaryColor ?: Color(0xFF0080C6),
            era = "2001-2011"
        ),
        PlayerProfile(
            id = "junior_seau",
            name = "Junior Seau",
            position = "LB",
            achievements = "12× Pro Bowl, Hall of Fame",
            biography = "Seau's sideline-to-sideline intensity and leadership made him a San Diego legend and league icon.",
            teamId = "chargers",
            accentColor = TeamsRepository.getTeamById("chargers")?.secondaryColor ?: Color(0xFFFFD200),
            era = "1990-2009"
        ),
        PlayerProfile(
            id = "troy_aikman",
            name = "Troy Aikman",
            position = "QB",
            achievements = "3× Super Bowl champion, Hall of Fame",
            biography = "Aikman's precision and leadership orchestrated the Cowboys' 1990s dynasty alongside the Triplets.",
            teamId = "cowboys",
            accentColor = TeamsRepository.getTeamById("cowboys")?.primaryColor ?: Color(0xFF041E42),
            era = "1989-2000"
        ),
        PlayerProfile(
            id = "micah_parsons",
            name = "Micah Parsons",
            position = "LB",
            achievements = "Defensive Rookie of the Year (2021), 2× All-Pro",
            biography = "Parsons' hybrid skillset terrorizes offenses, blending edge rush explosiveness with coverage versatility.",
            teamId = "cowboys",
            accentColor = TeamsRepository.getTeamById("cowboys")?.secondaryColor ?: Color(0xFF869397),
            era = "2021-present"
        ),
        PlayerProfile(
            id = "lawrence_taylor",
            name = "Lawrence Taylor",
            position = "LB",
            achievements = "2× Super Bowl champion, 2× Defensive Player of the Year",
            biography = "LT revolutionized defense with relentless pressure, forcing offenses to reinvent protection schemes.",
            teamId = "giants",
            accentColor = TeamsRepository.getTeamById("giants")?.primaryColor ?: Color(0xFF0B2265),
            era = "1981-1993"
        ),
        PlayerProfile(
            id = "eli_manning",
            name = "Eli Manning",
            position = "QB",
            achievements = "2× Super Bowl MVP",
            biography = "Manning's calm demeanor belied clutch heroics, including two iconic Super Bowl upsets over New England.",
            teamId = "giants",
            accentColor = TeamsRepository.getTeamById("giants")?.secondaryColor ?: Color(0xFFA71930),
            era = "2004-2019"
        ),
        PlayerProfile(
            id = "reggie_white",
            name = "Reggie White",
            position = "DE",
            achievements = "2× Defensive Player of the Year, Hall of Fame",
            biography = "The Minister of Defense paired power and spirituality, becoming a pillar for both the Eagles and Packers.",
            teamId = "eagles",
            accentColor = TeamsRepository.getTeamById("eagles")?.primaryColor ?: Color(0xFF004C54),
            era = "1985-1992"
        ),
        PlayerProfile(
            id = "jalen_hurts",
            name = "Jalen Hurts",
            position = "QB",
            achievements = "Pro Bowl, NFC champion",
            biography = "Hurts brings stoic leadership, dual-threat danger, and a power-running identity to Philadelphia's offense.",
            teamId = "eagles",
            accentColor = TeamsRepository.getTeamById("eagles")?.secondaryColor ?: Color(0xFFACC0C6),
            era = "2020-present"
        ),
        PlayerProfile(
            id = "darrell_green",
            name = "Darrell Green",
            position = "CB",
            achievements = "2× Super Bowl champion, Hall of Fame",
            biography = "Green's legendary speed and longevity made him a Washington fan favorite for two decades.",
            teamId = "commanders",
            accentColor = TeamsRepository.getTeamById("commanders")?.primaryColor ?: Color(0xFF5A1414),
            era = "1983-2002"
        ),
        PlayerProfile(
            id = "terry_mclaurin",
            name = "Terry McLaurin",
            position = "WR",
            achievements = "Pro Bowl, Team captain",
            biography = "McLaurin's precise routes and contested catches anchor Washington's modern passing attack.",
            teamId = "commanders",
            accentColor = TeamsRepository.getTeamById("commanders")?.secondaryColor ?: Color(0xFFFFB612),
            era = "2019-present"
        ),
        PlayerProfile(
            id = "walter_payton",
            name = "Walter Payton",
            position = "RB",
            achievements = "NFL MVP (1977), Hall of Fame",
            biography = "Sweetness blended endurance and elegance, becoming a Chicago icon on and off the field.",
            teamId = "bears",
            accentColor = TeamsRepository.getTeamById("bears")?.primaryColor ?: Color(0xFF0B162A),
            era = "1975-1987"
        ),
        PlayerProfile(
            id = "brian_urlacher",
            name = "Brian Urlacher",
            position = "LB",
            achievements = "Defensive Player of the Year (2005), Hall of Fame",
            biography = "Urlacher's range and leadership reestablished the Monsters of the Midway identity in the 2000s.",
            teamId = "bears",
            accentColor = TeamsRepository.getTeamById("bears")?.secondaryColor ?: Color(0xFFC83803),
            era = "2000-2012"
        ),
        PlayerProfile(
            id = "barry_sanders",
            name = "Barry Sanders",
            position = "RB",
            achievements = "NFL MVP (1997), Hall of Fame",
            biography = "Sanders' ankle-breaking cuts created poetry on turf, making Detroit must-watch every Sunday.",
            teamId = "lions",
            accentColor = TeamsRepository.getTeamById("lions")?.primaryColor ?: Color(0xFF0076B6),
            era = "1989-1998"
        ),
        PlayerProfile(
            id = "calvin_johnson",
            name = "Calvin Johnson",
            position = "WR",
            achievements = "NFL record 1,964 receiving yards, Hall of Fame",
            biography = "Megatron's size-speed combo shattered coverage plans and redefined the ceiling for receivers.",
            teamId = "lions",
            accentColor = TeamsRepository.getTeamById("lions")?.secondaryColor ?: Color(0xFFB0B7BC),
            era = "2007-2015"
        ),
        PlayerProfile(
            id = "bart_starr",
            name = "Bart Starr",
            position = "QB",
            achievements = "5× NFL champion, 2× Super Bowl MVP",
            biography = "Starr's calm leadership and precision guided Lombardi's Packers through the league's formative years.",
            teamId = "packers",
            accentColor = TeamsRepository.getTeamById("packers")?.primaryColor ?: Color(0xFF203731),
            era = "1956-1971"
        ),
        PlayerProfile(
            id = "aaron_rodgers",
            name = "Aaron Rodgers",
            position = "QB",
            achievements = "4× MVP, Super Bowl XLV champion",
            biography = "Rodgers' arm talent, improvisation, and accuracy extended Green Bay's tradition of elite quarterback play.",
            teamId = "packers",
            accentColor = TeamsRepository.getTeamById("packers")?.secondaryColor ?: Color(0xFFFFB612),
            era = "2005-present"
        ),
        PlayerProfile(
            id = "fran_tarkenton",
            name = "Fran Tarkenton",
            position = "QB",
            achievements = "NFL MVP (1975), Hall of Fame",
            biography = "Tarkenton's scrambling creativity and leadership delivered Minnesota to multiple Super Bowls.",
            teamId = "vikings",
            accentColor = TeamsRepository.getTeamById("vikings")?.primaryColor ?: Color(0xFF4F2683),
            era = "1961-1978"
        ),
        PlayerProfile(
            id = "justin_jefferson",
            name = "Justin Jefferson",
            position = "WR",
            achievements = "Offensive Player of the Year (2022)",
            biography = "Jefferson's elite releases and contested catches ignited a new generation of Vikings offense.",
            teamId = "vikings",
            accentColor = TeamsRepository.getTeamById("vikings")?.secondaryColor ?: Color(0xFFFFC62F),
            era = "2020-present"
        ),
        PlayerProfile(
            id = "matt_ryan",
            name = "Matt Ryan",
            position = "QB",
            achievements = "NFL MVP (2016)",
            biography = "Ryan's precision passing and leadership guided Atlanta to perennial contention and a Super Bowl berth.",
            teamId = "falcons",
            accentColor = TeamsRepository.getTeamById("falcons")?.primaryColor ?: Color(0xFFA71930),
            era = "2008-2021"
        ),
        PlayerProfile(
            id = "julio_jones",
            name = "Julio Jones",
            position = "WR",
            achievements = "7× Pro Bowl, 2× All-Pro",
            biography = "Jones' catch radius and speed made him virtually uncoverable, redefining what a wideout could do for Atlanta.",
            teamId = "falcons",
            accentColor = TeamsRepository.getTeamById("falcons")?.secondaryColor ?: Color.Black,
            era = "2011-present"
        ),
        PlayerProfile(
            id = "steve_smith",
            name = "Steve Smith Sr.",
            position = "WR",
            achievements = "5× Pro Bowl, 2005 Triple Crown",
            biography = "Smith's fiery competitiveness and yards-after-catch fireworks gave Carolina its offensive edge.",
            teamId = "panthers",
            accentColor = TeamsRepository.getTeamById("panthers")?.primaryColor ?: Color(0xFF0085CA),
            era = "2001-2016"
        ),
        PlayerProfile(
            id = "luke_kuechly",
            name = "Luke Kuechly",
            position = "LB",
            achievements = "Defensive Player of the Year (2013), 7× Pro Bowl",
            biography = "Kuechly's instincts, preparation, and sideline-to-sideline range made him the heartbeat of Carolina's defense.",
            teamId = "panthers",
            accentColor = TeamsRepository.getTeamById("panthers")?.secondaryColor ?: Color(0xFF101820),
            era = "2012-2019"
        ),
        PlayerProfile(
            id = "drew_brees",
            name = "Drew Brees",
            position = "QB",
            achievements = "Super Bowl XLIV MVP, all-time passing leader",
            biography = "Brees' accuracy and leadership symbolized New Orleans' revival, crafting a prolific offense for over a decade.",
            teamId = "saints",
            accentColor = TeamsRepository.getTeamById("saints")?.primaryColor ?: Color(0xFF101820),
            era = "2006-2020"
        ),
        PlayerProfile(
            id = "demario_davis",
            name = "Demario Davis",
            position = "LB",
            achievements = "All-Pro leader of the Dome Patrol revival",
            biography = "Davis' leadership and range stabilized the Saints' defense, embodying the franchise's modern toughness.",
            teamId = "saints",
            accentColor = TeamsRepository.getTeamById("saints")?.secondaryColor ?: Color(0xFFD3BC8D),
            era = "2018-present"
        ),
        PlayerProfile(
            id = "derrick_brooks",
            name = "Derrick Brooks",
            position = "LB",
            achievements = "Super Bowl XXXVII champion, Hall of Fame",
            biography = "Brooks' speed and smarts defined Tampa Bay's Tampa 2 defense and championship identity.",
            teamId = "buccaneers",
            accentColor = TeamsRepository.getTeamById("buccaneers")?.primaryColor ?: Color(0xFFD50A0A),
            era = "1995-2008"
        ),
        PlayerProfile(
            id = "mike_evans",
            name = "Mike Evans",
            position = "WR",
            achievements = "NFL record 10 straight 1,000-yard seasons",
            biography = "Evans' size, body control, and consistency keep Tampa Bay in the explosive play conversation every year.",
            teamId = "buccaneers",
            accentColor = TeamsRepository.getTeamById("buccaneers")?.secondaryColor ?: Color(0xFF34302B),
            era = "2014-present"
        ),
        PlayerProfile(
            id = "larry_fitzgerald",
            name = "Larry Fitzgerald",
            position = "WR",
            achievements = "11× Pro Bowl, Walter Payton Man of the Year",
            biography = "Fitzgerald's hands, routes, and grace made him the face of Arizona football for nearly two decades.",
            teamId = "cardinals",
            accentColor = TeamsRepository.getTeamById("cardinals")?.primaryColor ?: Color(0xFF97233F),
            era = "2004-2020"
        ),
        PlayerProfile(
            id = "pat_tillman",
            name = "Pat Tillman",
            position = "S",
            achievements = "Arizona Ring of Honor, US Army Ranger",
            biography = "Tillman's legacy extends beyond football—his sacrifice and integrity inspire across sports.",
            teamId = "cardinals",
            accentColor = TeamsRepository.getTeamById("cardinals")?.secondaryColor ?: Color.Black,
            era = "1998-2001"
        ),
        PlayerProfile(
            id = "aaron_donald",
            name = "Aaron Donald",
            position = "DT",
            achievements = "3× Defensive Player of the Year, Super Bowl champion",
            biography = "Donald overwhelms blockers with leverage and strength, redefining interior disruption in Los Angeles.",
            teamId = "rams",
            accentColor = TeamsRepository.getTeamById("rams")?.primaryColor ?: Color(0xFF003594),
            era = "2014-present"
        ),
        PlayerProfile(
            id = "cooper_kupp",
            name = "Cooper Kupp",
            position = "WR",
            achievements = "Super Bowl LVI MVP, Triple Crown",
            biography = "Kupp's route nuance and chemistry with Matthew Stafford delivered record production and a title.",
            teamId = "rams",
            accentColor = TeamsRepository.getTeamById("rams")?.secondaryColor ?: Color(0xFFFF8200),
            era = "2017-present"
        ),
        PlayerProfile(
            id = "russell_wilson",
            name = "Russell Wilson",
            position = "QB",
            achievements = "Super Bowl XLVIII champion, 9× Pro Bowl",
            biography = "Wilson's moonball deep passes and scramble magic ushered Seattle into a golden era.",
            teamId = "seahawks",
            accentColor = TeamsRepository.getTeamById("seahawks")?.primaryColor ?: Color(0xFF002244),
            era = "2012-present"
        ),
        PlayerProfile(
            id = "richard_sherman",
            name = "Richard Sherman",
            position = "CB",
            achievements = "Super Bowl champion, 3× All-Pro",
            biography = "Sherman's intellect and physical press coverage anchored the Legion of Boom identity.",
            teamId = "seahawks",
            accentColor = TeamsRepository.getTeamById("seahawks")?.secondaryColor ?: Color(0xFF69BE28),
            era = "2011-present"
        ),
        PlayerProfile(
            id = "joe_montana",
            name = "Joe Montana",
            position = "QB",
            achievements = "4× Super Bowl champion, 2× MVP",
            biography = "Joe Cool executed Bill Walsh's West Coast offense with unmatched calm and precision.",
            teamId = "forty_niners",
            accentColor = TeamsRepository.getTeamById("forty_niners")?.primaryColor ?: Color(0xFFAA0000),
            era = "1979-1994"
        ),
        PlayerProfile(
            id = "christian_mccaffrey",
            name = "Christian McCaffrey",
            position = "RB",
            achievements = "Offensive Player of the Year (2023)",
            biography = "McCaffrey's versatility and vision create mismatches everywhere, embodying modern offensive creativity in San Francisco.",
            teamId = "forty_niners",
            accentColor = TeamsRepository.getTeamById("forty_niners")?.secondaryColor ?: Color(0xFFB3995D),
            era = "2022-present"
        )
    )

    private val playersById = playersInternal.associateBy { it.id }

    fun getPlayer(id: String): PlayerProfile? = playersById[id]
}
object GamesRepository {
    val finals = listOf(
        Game(
            id = "2023_wk18_buf_kc",
            homeTeam = "chiefs",
            awayTeam = "bills",
            homeScore = 27,
            awayScore = 24,
            date = "Sun, Jan 21",
            record = "BUF 13-4 | KC 12-5",
            venue = "GEHA Field at Arrowhead Stadium",
            status = GameStatus.Final,
            odds = "KC -3.0"
        ),
        Game(
            id = "2023_wk18_sf_det",
            homeTeam = "forty_niners",
            awayTeam = "lions",
            homeScore = 34,
            awayScore = 31,
            date = "Sun, Jan 28",
            record = "DET 14-5 | SF 15-4",
            venue = "Levi's Stadium",
            status = GameStatus.Final,
            odds = "SF -7.5"
        ),
        Game(
            id = "2023_sb_lviii_kc_sf",
            homeTeam = "chiefs",
            awayTeam = "forty_niners",
            homeScore = 25,
            awayScore = 22,
            date = "Sun, Feb 11",
            record = "KC 15-6 | SF 15-5",
            venue = "Allegiant Stadium",
            status = GameStatus.Final,
            odds = "SF -2.5"
        )
    )

    val upcoming = listOf(
        Game(
            id = "2024_wk1_det_sf",
            homeTeam = "lions",
            awayTeam = "forty_niners",
            homeScore = null,
            awayScore = null,
            date = "Thu, Sep 5 • 8:20 PM",
            record = "Season Kickoff",
            venue = "Ford Field",
            status = GameStatus.Upcoming,
            odds = "SF -1.5"
        ),
        Game(
            id = "2024_wk1_cin_kc",
            homeTeam = "chiefs",
            awayTeam = "bengals",
            homeScore = null,
            awayScore = null,
            date = "Sun, Sep 8 • 4:25 PM",
            record = "Season Opener",
            venue = "Arrowhead Stadium",
            status = GameStatus.Upcoming,
            odds = "KC -4.5"
        ),
        Game(
            id = "2024_wk1_mia_buf",
            homeTeam = "bills",
            awayTeam = "dolphins",
            homeScore = null,
            awayScore = null,
            date = "Mon, Sep 9 • 8:15 PM",
            record = "Primetime Showcase",
            venue = "Highmark Stadium",
            status = GameStatus.Upcoming,
            odds = "BUF -3.0"
        )
    )

    private val gamesById = (finals + upcoming).associateBy { it.id }

    fun getGameById(id: String): Game? = gamesById[id]
}

object GoatRepository {
    val playerGoats = listOf(
        GoatEntry(1, "Tom Brady", "QB, Patriots", "7× Super Bowl champion", Color(0xFFD7192D)),
        GoatEntry(2, "Jerry Rice", "WR, 49ers", "All-time receiving leader", Color(0xFFAA0000)),
        GoatEntry(3, "Lawrence Taylor", "LB, Giants", "Changed pass protection", Color(0xFF0B2265)),
        GoatEntry(4, "Jim Brown", "RB, Browns", "3× MVP legend", Color(0xFFFB4F14)),
        GoatEntry(5, "Joe Montana", "QB, 49ers", "4× Super Bowl champion", Color(0xFFB3995D))
    )

    val teamGoats = listOf(
        GoatEntry(1, "New England Patriots", "2001-2019", "6× Super Bowls", Color(0xFF002244), R.drawable.new_england_patriots),
        GoatEntry(2, "San Francisco 49ers", "1981-1994", "4× Super Bowls", Color(0xFFAA0000), R.drawable.san_francisco_49ers),
        GoatEntry(3, "Pittsburgh Steelers", "1974-2008", "6× Super Bowls", Color(0xFFFFB612), R.drawable.pittsburgh_steelers),
        GoatEntry(4, "Dallas Cowboys", "1991-1995", "3× Super Bowls", Color(0xFF041E42), R.drawable.dallas_cowboys),
        GoatEntry(5, "Kansas City Chiefs", "2018-2023", "3× Super Bowls", Color(0xFFE31837), R.drawable.kansas_city_chiefs)
    )
}

object StatsRepository {
    val categories = listOf(
        StatCategory(
            title = "Passing Leaders",
            headers = listOf("Player", "TD", "INT", "YDS", "CMP%"),
            rows = listOf(
                StatRow("Patrick Mahomes", listOf("43", "9", "4,850", "67.4")),
                StatRow("Josh Allen", listOf("40", "12", "4,520", "65.7")),
                StatRow("Joe Burrow", listOf("36", "10", "4,110", "68.2")),
                StatRow("Jalen Hurts", listOf("34", "8", "4,020", "66.1"))
            )
        ),
        StatCategory(
            title = "Rushing Leaders",
            headers = listOf("Player", "TD", "YDS", "AVG", "FUM"),
            rows = listOf(
                StatRow("Derrick Henry", listOf("16", "1,620", "4.8", "2")),
                StatRow("Christian McCaffrey", listOf("14", "1,540", "4.9", "3")),
                StatRow("Nick Chubb", listOf("11", "1,320", "5.0", "1")),
                StatRow("Saquon Barkley", listOf("10", "1,210", "4.4", "2"))
            )
        ),
        StatCategory(
            title = "Receiving Leaders",
            headers = listOf("Player", "TD", "REC", "YDS", "YAC"),
            rows = listOf(
                StatRow("Justin Jefferson", listOf("12", "118", "1,780", "632")),
                StatRow("Tyreek Hill", listOf("14", "112", "1,720", "690")),
                StatRow("Stefon Diggs", listOf("11", "110", "1,450", "488")),
                StatRow("CeeDee Lamb", listOf("10", "105", "1,360", "521"))
            )
        ),
        StatCategory(
            title = "Defensive Impact",
            headers = listOf("Player", "Sacks", "TFL", "INT", "FF"),
            rows = listOf(
                StatRow("Micah Parsons", listOf("17", "23", "1", "4")),
                StatRow("Myles Garrett", listOf("16", "22", "0", "5")),
                StatRow("T.J. Watt", listOf("15", "21", "1", "4")),
                StatRow("Fred Warner", listOf("8", "12", "3", "3"))
            )
        )
    )
}
sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object ConferenceTeams : Screen("conference-teams/{conference}") {
        fun createRoute(conference: Conference) = "conference-teams/${conference.name}"
    }
    data object TeamDetail : Screen("team/{teamId}") {
        fun createRoute(teamId: String) = "team/$teamId"
    }
    data object PlayerDetail : Screen("player/{playerId}") {
        fun createRoute(playerId: String) = "player/$playerId"
    }
    data object Games : Screen("games")
    data object GameDetail : Screen("game/{gameId}") {
        fun createRoute(gameId: String) = "game/$gameId"
    }
    data object Goat : Screen("goat")
    data object Stats : Screen("stats")
}

@Composable
fun NFLApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(
                onConferenceSelected = { conference ->
                    navController.navigate(Screen.ConferenceTeams.createRoute(conference))
                },
                onNavigateToGames = { navController.navigate(Screen.Games.route) },
                onNavigateToGoat = { navController.navigate(Screen.Goat.route) },
                onNavigateToStats = { navController.navigate(Screen.Stats.route) }
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
                    onTeamSelected = { team -> navController.navigate(Screen.TeamDetail.createRoute(team.id)) }
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
                    onBack = { navController.popBackStack() },
                    onPlayerSelected = { playerId ->
                        navController.navigate(Screen.PlayerDetail.createRoute(playerId))
                    }
                )
            }
        }
        composable(
            Screen.PlayerDetail.route,
            arguments = listOf(navArgument("playerId") { type = NavType.StringType })
        ) { backStackEntry ->
            val playerId = backStackEntry.arguments?.getString("playerId")
            val player = playerId?.let(PlayerRepository::getPlayer)
            val team = player?.teamId?.let(TeamsRepository::getTeamById)
            if (player != null && team != null) {
                PlayerDetailScreen(player = player, team = team) { navController.popBackStack() }
            }
        }
        composable(Screen.Games.route) {
            GamesScreen(
                finals = GamesRepository.finals,
                upcoming = GamesRepository.upcoming,
                onBack = { navController.popBackStack() },
                onNavigateToDetail = { game -> navController.navigate(Screen.GameDetail.createRoute(game.id)) }
            )
        }
        composable(
            Screen.GameDetail.route,
            arguments = listOf(navArgument("gameId") { type = NavType.StringType })
        ) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getString("gameId")
            val game = gameId?.let(GamesRepository::getGameById)
            val homeTeam = game?.homeTeam?.let(TeamsRepository::getTeamById)
            val awayTeam = game?.awayTeam?.let(TeamsRepository::getTeamById)
            if (game != null && homeTeam != null && awayTeam != null) {
                GameDetailScreen(
                    game = game,
                    homeTeam = homeTeam,
                    awayTeam = awayTeam,
                    onBack = { navController.popBackStack() }
                )
            }
        }
        composable(Screen.Goat.route) {
            GoatRankingsScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Stats.route) {
            StatsScreen(onBack = { navController.popBackStack() })
        }
    }
}

@Composable
fun HomeScreen(
    onConferenceSelected: (Conference) -> Unit,
    onNavigateToGames: () -> Unit,
    onNavigateToGoat: () -> Unit,
    onNavigateToStats: () -> Unit
) {
    val isDark = isSystemInDarkTheme()
    val gradient = remember(isDark) {
        if (isDark) {
            Brush.verticalGradient(listOf(Color(0xFF050B18), Color(0xFF0E1A32)))
        } else {
            Brush.verticalGradient(listOf(Color(0xFF0F1D35), Color(0xFF1F2F4A)))
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(24.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(28.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "NFL Atlas",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Explora conferencias, partidos, leyendas y datos en un lienzo oscuro inspirado en Apple Sports.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
                    .clip(RoundedCornerShape(36.dp))
                    .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f), RoundedCornerShape(36.dp))
            ) {
                Column(Modifier.fillMaxSize()) {
                    ConferenceHalf(
                        conference = Conference.AFC,
                        logoRes = R.drawable.afc,
                        onClick = { onConferenceSelected(Conference.AFC) }
                    )
                    ConferenceHalf(
                        conference = Conference.NFC,
                        logoRes = R.drawable.nfc_logo,
                        onClick = { onConferenceSelected(Conference.NFC) }
                    )
                }
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.Center)
                        .clip(RoundedCornerShape(32.dp))
                        .background(Color(0xFF0D1C2F))
                        .border(3.dp, Color.White.copy(alpha = 0.4f), RoundedCornerShape(32.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.nfl),
                        contentDescription = "NFL Shield",
                        modifier = Modifier.size(80.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = "Atajos",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    QuickActionCard(
                        title = "Partidos",
                        subtitle = "Finales + próximos",
                        icon = Icons.Default.SportsFootball,
                        gradient = Brush.linearGradient(listOf(Color(0xFF15294B), Color(0xFF203E6B))),
                        onClick = onNavigateToGames
                    )
                    QuickActionCard(
                        title = "GOAT Charts",
                        subtitle = "Jugadores & Equipos",
                        icon = Icons.Default.EmojiEvents,
                        gradient = Brush.linearGradient(listOf(Color(0xFF311F48), Color(0xFF472F6C))),
                        onClick = onNavigateToGoat
                    )
                    QuickActionCard(
                        title = "Stats",
                        subtitle = "Passing • Defense",
                        icon = Icons.Default.BarChart,
                        gradient = Brush.linearGradient(listOf(Color(0xFF1C3A3A), Color(0xFF285050))),
                        onClick = onNavigateToStats
                    )
                }
            }
        }
    }
}

@Composable
private fun ConferenceHalf(
    conference: Conference,
    logoRes: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
            .background(Brush.linearGradient(listOf(conference.accent, conference.secondary)))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = logoRes),
            contentDescription = conference.displayName,
            modifier = Modifier.size(150.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
private fun QuickActionCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    gradient: Brush,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(24.dp))
            .clickable(onClick = onClick),
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .background(gradient)
                .padding(18.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White.copy(alpha = 0.18f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = icon, contentDescription = null, tint = Color.White)
                }
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }
            }
        }
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
                            text = "${teams.size} franquicias",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            items(teams) { team ->
                TeamCard(team = team, onClick = { onTeamSelected(team) })
            }
        }
    }
}

@Composable
private fun TeamCard(team: Team, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .clickable(onClick = onClick),
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.linearGradient(listOf(team.primaryColor, team.secondaryColor))
                )
                .padding(20.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TeamLogo(team = team, size = 72.dp)
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "${team.city} ${team.name}",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = team.textColor
                    )
                    Text(
                        text = team.championships,
                        style = MaterialTheme.typography.bodySmall,
                        color = team.textColor.copy(alpha = 0.85f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Fundado ${team.established}",
                        style = MaterialTheme.typography.bodySmall,
                        color = team.textColor.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}

@Composable
private fun TeamLogo(team: Team, size: androidx.compose.ui.unit.Dp) {
    if (team.logoRes != null) {
        Box(
            modifier = Modifier
                .size(size)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = team.logoRes),
                contentDescription = "${team.name} logo",
                modifier = Modifier.size(size * 0.75f),
                contentScale = ContentScale.Fit
            )
        }
    } else {
        Box(
            modifier = Modifier
                .size(size)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White.copy(alpha = 0.1f))
                .border(2.dp, Color.White.copy(alpha = 0.25f), RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = team.abbreviation, fontWeight = FontWeight.Bold, color = team.textColor)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamDetailScreen(
    team: Team,
    onBack: () -> Unit,
    onPlayerSelected: (String) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "${team.city} ${team.name}", fontWeight = FontWeight.Bold)
                        Text(
                            text = "${team.conference.shortName} • Est. ${team.established}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(28.dp),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(36.dp))
                        .background(Brush.linearGradient(listOf(team.primaryColor, team.secondaryColor)))
                        .padding(28.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        TeamLogo(team = team, size = 96.dp)
                        Text(
                            text = team.championships,
                            color = team.textColor,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "Historia de la franquicia",
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
            if (team.notablePlayers.isNotEmpty()) {
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Text(
                            text = "Jugadores legendarios",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        team.notablePlayers.forEach { playerId ->
                            val profile = PlayerRepository.getPlayer(playerId)
                            if (profile != null) {
                                PlayerLegendRow(profile = profile, onClick = { onPlayerSelected(profile.id) })
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PlayerLegendRow(profile: PlayerProfile, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .clickable(onClick = onClick),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            profile.accentColor.copy(alpha = 0.85f),
                            profile.accentColor.copy(alpha = 0.45f)
                        )
                    )
                )
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = profile.name, fontWeight = FontWeight.SemiBold, color = Color.White)
                Text(
                    text = "${profile.position} • ${profile.era}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
            Text(
                text = profile.achievements,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
fun PlayerDetailScreen(player: PlayerProfile, team: Team, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = player.name, fontWeight = FontWeight.Bold)
                        Text(
                            text = "${player.position} • ${team.city} ${team.name}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            contentPadding = PaddingValues(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(32.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(player.accentColor, team.secondaryColor.copy(alpha = 0.7f))
                            )
                        )
                        .padding(28.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(text = player.achievements, color = Color.White, fontWeight = FontWeight.SemiBold)
                        Text(
                            text = player.era,
                            color = Color.White.copy(alpha = 0.85f),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "Biografía",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = player.biography,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.85f)
                    )
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamesScreen(
    finals: List<Game>,
    upcoming: List<Game>,
    onBack: () -> Unit,
    onNavigateToDetail: (Game) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Partidos", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            item { SectionTitle(text = "Finalizados") }
            items(finals) { game ->
                GameCard(game = game, onDetails = { onNavigateToDetail(game) })
            }
            item { SectionTitle(text = "Próximos") }
            items(upcoming) { game ->
                GameCard(game = game, onDetails = { onNavigateToDetail(game) })
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(text = text, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
}

@Composable
private fun GameCard(game: Game, onDetails: () -> Unit) {
    val homeTeam = TeamsRepository.getTeamById(game.homeTeam)
    val awayTeam = TeamsRepository.getTeamById(game.awayTeam)
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .clickable(onClick = onDetails),
        color = Color.Transparent
    ) {
        Column(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        listOf(
                            homeTeam?.primaryColor ?: Color(0xFF1C1C1E),
                            awayTeam?.primaryColor ?: Color(0xFF1F2933)
                        )
                    )
                )
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.Start) {
                    Text(text = game.date, color = Color.White.copy(alpha = 0.7f))
                    Text(text = game.venue, color = Color.White.copy(alpha = 0.6f), style = MaterialTheme.typography.bodySmall)
                }
                Text(text = game.record, color = Color.White.copy(alpha = 0.8f), style = MaterialTheme.typography.bodySmall)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TeamScoreColumn(team = awayTeam, score = game.awayScore)
                Text(
                    text = if (game.status == GameStatus.Final) "${game.homeScore} - ${game.awayScore}" else "VS",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )
                TeamScoreColumn(team = homeTeam, score = game.homeScore, alignEnd = true)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = game.status.name.uppercase(), color = Color.White.copy(alpha = 0.6f), style = MaterialTheme.typography.bodySmall)
                Text(text = "Odds: ${game.odds}", color = Color.White.copy(alpha = 0.8f), style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
private fun TeamScoreColumn(team: Team?, score: Int?, alignEnd: Boolean = false) {
    Column(
        horizontalAlignment = if (alignEnd) Alignment.End else Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (team != null) {
            TeamLogo(team = team, size = 64.dp)
            Text(text = "${team.city} ${team.name}", color = Color.White, style = MaterialTheme.typography.bodySmall, textAlign = if (alignEnd) TextAlign.End else TextAlign.Start)
        }
        AnimatedVisibility(visible = score != null) {
            Text(text = score?.toString() ?: "", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetailScreen(game: Game, homeTeam: Team, awayTeam: Team, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Detalle de partido", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(36.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(awayTeam.primaryColor, homeTeam.primaryColor)
                        )
                    )
                    .padding(vertical = 32.dp, horizontal = 24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        TeamLogo(team = awayTeam, size = 72.dp)
                        Text(text = awayTeam.abbreviation, color = awayTeam.textColor, fontWeight = FontWeight.SemiBold)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(text = "VS", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Text(
                            text = if (game.status == GameStatus.Final) "${game.homeScore} - ${game.awayScore}" else "${awayTeam.abbreviation} vs ${homeTeam.abbreviation}",
                            color = Color.White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Black
                        )
                        Text(text = game.date, color = Color.White.copy(alpha = 0.75f))
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        TeamLogo(team = homeTeam, size = 72.dp)
                        Text(text = homeTeam.abbreviation, color = homeTeam.textColor, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(text = "Info del juego", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                GameInfoRow(label = "Venue", value = game.venue)
                GameInfoRow(label = "Récord", value = game.record)
                GameInfoRow(label = "Odds", value = game.odds)
            }

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(text = "Standings divisionales", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                DivisionStandingsBlock(team = homeTeam, label = "${homeTeam.conference.shortName} contender")
                DivisionStandingsBlock(team = awayTeam, label = "${awayTeam.conference.shortName} challenger")
            }
        }
    }
}

@Composable
private fun GameInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
        Text(text = value, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun DivisionStandingsBlock(team: Team, label: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp)),
        color = Color.Transparent
    ) {
        Column(
            modifier = Modifier
                .background(Brush.linearGradient(listOf(team.primaryColor.copy(alpha = 0.8f), team.secondaryColor.copy(alpha = 0.6f))))
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = label, color = Color.White.copy(alpha = 0.75f), style = MaterialTheme.typography.bodySmall)
            Text(text = "${team.city} ${team.name}", color = Color.White, fontWeight = FontWeight.SemiBold)
            Text(text = "Últimos 5: 4-1", color = Color.White.copy(alpha = 0.75f), style = MaterialTheme.typography.bodySmall)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoatRankingsScreen(onBack: () -> Unit) {
    var showPlayers by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "GOAT Rankings", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White.copy(alpha = 0.06f)),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                GoatToggleButton(text = "Players", selected = showPlayers) { showPlayers = true }
                GoatToggleButton(text = "Teams", selected = !showPlayers) { showPlayers = false }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(18.dp),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                val items = if (showPlayers) GoatRepository.playerGoats else GoatRepository.teamGoats
                items(items) { entry ->
                    GoatRow(entry = entry)
                }
            }
        }
    }
}

@Composable
private fun GoatToggleButton(text: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (selected) Color.White.copy(alpha = 0.15f) else Color.Transparent
            )
            .clickable(onClick = onClick)
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = Color.White, fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium)
    }
}

@Composable
private fun GoatRow(entry: GoatEntry) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp)),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .background(entry.badgeColor.copy(alpha = 0.85f))
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = entry.rank.toString(), color = Color.White, fontWeight = FontWeight.Bold)
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = entry.title, color = Color.White, fontWeight = FontWeight.SemiBold)
                Text(text = entry.subtitle, color = Color.White.copy(alpha = 0.85f), style = MaterialTheme.typography.bodySmall)
            }
            Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = entry.descriptor, color = Color.White, style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.End)
                entry.logoRes?.let {
                    Image(
                        painter = painterResource(id = it),
                        contentDescription = entry.title,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Estadísticas", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            items(StatsRepository.categories) { category ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(28.dp)),
                    color = Color.Transparent
                ) {
                    Column(
                        modifier = Modifier
                            .background(Color.White.copy(alpha = 0.04f))
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(text = category.title, fontWeight = FontWeight.SemiBold)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            category.headers.forEach { header ->
                                Text(
                                    text = header,
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                        category.rows.forEach { row ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color.White.copy(alpha = 0.05f))
                                    .padding(vertical = 12.dp, horizontal = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = row.label,
                                    modifier = Modifier.weight(1.2f),
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium
                                )
                                row.columns.forEach { value ->
                                    Text(
                                        text = value,
                                        modifier = Modifier.weight(1f),
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
