import re
import requests
from copy import copy
from dataclasses import dataclass
from bs4 import BeautifulSoup
from urllib.parse import urljoin
from requesting_urls import get_html
from typing import Dict, List
from fetch_player_statistics import get_teams, get_players, get_player_stats
from operator import itemgetter
import os
from matplotlib import pyplot as plt
import numpy as np
from collections import deque
import pandas as pd
import time_planner as tp

sample_table = """
<table>
  <tr>
    <th>Date</th>
    <th>Venue</th>
    <th>Type</th>
    <th>Info</th>
  </tr>
  <tr>
    <td>October</td>
    <td rowspan="2">UiO</td>
    <td>Assignment 3</td>
    <td>image filters</td>
  </tr>
  <tr>
    <td>November</td>
    <td colspan="2">Assignment 4</td>
  </tr>
</table>
"""

event_types = {
    "DH": "Downhill",
    "SL": "Slalom",
    "GS": "Giant Slalom",
    "SG": "Super Giant slalom",
    "AC": "Alpine Combined",
    "PG": "Parallel Giant Slalom",
}

#response = requests.get("https://en.wikipedia.org/wiki/2021%E2%80%9322_Golden_State_Warriors_season")

#html = response.text

base_url = "https://en.wikipedia.org"

playoff_url = "https://en.wikipedia.org/wiki/2022_NBA_playoffs"

# -------------------------------------------------

@dataclass
class TableEntry:
    """Data class representing a single entry in a table

    Records text content, rowspan, and colspan attributes
    """

    text: str
    rowspan: int
    colspan: int


html = get_html("https://en.wikipedia.org/wiki/2022–23_FIS_Alpine_Ski_World_Cup")
soup = BeautifulSoup(html, "html.parser")
calendar = soup.find(id="Calendar")
soup_table = calendar.find_next("table")

headings = soup_table.find_all("th")
labels = [th.text.strip() for th in headings]
#print(str(labels))

data = []

rows = soup_table.find_all("tr")
rows = rows[1:]
#print(str(rows))

for tr in rows:
    cells = tr.find_all("td")
    cells_text = [cell.text.strip() for cell in cells]
    row = []
    for cell in cells:
        colspan = int(cell.get("colspan", 1))
        rowspan = int(cell.get("rowspan" , 1))
        ...
        text = cell.get_text(strip=True)
        row.append(
            TableEntry(
                text=text,
                rowspan=rowspan,
                colspan=colspan,
            )
        )
    data.append(row)
all_data = tp.expand_row_col_span(data)
#print(all_data)

for i in all_data:
    ...

wanted = ["Date", "Venue", "Type"]
df = pd.DataFrame(all_data,columns=all_data)
#print(df)

#print(data)

#df = pd.DataFrame(data, columns=data)
#print(df)

# -------------------------------------------------

def filter_data(keys: list, data: list, wanted: list):
    
    #for i in data:
    #    print(i[0]) # 1855

    filtered = []

    keys.append(data)
    #print(keys)

    return filtered




labels = ['#', 'Event', 'Date', 'Venue', 'Slope (incline)', 'Type', 'Winner', 'Second', 'Third', 'Overall leader', 'Ref.']
all_data = [['1855', '1', '23 October 2022', 'Sölden', 'Rettenbach(68.2%)', 'GS438', 'Marco Odermatt', 'Žan Kranjec', 'Henrik Kristoffersen', 'Marco Odermatt', '[4]']]
wanted = ["Date", "Venue", "Type"]
#print(filter_data(labels, all_data, wanted))

# -------------------------------------------------

def top_3(url: str) -> list:
    
    teams = get_teams(url)
    #print(teams)

    all_players = {}
    test = []

    for team in teams:
        all_players[team['name']] = get_players(team['url'])
    #print(all_players)

    for team, players in all_players.items():
        print(team)

        for player in range(len(players)):
            #print(player)
            #stats = get_player_stats(player['url'], team)
            #print(stats)
            players[player] = players[player] | get_player_stats(players[player]['url'], team)
    
    #print(all_players)
    best = {}
    top_stat = ...

    for team, players in all_players.items():
        top_3 = sorted(best, key=lambda i: i[3], reverse=True)
        print(top_3)
        ...
    
    #stats_to_plot = ...
    #for stat in stats_to_plot:
        #...       


#top_3(playoff_url)

# -------------------------------------------------

month_names = [
    "January",
    "February",
    "March",
    "April",
    "May",
    "June",
    "July",
    "August",
    "September",
    "October",
    "November",
    "December",
]

# -------------------------------------------------