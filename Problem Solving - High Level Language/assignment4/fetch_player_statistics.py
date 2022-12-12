import os
import re
from operator import itemgetter
from typing import Dict, List
from urllib.parse import urljoin

import numpy as np
from bs4 import BeautifulSoup
from matplotlib import pyplot as plt
from requesting_urls import get_html

## --- Task 8, 9 and 10 --- ##

try:
    import requests_cache
except ImportError:
    print("install requests_cache to improve performance")
    pass
else:
    requests_cache.install_cache()

base_url = "https://en.wikipedia.org"


def find_best_players(url: str) -> None:
    """Find the best players in the semifinals of the nba.

    This is the top 3 scorers from every team in semifinals.
    Displays plot over points, assists, rebounds

    arguments:
        - html (str) : html string from wiki basketball
    returns:
        - None
    """
    # gets the teams
    teams = get_teams(url)
    # assert len(teams) == 8

    #print(([team['url'] for team in teams]))

    # Gets the player for every team and stores in dict (get_players)
    all_players = {}
    
    for team in teams:
        all_players[team['name']] = get_players(team['url'])
    #print(str(all_players))

    # get player statistics for each player,
    # using get_player_stats
    for team, players in all_players.items():

        for player in range(len(players)):
            players[player] = players[player] | get_player_stats(players[player]['url'], team)
        

    # at this point, we should have a dict of the form:
    # {
    #     "team name": [
    #         {
    #             "name": "player name",
    #             "url": "https://player_url",
    #             # added by get_player_stats
    #             "points": 5,
    #             "assists": 1.2,
    #             # ...,
    #         },
    #     ]
    # }

    # Select top 3 for each team by points:
    best = {}
    for team, players in all_players.items():
        top_stat = []
        # Sort and extract top 3 based on points
        top_3 = sorted(players, key=lambda x: x['points'], reverse=True)
        
        for i in range(3):
            top_3[i].pop('url')
            top_stat.append(top_3[i])
        
        best[team] = top_stat

    stats_to_plot = ["points", "assists", "rebounds"]
    for stat in stats_to_plot:
        plot_best(best, stat=stat)

def plot_best(best: Dict[str, List[Dict]], stat: str = "points") -> None:
    """Plots a single stat for the top 3 players from every team.

    Arguments:
        best (dict) : dict with the top 3 players from every team
            has the form:

            {
                "team name": [
                    {
                        "name": "player name",
                        "points": 5,
                        ...
                    },
                ],
            }

            where the _keys_ are the team name,
            and the _values_ are lists of length 3,
            containing dictionaries about each player,
            with their name and stats.

        stat (str) : [points | assists | rebounds] which stat to plot.
            Should be a key in the player info dictionary.
    """
    # Picked colors that resemble their own team color
    color_table = {
        "Dallas": "royalblue",
        "Miami": "red",
        "Boston": "limegreen",
        "Philadelphia": "blue",
        "Memphis": "gray",
        "Golden State": "gold",
        "Phoenix": "black",
        "Milwaukee": "green",
    }
    
    stats_dir = "NBA_player_statistics"
    
    counter = 0
    plot = []

    for team, players in best.items():

        color = color_table[team]

        points = []
        names = []

        for player in players:
            names.append(player["name"])
            points.append(player[stat])
        plot.extend(names)

        x = range(counter, counter + len(players))
        counter += len(players)

        bars = plt.bar(x, points, color=color, label=team)
        plt.bar_label(bars, rotation=65)
    
    plt.xticks(range(len(plot)), plot, rotation=90)
    plt.legend(title="Teams", bbox_to_anchor=(1.05, 1.0))
    plt.grid(False)
    plt.margins(y=0.2)

    plt.title(f"{stat} for top 3 players in all teams")
    plt.tight_layout()
    
    # Directory check - variable
    dir_exist = os.path.exists(stats_dir)

    # Create "NBA_player_statistics/" directory:
    if not dir_exist:
        os.makedirs(stats_dir)
    plt.savefig(f"{stats_dir}/{stat}.png")

    # Clear plot
    plt.clf()


def get_teams(url: str) -> list:
    """Extracts all the teams that were in the semi finals in nba

    arguments:
        - url (str) : url of the nba finals wikipedia page
    returns:
        teams (list) : list with all teams
            Each team is a dictionary of {'name': team name, 'url': team page
    """
    # Get the table
    html = get_html(url)
    soup = BeautifulSoup(html, "html.parser")
    table = soup.find(id="Bracket").find_next("table")

    # find all rows in table
    rows = table.find_all("tr")
    rows = rows[2:]
    # maybe useful: identify cells that look like 'E1' or 'W5', etc.
    seed_pattern = re.compile(r"^[EW][1-8]$")

    # lots of ways to do this,
    # but one way is to build a set of team names in the semifinal
    # and a dict of {team name: team url}

    team_links = {}  # dict of team name: team url
    in_semifinal = set()  # set of teams in the semifinal

    # Loop over every row and extract teams from semi finals
    # also locate the links tot he team pages from the First Round column
    for row in rows:
        cols = row.find_all("td")
        # useful for showing structure
        # print([c.get_text(strip=True) for c in cols])

        # TODO:
        # 1. if First Round column, record team link from `a` tag
        # 2. if semifinal column, record team name

        # quarterfinal, E1/W8 is in column 1
        # team name, link is in column 2
        if len(cols) >= 3 and seed_pattern.match(cols[1].get_text(strip=True)):
            team_col = cols[2]
            a = team_col.find("a")
            team_links[team_col.get_text(strip=True)] = urljoin(base_url, a["href"])

        elif len(cols) >= 4 and seed_pattern.match(cols[2].get_text(strip=True)):
            team_col = cols[3]
            in_semifinal.add(team_col.get_text(strip=True))

        elif len(cols) >= 5 and seed_pattern.match(cols[3].get_text(strip=True)):
            team_col = cols[4]
            in_semifinal.add(team_col.get_text(strip=True))

    # return list of dicts (there will be 8):
    # [
    #     {
    #         "name": "team name",
    #         "url": "https://team url",
    #     }
    # ]

    assert len(in_semifinal) == 8
    return [
        {
            "name": team_name.rstrip("*"),
            "url": team_links[team_name],
        }
        for team_name in in_semifinal
    ]


def get_players(team_url: str) -> list:
    """Gets all the players from a team that were in the roster for semi finals
    arguments:
        team_url (str) : the url for the team
    returns:
        player_infos (list) : list of player info dictionaries
            with form: {'name': player name, 'url': player wikipedia page url}
    """
    print(f"Finding players in {team_url}")

    # Get the table
    html = get_html(team_url)
    soup = BeautifulSoup(html, "html.parser")
    table = soup.find(id="Roster").find_next("table")

    players = []
    player_links = {}

    # Loop over every row and get the names from roster
    rows = table.find_all("tr")
    rows = rows[3:]

    for row in rows:
        # Get the columns
        cols = row.find_all("td")
        text = [col.get_text(strip=True) for col in cols]
        #print(text)

        # find name links (a tags)
        # and add to players a dict with
        # {'name':, 'url':}

        # if it's name column, record player link from `a` tag
        # add it to dictionary and append it to the players list
        if len(cols) >= 2:
            player_col = cols[2]
            a = player_col.find("a")
            player_links[player_col.get_text(strip=True)] = urljoin(base_url, a["href"])
            players.append(player_col.get_text(strip=True))

    # return list of players
    players = [
        {
            "name": player_name,
            "url": player_links[player_name],
        }
        for player_name in players
    ]

    return players


def get_player_stats(player_url: str, team: str) -> dict:
    """Gets the player stats for a player in a given team
    arguments:
        player_url (str) : url for the wiki page of player
        team (str) : the name of the team the player plays for
    returns:
        stats (dict) : dictionary with the keys (at least): points, assists, and rebounds keys
    """
    print(f"Fetching stats for player in {player_url}")

    # Get the table with stats
    html = get_html(player_url)
    soup = BeautifulSoup(html, "html.parser")
    #table = soup.find(id="Regular_season").find_next("table")
    table = soup.find(class_="toccolours").find_next("table")
    # changed the id="Regular_season" to class_="toccolours"
    # because of some players not having
    # enough information such as missing the Regular Season h4-tag
    # which causes errors in task 10

    stats = {
        "points": 0.0,
        "assists": 0.0,
        "rebounds": 0.0,
    }

    rows = table.find_all("tr")
    rows = rows[1:]
    #print(str(rows))

    year = re.compile(r'2021.22')

    # Loop over rows and extract the stats
    for row in rows:
        cols = row.find_all("td")
        text = [col.get_text(strip=True) for col in cols]
        #print(text)

        year_col = cols[0].text.strip()
        team_col = cols[1].text.strip()
        #print(team_col)

        check_year = bool(year.match(year_col))

        # Check correct team (some players change team within season)
        # load stats from columns
        # keys should be 'points', 'assists', etc.
        if len(cols) >= 0 and check_year and team == team_col:
            #print("ok")
            ppg = cols[12].text.strip()
            apg = cols[9].text.strip()
            rpg = cols[8].text.strip()
            #print(ppg, apg, rpg)
        
            stats = {
                "points": float(ppg.rstrip("*")),
                "assists": float(apg.rstrip("*")),
                "rebounds": float(rpg.rstrip("*")),
            }
        # Some wiki-articles have one less row:
        elif team == year_col:
            #print("ok")
            ppg = cols[11].text.strip()
            apg = cols[8].text.strip()
            rpg = cols[7].text.strip()
            #print(ppg, apg, rpg)
        
            stats = {
                "points": float(ppg.rstrip("*")),
                "assists": float(apg.rstrip("*")),
                "rebounds": float(rpg.rstrip("*")),
            }
        # Empty
        elif stats == {} and row == rows[-1]:
            stats = {
                "points": 0,
                "assists": 0,
                "rebounds": 0,
            }

    return stats


# run the whole thing if called as a script, for quick testing
if __name__ == "__main__":
    url = "https://en.wikipedia.org/wiki/2022_NBA_playoffs"
    find_best_players(url)

    '''
    #team
    print("T",get_teams(url),"\n")
    #plr
    u2 = "https://en.wikipedia.org/wiki/2021%E2%80%9322_Golden_State_Warriors_season"
    print("P",get_players(u2),"\n")
    #stat
    u3 = "https://en.wikipedia.org/wiki/Stephen_Curry"
    t = "Golden State"
    print("S",get_player_stats(u3, t),"\n")
    '''