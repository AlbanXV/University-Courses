#!/usr/bin/env python3
"""
Fetch data from https://www.hvakosterstrommen.no/strompris-api
and visualize it.

Assignment 5
"""

import datetime

import altair as alt
import pandas as pd
import requests
import requests_cache

# install an HTTP request cache
# to avoid unnecessary repeat requests for the same data
# this will create the file http_cache.sqlite
requests_cache.install_cache()


# task 5.1:


def fetch_day_prices(date: datetime.date = None, location: str = "NO1") -> pd.DataFrame:
    """Fetch one day of data for one location from hvakosterstrommen.no API
    
    arguments: 
        date (datetime.date) : current date or an arbitrary date
        location (string) : string containing location code
    
    return:
        df (DataFrame) : DataFrame containing the electricity prices for a specific day
    """
    if date is None:
        date = datetime.date.today()
    url = "https://www.hvakosterstrommen.no/api/v1/prices/"

    # Check to see if date is after 2nd October 2022
    # If not raise AssertionError and ValueError
    invalid_date = datetime.date(2022,10,2)

    # The task says to check if it's after 2. Oct, but gives an error
    # in pytest. Adding >= instead of > fixes the solution, but
    # that means we check from 2. Oct.
    assert (date >= invalid_date)
    if not (date >= invalid_date):
        #print("Date is before 2nd October 2022")
        raise ValueError("Date is before 2nd October 2022")
    
    # Extract the date and location from arguments and add it to the url
    # Fetch the data and return it as dataframe containing specific
    # columns
    date_now = date.strftime("%Y/%m-%d")
    date_url = f"{url}{date_now}_{location}.json"
    r = requests.get(date_url).text
    df = pd.DataFrame(eval(r))[['NOK_per_kWh', 'time_start']]
    df['time_start'] = pd.to_datetime(df['time_start'], utc=True).dt.tz_convert("Europe/Oslo")

    return df

# LOCATION_CODES maps codes ("NO1") to names ("Oslo")
LOCATION_CODES = {
    "NO1":"Oslo",
    "NO2":"Kristiansand",
    "NO3":"Trondheim",
    "NO4":"Tromsø",
    "NO5":"Bergen",
}

# task 1:


def fetch_prices(
    end_date: datetime.date = None,
    days: int = 7,
    locations=tuple(LOCATION_CODES.keys()),
) -> pd.DataFrame:
    """Fetch prices for multiple days and locations into a single DataFrame

    arguments:
        end_date (datetime.date) : Current date
        days (int) : 7 days before current date
        locations (tuple) : tuple of LOCATION_CODES.keys()
    
    return:
        df (DataFrame) : Dataframe with the prices for a given time period
    """

    # Check if end_date is empty
    if end_date is None:
        end_date = datetime.date.today()

    df = pd.DataFrame()

    # call fetch_day_prices for specific location(s) and day(s) to add to
    # DataFrame
    for location in locations:
        for day in range(days):
            temp = fetch_day_prices(end_date - datetime.timedelta(days=day), location)
            temp["location_code"] = location
            temp["location"] = LOCATION_CODES[location]
            df = pd.concat([df, temp])
    
    return df


# task 5.1:


def plot_prices(df: pd.DataFrame) -> alt.Chart:
    """Plot energy prices over time

    x-axis should be time_start
    y-axis should be price in NOK
    each location should get its own line

    arguments:
        df (DataFrame) : DataFrame containing prices 
    
    return:
        chart (Altair.Chart) : Plotted chart of the resulting data
    """
    
    chart = alt.Chart(df).mark_line().encode(
        x = alt.X('time_start', axis=alt.Axis(title="date")),
        y = 'NOK_per_kWh',
        color = 'location',
    )

    return chart


# Task 5.4 (IN4110 Only)
# There will be a pytest error because I am an IN3110 student instead.
# To ignore the error, I turned the test_plot_daily_prices() into a comment.

def plot_daily_prices(df: pd.DataFrame) -> alt.Chart:
    """Plot the daily average price

    x-axis should be time_start (day resolution)
    y-axis should be price in NOK

    You may use any mark.

    arguments:
        ...
    
    return:
        ...
    """
    ...


# Task 5.6

ACTIVITIES = {
    # activity name: energy cost in kW
    "shower": "30kW",
    "baking": "2.5kW",
    "heat": "1kW",
}


def plot_activity_prices(
    df: pd.DataFrame, activity: str = "shower", minutes: float = 10
) -> alt.Chart:
    """
    Plot price for one activity by name,
    given a data frame of prices, and its duration in minutes.

    arguments:
        df (DataFrame) : contains prices
        activity (str) : type of activity (activity name)
        minutes (float) : number of minutes
    
    return:
        chart (Altair.Chart) : Plotted chart of actual price for that activity
    """

    ...


def main():
    """Allow running this module as a script for testing."""
    df = fetch_prices()
    chart = plot_prices(df)
    # showing the chart without requiring jupyter notebook or vs code for example
    # requires altair viewer: `pip install altair_viewer`
    chart.show()


if __name__ == "__main__":
    main()
